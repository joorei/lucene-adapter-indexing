package org.codeturnery.lucene.transfer.accessor;

import java.util.Collection;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

/**
 * 
 * @param <T> The type of the handle.
 */
public interface IntegerListAccessor<T> extends Accessor<T> {

	int[] getAsPrimitiveIntArray(final T handle);

	List<Integer> getAsIntegerList(final T handle);

	Iterable<Integer> getAsIntegerIterable(final T handle);

	void addToIntegerCollection(final T handle, final Collection<Integer> collection);

	IntStream getAsIntStream(final T handle);

	List<IntSupplier> getAsIntSupplierList(final T handle);
}
