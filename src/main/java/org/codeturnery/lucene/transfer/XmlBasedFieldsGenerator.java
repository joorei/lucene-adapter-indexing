package org.codeturnery.lucene.transfer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.IndexableField;
import org.codeturnery.iteration.ArrayBackedIterable;
import org.codeturnery.lucene.transfer.accessor.Accessor;
import org.codeturnery.lucene.transfer.config.Config.Adapter.Entity;
import org.codeturnery.lucene.transfer.config.Config.Adapter.Entity.FieldMapping;
import org.codeturnery.lucene.transfer.config.Config.Adapter.Entity.FieldMapping.Parameter;

public class XmlBasedFieldsGenerator<T extends Handle> implements FieldsGenerator<T> {
	private final Entity entityConfig;
	private Map<String, Field<T, Accessor<T>>> fields;

	public XmlBasedFieldsGenerator(Entity entityConfig, Map<String, Field<T, Accessor<T>>> fields) {
		this.entityConfig = entityConfig;
		this.fields = fields;
	}

	@Override
	public Iterable<IndexableField> getFields(T handle) throws IOException {
		try (handle) {
			final var resultIterable = new ArrayBackedIterable<IndexableField>();
			// TODO: find a way to cache most of this logic, as it is just the handle that
			// differs on each call.
			for (final FieldMapping fieldMapping : this.entityConfig.getFieldMapping()) {
				// get parameters needed for method to be called via reflection
				final Object[] parameters = getEntityFieldData(fieldMapping, handle, this.fields);
				final Class<Object>[] parameterTypes = getParameterTypes(fieldMapping);
				final Class<?> classToCall = Class.forName(fieldMapping.getClazz());
				final Method method = classToCall.getMethod(fieldMapping.getMethod(), parameterTypes);
				// null must be passed as first argument because the method is static (i.e. no
				// instance of the class is needed)
				final var indexableFields = (IndexableField[]) method.invoke(null, parameters);
				resultIterable.addArray(indexableFields);
			}
			return resultIterable;
		} catch (final NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// obviously there are a lot of potential exception when calling methods made up
			// from a config
			// TODO: evaluate if there is a better way to handle them
			throw new IllegalStateException(e);
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param fieldMapping
	 * @param handle
	 * @param fields
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected <T> Object[] getEntityFieldData(final FieldMapping fieldMapping, Object handle,
			final Map<String, Field<T, Accessor<T>>> fields) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final List<Parameter> parameters = fieldMapping.getParameter();
		final int paramCount = parameters.size();
		final var objects = new Object[paramCount + 2];
		objects[0] = fieldMapping.getFor();
		objects[1] = handle;
		for (int i = 0; i < paramCount; i++) {
			final Parameter parameter = parameters.get(i);
			final Class<?> convertClass = Class.forName(parameter.getConvertClass());
			objects[i + 2] = getConvertMethodResult(parameter, convertClass, fields);
		}
		return objects;
	}

	protected <T> Object getConvertMethodResult(final Parameter parameter, final Class<?> convertClass,
			final Map<String, Field<T, Accessor<T>>> fields) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		// try to find a method that takes both the string and the map
		for (final Method methodToCheck : convertClass.getDeclaredMethods()) {
			if (!methodToCheck.getName().equals(parameter.getConvertMethod())) {
				continue;
			}
			final Class<?>[] parametersToCheck = methodToCheck.getParameterTypes();
			if (parametersToCheck.length != 2) {
				continue;
			}
			if (parametersToCheck[0].equals(String.class) && parametersToCheck[1].equals(Map.class)) {
				return methodToCheck.invoke(null, parameter.getValue(), fields);
			}
		}
		// if no method taking both parameters exist, there must be at least a method
		// taking the string as only parameter
		final Method convertMethod = convertClass.getMethod(parameter.getConvertMethod(), String.class);
		return convertMethod.invoke(null, parameter.getValue());
	}

	protected Class<Object>[] getParameterTypes(final FieldMapping fieldMapping) throws ClassNotFoundException {
		final List<Parameter> parameters = fieldMapping.getParameter();
		final int parameterCount = parameters.size();
		final var types = new Class[parameterCount + 2];
		types[0] = String.class;
		types[1] = Object.class;

		for (int i = 0; i < parameterCount; i++) {
			final Parameter parameter = parameters.get(i);
			types[i + 2] = Class.forName(parameter.getType());
		}
		return types;
	}
}
