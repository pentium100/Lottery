package com.itg.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class OddPredictDAO extends HibernateDaoSupport implements IOddPredictDAO {
	
	@Override
	public void deleteOddPredict(OddPredict p) {

		getHibernateTemplate().delete(p);

	}

	@Override
	public void insertOddPredict(OddPredict p) {
		getHibernateTemplate().save(p);

	}

	@Override
	public void modifyOddPredict(OddPredict p) {
		getHibernateTemplate().saveOrUpdate(p);


	}

	public void deleteByMatchMouth(MatchMouth mm) {
		// TODO Auto-generated method stub
		String sql="from OddPredict where matchMouthId=? ";
		List<OddPredict> oddPredicts = getHibernateTemplate().find(sql, new Object[]{mm.getMatchMouthId()});
		getHibernateTemplate().deleteAll(oddPredicts);

	}
	
	public List<OddPredict> getOddPredicts(Date date, int inDays){
		
		
		String sql="from OddPredict where date>=? and date<=? order by date";
		
		
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		

		c1.setTime(date);
		c1.set(Calendar.HOUR_OF_DAY, 12);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.add(Calendar.DATE, -inDays);
		
		c2.setTime(date);
		c2.set(Calendar.HOUR_OF_DAY, 12);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		
		
		
		List<OddPredict> oddPredicts = getHibernateTemplate().find(sql, new Object[]{c1.getTime(), c2.getTime() });
		return oddPredicts;
		
		
	}

	@Override
	public List<String> findStrategyByMatchMouthId(int matchMouthId) {
		// TODO Auto-generated method stub
		
		String sql="select distinct o.pk.strategyName from OddPredict o where matchmouthid=:matchMouthId";
		Session s = getSession();
		
		Query q = s.createQuery(sql);
		q.setParameter("matchMouthId", new Integer(matchMouthId));
		List<String> oddPredicts = q.list();
		s.close();
		return oddPredicts;
		
		
		
	}

}
