package com.itg.dao;

import java.util.List;


import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class MenuItemDAO extends HibernateDaoSupport implements IMenuItemDAO {

	public void insertMenuItem(MenuItem menuItem) {
		
		
		getHibernateTemplate().saveOrUpdate(menuItem);


	}
	
	@SuppressWarnings("unchecked")
	public List<MenuItem> selectMenuItem(MenuItem parentMenuItem) {
		
        String sql="From MenuItem where parentID=? Order By ID";

        //int parentID = 0;
        Integer parentID = Integer.valueOf(0);
        
        if (parentMenuItem!=null )
        {
        	parentID = Integer.valueOf(parentMenuItem.getID());
        }
        
        List<MenuItem> findByNamedQuery = getHibernateTemplate().find(sql, new Object[]{parentID});
        
        return findByNamedQuery;
	}

	public List<MenuItem> selectMenuItem(String userName,
			MenuItem parentMenuItem) {
		// TODO Auto-generated method stub
		return null;
	}

	public MenuItem selectMenuItemByID(Integer ID) {
		// TODO Auto-generated method stub
		
		String sql="From MenuItem where ID=? Order By ID";
		List<MenuItem> findByNamedQuery = getHibernateTemplate().find(sql, new Object[]{ID});
		if (findByNamedQuery.size() > 0){
			return findByNamedQuery.get(0);
		}
		else{
			return null;
		}
	}

}
