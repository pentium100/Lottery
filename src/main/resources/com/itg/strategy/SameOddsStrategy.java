package com.itg.strategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.itg.dao.FilterItem;
import com.itg.dao.FilterItemOpt;
import com.itg.dao.HistoryResult;
import com.itg.dao.IMatchDAO;
import com.itg.dao.IMatchMouthDAO;
import com.itg.dao.IPropertyDAO;
import com.itg.dao.Match;
import com.itg.dao.MatchDAO;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.OddPredictPK;
import com.itg.dao.Property;
import com.itg.dao.WarningFilter;

public class SameOddsStrategy extends BaseStrategy {

	private String remarks;

	private IMatchDAO matchDAO;
	private IMatchMouthDAO matchMouthDAO;
	private IPropertyDAO propertyDAO;

	public IPropertyDAO getPropertyDAO() {
		return propertyDAO;
	}

	public void setPropertyDAO(IPropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}

	public IMatchMouthDAO getMatchMouthDAO() {
		return matchMouthDAO;
	}

	public void setMatchMouthDAO(IMatchMouthDAO matchMouthDAO) {
		this.matchMouthDAO = matchMouthDAO;
	}

	@Override
	public List<OddPredict> predict(Match m, MatchMouth mm,
			List<HistoryResult> hl) {
		int oldWarningBeforeMatchTime = warningBeforeMatchTime;
		if ((new Date()).getHours() == 13) {
			warningBeforeMatchTime = 48 * 60;
		}
		List<OddPredict> l = super.predict(m, mm, hl);

		warningBeforeMatchTime = oldWarningBeforeMatchTime;
		return l;
	}

	@Override
	public List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl) {

		List<OddPredict> result = new ArrayList<OddPredict>();
		

		List<Match> l = null;
		if (mm.getCompany() == 33 && m.getLevel() <= 1) {
			
			
			
			List<FilterItem> fl = new ArrayList<FilterItem>();
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "company",
					new String[] { String.valueOf(33) }));
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "euro_final_win",
					new String[] { String.valueOf(mm.getEuro_final_win()) }));
			fl.add(FilterItemOpt.makeFilter(
					"numeric",
					"eq",
					"euro_final_standoff",
					new String[] { String.valueOf(mm.getEuro_final_standoff()) }));
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "euro_final_loss",
					new String[] { String.valueOf(mm.getEuro_final_loss()) }));
			fl.add(FilterItemOpt.makeFilter("numeric", "notEqual", "id2",
					new String[] { String.valueOf(m.getId2()) }));
			fl.add(FilterItemOpt.makeFilter("string", "is", "score",
					new String[] { String.valueOf("null") }));

			fl.add(FilterItemOpt.makeFilter("numeric", "le", "level",
					new String[] { String.valueOf("1") }));
			
			

			Property p = propertyDAO.findPropertyById(m.getChampionship());
			
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "country",
					new String[] { String.valueOf(p.getCountry().getId()) }));

			Date before = new Date(m.getDate().getTime() + (24 * 60 * 1000 * 60));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			fl.add(FilterItemOpt.makeFilter("datetime", "le", "date",
					new String[] { sdf.format(before) }));


			
			fl.add(FilterItemOpt.makeFilter("datetime", "ge", "date",
					new String[] { sdf.format(m.getDate()) }));

			try {
				l = matchDAO.getMatchMouth(fl, 0, 10, null, null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (l != null && l.size() > 0) {
				OddPredict op = createOddPredict(m, mm, null, "亚赔下");

				op.setMatchCount(0);
				op.setPossibility(0.00f);

				op.setRemark(getRemarks());
				result.add(op);

				for(Match m2:l){
				
				//Long restTime = m2.getDate().getTime() - (new Date()).getTime();
				//if (restTime > warningBeforeMatchTime * 1000 * 60) {

					MatchMouth mm2 = matchMouthDAO.findMatchMouthById(m2
							.getId());
					op = createOddPredict(m2, mm2, null, "亚赔下");

					op.setMatchCount(0);
					op.setPossibility(0.00f);

					op.setRemark(getRemarks());
					result.add(op);
					addToPromptList(mm2);
				//}

			}}
			

		}
		return result;

	}

	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}

	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
