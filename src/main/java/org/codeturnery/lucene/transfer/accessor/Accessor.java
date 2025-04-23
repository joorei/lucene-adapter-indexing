package org.codeturnery.lucene.transfer.accessor;

import java.util.Set;

/**
 * 
 * @param <T> The type of the handle.
 */
public interface Accessor<T> {
	public static final Set<Class<?>> MARKER_INTERFACES = Set.of(ByteAccessor.class, ByteListAccessor.class,
			IntegerAccessor.class, IntegerListAccessor.class, TextAccessor.class, TextListAccessor.class,
			UnsignedIntegerAccessor.class);
}
