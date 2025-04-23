package org.codeturnery.lucene.transfer.accessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 
 * @param <T> The type of the handle.
 * @param <V> The type of the (object) values.
 */
abstract class KnownCountSupplierBasedObjectListAccessor<T, V> implements Accessor<T> {
	protected final ToIntFunction<T> toCount;

	KnownCountSupplierBasedObjectListAccessor(final ToIntFunction<T> toCount) {
		this.toCount = toCount;
	}

	protected V[] getAsArray(T handle) {
		final int count = getCount(handle);
		final var array = (V[]) new Object[count];
		for (int i = 0; i < count; i++) {
			array[i] = toValue(handle, i);
		}
		return array;
	}

	protected List<V> getAsList(T handle) {
		return Arrays.asList(getAsArray(handle));
	}

	protected Iterable<V> getAsIterable(T handle) {
		return getAsList(handle);
	}

	protected void addToCollection(T handle, Collection<V> collection) {
		final int count = getCount(handle);
		for (int i = 0; i < count; i++) {
			collection.add(toValue(handle, i));
		}
	}

	protected Stream<V> getAsStream(T handle) {
		final int count = getCount(handle);
		return IntStream.range(0, count).mapToObj(index -> toValue(handle, index));
	}

	protected Set<V> getAsSet(T handle) {
		final int count = getCount(handle);
		final var set = new LinkedHashSet<V>(count);
		for (int i = 0; i < count; i++) {
			set.add(toValue(handle, i));
		}
		return set;
	}

	protected List<Supplier<V>> getAsSupplierList(T handle) {
		final int count = getCount(handle);
		final var list = new ArrayList<Supplier<V>>(count);
		for (int i = 0; i < count; i++) {
			final int index = i;
			list.add(() -> toValue(handle, index));
		}
		return list;
	}

	abstract protected V toValue(final T handle, final int index);

	protected int getCount(final T handle) {
		return this.toCount.applyAsInt(handle);
	}

}
