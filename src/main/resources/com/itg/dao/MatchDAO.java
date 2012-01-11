package com.itg.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class MatchDAO extends HibernateDaoSupport implements IMatchDAO {

	
	private IMatchMouthDAO matchMouthDAO;
	
	public IMatchMouthDAO getMatchMouthDAO() {
		return matchMouthDAO;
	}

	public void setMatchMouthDAO(IMatchMouthDAO matchMouthDAO) {
		this.matchMouthDAO = matchMouthDAO;
	}

	public Match findMatchById2(Integer id2) {
		
		String sql = "From Match where ID2=? ";
		List<Match> findByNamedQuery = getHibernateTemplate().find(sql,
				new Object[] { id2 });
		if (findByNamedQuery.size() > 0) {
			return findByNamedQuery.get(0);
		} else {
			return null;
		}
		
		
	}
	
	

	private NamedParameterJdbcTemplate jdbcTemplate;

	public void deleteMatch(Match m) {

		getHibernateTemplate().delete(m);

	}

	public Match findMatchById(Integer id) {

		String sql = "From Match where ID=? ";
		List<Match> findByNamedQuery = getHibernateTemplate().find(sql,
				new Object[] { id });
		if (findByNamedQuery.size() > 0) {
			return findByNamedQuery.get(0);
		} else {
			return null;
		}

	}

	public void insertMatch(Match m) {
		getHibernateTemplate().save(m);

	}

	public void modifyMatch(Match m) {
		getHibernateTemplate().saveOrUpdate(m);
		
		
		if((m.getScore()!=null)&&(!m.getScore().equals(""))){
			String sql = "From MatchMouth where matchId=? ";
			List<MatchMouth> l = getHibernateTemplate().find(sql,
				new Object[] { m.getId2() });
		
			for(int i=0;i<l.size();i++){
			
				matchMouthDAO.setResult(l.get(i), m.getScore());;
				matchMouthDAO.modifyMatchMouth(l.get(i));
			}
		}
		

	}

	@SuppressWarnings("unchecked")
	public List<Match> getMatchMouth(List<FilterItem>fl,  final int start, final int limit, String sort, String dir) throws ParseException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(" select ");
		sql.append("  mm.matchMouthId as id");  //�̿�ԭ��ID.
		sql.append(", m.id2 as id2");            //�������ID.
		sql.append(", m.date as date");
		sql.append(", m.championShip as championShip");
		sql.append(", mm.company as company");
		sql.append(", m.homeTeam as homeTeam");
		sql.append(", m.awayTeam as awayTeam");

		sql.append(", m.homeTeamPosition as homeTeamPosition");
		sql.append(", m.awayTeamPosition as awayTeamPosition");
		sql.append(", m.rankDiff as rankDiff");

		sql.append(", m.score as score");
		sql.append(", m.level as level");
		sql.append(", m.isInHome as isInHome");
		sql.append(", p.country_id as country");
		sql.append(", mm.offerTime as offerTime");
		sql.append(", mm.euro_final_win as euro_final_win");
		sql.append(", mm.euro_final_standoff as euro_final_standoff");
		sql.append(", mm.euro_final_loss as euro_final_loss");
		
		sql.append(", mm.euro_early_win as euro_early_win");
		sql.append(", mm.euro_early_standoff as euro_early_standoff");
		sql.append(", mm.euro_early_loss as euro_early_loss");


		sql.append(", mm.asia_final_up as asia_final_up");
		sql.append(", mm.asia_final_ud_mouth as asia_final_ud_mouth");
		sql.append(", mm.asia_final_down as asia_final_down");
		
		sql.append(", mm.asia_early_up as asia_early_up");
		sql.append(", mm.asia_early_ud_mouth as asia_early_ud_mouth");
		sql.append(", mm.asia_early_down as asia_early_down");
		
		sql.append(", mm.asia_final_big as asia_final_big");
		sql.append(", mm.asia_final_bs_mouth as asia_final_bs_mouth");
		sql.append(", mm.asia_final_small as asia_final_small");
		
		sql.append(", mm.asia_early_big as asia_early_big");
		sql.append(", mm.asia_early_bs_mouth as asia_early_bs_mouth");
		sql.append(", mm.asia_early_small as asia_early_small");
        sql.append(", mm.win_standoff_loss as win_standoff_loss");
        sql.append(", mm.up_down as up_down");
        sql.append(", mm.big_small as big_small");
        sql.append(", mm.asia_win as asia_win");
		
        sql.append(", mm.interval as interval");
        sql.append(", mm.water_level as water_level");
        
        sql.append(", mm.fen as fen");
        sql.append(", mm.pan as pan");
        
        sql.append(", m.home_power as home_power");
        sql.append(", m.away_power as away_power");
        sql.append(", m.remark as remark");
        
        
        sql.append(" from Matches m , MatchMouth mm, ");
		sql.append("      Property p, Property t1, Property t2  ");
		 
		sql.append(" where m.id2 = mm.matchId");
		sql.append(" and p.propertytype_id = 1 ");
		sql.append(" and t1.propertytype_id = 2 ");
		sql.append(" and t2.propertytype_id = 2 ");
		sql.append(" and m.hometeam = t1.id");
		sql.append(" and m.awayteam = t2.id");
		sql.append(" and p.id = m.championship ");
		//sql.append(" and p.id in (49,50,53,55,57,64,1272,6662,6663,6664,6693,6703)");
		sql.append(" and p.id in (49,50,6455)");
		
		StringBuffer con = new StringBuffer();
		for(int i = 0;i<fl.size();i++){
			con.append(" and " + fl.get(i).getSql());
		}
		
		String t = con.toString();
		t = t.replaceAll("date", "m.date");
		t = t.replaceAll("championship", "m.championship");
		t = t.replaceAll("company", "mm.company");
		
		t = t.replaceAll("euro_final_win", "mm.euro_final_win");
		t = t.replaceAll("euro_final_standoff", "mm.euro_final_standoff");
		t = t.replaceAll("euro_final_loss", "mm.euro_final_loss");

		t = t.replaceAll("euro_early_win", "mm.euro_early_win");
        t = t.replaceAll("euro_early_standoff", "mm.euro_early_standoff");
		t = t.replaceAll("euro_early_loss", "mm.euro_early_loss");
		
		t = t.replaceAll("asia_final_up", "mm.asia_final_up");
		t = t.replaceAll("asia_final_ud_mouth", "mm.asia_final_ud_mouth");
		t = t.replaceAll("asia_final_down", "mm.asia_final_down");

		t = t.replaceAll("asia_early_up", "mm.asia_early_up");
        t = t.replaceAll("asia_early_ud_mouth", "mm.asia_early_ud_mouth");
		t = t.replaceAll("asia_early_down", "mm.asia_early_down");

		t = t.replaceAll("asia_final_big", "mm.asia_final_big");
		t = t.replaceAll("asia_final_bs_mouth", "mm.asia_final_bs_mouth");
		t = t.replaceAll("asia_final_small", "mm.asia_final_small");

		t = t.replaceAll("asia_early_big", "mm.asia_early_big");
        t = t.replaceAll("asia_early_bs_mouth", "mm.asia_early_bs_mouth");
		t = t.replaceAll("asia_early_small", "mm.asia_early_small");


		t = t.replaceAll("win_standoff_loss", "mm.win_standoff_loss");
        t = t.replaceAll("up_down", "mm.up_down");
		t = t.replaceAll("big_small", "mm.big_small");
		t = t.replaceAll("asia_win", "mm.asia_win");
		
		t = t.replaceAll("country", "p.country_id");
		t = t.replaceAll("homeTeam", "t1.id");
		t = t.replaceAll("awayTeam", "t2.id");
		t = t.replaceAll("id2", "m.id2");
		t = t.replaceAll("rankDiff", "m.rankDiff");
		t = t.replaceAll("interval", "mm.interval");
		t = t.replaceAll("water_level", "mm.water_level");
		
		t = t.replaceAll("^fen$", "mm.fen");
		t = t.replaceAll("^pan$", "mm.pan");
	    //t.replaceAll(regex, replacement)
		
		

		t = t.replaceAll("home_power", "m.home_power");
		t = t.replaceAll("away_power", "m.away_power");
		t = t.replaceAll("remark", "m.remark");
		
		sql.append(t);

		
        if((sort==null)||sort.equals("")){
        	sql.append("   Order By date desc, m.id2, company  ");
        }
        else{
        	sql.append("   order by " + sort +" " +dir);
        	if(!sort.contains("id2")){
        		
        		sql.append(", m.id2");
        	}
        	
        	if (!sort.contains("company")){
        		sql.append(", company ");
        	}
        	
        	
        }
		
	    ParameterizedRowMapper<Match> mapper = new ParameterizedRowMapper<Match>() {
	        
	        // notice the return type with respect to Java 5 covariant return types
	        public Match mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	
	        	if(rowNum<start) return null;
	        	if(rowNum>start+limit) return null;
	            Match m = new Match();
	            m.setId(rs.getInt("id"));
	            m.setId2(rs.getInt("id2"));
	            m.setDate(rs.getTimestamp("date"));
	            m.setCompany(rs.getInt("company"));
	            m.setChampionship(rs.getInt("championShip"));
	            m.setLevel(rs.getInt("level"));
	            m.setCountry(rs.getInt("country"));
	            
	            m.setIsInHome(rs.getBoolean("isInHome"));
	            
	            m.setHomeTeam(rs.getInt("homeTeam"));
	            m.setAwayTeam(rs.getInt("awayTeam"));
	            m.setOfferTime(rs.getTimestamp("offerTime"));
	            m.setHomeTeamPosition(rs.getInt("homeTeamPosition"));
	            m.setAwayTeamPosition(rs.getInt("awayTeamPosition"));
	            m.setRankDiff(rs.getInt("rankDiff"));

	            m.setScore(rs.getString("score"));
				m.setEuro_final_win(rs.getDouble("euro_final_win"));
				m.setEuro_final_standoff(rs.getDouble("euro_final_standoff"));
				m.setEuro_final_loss(rs.getDouble("euro_final_loss"));
				
				m.setEuro_early_win(rs.getDouble("euro_early_win"));
				m.setEuro_early_standoff(rs.getDouble("euro_early_standoff"));
				m.setEuro_early_loss(rs.getDouble("euro_early_loss"));
				
				m.setAsia_final_up(rs.getDouble("asia_final_up"));
				m.setAsia_final_ud_mouth(rs.getInt("asia_final_ud_mouth"));
				m.setAsia_final_down(rs.getDouble("asia_final_down"));
				
				m.setAsia_early_up(rs.getDouble("asia_early_up"));
				m.setAsia_early_ud_mouth(rs.getInt("asia_early_ud_mouth"));
				m.setAsia_early_down(rs.getDouble("asia_early_down"));

				m.setAsia_final_big(rs.getDouble("asia_final_big"));
				m.setAsia_final_bs_mouth(rs.getInt("asia_final_bs_mouth"));
				m.setAsia_final_small(rs.getDouble("asia_final_small"));
				
				m.setAsia_early_big(rs.getDouble("asia_early_big"));
				m.setAsia_early_bs_mouth(rs.getInt("asia_early_bs_mouth"));
				m.setAsia_early_small(rs.getDouble("asia_early_small"));

				m.setWin_standoff_loss(rs.getString("win_standoff_loss"));
				m.setBig_small(rs.getString("big_small"));
				m.setUp_down(rs.getString("up_down"));
				m.setAsia_win(rs.getString("asia_win"));
	            	            
	            m.setInterval(rs.getString("interval"));
	            m.setWater_level(rs.getString("water_level"));
	            
	            m.setFen(rs.getString("fen"));
	            m.setPan(rs.getString("pan"));
	            
	            m.setHome_power(rs.getString("home_power"));
	            m.setAway_power(rs.getString("away_power"));
	            m.setRemark(rs.getString("remark"));
	            
	            return m;
	        }
	    };


		List<Match> l = jdbcTemplate.query(sql.toString(), params, mapper);
		List<Match> l2 = new ArrayList<Match>();
		for(int i=0;i<limit&&(start+i)<l.size();i++){
			l2.add(l.get(start+i));
		}
		


		return l2;
		

		
	}
	
	public List<Match> getMatches(Date fromDate, Date toDate, int company,
			int championShip, int start, int limit) {

		StringBuffer sql = new StringBuffer("From Match where 1=1 ");
		if (fromDate != null) {
			sql.append("and date>=:fromDate");

		}
		if (toDate != null) {
			sql.append("and date<=:toDate");

		}

		if (company != 0) {
			sql.append("and company = :company");
		}
		if (championShip != 0) {
			sql.append("and championShip = :championShip ");
		}

		sql.append("Order By date desc");

		org.hibernate.Query q = getSession().createQuery(sql.toString());
		if (fromDate != null) {
			q.setParameter("fromDate", fromDate);

		}
		if (toDate != null) {
			q.setParameter("toDate", toDate);

		}

		if (company != 0) {
			q.setParameter("company", company);
		}
		if (championShip != 0) {
			q.setParameter("championShip", championShip);
		}

		q.setFirstResult(start);
		q.setMaxResults(limit);

		// List<ReportMemo> findByNamedQuery = getHibernateTemplate().find(sql,
		// new Object[]{keyDate, keyValue});
		List<Match> findByNamedQuery = q.list();
		return (List<Match>) findByNamedQuery;

	}
	public Long getMatchMouthCount(List<FilterItem>fl) throws ParseException {
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//SqlParameterSource params = new SqlParameterSource();
		
		StringBuffer sql = new StringBuffer(" select count(0)");
		
		sql.append(" from Matches m , MatchMouth mm, ");
		sql.append("      Property p  ");
		 
		sql.append(" where m.id2 = mm.matchId");
		sql.append("  and p.propertytype_id = 1 ");
		sql.append("  and p.id = m.championship ");
		//sql.append("  and p.id in (49,50,53,55,57,64,1272,6662,6663,6664,6693,6703)");
		sql.append("  and p.id in (49,50,6455)");
		 

		StringBuffer con = new StringBuffer();
		for(int i = 0;i<fl.size();i++){
			con.append(" and " + fl.get(i).getSql());
		}
		
		String t = con.toString();
		t = t.replaceAll("date", "m.date");
		t = t.replaceAll("championship", "m.championship");
		t = t.replaceAll("company", "mm.company");
		
		t = t.replaceAll("euro_final_win", "mm.euro_final_win");
		t = t.replaceAll("euro_final_standoff", "mm.euro_final_standoff");
		t = t.replaceAll("euro_final_loss", "mm.euro_final_loss");

		t = t.replaceAll("euro_early_win", "mm.euro_early_win");
        t = t.replaceAll("euro_early_standoff", "mm.euro_early_standoff");
		t = t.replaceAll("euro_early_loss", "mm.euro_early_loss");
		
		t = t.replaceAll("asia_final_up", "mm.asia_final_up");
		t = t.replaceAll("asia_final_ud_mouth", "mm.asia_final_ud_mouth");
		t = t.replaceAll("asia_final_down", "mm.asia_final_down");

		t = t.replaceAll("asia_early_up", "mm.asia_early_up");
        t = t.replaceAll("asia_early_ud_mouth", "mm.asia_early_ud_mouth");
		t = t.replaceAll("asia_early_down", "mm.asia_early_down");

		t = t.replaceAll("asia_final_big", "mm.asia_final_big");
		t = t.replaceAll("asia_final_bs_mouth", "mm.asia_final_bs_mouth");
		t = t.replaceAll("asia_final_small", "mm.asia_final_small");

		t = t.replaceAll("asia_early_big", "mm.asia_early_big");
        t = t.replaceAll("asia_early_bs_mouth", "mm.asia_early_bs_mouth");
		t = t.replaceAll("asia_early_small", "mm.asia_early_small");


		t = t.replaceAll("win_standoff_loss", "mm.win_standoff_loss");
        t = t.replaceAll("up_down", "mm.up_down");
		t = t.replaceAll("big_small", "mm.big_small");
		t = t.replaceAll("country", "p.country_id");
		t = t.replaceAll("id2", "m.id2");
		t = t.replaceAll("rankDiff", "m.rankDiff");
		
		t = t.replaceAll("interval", "mm.interval");
		t = t.replaceAll("water_level", "mm.water_level");
		t = t.replaceAll("home_power", "m.home_power");
		t = t.replaceAll("away_power", "m.away_power");
		t = t.replaceAll("remark", "m.remark");
		
		
		
		sql.append(t);


	    return jdbcTemplate.queryForLong(sql.toString(), params);
		
		
	}

	public Long getMatchesCount(Date fromDate, Date toDate, int company,
			int championShip) {

		StringBuffer sql = new StringBuffer(
				"select count(*) From Match where 1=1");

		if (fromDate != null) {
			sql.append("and date>=:fromDate");

		}
		if (toDate != null) {
			sql.append("and date<=:toDate");

		}

		if (company != 0) {
			sql.append("and company = :company");
		}
		if (championShip != 0) {
			sql.append("and championShip = :championShip ");
		}

		org.hibernate.Query q = getSession().createQuery(sql.toString());
		if (fromDate != null) {
			q.setParameter("fromDate", fromDate);

		}
		if (toDate != null) {
			q.setParameter("toDate", toDate);

		}

		if (company != 0) {
			q.setParameter("company", company);
		}
		if (championShip != 0) {
			q.setParameter("championShip", championShip);
		}
		List<?> l = q.list();
		return (Long) l.get(0);

	}

	public List<?> analyzeEuroMouthMix(Date fromDate, Date toDate, int company,
			double euro_mouth, int asia_big_small_mouth) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();

		Map<String, Object> params = new HashMap<String, Object>();

		Calendar c = Calendar.getInstance();
		c.setTime(toDate);
		c.add(Calendar.DATE, 1);
		toDate = c.getTime();
		params.put("euro_mouth", euro_mouth);
		params.put("asia_big_small_mouth", asia_big_small_mouth);
		params.put("company", company);
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);

		sql.append("select case when mm.euro_final_win  = " + euro_mouth
				+ " then '" + euro_mouth + "左"
				+ "               when mm.euro_final_loss = " + euro_mouth
				+ " then '" + euro_mouth + "右"
				+ "               when mm.euro_early_win  = " + euro_mouth
				+ " then '" + euro_mouth + "左"
				+ "               when mm.euro_early_loss = " + euro_mouth
				+ " then '" + euro_mouth + "右" + "               else '' "
				+ "          end as euro_mouth ");

		sql.append(", mm.asia_final_ud_mouth");
		sql
				.append(", sum(case when mm.big_small = '大' then 1 else 0 end ) as big_count");
		sql
				.append(", sum(case when mm.big_small = '平' then 1 else 0 end ) as standoff_count");
		sql
				.append(", sum(case when mm.big_small = '小' then 1 else 0 end ) as small_count");
		sql.append(", count(*) as  total_count ");

		sql
				.append(", (sum(case when mm.big_small = '大' then 1 else 0 end )/count(*))*100.00 as big_pct");
		sql
				.append(", (sum(case when mm.big_small = '平' then 1 else 0 end )/count(*))*100.00 as standoff_pct");
		sql
				.append(", (sum(case when mm.big_small = '小' then 1 else 0 end )/count(*))*100.00 as small_pct");

		sql.append(" from Matches m, MatchMouth mm");
		sql.append(" where M.id2 = mm.MatchId and m.date>=:fromDate and m.date<=:toDate ");
		sql.append("  and mm.company = :company");
		sql
				.append("  and (mm.euro_final_win =:euro_mouth or mm.euro_final_loss =:euro_mouth ");
		sql
				.append("         or mm.euro_early_win=:euro_mouth or mm.euro_early_loss = :euro_mouth)");
		sql.append("  and mm.asia_final_bs_mouth=:asia_big_small_mouth");
		sql.append(" group by case when mm.euro_final_win  = " + euro_mouth
				+ " then '" + euro_mouth + "左"
				+ "               when mm.euro_final_loss = " + euro_mouth
				+ " then '" + euro_mouth + "右"
				+ "               when mm.euro_early_win  = " + euro_mouth
				+ " then '" + euro_mouth + "左"
				+ "               when mm.euro_early_loss = " + euro_mouth
				+ " then '" + euro_mouth + "右" + "               else '' "
				+ "             end ");

		sql.append("         ,mm.asia_final_ud_mouth");
		List<?> l = jdbcTemplate.queryForList(sql.toString(), params);
		System.out.println(sql.toString());

		return l;

	}

	public List<?> test(Date fromDate, Date toDate, int company,
			double euro_mouth, int asia_big_small_mouth) {

		StringBuffer sql = new StringBuffer();
		sql.append("select case when match0_.euro_final_win=3 then '3.0��'");
		sql.append("when match0_.euro_final_loss=3 then '3.0��' ");
		sql.append("when match0_.euro_early_win=3 then '3.0��'  ");
		sql.append("when match0_.euro_early_loss=3 then '3.0��' ");
		sql.append("else '' end as col_0_0_,  ");
		sql.append("match0_.asia_final_ud_mouth as col_1_0_,  ");
		sql
				.append("sum(case when match0_.big_small='��' then 1 else 0 end) as col_2_0_,  ");
		sql
				.append("sum(case when match0_.big_small='ƽ' then 1 else 0 end) as col_3_0_,  ");
		sql
				.append("sum(case when match0_.big_small='С' then 1 else 0 end) as col_4_0_, ");
		sql.append("count(*) as col_5_0_  ");
		sql.append("from Matches match0_  ");
		sql
				.append("where match0_.date>='1900/1/1' and match0_.date<='2090/1/1' ");
		sql.append("and match0_.company=11 and (match0_.euro_final_win=3 ");
		sql
				.append("or match0_.euro_final_loss=3 or match0_.euro_early_win=3 or match0_.euro_early_loss=3) ");
		sql.append("and match0_.asia_final_bs_mouth=15  ");

		sql.append("group by case when match0_.euro_final_win=3 then '3.0��' ");
		sql.append("when match0_.euro_final_loss=3 then '3.0��'  ");
		sql.append("when match0_.euro_early_win=3 then '3.0��'  ");
		sql.append("when match0_.euro_early_loss=3 then '3.0��' ");
		sql.append("else '' end  , match0_.asia_final_ud_mouth ");
		return null;

	}

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<?> analyzeEuroMouth(Date fromDate, Date toDate, int company,
			double euro_mouth) {
		StringBuffer sql = new StringBuffer();

		Map<String, Object> params = new HashMap<String, Object>();

		Calendar c = Calendar.getInstance();
		c.setTime(toDate);
		c.add(Calendar.DATE, 1);
		toDate = c.getTime();
		
		params.put("euro_mouth", euro_mouth);
		params.put("company", company);
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);

		sql.append("select mm.euro_final_loss");
		sql.append("  	  ,mm.euro_final_win"
						+ "         ,sum(case when mm.win_standoff_loss='胜' then 1 else 0 end ) as win_count"
						+ "         ,sum(case when mm.win_standoff_loss='平' then 1 else 0 end ) as standoff_count"
						+ "         ,sum(case when mm.win_standoff_loss='负' then 1 else 0 end ) as loss_count");
		sql.append("      ,count(*) as total_count");
		sql.append("      ,(sum(case when mm.win_standoff_loss='胜' then 1 else 0 end )/count(*))*100 as win_pct");
		sql.append("      ,(sum(case when mm.win_standoff_loss='平' then 1 else 0 end )/count(*))*100 as standoff_pct");
		sql.append("      ,(sum(case when mm.win_standoff_loss='负' then 1 else 0 end )/count(*))*100 as loss_pct");
		sql.append(" from matches m, MatchMouth mm ");
		
		sql.append(" where m.id2 = mm.matchid ");
		sql.append("   and mm.euro_final_loss = :euro_mouth ");
		sql.append("   and mm.euro_final_win <= :euro_mouth ");
		sql.append("   and m.date>=:fromDate and m.date<=:toDate ");
		sql.append("   and mm.company =:company ");
		sql.append(" group by mm.euro_final_loss, mm.euro_final_win ");

		List<?> l = jdbcTemplate.queryForList(sql.toString(), params);

		return l;
	}

	@Override
	public List<Match> getMatchMouthByCondition(int company,
			Double euroWinFrom, Double euroWinTo, Double euroStandFrom,
			Double euroStandTo, Double euroLossFrom, Double euroLossTo,
			String[] matchMouthIdList) throws ParseException {
		// TODO Auto-generated method stub
		List<FilterItem> fl = new ArrayList<FilterItem>();
		
		

        fl.add(FilterItemOpt.makeFilter("numeric", "eq", "company", new String[]{String.valueOf(company)}));

		fl.add(FilterItemOpt.makeFilter("numeric", "ge", "euro_final_win", new String[]{String.valueOf(euroWinFrom)}));
		fl.add(FilterItemOpt.makeFilter("numeric", "le", "euro_final_win", new String[]{String.valueOf(euroWinTo)}));
		
		fl.add(FilterItemOpt.makeFilter("numeric", "ge", "euro_final_standoff", new String[]{String.valueOf(euroStandFrom)}));
		fl.add(FilterItemOpt.makeFilter("numeric", "le", "euro_final_standoff", new String[]{String.valueOf(euroStandTo)}));
		
		fl.add(FilterItemOpt.makeFilter("numeric", "ge", "euro_final_loss", new String[]{String.valueOf(euroLossFrom)}));
		fl.add(FilterItemOpt.makeFilter("numeric", "le", "euro_final_loss", new String[]{String.valueOf(euroLossTo)}));
		
		//fl.add(FilterItemOpt.makeFilter("numeric", "eq", "asia_final_ud_mouth", new String[]{String.valueOf(asiaMouth)}));
		
		if(matchMouthIdList!=null&&matchMouthIdList.length > 0){
			fl.add(FilterItemOpt.makeFilter("list", "in", "id2", matchMouthIdList));
		}
		
		
		return getMatchMouth(fl,  0, 10000, "", "");
		

	}

		
	

}
