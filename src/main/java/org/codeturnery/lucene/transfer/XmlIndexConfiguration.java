package org.codeturnery.lucene.transfer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.facet.FacetsConfig.DrillDownTermsIndexing;
import org.apache.lucene.index.IndexableField;
import org.codeturnery.lucene.document.FacetConfigFactory;
import org.codeturnery.lucene.document.FacetFieldConfig;
import org.codeturnery.lucene.transfer.accessor.Accessor;
import org.codeturnery.lucene.transfer.config.Config;
import org.codeturnery.lucene.transfer.config.Config.Adapter;
import org.codeturnery.lucene.transfer.config.Config.Adapter.Entity;
import org.codeturnery.lucene.transfer.config.Config.Index;
import org.codeturnery.lucene.transfer.config.Config.Index.DocumentField;
import org.codeturnery.lucene.transfer.config.Config.Index.FacetConfig;
import org.xml.sax.SAXException;

public class XmlIndexConfiguration implements IndexConfiguration {
	private final Config config;
	private final FacetConfigFactory facetConfigFactory;
	private final Map<String, FacetConfig> facetConfigs;

	/**
	 * TODO: refactor, so that the XSD is loaded differently (maybe from a URL?)
	 * 
	 * @param xmlConfigFileName
	 * @param xsdFilePath
	 * @param facetConfigFactory
	 * @throws SAXException 
	 * @throws JAXBException 
	 * @throws IOException
	 * 
	 */
	public XmlIndexConfiguration(final String xmlConfigFileName, final String xsdFilePath, final FacetConfigFactory facetConfigFactory) throws IOException, JAXBException, SAXException {
		try (final InputStream configStream = getClass().getClassLoader().getResourceAsStream(xmlConfigFileName);) {
			this.config = new ConfigLoader().loadConfig(configStream, xsdFilePath);
			final List<FacetConfig> facetConfigList = config.getIndex().getFacetConfig();
			this.facetConfigs = new HashMap<>(facetConfigList.size());
			for (final FacetConfig facetConfig : facetConfigList) {
				this.facetConfigs.put(facetConfig.getIdentifier(), facetConfig);
			}
		}
		this.facetConfigFactory = facetConfigFactory;
	}

	@Override
	public Set<String> getAdapters() {
		return this.config.getAdapter().stream().map(adapter -> adapter.getIdentifier()).collect(Collectors.toSet());
	}

	@Override
	public <T extends Handle> FieldsGenerator<T> getFieldsGenerator(final String adapterIdentifier,
			String entityIdentifier, Map<String, Field<T, Accessor<T>>> fields) {
		// select the config for the kind of documents to index
		final Entity entityConfig = getEntityConfig(adapterIdentifier, entityIdentifier);
		return new XmlBasedFieldsGenerator(entityConfig, fields);
	}

	private Entity getEntityConfig(String adapterIdentifier, String entityIdentifier) {
		for (final Adapter adapter : this.config.getAdapter()) {
			if (adapterIdentifier.equals(adapter.getIdentifier())) {
				for (final Entity entity : adapter.getEntity()) {
					if (entityIdentifier.equals(entity.getIdentifier())) {
						return entity;
					}
				}
			}
		}
		throw new NoSuchElementException(
				"Could not find an entity '" + entityIdentifier + "' in an adapter '" + adapterIdentifier + "'");
	}

	@Override
	public Set<String> getKeyFields() {
		return this.config.getIndex().getDocumentField().stream().filter(DocumentField::isKeyField)
				.map(DocumentField::getName).collect(Collectors.toSet());
	}

	protected Iterable<FacetFieldConfig> getFacetFieldConfigs() {
		final Index indexConfig = this.config.getIndex();
		return indexConfig.getDocumentField().stream().map(documentField -> {
					final FacetConfig facetConfig = this.facetConfigs.get(documentField.getFacetConfig());
					return new FacetFieldConfig() {

						@Override
						public boolean isMultiValued() {
							return facetConfig.isMultiValued();
						}

						@Override
						public boolean isHierarchical() {
							return facetConfig.isHierarchical();
						}

						@Override
						public boolean isDimCountRequired() {
							return facetConfig.isDimCountRequired();
						}

						@Override
						public String getIndexFieldName() {
							return facetConfig.getIndexFieldName();
						}

						@Override
						public String getFacetDimension() {
							return documentField.getFacetDimension();
						}

						@Override
						public DrillDownTermsIndexing getDrillDownTermsIndexing() {
							return DrillDownTermsIndexing.valueOf(facetConfig.getDrillDownTermsIndexing());
						}
					};
				}).collect(Collectors.toList());
	}

	@Override
	public Path getIndexPath() {
		return Paths.get(this.config.getIndex().getIndexPath());
	}

	@Override
	public Path getTaxonomyPath() {
		return Paths.get(this.config.getIndex().getTaxonomyPath());
	}

	@Override
	public String getDisplayIdentifier() {
		// FIXME: adjust method and usage, so that different languages are considered
		return this.config.getLabels().getFallback();
	}

	@Override
	public double getRamBufferSizeMb() {
		return this.config.getIndex().getRamBufferSizeMb();
	}

	@Override
	public FacetsConfig getFacetsContig() {
		return this.facetConfigFactory.createFacetsConfig(getFacetFieldConfigs());
	}

	@Override
	public String getFieldNamesDimension() {
		return this.config.getIndex().getFieldNamesDimension();
	}
}
