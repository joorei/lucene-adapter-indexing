package org.codeturnery.lucene.transfer;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexableField;
import org.codeturnery.lucene.access.IndexConfig;
import org.codeturnery.lucene.access.IndexManager;
import org.codeturnery.lucene.access.WriteToolbox;
import org.codeturnery.lucene.document.CommonFieldTypes;
import org.codeturnery.lucene.document.DocumentBuilder;
import org.codeturnery.lucene.document.FieldFactory;

abstract public class AbstractIndexer implements Closeable {

	private final IndexManager indexManager;
	private final WriteToolbox writeToolbox;
	private final FieldFactory fieldFactory;
	private final String fieldNamesDimension;

	/**
	 * 
	 * @param config
	 * @param analyzer
	 * @param fieldFactory
	 * @param fieldNamesDimension The field name to use to store all used field
	 *                            names to support "missing field" searches.
	 * @throws IOException
	 */
	public AbstractIndexer(final IndexConfig config, final Analyzer analyzer, final FieldFactory fieldFactory,
			final String fieldNamesDimension) throws IOException {
		this.indexManager = new IndexManager(config);
		this.writeToolbox = new WriteToolbox(this.indexManager.getWriteExecuter(analyzer));
		this.fieldFactory = fieldFactory;
		this.fieldNamesDimension = fieldNamesDimension;
	}

	@Override
	public void close() throws IOException {
		this.indexManager.close();
	}

	public void commitAndMerge() throws IOException {
		this.writeToolbox.commit();
		this.writeToolbox.merge();
	}

	protected <T extends Handle> void indexDocuments(final HandleSupplier<T> handleSupplier,
			final FieldsGenerator<T> fieldsGenerator) throws IOException {
		try (final Stream<T> stream = handleSupplier.getHandles()) {
			stream.map(handle -> {
				try {
					return fieldsGenerator.getFields(handle);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}).forEach(this::indexDocument);
			concludeIndexing();
		} catch (UncheckedIOException e) {
			throw e.getCause();
		}
	}

	protected void indexDocument(Iterable<IndexableField> indexableFields) {
		try {
			final var documentBuilder = new DocumentBuilder();
			documentBuilder.addAll(indexableFields);
			// Add support for "missing fields" search to the document.
			// We need to get all the fields from the document before changing the document,
			// as changing the document while iterating over it is not allowed.
			final Set<String> fieldNames = documentBuilder.getFieldNamesInUse();
			documentBuilder.addAll(this.fieldFactory.createStrings(this.fieldNamesDimension, fieldNames,
					CommonFieldTypes.TERM_EXACT_MATCHING, false));

			this.writeToolbox.accept(documentBuilder.getDocument());
			concludeDocument();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	abstract protected void concludeIndexing();

	abstract protected void concludeDocument();
}
