
package rates;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rates package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _RateGroup_QNAME = new QName("http://www.raiffeisen.ru/esb/srv/RatesPubl/v4", "RateGroup");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rates
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RateGroup }
     * 
     */
    public RateGroup createRateGroup() {
        return new RateGroup();
    }

    /**
     * Create an instance of {@link RateVolume }
     * 
     */
    public RateVolume createRateVolume() {
        return new RateVolume();
    }

    /**
     * Create an instance of {@link RateValue }
     * 
     */
    public RateValue createRateValue() {
        return new RateValue();
    }

    /**
     * Create an instance of {@link Rate }
     * 
     */
    public Rate createRate() {
        return new Rate();
    }

    /**
     * Create an instance of {@link RateList }
     * 
     */
    public RateList createRateList() {
        return new RateList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RateGroup }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RateGroup }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.raiffeisen.ru/esb/srv/RatesPubl/v4", name = "RateGroup")
    public JAXBElement<RateGroup> createRateGroup(RateGroup value) {
        return new JAXBElement<>(_RateGroup_QNAME, RateGroup.class, null, value);
    }

}
