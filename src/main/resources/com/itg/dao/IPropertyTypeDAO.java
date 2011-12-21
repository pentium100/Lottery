package com.itg.dao;


import java.util.List;

public interface IPropertyTypeDAO {
	
	
	   public void insertPropertyType(PropertyType pt);
	   public List<PropertyType> getPropertyTypes();
	   public void modifyPropertyType(PropertyType pt);
	   public void deletePropertyType(PropertyType pt);
	   public PropertyType findPropertyTypeById(Integer id);
	   
	   

}
