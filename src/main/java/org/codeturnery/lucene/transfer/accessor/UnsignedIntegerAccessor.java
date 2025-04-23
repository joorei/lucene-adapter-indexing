package org.codeturnery.lucene.transfer.accessor;

import java.nio.ByteBuffer;

import org.codeturnery.typesystem.NonNegative;

/**
 * Implement the methods in this class so that the least conversions are
 * necessary from the format your data is actually stored in.
 * 
 * @param <T>
 */
public interface UnsignedIntegerAccessor<T> extends Accessor<T> {

	@NonNegative
	int getAsUnsignedPrimitive(final T handle);

	/**
	 * Use {@link ByteBuffer#getInt} to retrieve the integer.
	 * 
	 * @param handle
	 * @return
	 */
	@NonNegative
	ByteBuffer getAsByteBuffer(final T handle);

	@NonNegative
	Integer getAsObject(final T handle);

	void addToByteBuffer(final T handle, final ByteBuffer byteBuffer);
}
