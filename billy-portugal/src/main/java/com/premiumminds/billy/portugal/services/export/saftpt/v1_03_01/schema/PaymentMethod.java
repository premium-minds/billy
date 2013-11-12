//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.25 at 05:06:02 PM WEST 
//


package com.premiumminds.billy.portugal.services.export.saftpt.v1_03_01.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * <p>Java class for PaymentMethod complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentMethod">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}PaymentMechanism"/>
 *         &lt;element name="PaymentAmount" type="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}SAFmonetaryType"/>
 *         &lt;element name="PaymentDate" type="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}SAFdateType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentMethod", propOrder = {
    "paymentMechanism",
    "paymentAmount",
    "paymentDate"
})
public class PaymentMethod
    implements ToString
{

    @XmlElement(name = "PaymentMechanism", required = true)
    protected String paymentMechanism;
    @XmlElement(name = "PaymentAmount", required = true)
    protected BigDecimal paymentAmount;
    @XmlElement(name = "PaymentDate", required = true)
    protected XMLGregorianCalendar paymentDate;

    /**
     * Gets the value of the paymentMechanism property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentMechanism() {
        return paymentMechanism;
    }

    /**
     * Sets the value of the paymentMechanism property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentMechanism(String value) {
        this.paymentMechanism = value;
    }

    /**
     * Gets the value of the paymentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPaymentAmount(BigDecimal value) {
        this.paymentAmount = value;
    }

    /**
     * Gets the value of the paymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the value of the paymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPaymentDate(XMLGregorianCalendar value) {
        this.paymentDate = value;
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            String thePaymentMechanism;
            thePaymentMechanism = this.getPaymentMechanism();
            strategy.appendField(locator, this, "paymentMechanism", buffer, thePaymentMechanism);
        }
        {
            BigDecimal thePaymentAmount;
            thePaymentAmount = this.getPaymentAmount();
            strategy.appendField(locator, this, "paymentAmount", buffer, thePaymentAmount);
        }
        {
            XMLGregorianCalendar thePaymentDate;
            thePaymentDate = this.getPaymentDate();
            strategy.appendField(locator, this, "paymentDate", buffer, thePaymentDate);
        }
        return buffer;
    }

}