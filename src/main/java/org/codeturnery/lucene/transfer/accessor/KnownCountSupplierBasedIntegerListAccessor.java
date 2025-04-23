package org.codeturnery.lucene.transfer.accessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

import org.codeturnery.lucene.transfer.accessor.AccessorFactory.HandleIndexToIntFunction;

/**
 * 
 * @param <T> The type of the handle.
 */
class KnownCountSupplierBasedIntegerListAccessor<T> extends KnownCountSupplierBasedObjectListAccessor<T, Integer>
		implements IntegerListAccessor<T> {
	private final HandleIndexToIntFunction<T> toIntFunction;

	KnownCountSupplierBasedIntegerListAccessor(final HandleIndexToIntFunction<T> toIntFunction,
			final ToIntFunction<T> toCount) {
		super(toCount);
		this.toIntFunction = toIntFunction;
	}

	@Override
	public IntStream getAsIntStream(T handle) {
		final int count = this.toCount.applyAsInt(handle);
		return IntStream.range(0, count).map(index -> this.toIntFunction.apply(handle, index));
	}

	@Override
	public List<IntSupplier> getAsIntSupplierList(final T handle) {
		final int count = this.toCount.applyAsInt(handle);
		final var list = new ArrayList<IntSupplier>(count);
		for (int i = 0; i < count; i++) {
			final int index = i;
			list.add(() -> this.toIntFunction.apply(handle, index));
		}
		return list;
	}

	@Override
	public int[] getAsPrimitiveIntArray(T handle) {
		final int count = this.toCount.applyAsInt(handle);
		final var array = new int[count];
		for (int i = 0; i < count; i++) {
			array[i] = this.toIntFunction.apply(handle, i);
		}
		return array;
	}

	@Override
	protected Integer toValue(T handle, int index) {
		return Integer.valueOf(this.toIntFunction.apply(handle, index));
	}

	@Override
	public List<Integer> getAsIntegerList(T handle) {
		return getAsList(handle);
	}

	@Override
	public Iterable<Integer> getAsIntegerIterable(T handle) {
		return getAsIterable(handle);
	}

	@Override
	public void addToIntegerCollection(T handle, Collection<Integer> collection) {
		addToCollection(handle, collection);
	}
}
