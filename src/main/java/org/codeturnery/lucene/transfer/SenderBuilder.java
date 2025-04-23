package org.codeturnery.lucene.transfer;

import java.util.HashMap;
import java.util.Map;
import org.codeturnery.lucene.transfer.accessor.Accessor;

/**
 * TODO: use lazy initialization of members. provide methods with optimized
 * sizes (e.g. {@code setTextField(Set<TextField<T>>)}).
 * 
 * @param <T> The type of the handle.
 */
public class SenderBuilder<T extends Handle> {

	private final Map<String, Field<T, Accessor<T>>> fields;
	
	public SenderBuilder(final int expectedFieldCount) {
		this.fields = new HashMap<>(expectedFieldCount);
	}

	public void addField(final String name, final String description, Accessor<T> data) {
		this.fields.put(name, new Field<>(name, description, data));
	}

	public Sender<T> build(final HandleSupplier<T> handles, final CountSupplier countSupplier) {
		return new Sender(handles, countSupplier, this.fields);
	}
}
