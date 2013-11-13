//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.25 at 05:06:02 PM WEST 
//


package com.premiumminds.billy.portugal.services.export.saftpt.v1_03_01.schema;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
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
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}AuditFileVersion"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}CompanyID"/>
 *         &lt;element name="TaxRegistrationNumber" type="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}SAFPTPortugueseVatNumber"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}TaxAccountingBasis"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}CompanyName"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}BusinessName" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}CompanyAddress"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}FiscalYear"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}StartDate"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}EndDate"/>
 *         &lt;element name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}DateCreated"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}TaxEntity"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}ProductCompanyTaxID"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}SoftwareCertificateNumber"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}ProductID"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}ProductVersion"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}HeaderComment" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}Telephone" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}Fax" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}Email" minOccurs="0"/>
 *         &lt;element ref="{urn:OECD:StandardAuditFile-Tax:PT_1.03_01}Website" minOccurs="0"/>
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
    "auditFileVersion",
    "companyID",
    "taxRegistrationNumber",
    "taxAccountingBasis",
    "companyName",
    "businessName",
    "companyAddress",
    "fiscalYear",
    "startDate",
    "endDate",
    "currencyCode",
    "dateCreated",
    "taxEntity",
    "productCompanyTaxID",
    "softwareCertificateNumber",
    "productID",
    "productVersion",
    "headerComment",
    "telephone",
    "fax",
    "email",
    "website"
})
@XmlRootElement(name = "Header")
public class Header
    implements ToString
{

    @XmlElement(name = "AuditFileVersion", required = true)
    protected String auditFileVersion;
    @XmlElement(name = "CompanyID", required = true)
    protected String companyID;
    @XmlElement(name = "TaxRegistrationNumber")
    protected int taxRegistrationNumber;
    @XmlElement(name = "TaxAccountingBasis", required = true)
    protected String taxAccountingBasis;
    @XmlElement(name = "CompanyName", required = true)
    protected String companyName;
    @XmlElement(name = "BusinessName")
    protected String businessName;
    @XmlElement(name = "CompanyAddress", required = true)
    protected AddressStructurePT companyAddress;
    @XmlElement(name = "FiscalYear")
    protected int fiscalYear;
    @XmlElement(name = "StartDate", required = true)
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate", required = true)
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "CurrencyCode", required = true)
    protected Object currencyCode;
    @XmlElement(name = "DateCreated", required = true)
    protected XMLGregorianCalendar dateCreated;
    @XmlElement(name = "TaxEntity", required = true)
    protected String taxEntity;
    @XmlElement(name = "ProductCompanyTaxID", required = true)
    protected String productCompanyTaxID;
    @XmlElement(name = "SoftwareCertificateNumber", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger softwareCertificateNumber;
    @XmlElement(name = "ProductID", required = true)
    protected String productID;
    @XmlElement(name = "ProductVersion", required = true)
    protected String productVersion;
    @XmlElement(name = "HeaderComment")
    protected String headerComment;
    @XmlElement(name = "Telephone")
    protected String telephone;
    @XmlElement(name = "Fax")
    protected String fax;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "Website")
    protected String website;

    /**
     * Gets the value of the auditFileVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditFileVersion() {
        return auditFileVersion;
    }

    /**
     * Sets the value of the auditFileVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditFileVersion(String value) {
        this.auditFileVersion = value;
    }

    /**
     * Gets the value of the companyID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyID() {
        return companyID;
    }

    /**
     * Sets the value of the companyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyID(String value) {
        this.companyID = value;
    }

    /**
     * Gets the value of the taxRegistrationNumber property.
     * 
     */
    public int getTaxRegistrationNumber() {
        return taxRegistrationNumber;
    }

    /**
     * Sets the value of the taxRegistrationNumber property.
     * 
     */
    public void setTaxRegistrationNumber(int value) {
        this.taxRegistrationNumber = value;
    }

    /**
     * Gets the value of the taxAccountingBasis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxAccountingBasis() {
        return taxAccountingBasis;
    }

    /**
     * Sets the value of the taxAccountingBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxAccountingBasis(String value) {
        this.taxAccountingBasis = value;
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
     * Gets the value of the businessName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * Sets the value of the businessName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessName(String value) {
        this.businessName = value;
    }

    /**
     * Gets the value of the companyAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressStructurePT }
     *     
     */
    public AddressStructurePT getCompanyAddress() {
        return companyAddress;
    }

    /**
     * Sets the value of the companyAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressStructurePT }
     *     
     */
    public void setCompanyAddress(AddressStructurePT value) {
        this.companyAddress = value;
    }

    /**
     * Gets the value of the fiscalYear property.
     * 
     */
    public int getFiscalYear() {
        return fiscalYear;
    }

    /**
     * Sets the value of the fiscalYear property.
     * 
     */
    public void setFiscalYear(int value) {
        this.fiscalYear = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setCurrencyCode(Object value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the dateCreated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the value of the dateCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCreated(XMLGregorianCalendar value) {
        this.dateCreated = value;
    }

    /**
     * Gets the value of the taxEntity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxEntity() {
        return taxEntity;
    }

    /**
     * Sets the value of the taxEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxEntity(String value) {
        this.taxEntity = value;
    }

    /**
     * Gets the value of the productCompanyTaxID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductCompanyTaxID() {
        return productCompanyTaxID;
    }

    /**
     * Sets the value of the productCompanyTaxID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductCompanyTaxID(String value) {
        this.productCompanyTaxID = value;
    }

    /**
     * Gets the value of the softwareCertificateNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSoftwareCertificateNumber() {
        return softwareCertificateNumber;
    }

    /**
     * Sets the value of the softwareCertificateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSoftwareCertificateNumber(BigInteger value) {
        this.softwareCertificateNumber = value;
    }

    /**
     * Gets the value of the productID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductID() {
        return productID;
    }

    /**
     * Sets the value of the productID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductID(String value) {
        this.productID = value;
    }

    /**
     * Gets the value of the productVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductVersion() {
        return productVersion;
    }

    /**
     * Sets the value of the productVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductVersion(String value) {
        this.productVersion = value;
    }

    /**
     * Gets the value of the headerComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeaderComment() {
        return headerComment;
    }

    /**
     * Sets the value of the headerComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeaderComment(String value) {
        this.headerComment = value;
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
            String theAuditFileVersion;
            theAuditFileVersion = this.getAuditFileVersion();
            strategy.appendField(locator, this, "auditFileVersion", buffer, theAuditFileVersion);
        }
        {
            String theCompanyID;
            theCompanyID = this.getCompanyID();
            strategy.appendField(locator, this, "companyID", buffer, theCompanyID);
        }
        {
            int theTaxRegistrationNumber;
            theTaxRegistrationNumber = (true?this.getTaxRegistrationNumber(): 0);
            strategy.appendField(locator, this, "taxRegistrationNumber", buffer, theTaxRegistrationNumber);
        }
        {
            String theTaxAccountingBasis;
            theTaxAccountingBasis = this.getTaxAccountingBasis();
            strategy.appendField(locator, this, "taxAccountingBasis", buffer, theTaxAccountingBasis);
        }
        {
            String theCompanyName;
            theCompanyName = this.getCompanyName();
            strategy.appendField(locator, this, "companyName", buffer, theCompanyName);
        }
        {
            String theBusinessName;
            theBusinessName = this.getBusinessName();
            strategy.appendField(locator, this, "businessName", buffer, theBusinessName);
        }
        {
            AddressStructurePT theCompanyAddress;
            theCompanyAddress = this.getCompanyAddress();
            strategy.appendField(locator, this, "companyAddress", buffer, theCompanyAddress);
        }
        {
            int theFiscalYear;
            theFiscalYear = (true?this.getFiscalYear(): 0);
            strategy.appendField(locator, this, "fiscalYear", buffer, theFiscalYear);
        }
        {
            XMLGregorianCalendar theStartDate;
            theStartDate = this.getStartDate();
            strategy.appendField(locator, this, "startDate", buffer, theStartDate);
        }
        {
            XMLGregorianCalendar theEndDate;
            theEndDate = this.getEndDate();
            strategy.appendField(locator, this, "endDate", buffer, theEndDate);
        }
        {
            Object theCurrencyCode;
            theCurrencyCode = this.getCurrencyCode();
            strategy.appendField(locator, this, "currencyCode", buffer, theCurrencyCode);
        }
        {
            XMLGregorianCalendar theDateCreated;
            theDateCreated = this.getDateCreated();
            strategy.appendField(locator, this, "dateCreated", buffer, theDateCreated);
        }
        {
            String theTaxEntity;
            theTaxEntity = this.getTaxEntity();
            strategy.appendField(locator, this, "taxEntity", buffer, theTaxEntity);
        }
        {
            String theProductCompanyTaxID;
            theProductCompanyTaxID = this.getProductCompanyTaxID();
            strategy.appendField(locator, this, "productCompanyTaxID", buffer, theProductCompanyTaxID);
        }
        {
            BigInteger theSoftwareCertificateNumber;
            theSoftwareCertificateNumber = this.getSoftwareCertificateNumber();
            strategy.appendField(locator, this, "softwareCertificateNumber", buffer, theSoftwareCertificateNumber);
        }
        {
            String theProductID;
            theProductID = this.getProductID();
            strategy.appendField(locator, this, "productID", buffer, theProductID);
        }
        {
            String theProductVersion;
            theProductVersion = this.getProductVersion();
            strategy.appendField(locator, this, "productVersion", buffer, theProductVersion);
        }
        {
            String theHeaderComment;
            theHeaderComment = this.getHeaderComment();
            strategy.appendField(locator, this, "headerComment", buffer, theHeaderComment);
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
        return buffer;
    }

}
