
package com.sinosure.exchange.edi.po;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfClaimApply complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ArrayOfClaimApply"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ClaimApply" type="{http://po.edi.exchange.sinosure.com}ClaimApply" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfClaimApply", propOrder = {
    "claimApply"
})
public class ArrayOfClaimApply {

    @XmlElement(name = "ClaimApply", nillable = true)
    protected List<ClaimApply> claimApply;

    /**
     * Gets the value of the claimApply property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the claimApply property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClaimApply().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClaimApply }
     * 
     * 
     */
    public List<ClaimApply> getClaimApply() {
        if (claimApply == null) {
            claimApply = new ArrayList<ClaimApply>();
        }
        return this.claimApply;
    }

}
