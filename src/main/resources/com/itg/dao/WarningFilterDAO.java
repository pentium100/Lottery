package com.itg.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class WarningFilterDAO extends org.springframework.orm.hibernate3.support.HibernateDaoSupport implements
		IWarningFilterDAO {

	public void deleteWarningFilter(WarningFilter w) {
		// TODO Auto-generated method stub
		
	}

	public List<WarningFilter> findWarningFilter(MatchMouth mm) {

		String sql = "From WarningFilter " +
				     "   where (company = ?  or company is null)" +
				     "     and (euro_final_win = ?  or euro_final_win is null)" +
				     "     and (euro_final_standoff = ?  or euro_final_standoff is null)" +
				     "     and (euro_final_loss = ?  or euro_final_loss is null)" +
				     "     and (asia_final_ud_mouth = ?  or asia_final_ud_mouth is null)" +
				     "     and (asia_final_bs_mouth = ?  or asia_final_bs_mouth is null)" ;
		
		List<WarningFilter> findByNamedQuery = getHibernateTemplate().find(sql,
				new Object[] {mm.getCompany(), mm.getEuro_final_win(), mm.getEuro_final_standoff(), mm.getEuro_final_loss(),
				              mm.getAsia_final_ud_mouth(), mm.getAsia_final_bs_mouth()});
		Logger logger = Logger.getLogger(this.getClass());
		logger.info("Warning Fitler Sql:"+sql);
		
		if(findByNamedQuery.size()>0){
			logger.info("find it!");
		}
		return findByNamedQuery; 

	}

	public void insertWarningFilter(WarningFilter w) {
		// TODO Auto-generated method stub
		
	}

	public void modifyWarningFilter(WarningFilter w) {
		// TODO Auto-generated method stub
		
	}



}
