package com.itg.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class PropertyDAO extends HibernateDaoSupport implements IPropertyDAO {

	
	
	public void deleteProperty(Property p) {
		getHibernateTemplate().delete(p);

	}

	public Property findPropertyById(Integer id) {
		
		String sql="From Property where id=? ";
		
		List l = getHibernateTemplate().find(sql, new Object[]{id});
		if(l.size()>0){
			return (Property) l.get(0);
		}
		else{
			return null;
		}


	}

	
	public List<Property> getProperties(int propertyType) {
		
		String sql="From Property where PropertyType_id=? order by text";
		
		if(propertyType==1){
			//sql="From Property where PropertyType_id=? and id in (49,50,53,55,57,64,1272,6662,6663,6664,6693,6703) order by text";
			sql="From Property where PropertyType_id=? and id in (49,50, 6455,64,52) order by text";
	
		}
		
		if (propertyType==6){
			//sql="From Property where PropertyType_id=? and id in (6454,6458,6462,6464,6466,6661,6692,6702) order by text";
			sql="From Property where PropertyType_id=? and id in (6454) order by text";
		}
		
		if (propertyType==2){
			sql="select distinct p From Property p, Match m where p.propertyType_id=? and (p.id = m.homeTeam  or p.id = m.awayTeam )"+			
		           //" and m.championship in (49,50,53,55,57,64,1272,6662,6663,6664,6693,6703)"+
		           " and m.championship in (49,50,6455,64,52)"+
					"order by text";
		}

		return (List<Property>) getHibernateTemplate().find(sql, new Object[]{propertyType});
		
	}

	public void insertProperty(Property p) {

		getHibernateTemplate().save(p);

	}

	public void modifyProperty(Property p) {
		getHibernateTemplate().update(p);

	}
	
	public void insertPropertyIfNoExists(Property p){
		
		

			getHibernateTemplate().saveOrUpdate(p);

	
		
	}

	public Property findPropertyByText(String text, int propertyType_id) {
		
		String sql="From Property where text=? and propertyType_id=? ";
		List l = getHibernateTemplate().find(sql, new Object[]{text, propertyType_id});
		
		
		if(l.size()>0){
			return (Property) l.get(0);
		}
		else{
			return null;
		}
		
	}
	
	
	public Property findPropertyById2(int id2, int propertyType_id) {
		
		String sql="From Property where id2=? and propertyType_id=? ";
		List l = getHibernateTemplate().find(sql, new Object[]{id2, propertyType_id});
		
		
		if(l.size()>0){
			return (Property) l.get(0);
		}
		else{
			return null;
		}
		
	}
	

	
	

}
