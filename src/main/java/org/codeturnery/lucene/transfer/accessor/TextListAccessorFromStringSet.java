package org.codeturnery.lucene.transfer.accessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

class TextListAccessorFromStringSet<T> implements TextListAccessor<T> {

	private final Function<T, Set<String>> toValue;

	public TextListAccessorFromStringSet(final Function<T, Set<String>> toValue) {
		this.toValue = toValue;
	}

	@Override
	public String[] getAsStringArray(T handle) {
		return getAsStringSet(handle).toArray(String[]::new);
	}

	@Override
	public List<String> getAsStringList(T handle) {
		return new ArrayList<>(getAsStringSet(handle));
	}

	@Override
	public Iterable<String> getAsStringIterable(T handle) {
		return getAsStringSet(handle);
	}

	@Override
	public void addToStringCollection(T handle, Collection<String> collection) {
		collection.addAll(getAsStringSet(handle));
	}

	@Override
	public Stream<String> getAsStringStream(T handle) {
		return getAsStringSet(handle).stream();
	}

	@Override
	public Iterable<Supplier<String>> getAsStringSupplierList(T handle) {
		final Set<String> set = getAsStringSet(handle);
		final var list = new ArrayList<Supplier<String>>(set.size());
		for (final String value : set) {
			list.add(() -> value);
		}
		return list;
	}

	@Override
	public void addToStringBuilder(T handle, StringBuilder builder) {
		for (final String value : getAsStringSet(handle)) {
			builder.append(value);
		}
	}

	@Override
	public Set<String> getAsStringSet(T handle) {
		return this.toValue.apply(handle);
	}
}
