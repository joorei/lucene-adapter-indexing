package org.codeturnery.lucene.transfer.accessor;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

import org.codeturnery.lucene.transfer.accessor.AccessorFactory.HandleIndexToObjectFunction;

/**
 * @param <T> The type of the handle.
 */
class KnownCountSupplierBasedStringListAccessor<T> extends KnownCountSupplierBasedObjectListAccessor<T, String>
		implements TextListAccessor<T> {

	private final HandleIndexToObjectFunction<T, String> toValue;

	KnownCountSupplierBasedStringListAccessor(HandleIndexToObjectFunction<T, String> toValue,
			ToIntFunction<T> toCount) {
		super(toCount);
		this.toValue = toValue;
	}

	@Override
	public void addToStringBuilder(T handle, StringBuilder builder) {
		final int count = getCount(handle);
		for (int i = 0; i < count; i++) {
			builder.append(this.toValue.apply(handle, i));
		}
	}

	@Override
	protected String toValue(T handle, int index) {
		return this.toValue.apply(handle, index);
	}

	@Override
	public Set<String> getAsStringSet(T handle) {
		return getAsSet(handle);
	}

	@Override
	public String[] getAsStringArray(T handle) {
		return getAsArray(handle);
	}

	@Override
	public List<String> getAsStringList(T handle) {
		return getAsList(handle);
	}

	@Override
	public Iterable<String> getAsStringIterable(T handle) {
		return getAsIterable(handle);
	}

	@Override
	public void addToStringCollection(T handle, Collection<String> collection) {
		addToCollection(handle, collection);
	}

	@Override
	public Stream<String> getAsStringStream(T handle) {
		return getAsStream(handle);
	}

	@Override
	public Iterable<Supplier<String>> getAsStringSupplierList(T handle) {
		return getAsSupplierList(handle);
	}
}
