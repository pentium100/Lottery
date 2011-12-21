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

public class SameOdds30Strategy extends BaseStrategy {

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
			
			
			
			if ((mm.getEuro_final_win().equals(mm.getEuro_final_loss()))){
				OddPredict op = createOddPredict(m, mm, null, "亚赔中");

				op.setMatchCount(0);
				op.setPossibility(0.00f);

				op.setRemark("主客队赔率相同");
				result.add(op);

			}

			
			

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
