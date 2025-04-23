package org.codeturnery.lucene.transfer;

import org.codeturnery.lucene.transfer.accessor.Accessor;

/**
 * @param <T> The type of the handle.
 * @param <D> The type of the data stored in this field.
 */
public class Field<T, D extends Accessor<T>> {
	private final String name;
	private final String description;
	private final D data;

	Field(final String name, final String description, final D data) {
		this.name = name;
		this.description = description;
		this.data = data;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public D getData() {
		return this.data;
	}
}
