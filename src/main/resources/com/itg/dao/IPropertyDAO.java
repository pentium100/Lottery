package com.itg.dao;


import java.util.List;

public interface IPropertyDAO {
	
	
	   public void insertProperty(Property p);
	   public List<Property> getProperties(int pt);
	   public void modifyProperty(Property p);
	   public void deleteProperty(Property p);
	   public Property findPropertyById(Integer id);
	   public void insertPropertyIfNoExists(Property p);
	   
	   public Property findPropertyByText(String text, int propertyType_id);
	   public Property findPropertyById2(int id2, int propertyType_id); 
	   
	   

}
