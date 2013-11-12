//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.25 at 04:11:45 PM WEST 
//


package com.premiumminds.billy.portugal.services.export.saftpt.v1_02_01.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * <p>Java class for WithholdingTax complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WithholdingTax">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}WithholdingTaxType" minOccurs="0"/>
 *         &lt;element name="WithholdingTaxDescription" type="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}SAFPTtextTypeMandatoryMax60Car" minOccurs="0"/>
 *         &lt;element name="WithholdingTaxAmount" type="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}SAFmonetaryType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WithholdingTax", propOrder = {
    "withholdingTaxType",
    "withholdingTaxDescription",
    "withholdingTaxAmount"
})
public class WithholdingTax
    implements ToString
{

    @XmlElement(name = "WithholdingTaxType")
    protected String withholdingTaxType;
    @XmlElement(name = "WithholdingTaxDescription")
    protected String withholdingTaxDescription;
    @XmlElement(name = "WithholdingTaxAmount", required = true)
    protected BigDecimal withholdingTaxAmount;

    /**
     * Gets the value of the withholdingTaxType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWithholdingTaxType() {
        return withholdingTaxType;
    }

    /**
     * Sets the value of the withholdingTaxType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWithholdingTaxType(String value) {
        this.withholdingTaxType = value;
    }

    /**
     * Gets the value of the withholdingTaxDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWithholdingTaxDescription() {
        return withholdingTaxDescription;
    }

    /**
     * Sets the value of the withholdingTaxDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWithholdingTaxDescription(String value) {
        this.withholdingTaxDescription = value;
    }

    /**
     * Gets the value of the withholdingTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWithholdingTaxAmount() {
        return withholdingTaxAmount;
    }

    /**
     * Sets the value of the withholdingTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWithholdingTaxAmount(BigDecimal value) {
        this.withholdingTaxAmount = value;
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
            String theWithholdingTaxType;
            theWithholdingTaxType = this.getWithholdingTaxType();
            strategy.appendField(locator, this, "withholdingTaxType", buffer, theWithholdingTaxType);
        }
        {
            String theWithholdingTaxDescription;
            theWithholdingTaxDescription = this.getWithholdingTaxDescription();
            strategy.appendField(locator, this, "withholdingTaxDescription", buffer, theWithholdingTaxDescription);
        }
        {
            BigDecimal theWithholdingTaxAmount;
            theWithholdingTaxAmount = this.getWithholdingTaxAmount();
            strategy.appendField(locator, this, "withholdingTaxAmount", buffer, theWithholdingTaxAmount);
        }
        return buffer;
    }

}