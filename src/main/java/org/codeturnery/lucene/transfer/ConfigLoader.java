package org.codeturnery.lucene.transfer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.codeturnery.lucene.transfer.config.Config;

import org.xml.sax.SAXException;

public class ConfigLoader {

	public Config loadConfig(Source xmlFileContent, Source xsdContent) throws JAXBException, SAXException, IOException {
		final JAXBContext context = JAXBContext.newInstance(Config.class);
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		final Schema schema = schemaFactory.newSchema(xsdContent);
		unmarshaller.setSchema(schema);
		return (Config) unmarshaller.unmarshal(xmlFileContent);
	}

	public Config loadConfig(InputStream xmlFileContent, String xsdFilePath)
			throws JAXBException, SAXException, IOException {
		return loadConfig(new StreamSource(xmlFileContent), new StreamSource(Paths.get(xsdFilePath).toFile()));
	}
}
