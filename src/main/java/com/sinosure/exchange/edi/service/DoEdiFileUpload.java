
package com.sinosure.exchange.edi.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.sinosure.exchange.edi.po.ArrayOfAttafile;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="attaFile" type="{http://po.edi.exchange.sinosure.com}ArrayOfAttafile"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "attaFile"
})
@XmlRootElement(name = "doEdiFileUpload")
public class DoEdiFileUpload {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfAttafile attaFile;

    /**
     * 获取attaFile属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAttafile }
     *     
     */
    public ArrayOfAttafile getAttaFile() {
        return attaFile;
    }

    /**
     * 设置attaFile属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAttafile }
     *     
     */
    public void setAttaFile(ArrayOfAttafile value) {
        this.attaFile = value;
    }

}
