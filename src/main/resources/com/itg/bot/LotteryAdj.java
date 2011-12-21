package com.itg.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.itg.dao.HistoryResult;
import com.itg.dao.IMatchDAO;
import com.itg.dao.IOddPredictDAO;
import com.itg.dao.IPropertyDAO;
import com.itg.dao.IMatchMouthDAO;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.Property;
import com.itg.strategy.IStrategy;

public class LotteryAdj {

	private String smtp = "mail.itg.com.cn";

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCopyto() {
		return copyto;
	}

	public void setCopyto(String copyto) {
		this.copyto = copyto;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String from;
	private String to;
	private String copyto;
	private String subject;
	private String username;
	private String password;

	private IPropertyDAO propertyDAO;
	private IMatchDAO matchDAO;
	private IMatchMouthDAO matchMouthDAO;
	private List<IStrategy> strategyList;
	private IOddPredictDAO oddPredictDAO;
	
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml", "Lottery-dao.xml",
						"Lottery-bot.xml" });

		// an ApplicationContext is also a BeanFactory (via inheritance)
		BeanFactory factory = context;

		LotteryAdj lbot = (LotteryAdj) context.getBean("lotteryAdj");

		lbot.updateAllResult();

	}

	private void updatePredict(MatchMouth mm, Match m) {

		List<HistoryResult> l = matchMouthDAO.checkMatchMouthHistory(mm, m
				.getChampionship(), 0, 365);
		List<HistoryResult> l2 = matchMouthDAO.checkMatchMouthHistory(mm, 0, 0,
				365);
		if (l2.size() > 0) {
			l.add(l2.get(0));
		}

		List<OddPredict> odds = new ArrayList<OddPredict>();
		for (IStrategy s : strategyList) {

			odds.addAll(s.predict(m, mm, l));

		}

		for (OddPredict op : odds) {

			// o.setProfit(mm.getBsWin(o.getPk().getPredict(), o
			// .getBsMouth(), o.getScore()));

			if (op.getScore() != null) {
				Property p;
				p = propertyDAO.findPropertyById(op.getAsia_final_bs_mouth());
				if (p != null) {
					op.setBsMouth(p.getText());
				}

				p = propertyDAO.findPropertyById(op.getAsia_final_ud_mouth());
				if (p != null) {
					op.setUdMouth(p.getText());
				}

				op.setProfit(-1.00);
				if (op.getPk().getPredict().contains("欧赔")) {
					op.setActual(mm.getWin_standoff_loss());
					// if (op.getPk().getPredict().substring(2, 3).equals(
					// mm.getWin_standoff_loss())) {
					// op.setProfit(mm.getEuroWin(op.getPk().getPredict()));
					op.setProfit(op.getEuroWin());
					// }
				}
				if (op.getPk().getPredict().contains("亚赔")) {
					op.setActual(mm.getUp_down());
					// if (op.getPk().getPredict().substring(2, 3).equals(
					// mm.getUp_down())) {
					// op.setProfit(mm.getUdWin(op.getPk().getPredict(), op
					// .getUdMouth(), op.getScore()));
					op.setProfit(op.getUdWin(op.getUdMouth()));
					// }

				}
				if (op.getPk().getPredict().contains("大小")) {

					op.setActual(mm.getBig_small());
					// if (op.getPk().getPredict().substring(2, 3).equals(
					// mm.getBig_small())) {
					// op.setProfit(mm.getBsWin(op.getPk().getPredict(), op
					// .getBsMouth(), op.getScore()));
					op.setProfit(op.getBsWin(op.getBsMouth()));
					// }

				}
			}

			oddPredictDAO.modifyOddPredict(op);
		}

	}

	
	private void setMatchMouthInterval(MatchMouth mm) {
		// TODO Auto-generated method stub

		Double level = mm.getEuro_early_loss();
		
		if(mm.getEuro_early_loss()==null||mm.getEuro_early_win()==null){
			return;
		}
		if (level > mm.getEuro_early_win()) {
			level = mm.getEuro_early_win();
		}
		String sql = "select * " + "  from euro_interval"
				+ "  where low <= :level" 
				+ "  order by low";
		

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("level", level);

		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, params);
		if (rs.first()) {
			mm.setWater_level(rs.getString("water_level"));
			mm.setInterval(rs.getString("interval"));
		}

	}

	
	private void updateAllResult() {

		List<MatchMouth> r;

		int start = 0;
		r = matchMouthDAO.getMathcMouth(start, 5000);
		Property p;
		while (r.size() > 0) {

			for (int i = 0; i < r.size(); i++) {
				MatchMouth mm = r.get(i);
				//
				//matchMouthDAO.lock(mm);
				setMatchMouthInterval(mm);
				matchMouthDAO.modifyMatchMouth(mm);

				Logger.getLogger(this.getClass()).info(start+i);

			}

			// updatePredict(r.get(i), m);
			start = start + r.size();
			r = matchMouthDAO.getMathcMouth(start - 1, 5000);


		}

	}

	public IPropertyDAO getPropertyDAO() {
		return propertyDAO;
	}

	public void setPropertyDAO(IPropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}

	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}

	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}

	public IMatchMouthDAO getMatchMouthDAO() {
		return matchMouthDAO;
	}

	public void setMatchMouthDAO(IMatchMouthDAO matchMouthDAO) {
		this.matchMouthDAO = matchMouthDAO;
	}

	public List<IStrategy> getStrategyList() {
		return strategyList;
	}

	public void setStrategyList(List<IStrategy> strategyList) {
		this.strategyList = strategyList;
	}

	public IOddPredictDAO getOddPredictDAO() {
		return oddPredictDAO;
	}

	public void setOddPredictDAO(IOddPredictDAO oddPredictDAO) {
		this.oddPredictDAO = oddPredictDAO;
	}

}
