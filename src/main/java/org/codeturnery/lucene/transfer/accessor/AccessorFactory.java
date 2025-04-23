package org.codeturnery.lucene.transfer.accessor;

import java.nio.ByteBuffer;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class AccessorFactory {

	@FunctionalInterface
	public interface HandleIndexToIntFunction<T> {
		int apply(T handle, int index);
	}

	@FunctionalInterface
	public interface HandleIndexToObjectFunction<T, V> {
		V apply(T handle, int index);
	}
	
	public <T> ByteAccessor<T> getByteAccessorFromByteBuffer(final Function<T, ByteBuffer> toByteBuffer) {
		return new ByteAccessorFromByteBuffer(toByteBuffer);
	}

	public <T> IntegerListAccessor<T> getIntegerListFromKnownSizedCallable(final HandleIndexToIntFunction<T> toIntFunction,
			final ToIntFunction<T> toCount) {
		return new KnownCountSupplierBasedIntegerListAccessor<>(toIntFunction, toCount);
	}

	public <T> IntegerListAccessor<T> getIntegerListFromPrimitiveArray(final Function<T, int[]> integerValues) {
		return new PrimitiveArrayBasedIntegerListAccessor<>(integerValues);
	}

	public <T> TextAccessor<T> getTextFromString(final Function<T, String> handleToString) {
		return new StringBasedTextAccessor<>(handleToString);
	}

	public <T> UnsignedIntegerAccessor<T> getUnsignedIntegerFromInt(final ToIntFunction<T> handleToInt) {
		return new IntBasedUnsignedIntegerAccessor<>(handleToInt);
	}

	public <T> TextListAccessor<T> getTextListDataFromKnownSizedStringCallable(
			HandleIndexToObjectFunction<T, String> toValue, ToIntFunction<T> toCount) {
		return new KnownCountSupplierBasedStringListAccessor<>(toValue, toCount);
	}

	public <T> TextListAccessor<T> getTextListDataFromStringSet(final Function<T, Set<String>> toValue) {
		return new TextListAccessorFromStringSet<>(toValue);
	}

	public <T> ByteAccessor<T> getByteDataFromInputStreamWithLength(final Function<T, InputStreamWithLength> toValue) {
		return new ByteAccessorFromInputStream<>(toValue);
	}
}
