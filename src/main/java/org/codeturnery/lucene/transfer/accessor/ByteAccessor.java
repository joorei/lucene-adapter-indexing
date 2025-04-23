package org.codeturnery.lucene.transfer.accessor;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.function.IntFunction;

/**
 * Represents multiple bytes. Not to be confused with {@link ByteListAccessor}.
 * 
 * @param <T>
 */
public interface ByteAccessor<T> extends Accessor<T> {
	/**
	 * Writes the byte data into a given {@link ByteBuffer}.
	 * 
	 * @param handle
	 * @param byteBufferProvider is passed the length of the byte data to receive a
	 *                           {@link ByteBuffer} instance with sufficient free
	 *                           space.
	 */
	void addToByteBufferWithKnownLength(T handle, IntFunction<ByteBuffer> byteBufferProvider);

	InputStreamWithLength getAsInputStreamWithLength(T handle);

	/**
	 * 
	 * 
	 * @param handle
	 * @return
	 */
	InputStream getAsInputStream(T handle);

	ByteBuffer getAsByteBuffer(T handle);

	byte[] getAsByteArray(T handle);
}
