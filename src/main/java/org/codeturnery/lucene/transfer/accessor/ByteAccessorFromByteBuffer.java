package org.codeturnery.lucene.transfer.accessor;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.function.Function;
import java.util.function.IntFunction;
/**
 * FIXME: implement correctly
 * @param <T>
 */
class ByteAccessorFromByteBuffer<T> implements ByteAccessor<T> {

	private final Function<T, ByteBuffer> toByteBuffer;

	public ByteAccessorFromByteBuffer(Function<T, ByteBuffer> toByteBuffer) {
		this.toByteBuffer = toByteBuffer;
	}

	@Override
	public void addToByteBufferWithKnownLength(T handle, IntFunction<ByteBuffer> byteBufferProvider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStreamWithLength getAsInputStreamWithLength(T handle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsInputStream(T handle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ByteBuffer getAsByteBuffer(T handle) {
		return this.toByteBuffer.apply(handle);
	}

	@Override
	public byte[] getAsByteArray(T handle) {
		final ByteBuffer buffer = getAsByteBuffer(handle);
		if (buffer.isDirect()) {
			// FIXME: handle direct buffer, where there is no backing byte array
		}
		final var bytes = new byte[buffer.remaining()];
		System.arraycopy(buffer.array(), buffer.position(), bytes, 0, bytes.length);
		return bytes;
	}

}
