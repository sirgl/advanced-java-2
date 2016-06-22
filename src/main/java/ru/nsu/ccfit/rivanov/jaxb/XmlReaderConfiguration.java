package ru.nsu.ccfit.rivanov.jaxb;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.rivanov.jaxb.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class XmlReaderConfiguration {
    private static final String CONTEXT_PATH = "org.openstreetmap.osm._0";
    public static final String SCHEMA_FILE = "OSMSchema.xsd";
    public static final String FILE_NAME = "RU-NVS.osm.bz2"; // TODO to properties

    @Bean
    Schema osmSchema() {
        Schema schema;
        try(InputStream schemaStream = ClassLoader.getSystemResourceAsStream(SCHEMA_FILE)) {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            StreamSource source = new StreamSource(schemaStream);
            schema = factory.newSchema(source);
        } catch (IOException | SAXException e) {
            //TODO logger
            return null;
        }
        return schema;
    }

    @Bean
    @Scope("prototype")
    XMLReader xmlReader() throws IOException, JAXBException, XMLStreamException {
        try(InputStream stream = ClassLoader.getSystemResourceAsStream(FILE_NAME)){
            BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);
            BZip2CompressorInputStream bzip = new BZip2CompressorInputStream(bufferedInputStream);
            return new XMLReader(bzip, CONTEXT_PATH, osmSchema());
        }

    }
}
