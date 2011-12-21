package com.itg.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HistoryResultDAO extends HibernateDaoSupport implements
		IHistoryResultDAO {

	private IMatchMouthDAO matchMouthDAO;
	private IMatchDAO matchDAO;

	public void deleteHistoryResult(HistoryResult h) {
		// TODO Auto-generated method stub
		getHibernateTemplate().delete(h);

	}

	public List<HistoryResult> findHistoryResult(Integer matchMouthId,
			Date reportFrom, Date reportTo) {
		String sql = "From HistoryResult where matchMouthId= ? and reportDate>=? and reportDate<=?";
		List<HistoryResult> findByNamedQuery = getHibernateTemplate().find(sql,
				new Object[] { matchMouthId, reportFrom, reportTo });

		return findByNamedQuery;

	}

	public List<HistoryResult> findHistoryResultByMatchId(Integer matchId,
			Integer companyId) {
		
		String sql = null ;
		Object[] paramList = null;
		if (companyId != -1) {
			sql = "From HistoryResult where MatchId= ?  and company = ?";
			paramList = new Object[] { matchId, companyId };
		}
		
		if (companyId == -1) {
			sql = "From HistoryResult where MatchId= ? ";
			paramList = new Object[] { matchId };
		}
			
			
		
		
		List<HistoryResult> findByNamedQuery = getHibernateTemplate().find(sql, paramList);

		return findByNamedQuery;
	}

	public HistoryResult findHistoryResultById(Integer Id) {
		// TODO Auto-generated method stub

		String sql = "From HistoryResult where reportId= ? ";
		List<HistoryResult> findByNamedQuery = getHibernateTemplate().find(sql,
				new Object[] { Id });
		if (findByNamedQuery.size() > 0) {
			return findByNamedQuery.get(0);
		} else {
			return null;
		}

	}

	public void insertHistoryResult(HistoryResult h) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(h);

	}

	public void modifyHistoryResult(HistoryResult h) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(h);

	}

	@SuppressWarnings("unchecked")
	public List<HistoryResult> findHistoryResult(Date date, int summaryHistoryDays) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		

		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		if (c.get(Calendar.HOUR_OF_DAY) <= 12) {

		} else {
			c.add(Calendar.DATE, 1);

		}
		c1.setTime(c.getTime());
		
		c1.add(Calendar.DATE, -summaryHistoryDays);
		

		// String sql = "From HistoryResult where"
		// + " convert(float, reportTime-'" + f.format(c.getTime())
		// + "')  >= -0.5 " + " AND convert(float, reportTime-'"
		// + f.format(c.getTime()) + "')  <= 0.5 " + " AND sent = 0";

		String sql = "From HistoryResult where"
				+ " convert(float, reportTime-'" + f.format(c.getTime())
				+ "')  <= 0.5 "
				+ " AND convert(float, reportTime-'" + f.format(c1.getTime())
				+ "')  >= 0.5 "
				
				+ " AND totalUbMatches is not null ";
		

		List<HistoryResult> findByNamedQuery = getHibernateTemplate().find(sql);

		return findByNamedQuery;

	}

//	@Override
//	public String getPrompt(HistoryResult h, String mouth, Boolean inFilter) {
//
//		int asiaMatchCount = 10;
//		int euroMatchCount = 20;
//		Float asiaWarningLevel = new Float(70);
//		Float euroWarningLevel = new Float(120);
//		MatchMouth mm = matchMouthDAO.findMatchMouthById(h.getMatchMouthId());
//		Match m = matchDAO.findMatchById(mm.getMatchId());
//		String prompt = "";
//		if (mouth.equals("欧赔胜")) {
//			if (((h.getWinPercent() * mm.getEuro_final_win() >= euroWarningLevel) && ((h
//					.getTotalMatches() >= euroMatchCount)))
//					|| inFilter) {
//
//				// if(mm.getWin_standoff_loss()!=null&&mm.getWin_standoff_loss().equals("胜")){
//				// correctPd++;
//				// }
//				prompt += "<td>%1$tY-%1$tm-%1$te %1$tH:%1$tM</td><td>%2$s</td><td>(%3$s)</td><td>(%4$s)</td><td>(%5$s)</td>"
//						+ "<td>"
//						+ m.getScore()
//						+ "</td>" // 比分
//						+ "<td>"
//						+ mm.getWin_standoff_loss()
//						+ "</td>" // 结果
//
//						+ "<td>"
//						+ h.getTotalMatches().toString()
//						+ "</td>"
//						+ "<td>%15$s</td>" // 统计赛事
//						+ "<td>欧赔胜</td>"
//
//						+ "<td>" + String.format("%5.2f", h.getWinPercent());
//
//			}
//
//		}
//		prompt += "%%</td>"
//				+ "<td>%6$6.3f</td><td>%7$6.3f</td><td>%8$6.3f</td>\n\r"
//				+ "<td>%9$6.3f</td><td>%10$s</td><td>%11$6.3f</td>\n\r"
//				+ "<td>%12$6.3f</td><td>%13$s</td><td>%14$6.3f</td>\n\r";
//
//		prompt = "<tr>" + prompt + "</tr>";
//
//		return prompt;
//
//	}

	public IMatchMouthDAO getMatchMouthDAO() {
		return matchMouthDAO;
	}

	public void setMatchMouthDAO(IMatchMouthDAO matchMouthDAO) {
		this.matchMouthDAO = matchMouthDAO;
	}

	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}

	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}

}
