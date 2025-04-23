package org.codeturnery.lucene.transfer.accessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

class PrimitiveArrayBasedIntegerListAccessor<T> implements IntegerListAccessor<T> {

	private final Function<T, int[]> integerValues;

	PrimitiveArrayBasedIntegerListAccessor(final Function<T, int[]> integerValues) {
		this.integerValues = integerValues;
	}
	
	@Override
	public int[] getAsPrimitiveIntArray(T handle) {
		return this.integerValues.apply(handle);
	}

	@Override
	public List<Integer> getAsIntegerList(T handle) {
		final int[] values = getAsPrimitiveIntArray(handle);
		final var list = new ArrayList<Integer>(values.length);
		for (int i = 0; i < values.length; i++) {
			list.add(Integer.valueOf(values[i]));
		}
		return list;
	}

	@Override
	public Iterable<Integer> getAsIntegerIterable(T handle) {
		return getAsIntegerList(handle);
	}

	@Override
	public void addToIntegerCollection(T handle, Collection<Integer> collection) {
		final int[] values = getAsPrimitiveIntArray(handle);
		for (int i = 0; i < values.length; i++) {
			collection.add(values[i]);
		}
	}

	@Override
	public IntStream getAsIntStream(T handle) {
		final int[] values = getAsPrimitiveIntArray(handle);
		return Arrays.stream(values);
	}

	@Override
	public List<IntSupplier> getAsIntSupplierList(T handle) {
		final int[] values = getAsPrimitiveIntArray(handle);
		final var list = new ArrayList<IntSupplier>(values.length);
		for (int i = 0; i < values.length; i++) {
			final int index = 0;
			list.add(() -> values[index]);
		}
		return list;
	}

}
