package com.itg.dao;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserRolesDAO  extends HibernateDaoSupport implements IUserRolesDAO{
	public void insertUserRole(UserRole userRole) {
		
		getHibernateTemplate().saveOrUpdate(userRole);
	}
    public List<UserRole> findRolesByID(String userName) {
    	
        String sql="From UserRole where userName=?";
       
        List<UserRole> findByNamedQuery = getHibernateTemplate().find(sql, new Object[]{userName});
        
        return findByNamedQuery;
        
		
	}
}
