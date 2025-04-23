package org.codeturnery.lucene.transfer.accessor;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Represents multiple instances of multiple bytes. Not to be confused with
 * {@link ByteAccessor}.
 * 
 * @param <T> The type of the handle.
 */
public interface ByteListAccessor<T> {
	/**
	 * Writes each byte data into given {@link ByteBuffer} instances.
	 * <p>
	 * The order in which the {@link IntFunction} is called informs the caller of
	 * this method about the order of the byte data.
	 * <p>
	 * The number of byte data will only be known after this method returns, by the
	 * number of times the provided {@link IntFunction} has been called.
	 * 
	 * @param handle
	 * @param byteBufferProvider Is passed the length of the byte data to receive a
	 *                           {@link ByteBuffer} instance with sufficient free
	 *                           space.
	 */
	void addToByteBufferWithKnownLength(T handle, IntFunction<ByteBuffer> byteBufferProvider);

	void addToByteArrayCollection(T handle, Collection<byte[]> collection);

	List<InputStreamWithLength> getAsInputStreamWithLength(T handle);

	List<InputStream> getAsInputStreamList(T handle);

	List<ByteBuffer> getAsByteBufferList(T handle);

	List<byte[]> getAsByteArrayList(T handle);
}
