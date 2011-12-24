package com.itg.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.LockMode;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class MatchMouthDAO extends HibernateDaoSupport implements
		IMatchMouthDAO {



	private IPropertyDAO propertyDAO;
	private IMatchDAO matchDAO;
	

	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}

	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}

	public IPropertyDAO getPropertyDAO() {
		return propertyDAO;
	}

	public Long getOddCounts(Integer matchId) {
		StringBuffer sql = new StringBuffer(
				"select count(*) From MatchMouth where matchId = :matchId");

		org.hibernate.Query q = getSession().createQuery(sql.toString());

		q.setParameter("matchId", matchId);
		List l = q.list();
		return (Long) l.get(0);

	}

	public void setPropertyDAO(IPropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}

	public void deleteMatchMouth(MatchMouth m) {

		getHibernateTemplate().delete(m);

	}

	public MatchMouth findMatchMouthByMatchId(Integer matchId, Integer company) {

		String sql = "From MatchMouth where matchID= ? and company = ?";
		List<MatchMouth> findByNamedQuery = getHibernateTemplate().find(sql,
				new Object[] { matchId, company });
		if (findByNamedQuery.size() > 0) {
			return findByNamedQuery.get(0);
		} else {
			return null;
		}

	}

	public void insertMatchMouth(MatchMouth m) {
		getHibernateTemplate().save(m);

	}

	public void modifyMatchMouth(MatchMouth m) {
		getHibernateTemplate().saveOrUpdate(m);

	}

	public void setResult(MatchMouth mm, String score) {

		if ((score == null) || (score.equals(""))) {

			mm.setWin_standoff_loss(null);
			mm.setUp_down(null);
			mm.setBig_small(null);

			return;
		}

		Pattern p = Pattern.compile("(\\d{1,2}):(\\d{1,2})");
		Matcher m = p.matcher(score);
		int score1 = 0, score2 = 0;
		if (m.find()) {
			score1 = Integer.parseInt(m.group(1));
			score2 = Integer.parseInt(m.group(2));
		}

		mm.setWin_standoff_loss(calcEuroOdd(mm, score1, score2));
		mm.setUp_down(calcAsiaOdd(mm, score1, score2));
		mm.setBig_small(calcBigSmallOdd(mm, score1, score2));
		mm.setAsia_win(calcAsiaWin(mm, score1, score2));

	}

	private String calcBigSmallOdd(MatchMouth mm, int score1, int score2) {

		if ((mm.getAsia_final_big() == null)
				|| (mm.getAsia_final_big() == 0.00)) {
			return null;
		}

		Property p;
		p = propertyDAO.findPropertyById(mm.getAsia_final_bs_mouth());
		if (null == p) {
			return null;
		}
		Double odd = 0.00;

		Pattern pattern = Pattern.compile("(.*)/");
		Matcher m = pattern.matcher(p.getText());
		boolean mode = m.find();
		if (mode) {
			odd = Double.parseDouble(m.group(1));

			if ((score1 + score2) > odd) {
				return "大";
			}
			if ((score1 + score2) <= odd) {
				return "小";
			}

		} else {
			odd = Double.parseDouble(p.getText());

			if ((score1 + score2) > odd) {
				return "大";
			}
			if ((score1 + score2) == odd) {
				return "平";
			}
			if ((score1 + score2) < odd) {
				return "小";
			}

		}

		return null;
	}

	private String calcAsiaWin(MatchMouth mm, int score1, int score2) {

		Property p;
		p = propertyDAO.findPropertyById(mm.getAsia_final_ud_mouth());
		if (null == p) {
			return null;
		}
		Pattern pattern = Pattern.compile("(-?\\d\\.\\d)/|/(-?\\d\\.\\d)");
		Matcher m = pattern.matcher(p.getText());
		Double odd = 0.00;
		boolean find = m.find();
		if (find) {
			if (m.group(1) == null) {
				odd = Double.parseDouble(m.group(2));
			} else {
				odd = Double.parseDouble(m.group(1));
			}
		} else {
			odd = Double.parseDouble(p.getText());
		}

		if ((score1 - score2) > odd) {
			return "主";
		}

		// if(((score1 - score2)<odd)||(find&&((score1 - score2)==odd))){
		if (((score1 - score2) < odd)) {
			return "客";
		}

		if ((score1 - score2) == odd) {
			return "平";
		}
		return null;

	}

	private String calcAsiaOdd(MatchMouth mm, int score1, int score2) {
		if ((mm.getAsia_final_ud_mouth() == null)) {
			return null;
		}

		Property p;
		p = propertyDAO.findPropertyById(mm.getAsia_final_ud_mouth());
		if (null == p) {
			return null;
		}
		if (p.getText().equals("0")) {
			if (score1 == score2) { // 结果平时,为平手
				return "平";
			}

			if (score1 > score2) { // 主队WIN时
				return "上";
			}

			if (score1 < score2) { // 客队WIN时
				return "下";

			}

		}
		
		
		if (p.getText().equals("+0")) {
			if (score1 == score2) { // 结果平时,为平手
				return "平";
			}

			if (score1 > score2) { // 主队WIN时
				return "上";
			}

			if (score1 < score2) { // 客队WIN时
				return "下";

			}

		}

		if (p.getText().equals("-0")) {
			if (score1 == score2) { // 结果平时,为平手
				return "平";
			}

			if (score1 > score2) { // 主队WIN时
				return "下";
			}

			if (score1 < score2) { // 客队WIN时
				return "上";

			}

		}


		Pattern pattern = Pattern.compile("(-?\\d\\.\\d)/|/(-?\\d\\.\\d)");
		Matcher m = pattern.matcher(p.getText());
		Double odd = 0.00;
		boolean find = m.find();
		if (find) {

			if (m.group(1) == null) {
				odd = Double.parseDouble(m.group(2));
			} else {
				odd = Double.parseDouble(m.group(1));
			}

		} else {
			odd = Double.parseDouble(p.getText());
		}

		if (odd < 0) {

			int temp;
			temp = score1;
			score1 = score2;
			score2 = temp;
			odd = Math.abs(odd);

		}
		if ((score1 - score2) > odd) {
			return "上";
		}
		if ((score1 - score2) < odd) {
			return "下";
		} else {
			return "平";
		}

	}

	private String calcEuroOdd(MatchMouth mm, int score1, int score2) {

		if ((mm.getEuro_final_win() == null) || (mm.getEuro_final_win() == 0)) { // 计算欧赔
			return null;
		}
		if (score1 > score2) {
			return "胜";
		}
		if (score1 == score2) {
			return "平";
		}
		if (score1 < score2) {
			return "负";
		}
		return null;

	}

	public MatchMouth findMatchMouthById(Integer matchMouthId) {
		String sql = "From MatchMouth where matchMouthId=?";
		List<MatchMouth> findByNamedQuery = getHibernateTemplate().find(sql,
				new Object[] { matchMouthId });
		if (findByNamedQuery.size() > 0) {
			return findByNamedQuery.get(0);
		} else {
			return null;
		}
	}

	public void deleteUnuseMatchMouth() {
		// TODO Auto-generated method stub

		// Property p = propertyDAO.findPropertyByText("威廉", 5) ;

		// String sql = "delete MatchMouth where ( asia_early_bs_mouth = 0 "
		// + " or asia_final_bs_mouth = 0 " + " or asia_early_ud_mouth = 0 "
		// + " or asia_final_ud_mouth = 0 " + " or euro_final_win is null "
		// + " or euro_early_win is null ) and company <> " + p.getId();

		// getHibernateTemplate().bulkUpdate(sql, new Object[] {});

		String sql = "delete MatchMouth from matches m "
				+ "where MatchMouth.matchid = m.id2"
				+ "  and m.date < getDate()-3" + "  and m.score is null";

		Map m = new HashMap();
		jdbcTemplate.update(sql, m);

	}

	private NamedParameterJdbcTemplate jdbcTemplate;

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<HistoryResult> checkMatchMouthHistory(final MatchMouth mm,
			int championship, int country, Integer inDays) {

		StringBuffer sql = new StringBuffer();

		Map<String, Object> params = new HashMap();

		sql.append("select	mm.company, ");
		if (championship == 0) // isAllChampionship = 0 表示计算所有的赛事.
		{

			sql.append("        championship = 0, ");
		} else {

			sql.append("        championship, ");

		}

		if (country == 0) // isAllChampionship = 0 表示计算所有的赛事.
		{

			sql.append("        country = 0, ");
		} else {

			sql.append("        p.country_id as country, ");

		}

		sql.append("        count(*) as totalMatches,");
		sql
				.append("		sum(case when mm.win_standoff_loss = '胜' then 1 else 0 end) as winCount,");
		sql
				.append("		sum(case when mm.win_standoff_loss = '平' then 1 else 0 end) as standoffCount,");
		sql
				.append("		sum(case when mm.win_standoff_loss = '负' then 1 else 0 end) as lossCount,");

		sql
				.append("		sum(case when mm.win_standoff_loss = '胜' then 1 else 0 end)*100.00/count(*) as winPercent,");
		sql
				.append("		sum(case when mm.win_standoff_loss = '平' then 1 else 0 end)*100.00/count(*) as standoffPercent,");
		sql
				.append("		sum(case when mm.win_standoff_loss = '负' then 1 else 0 end)*100.00/count(*) as lossPercent,");
		sql
				.append("		sum(case when mm.big_small = '大' and  (mm.asia_final_bs_mouth = :asia_final_bs_mouth  or mm.asia_final_bs_mouth is null) and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as bigCount,");

		// sql.append("  and mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth");
		// sql.append("  ");

		sql
				.append("		sum(case when mm.big_small = '平' and  (mm.asia_final_bs_mouth = :asia_final_bs_mouth or mm.asia_final_bs_mouth is null) and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as bsStandoffCount,");
		sql
				.append("		sum(case when mm.big_small = '小' and (mm.asia_final_bs_mouth = :asia_final_bs_mouth or mm.asia_final_bs_mouth is null) and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as smallCount,");

		// sql
		// .append("		sum(case when mm.big_small = '大' and  mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end)*100.00/sum(case when mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as bigPercent,");
		// sql
		// .append("		sum(case when mm.big_small = '平' and  mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end)*100.00/sum(case when mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as bsStandoffPercent,");
		// sql
		// .append("		sum(case when mm.big_small = '小' and  mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end)*100.00/sum(case when mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as smallPercent, ");

		sql
				.append("			sum(case when mm.up_down = '上' and  (mm.asia_final_bs_mouth = :asia_final_bs_mouth or mm.asia_final_bs_mouth is null) and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as upCount,");
		sql
				.append("			sum(case when mm.up_down = '平' and  (mm.asia_final_bs_mouth = :asia_final_bs_mouth or mm.asia_final_bs_mouth is null) and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as udstandoffCount,");
		sql
				.append("			sum(case when mm.up_down = '下' and  (mm.asia_final_bs_mouth = :asia_final_bs_mouth or mm.asia_final_bs_mouth is null) and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as downCount,");
		sql
				.append("            sum(case when (mm.asia_final_bs_mouth = :asia_final_bs_mouth  or mm.asia_final_bs_mouth is null) and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as mCount");
		// sql.append("			sum(case when mm.up_down = '上' and  mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end)*100.00/sum(case when mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as upPercent,");
		// sql.append("			sum(case when mm.up_down = '平' and  mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end)*100.00/sum(case when mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as udstandoffPercent,");
		// sql.append("			sum(case when mm.up_down = '下' and  mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end)*100.00/sum(case when mm.asia_final_bs_mouth = :asia_final_bs_mouth and mm.asia_final_ud_mouth = :asia_final_ud_mouth then 1 else 0 end) as downPercent");

		sql.append(" from matchMouth mm , matches m, property p ");
		sql.append(" where mm.matchid = m.id2 ");
		sql.append("  and p.id = m.championship ");
		sql.append("  and p.propertytype_id = 1 ");
		sql.append("  and mm.euro_final_win = :euro_final_win");
		sql.append("  and mm.euro_final_standoff = :euro_final_standoff");
		sql.append("  and mm.euro_final_loss = :euro_final_loss");
		sql.append("  and mm.company = :company");
		sql.append("  and mm.win_Standoff_Loss is not null");
		// sql.append("  and mm.big_small is not null");
		// sql.append("  and mm.up_down is not null");
		sql.append("  and m.date>=getDate()-:inDays ");
		sql.append("  and m.date<getDate()");

		if (championship != 0) {

			sql.append("  and championship = :championship");
			params.put("championship", championship);

		}

		if (country != 0) {

			sql.append("  and p.country_id = :country");
			params.put("country", country);

		}

		sql.append(" group by mm.company");

		if (championship != 0) {

			sql.append(" , m.championship ");

		}

		if (country != 0) {

			sql.append(" , p.country_id ");

		}

		// if( mm.getAsia_final_bs_mouth()==null||(mm.getCompany()==6259)){
		// System.out.println("find ");
		// }
		params.put("asia_final_bs_mouth",
				mm.getAsia_final_bs_mouth() != null ? mm
						.getAsia_final_bs_mouth() : 0);
		params.put("asia_final_ud_mouth",
				mm.getAsia_final_ud_mouth() != null ? mm
						.getAsia_final_ud_mouth() : 0);

		params.put("euro_final_win", mm.getEuro_final_win());
		params.put("euro_final_standoff", mm.getEuro_final_standoff());
		params.put("euro_final_loss", mm.getEuro_final_loss());
		params.put("company", mm.getCompany());
		params.put("inDays", inDays);

		ParameterizedRowMapper<HistoryResult> mapper = new ParameterizedRowMapper<HistoryResult>() {

			// notice the return type with respect to Java 5 covariant return
			// types
			public HistoryResult mapRow(ResultSet rs, int rowNum)
					throws SQLException {

				HistoryResult h = new HistoryResult();

				h.setCompany(rs.getInt("company"));
				h.setChampionship(rs.getInt("championship"));
				h.setCountry(rs.getInt("country"));

				h.setTotalMatches(rs.getInt("totalMatches"));
				h.setWinCount(rs.getInt("winCount"));
				h.setStandoffCount(rs.getInt("standoffCount"));
				h.setLossCount(rs.getInt("lossCount"));

				h.setBigCount(rs.getInt("bigCount"));
				h.setBsStandoffCount(rs.getInt("bsStandoffCount"));
				h.setSmallCount(rs.getInt("smallCount"));

				h.setUpCount(rs.getInt("upCount"));
				h.setUdStandoffCount(rs.getInt("udStandoffCount"));
				h.setDownCount(rs.getInt("downCount"));

				h.setWinPercent(rs.getFloat("winPercent"));
				h.setStandoffPercent(rs.getFloat("standoffPercent"));
				h.setLossPercent(rs.getFloat("lossPercent"));

				h.setBigPercent(rs.getInt("mCount") == 0 ? 0 : rs
						.getFloat("bigCount")
						/ rs.getFloat("mCount") * 100);
				h.setBsStandoffPercent(rs.getInt("mCount") == 0 ? 0 : rs
						.getFloat("bsStandoffCount")
						/ rs.getFloat("mCount") * 100);
				h.setSmallPercent(rs.getInt("mCount") == 0 ? 0 : rs
						.getFloat("smallCount")
						/ rs.getInt("mCount") * 100);

				h.setUpPercent(rs.getInt("mCount") == 0 ? 0 : rs
						.getFloat("upCount")
						/ rs.getInt("mCount") * 100);
				h.setUdStandoffPercent(rs.getInt("mCount") == 0 ? 0 : rs
						.getFloat("udStandoffCount")
						/ rs.getInt("mCount") * 100);
				h.setDownPercent(rs.getInt("mCount") == 0 ? 0 : rs
						.getFloat("downCount")
						/ rs.getInt("mCount") * 100);
				h.setTotalUbMatches(rs.getInt("mCount"));
				h.setMatchMouthId(mm.getMatchMouthId());
				h.setMatchId(mm.getMatchId());

				return h;
			}
		};

		List l = jdbcTemplate.query(sql.toString(), params, mapper);

		return l;

	}

	public List<MatchMouth> getMathcMouth(int start, int limit) {

		StringBuffer sql = new StringBuffer("From MatchMouth where 1=1 ");

		org.hibernate.Query q = getSession().createQuery(sql.toString());

		q.setFirstResult(start);
		q.setMaxResults(limit);

		// List<ReportMemo> findByNamedQuery = getHibernateTemplate().find(sql,
		// new Object[]{keyDate, keyValue});
		List<MatchMouth> findByNamedQuery = q.list();
		
		return (List<MatchMouth>) findByNamedQuery;

	}

	public int findMatchMouthIndexInList(List<MatchMouth> l, MatchMouth mm) {

		for (int i = 0; i < l.size(); i++) {

			if (l.get(i).getMatchMouthId() == mm.getMatchMouthId()) {

				return i;
			}

		}

		return -1;
	}
	
	
	public void setMatchMouthInterval(MatchMouth mm) {

		if (mm.getEuro_early_loss() == null || mm.getEuro_early_win() == null) {
			return;
		}

		Double level = mm.getEuro_early_loss();
		if (level > mm.getEuro_early_win()) {
			level = mm.getEuro_early_win();
		}
		// level = Math.floor(level * 100) / 100;
		String sql = "select * " + "  from euro_interval"
				+ "  where low <= :level" + "  order by low desc";

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("level", level);

		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, params);
		if (rs.first()) {
			mm.setWater_level(rs.getString("water_level"));
			mm.setInterval(rs.getString("interval"));
		}

	}	

	@Override
	public List<MatchMouth> findMatchMouthByMatchId(int matchId2) {

		String sql = "From MatchMouth where matchID= ?";
		List<MatchMouth> findByNamedQuery = getHibernateTemplate().find(sql,
				new Object[] { matchId2 });

		return findByNamedQuery;

	}

}
