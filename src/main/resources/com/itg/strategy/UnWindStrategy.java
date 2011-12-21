package com.itg.strategy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.itg.dao.FilterItem;
import com.itg.dao.FilterItemOpt;
import com.itg.dao.HistoryResult;
import com.itg.dao.IMatchDAO;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.OddPredictPK;
import com.itg.dao.Property;
import com.itg.dao.WarningFilter;

public class UnWindStrategy extends BaseStrategy {

	private Float warningPct = new Float(90);
	public Float getWarningPct() {
		return warningPct;
	}

	public void setWarningPct(Float warningPct) {
		this.warningPct = warningPct;
	}

	public int getWarningCount() {
		return warningCount;
	}

	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}

	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}

	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}

	private int warningCount = 10;

	private IMatchDAO matchDAO;

	@Override
	public List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl) {

		List<OddPredict> result = new ArrayList<OddPredict>();
		
		List<Match> l = null;
		
		List<FilterItem> fl = new ArrayList<FilterItem>();
		fl.add(FilterItemOpt.makeFilter("numeric", "eq", "company",
				new String[] { String.valueOf(mm.getCompany()) }));
		fl.add(FilterItemOpt.makeFilter("numeric", "eq", "euro_final_win",
				new String[] { String.valueOf(mm.getEuro_final_win()) }));
		fl.add(FilterItemOpt.makeFilter("numeric", "eq",
				"euro_final_standoff", new String[] { String
						.valueOf(mm.getEuro_final_standoff()) }));
		fl.add(FilterItemOpt.makeFilter("numeric", "eq", "euro_final_loss",
				new String[] { String.valueOf(mm.getEuro_final_loss()) }));
		
		
		try {
			l = matchDAO.getMatchMouth(fl, 0, 10, null, null);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(l.size()<warningCount){
			return result;
		}
		int winCount=0, lossCount=0, ttl=0;
		Date before2Hours = new Date(new Date().getTime()-(120*60*1000));
		for(Match m2 : l){
			
			if((!m2.getDate().before(before2Hours))){
				continue;
			}
			if(m2.getWin_standoff_loss()==null){
				continue;
			}
			ttl++;
			if(m2.getWin_standoff_loss().equals("胜")){
				winCount++;
			}
			
			if(m2.getWin_standoff_loss().equals("负")){
				lossCount++;
			}
			
		}
		
		if(ttl<warningCount){
			return result;
		}
		String[] r = m.getScore().split(":");
		
		Integer home = 0;
		Integer away = 0;
		
		if(r.length==2){
			home = Integer.valueOf(r[0]);
			away = Integer.valueOf(r[1]);
		}
		
		if(((winCount/ttl*100.0f)>=warningPct)&&(home<away)){
			OddPredict op = createOddPredict(m, mm, null, "欧赔胜");
			op.setMatchCount(l.size());
			op.setPossibility(winCount/l.size()*1.0f);

			op.setRemark("客队先进球");
			result.add(op);
			
		}
		

		if(((lossCount/ttl*100.0f)>=warningPct)&&(home>away)){
			OddPredict op = createOddPredict(m, mm, null, "欧赔负");
			op.setMatchCount(l.size());
			op.setPossibility(winCount/l.size()*1.0f);

			op.setRemark("主队先进球");
			result.add(op);
			
		}

		
		return result;

	}

}
