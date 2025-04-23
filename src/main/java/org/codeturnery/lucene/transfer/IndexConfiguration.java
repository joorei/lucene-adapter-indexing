package org.codeturnery.lucene.transfer;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.lucene.index.IndexableField;
import org.codeturnery.lucene.access.IndexConfig;
import org.codeturnery.lucene.transfer.accessor.Accessor;

public interface IndexConfiguration extends IndexConfig {

	/**
	 * FIXME: ensure somehow that the adapters don't use the same identifier
	 */
	String getDisplayIdentifier();

	/**
	 * The identifiers of the adapters this configuration shall be used for.
	 * 
	 * @return
	 */
	Set<String> getAdapters();

	<T extends Handle> FieldsGenerator<T> getFieldsGenerator(final String adapterIdentifier,
			String entityIdentifier, Map<String, Field<T, Accessor<T>>> fieldsDefinition) throws IOException;

	Set<String> getKeyFields();

	/**
	 * The field name to use to store all field names used in the document, to allow
	 * "missing field" searches.
	 * 
	 * @return
	 */
	String getFieldNamesDimension();
}
