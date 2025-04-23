package org.codeturnery.lucene.transfer.accessor;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface TextListAccessor<T> extends Accessor<T> {

	String[] getAsStringArray(final T handle);

	List<String> getAsStringList(final T handle);

	Iterable<String> getAsStringIterable(final T handle);

	void addToStringCollection(final T handle, final Collection<String> collection);

	Stream<String> getAsStringStream(final T handle);

	Iterable<Supplier<String>> getAsStringSupplierList(final T handle);
	
	void addToStringBuilder(final T handle, final StringBuilder builder);
	
	Set<String> getAsStringSet(T handle);
}
