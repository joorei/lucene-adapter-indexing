package org.codeturnery.lucene.transfer.accessor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

class StringBasedTextAccessor<T> implements TextAccessor<T> {

	private final Function<T, String> handleToString;

	StringBasedTextAccessor(final Function<T, String> handleToString) {
		this.handleToString = handleToString;
	}

	@Override
	public InputStream getAsUtf8InputStream(final T handle) {
		return new ByteArrayInputStream(getAsUtf8Bytes(handle));
	}

	@Override
	public InputStreamWithLength getAsUtf8InputStreamWithLength(final T handle) {
		final byte[] bytes = getAsUtf8Bytes(handle);
		return new InputStreamWithLength(bytes.length, new ByteArrayInputStream(bytes));
	}

	@Override
	public String getAsString(final T handle) {
		return this.handleToString.apply(handle);
	}

	@Override
	public Reader getAsReader(final T handle) {
		return new StringReader(getAsString(handle));
	}

	@Override
	public CharSequence getAsCharSequence(T handle) {
		return getAsString(handle);
	}

	@Override
	public byte[] getAsUtf8Bytes(T handle) {
		return getAsString(handle).getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public void addToStringBuilder(T handle, StringBuilder stringBuilder) {
		stringBuilder.append(getAsString(handle));
	}
}
