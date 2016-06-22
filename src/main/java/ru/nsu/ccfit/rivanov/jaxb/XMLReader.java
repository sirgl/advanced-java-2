package ru.nsu.ccfit.rivanov.jaxb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.ccfit.rivanov.entities.Node;
import ru.nsu.ccfit.rivanov.entities.Tag;
import ru.nsu.ccfit.rivanov.services.NodeSupplier;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.validation.Schema;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class XMLReader implements NodeSupplier {
    private final XMLStreamReader reader;
    private final Unmarshaller unmarshaller;
    //    private final Logger logger = LogManager.getLogger();
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public XMLReader(InputStream stream, String contextPath, Schema schema) throws XMLStreamException, JAXBException {
        XMLInputFactory xmlFactory = XMLInputFactory.newFactory();
        reader = new XsiTypeReader(xmlFactory.createXMLStreamReader(stream));
        JAXBContext jc = JAXBContext.newInstance(contextPath);
        unmarshaller = jc.createUnmarshaller();
        unmarshaller.setSchema(schema);
    }

    @Override
    public Node nextNode() {
        try {
            while (reader.hasNext()) {
                if (reader.isStartElement()) {
                    String localName = reader.getLocalName();
                    if (localName.equals("node")) {
                        JAXBElement<org.openstreetmap.osm._0.Node> nodeJAXBElement = unmarshaller.unmarshal(reader, org.openstreetmap.osm._0.Node.class);
                        org.openstreetmap.osm._0.Node value = nodeJAXBElement.getValue();
                        Timestamp timestamp = new Timestamp(value.getTimestamp().toGregorianCalendar().getTimeInMillis());
                        Node node = new Node(value.getId(), value.getLat(), value.getLon(), value.getUser(), value.getUid(),
                                value.isVisible(), value.getVersion(), value.getChangeset(), timestamp);
                        List<Tag> tags = value.getTag().stream()
                                .map(tag -> {
                                    Tag tag1 = new Tag(tag.getK(), tag.getV());
                                    tag1.setNode(node);
                                    return tag1;
                                })
                                .collect(Collectors.toList());
                        node.setTag(tags);
                        return node;
                    }
                }
                reader.next();
            }
        } catch (XMLStreamException | JAXBException e) {
            logger.error("Error while reading xml");
        }
        return null;
    }


    // To avoid problems with scheme validation
    // Because source file namespaces don't satisfy schema
    private static class XsiTypeReader extends StreamReaderDelegate {
        XsiTypeReader(XMLStreamReader reader) {
            super(reader);
        }

        @Override
        public String getNamespaceURI() {
            return "http://openstreetmap.org/osm/0.6";
        }
    }
}
