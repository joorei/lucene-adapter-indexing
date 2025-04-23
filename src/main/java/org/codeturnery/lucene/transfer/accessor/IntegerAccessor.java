package org.codeturnery.lucene.transfer.accessor;

import java.nio.ByteBuffer;

/**
 * Implement the methods in this class so that the least conversions are
 * necessary from the format your data is actually stored in.
 * 
 * @param <T>
 */
public interface IntegerAccessor<T> {
	int getAsPrimitiveInt(final T handle);

	ByteBuffer getAsByteBuffer(final T handle);

	Integer getAsInteger(final T handle);
}
