package com.itg.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class PropertyTypeDAO extends HibernateDaoSupport implements IPropertyTypeDAO {

	public void deletePropertyType(PropertyType pt) {
		getHibernateTemplate().delete(pt);

	}

	public PropertyType findPropertyTypeById(Integer id) {
		
		String sql="From PropertyType where id=? ";
		return (PropertyType) getHibernateTemplate().find(sql, new Object[]{id}).get(0);


	}

	public List<PropertyType> getPropertyTypes() {
		
		String sql="From PropertyType ";
		return (List<PropertyType>) getHibernateTemplate().find(sql);
		
	}

	public void insertPropertyType(PropertyType pt) {

		getHibernateTemplate().save(pt);

	}

	public void modifyPropertyType(PropertyType pt) {
		getHibernateTemplate().update(pt);

	}



}
