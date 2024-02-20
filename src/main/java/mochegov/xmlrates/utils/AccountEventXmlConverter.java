package mochegov.xmlrates.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rates.SrvAccountPublEvent;

@Component
@Slf4j
public class AccountEventXmlConverter {
    private final Marshaller marshaller;

    public AccountEventXmlConverter() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SrvAccountPublEvent.class);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public String serializeToXmlFormat(SrvAccountPublEvent event) {
        try {
            StringWriter writer = new StringWriter();
            marshaller.marshal(event, writer);
            return writer.toString();
        } catch (JAXBException e) {
            log.error("Unexpected error occurred during serializing to xml", e);
            throw new RuntimeException(e);
        }
    }
}
