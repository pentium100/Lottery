package com.itg.dao;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.CacheConcurrencyStrategy;   


@Entity
@Table(name = "Property")
@XmlRootElement(name="properties")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)   
public class Property {
    private int id;
    
	private int propertyType_id;
	private int id2;
	
	private Property country;
	
    public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}
	public int getPropertyType_id() {
		return propertyType_id;
	}
	public void setPropertyType_id(int propertyType_id) {
		this.propertyType_id = propertyType_id;
	}
	private String text;
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	//@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)   
    //@JoinColumn(name="PropertyType_ID")  
    //@XmlElement(type=PropertyType.class)
	//public PropertyType getPropertyType() {
	//	return propertyType;
	//}
	//public void setPropertyType(PropertyType propertyType) {
	//	this.propertyType = propertyType;
	//}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity=Property.class, fetch=FetchType.LAZY)   
    @JoinColumn(name = "COUNTRY_ID", insertable = false, updatable = true)   
    @NotFound(action=NotFoundAction.IGNORE) 
    public Property getCountry() {   
        return country;   
    } 
    
    public void setCountry(Property country){
    	this.country = country; 
    }
	
	

}
