package org.codeturnery.lucene.transfer.accessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * 
 * @param <T> The type of the handle.
 */
class ByteAccessorFromInputStream<T> implements ByteAccessor<T> {
	private final Function<T, InputStreamWithLength> toValue;

	ByteAccessorFromInputStream(final Function<T, InputStreamWithLength> toValue) {
		this.toValue = toValue;
	}

	@Override
	public void addToByteBufferWithKnownLength(T handle, IntFunction<ByteBuffer> byteBufferProvider) {
		final InputStreamWithLength inputStreamWithLength = getAsInputStreamWithLength(handle);
		final ByteBuffer byteBuffer = byteBufferProvider.apply(inputStreamWithLength.length());
		try (final InputStream inputStream = inputStreamWithLength.inputStream();
				final ReadableByteChannel channel = Channels.newChannel(inputStream);) {
			int bytesRead = 0;
			do {
				bytesRead = channel.read(byteBuffer);
			} while (bytesRead > 0);
		} catch (final IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}

	@Override
	public InputStreamWithLength getAsInputStreamWithLength(T handle) {
		return this.toValue.apply(handle);
	}

	@Override
	public InputStream getAsInputStream(T handle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ByteBuffer getAsByteBuffer(T handle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getAsByteArray(T handle) {
		// TODO Auto-generated method stub
		return null;
	}

}
