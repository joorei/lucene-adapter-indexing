package org.codeturnery.lucene.transfer;

import java.util.Map;
import java.util.OptionalInt;
import org.codeturnery.lucene.transfer.accessor.Accessor;

/**
 * 
 * @param <T> Type of the handle
 */
public class Sender<T extends Handle> {
	private final HandleSupplier<T> handleSupplier;
	private final CountSupplier countSupplier;
	private final Map<String, Field<T, Accessor<T>>> fields;

	Sender(final HandleSupplier<T> handleSupplier, final CountSupplier countSupplier, final Map<String, Field<T, Accessor<T>>> fields) {
		this.handleSupplier = handleSupplier;
		this.countSupplier = countSupplier;
		this.fields = fields;
	}

	public HandleSupplier<T> getHandleSupplier() {
		return this.handleSupplier;
	}

	public Map<String, Field<T, Accessor<T>>> getFields() {
		return this.fields;
	}

	public CountSupplier getCountSupplier() {
		return this.countSupplier;
	}
}
