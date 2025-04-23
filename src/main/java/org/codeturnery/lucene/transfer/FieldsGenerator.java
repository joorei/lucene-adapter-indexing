package org.codeturnery.lucene.transfer;

import java.io.IOException;

import org.apache.lucene.index.IndexableField;

@FunctionalInterface
public interface FieldsGenerator<T extends Handle> {
	public Iterable<IndexableField> getFields(T handle) throws IOException;
}