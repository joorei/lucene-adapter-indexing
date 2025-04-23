package org.codeturnery.lucene.transfer.accessor;

import java.io.InputStream;
import java.io.Reader;

/**
 * @param <T> type of the handle
 */
public interface TextAccessor<T> extends Accessor<T> {
	abstract public InputStream getAsUtf8InputStream(final T handle);

	abstract public InputStreamWithLength getAsUtf8InputStreamWithLength(final T handle);

	abstract public String getAsString(final T handle);

	abstract public Reader getAsReader(final T handle);

	abstract public byte[] getAsUtf8Bytes(final T handle);

	abstract public CharSequence getAsCharSequence(final T handle);

	abstract public void addToStringBuilder(final T handle, final StringBuilder stringBuilder);
}
