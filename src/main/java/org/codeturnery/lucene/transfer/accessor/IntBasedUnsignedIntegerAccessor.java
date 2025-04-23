package org.codeturnery.lucene.transfer.accessor;

import java.nio.ByteBuffer;
import java.util.function.ToIntFunction;

import org.codeturnery.typesystem.NonNegative;

/**
 * 
 * @param <T> The type of the handle.
 */
class IntBasedUnsignedIntegerAccessor<T> implements UnsignedIntegerAccessor<T>{

	private final ToIntFunction<T> handleToInt;

	IntBasedUnsignedIntegerAccessor(final ToIntFunction<T> handleToInt) {
		this.handleToInt = handleToInt;
	}
	
	@Override
	public @NonNegative int getAsUnsignedPrimitive(T handle) {
		return this.handleToInt.applyAsInt(handle);
	}

	@Override
	public @NonNegative ByteBuffer getAsByteBuffer(T handle) {
		final ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
		byteBuffer.putInt(getAsUnsignedPrimitive(handle));
		byteBuffer.flip();
		return byteBuffer;
	}

	@Override
	public @NonNegative Integer getAsObject(T handle) {
		return Integer.valueOf(getAsUnsignedPrimitive(handle));
	}

	@Override
	public void addToByteBuffer(final T handle, final ByteBuffer byteBuffer) {
		byteBuffer.putInt(getAsUnsignedPrimitive(handle));
	}

}
