
package com.sinosure.exchange.edi.po;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfReceiptAltApproveInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ArrayOfReceiptAltApproveInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReceiptAltApproveInfo" type="{http://po.edi.exchange.sinosure.com}ReceiptAltApproveInfo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfReceiptAltApproveInfo", propOrder = {
    "receiptAltApproveInfo"
})
public class ArrayOfReceiptAltApproveInfo {

    @XmlElement(name = "ReceiptAltApproveInfo", nillable = true)
    protected List<ReceiptAltApproveInfo> receiptAltApproveInfo;

    /**
     * Gets the value of the receiptAltApproveInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receiptAltApproveInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceiptAltApproveInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReceiptAltApproveInfo }
     * 
     * 
     */
    public List<ReceiptAltApproveInfo> getReceiptAltApproveInfo() {
        if (receiptAltApproveInfo == null) {
            receiptAltApproveInfo = new ArrayList<ReceiptAltApproveInfo>();
        }
        return this.receiptAltApproveInfo;
    }

}
