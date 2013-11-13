//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.25 at 04:11:45 PM WEST 
//


package com.premiumminds.billy.portugal.services.export.saftpt.v1_02_01.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}CustomerID"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}AccountID"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}CustomerTaxID"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}CompanyName"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}Contact" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}BillingAddress"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}ShipToAddress" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}Telephone" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}Fax" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}Email" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}Website" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.02_01}SelfBillingIndicator"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "customerID",
    "accountID",
    "customerTaxID",
    "companyName",
    "contact",
    "billingAddress",
    "shipToAddress",
    "telephone",
    "fax",
    "email",
    "website",
    "selfBillingIndicator"
})
@XmlRootElement(name = "Customer")
public class Customer
    implements ToString
{

    @XmlElement(name = "CustomerID", required = true)
    protected String customerID;
    @XmlElement(name = "AccountID", required = true)
    protected String accountID;
    @XmlElement(name = "CustomerTaxID", required = true)
    protected String customerTaxID;
    @XmlElement(name = "CompanyName", required = true)
    protected String companyName;
    @XmlElement(name = "Contact")
    protected String contact;
    @XmlElement(name = "BillingAddress", required = true)
    protected AddressStructure billingAddress;
    @XmlElement(name = "ShipToAddress")
    protected List<AddressStructure> shipToAddress;
    @XmlElement(name = "Telephone")
    protected String telephone;
    @XmlElement(name = "Fax")
    protected String fax;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "Website")
    protected String website;
    @XmlElement(name = "SelfBillingIndicator")
    protected int selfBillingIndicator;

    /**
     * Gets the value of the customerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * Sets the value of the customerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerID(String value) {
        this.customerID = value;
    }

    /**
     * Gets the value of the accountID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountID() {
        return accountID;
    }

    /**
     * Sets the value of the accountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountID(String value) {
        this.accountID = value;
    }

    /**
     * Gets the value of the customerTaxID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerTaxID() {
        return customerTaxID;
    }

    /**
     * Sets the value of the customerTaxID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerTaxID(String value) {
        this.customerTaxID = value;
    }

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContact(String value) {
        this.contact = value;
    }

    /**
     * Gets the value of the billingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressStructure }
     *     
     */
    public AddressStructure getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets the value of the billingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressStructure }
     *     
     */
    public void setBillingAddress(AddressStructure value) {
        this.billingAddress = value;
    }

    /**
     * Gets the value of the shipToAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shipToAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShipToAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AddressStructure }
     * 
     * 
     */
    public List<AddressStructure> getShipToAddress() {
        if (shipToAddress == null) {
            shipToAddress = new ArrayList<AddressStructure>();
        }
        return this.shipToAddress;
    }

    /**
     * Gets the value of the telephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the value of the telephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelephone(String value) {
        this.telephone = value;
    }

    /**
     * Gets the value of the fax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the value of the fax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the website property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets the value of the website property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebsite(String value) {
        this.website = value;
    }

    /**
     * Gets the value of the selfBillingIndicator property.
     * 
     */
    public int getSelfBillingIndicator() {
        return selfBillingIndicator;
    }

    /**
     * Sets the value of the selfBillingIndicator property.
     * 
     */
    public void setSelfBillingIndicator(int value) {
        this.selfBillingIndicator = value;
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
            String theCustomerID;
            theCustomerID = this.getCustomerID();
            strategy.appendField(locator, this, "customerID", buffer, theCustomerID);
        }
        {
            String theAccountID;
            theAccountID = this.getAccountID();
            strategy.appendField(locator, this, "accountID", buffer, theAccountID);
        }
        {
            String theCustomerTaxID;
            theCustomerTaxID = this.getCustomerTaxID();
            strategy.appendField(locator, this, "customerTaxID", buffer, theCustomerTaxID);
        }
        {
            String theCompanyName;
            theCompanyName = this.getCompanyName();
            strategy.appendField(locator, this, "companyName", buffer, theCompanyName);
        }
        {
            String theContact;
            theContact = this.getContact();
            strategy.appendField(locator, this, "contact", buffer, theContact);
        }
        {
            AddressStructure theBillingAddress;
            theBillingAddress = this.getBillingAddress();
            strategy.appendField(locator, this, "billingAddress", buffer, theBillingAddress);
        }
        {
            List<AddressStructure> theShipToAddress;
            theShipToAddress = (((this.shipToAddress!= null)&&(!this.shipToAddress.isEmpty()))?this.getShipToAddress():null);
            strategy.appendField(locator, this, "shipToAddress", buffer, theShipToAddress);
        }
        {
            String theTelephone;
            theTelephone = this.getTelephone();
            strategy.appendField(locator, this, "telephone", buffer, theTelephone);
        }
        {
            String theFax;
            theFax = this.getFax();
            strategy.appendField(locator, this, "fax", buffer, theFax);
        }
        {
            String theEmail;
            theEmail = this.getEmail();
            strategy.appendField(locator, this, "email", buffer, theEmail);
        }
        {
            String theWebsite;
            theWebsite = this.getWebsite();
            strategy.appendField(locator, this, "website", buffer, theWebsite);
        }
        {
            int theSelfBillingIndicator;
            theSelfBillingIndicator = (true?this.getSelfBillingIndicator(): 0);
            strategy.appendField(locator, this, "selfBillingIndicator", buffer, theSelfBillingIndicator);
        }
        return buffer;
    }

}
