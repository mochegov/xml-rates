package mochegov.xmlrates.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeUtils {
    public static XMLGregorianCalendar buildXMLGregorianCalendar(int year, int month, int day) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, day, 3, 0);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            log.error("There was an error constructing XMLGregorianCalendar from year, month and day");
        }
        return null;
    }

    public static XMLGregorianCalendar convertLocalDateToXMLGregorianCalendar(LocalDate localDate) {
        try {
            GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            log.error("There was an error converting LocalDate to XMLGregorianCalendar");
        }
        return null;
    }

    public static XMLGregorianCalendar localDateTimeToXmlGregorianCalendar(LocalDateTime dateTime) {
        try {
            Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(instant.toString());
        } catch (DatatypeConfigurationException e) {
            log.error("There was an error converting LocalDateTime to XMLGregorianCalendar");
        }
        return null;
    }
}
