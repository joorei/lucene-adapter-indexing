package org.codeturnery.lucene.transfer;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.codeturnery.characters.ProgressPrinter;
import org.codeturnery.lucene.document.FieldFactory;
import org.codeturnery.lucene.transfer.accessor.Accessor;

/**
 * Holds the helper classes to write into a Lucene index.
 */
public class ProgressPrintingIndexer extends AbstractIndexer {
	private final IndexConfiguration config;
	private final ProgressPrinter printer;

	public ProgressPrintingIndexer(IndexConfiguration config, final ProgressPrinter printer, Analyzer analyzer,
			final FieldFactory fieldFactory) throws IOException {
		super(config, analyzer, fieldFactory, config.getFieldNamesDimension());
		this.printer = printer;
		this.config = config;
	}

	/**
	 * 
	 * @param <T>
	 * @param adapterIdentifier
	 * @param entityIdentifier  the name of the documents to index, will not be
	 *                          stored in the index, but determines what fields will
	 *                          be created for each document
	 * @param fields
	 * @param handleSupplier
	 * @throws IOException
	 */
	public <T extends Handle> void index(final String adapterIdentifier, final String entityIdentifier,
			Map<String, Field<T, Accessor<T>>> fields, final HandleSupplier<T> handleSupplier) throws IOException {
		final FieldsGenerator<T> fieldsGenerator = this.config.getFieldsGenerator(adapterIdentifier, entityIdentifier,
				fields);
		indexDocuments(handleSupplier, fieldsGenerator);

	}

	/**
	 * 
	 * @param <T>
	 * @param indexer
	 * @param documentName
	 * @param sender
	 * @throws IOException
	 */
	public <T extends Handle> void index(ProgressPrintingIndexer indexer, String adapterIdentifier, String documentName,
			Sender<T> sender) throws IOException {
		this.index(adapterIdentifier, documentName, sender.getFields(), sender.getHandleSupplier());
	}

	@Override
	protected void concludeDocument() {
		this.printer.next();
	}

	@Override
	protected void concludeIndexing() {
		this.printer.reset();
	}
}
