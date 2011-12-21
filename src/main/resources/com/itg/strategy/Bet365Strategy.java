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

public class Bet365Strategy extends BaseStrategy {

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
		Date after3min = new Date((new Date()).getTime() + (3 * 1000 * 60));
		Date now = new Date();
		if(mm.getAsia_early_ud_mouth()==null||mm.getAsia_final_ud_mouth()==null||mm.getEuro_early_win()==null||mm.getEuro_final_win()==null){
			return result;
		}
		if ((mm.getCompany() == 43) && (m.getDate().before(after3min))) {

			if ((mm.getAsia_early_ud_mouth()
					.equals(mm.getAsia_final_ud_mouth()))
					&&(mm.getAsia_early_up().equals(mm.getAsia_final_up()))
					&&(mm.getAsia_early_down().equals(mm.getAsia_final_down()))
					&& (
					      (!mm.getEuro_early_win().equals(mm.getEuro_final_win()))
						||(!mm.getEuro_early_standoff().equals(mm.getEuro_final_standoff()))
						||(!mm.getEuro_early_loss().equals(mm.getEuro_final_loss())))
						) {

				OddPredict op = createOddPredict(m, mm, null, "欧赔胜");
				op.setMatchCount(0);
				op.setPossibility(0.00f);

				op.setRemark("BET365,亚赔不变,欧赔变");
				result.add(op);

			}
		}

		return result;

	}
}
