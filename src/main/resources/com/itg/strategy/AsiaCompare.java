package com.itg.strategy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itg.dao.FilterItem;
import com.itg.dao.FilterItemOpt;
import com.itg.dao.HistoryResult;
import com.itg.dao.IMatchDAO;
import com.itg.dao.IMatchMouthDAO;
import com.itg.dao.IPropertyDAO;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.Property;

public class AsiaCompare extends BaseStrategy {

	// private int company1;
	// private int company2;
	//
	// private Double euro_final_win1;
	// private Double euro_final_win2;
	//
	// private Double euro_final_standoff1;
	// private Double euro_final_standoff2;
	//
	// private Double euro_final_loss1;
	// private Double euro_final_loss2;

	private IMatchDAO matchDAO;
	private IPropertyDAO propertyDAO;
	private IMatchMouthDAO matchMouthDAO;

	public IMatchMouthDAO getMatchMouthDAO() {
		return matchMouthDAO;
	}

	public void setMatchMouthDAO(IMatchMouthDAO matchMouthDAO) {
		this.matchMouthDAO = matchMouthDAO;
	}

	public List<OddPredict> predict_asiaMouth3(Match m, MatchMouth mm) {
		List<OddPredict> result = new ArrayList<OddPredict>();

		if (mm.getCompany() == 43 || mm.getCompany() == 38) {

			if ((mm.getAsia_final_up().equals(mm.getAsia_early_up()))
					&& mm.getAsia_early_up() >= 1.00d) {

				Property earlyMouth = propertyDAO.findPropertyById(mm
						.getAsia_early_ud_mouth());
				Property finalMouth = propertyDAO.findPropertyById(mm
						.getAsia_final_ud_mouth());

				if (earlyMouth.getId2() > finalMouth.getId2()) {
					OddPredict op = createOddPredict(m, mm, null, "亚赔上");

					op.setMatchCount(0);
					op.setPossibility(0.00f);

					op.setRemark("降盘水位不变");
					result.add(op);

				}

			}

		}

		return result;
	}

	public List<OddPredict> predict_asiaMouth2(Match m, MatchMouth mm) {

		List<OddPredict> result = new ArrayList<OddPredict>();
		// 2、365和易胜博主队最危险的是降盘略降水，尤其是初盘高水临场降为略低些的高水或中高水；统计的结果是打出客胜的次数达到80%以上

		if (mm.getCompany() == 43 || mm.getCompany() == 38) {

			if ((mm.getAsia_early_up() - mm.getAsia_final_up() <= 0.15d) // 终盘比初盘小,
																			// 小0.15以内.
					&& (mm.getAsia_early_up() > mm.getAsia_final_up()) // 终盘比初盘小.
					&& (mm.getAsia_early_up() > 1.0d)) {
				Property earlyMouth = propertyDAO.findPropertyById(mm
						.getAsia_early_ud_mouth());
				Property finalMouth = propertyDAO.findPropertyById(mm
						.getAsia_final_ud_mouth());

				if (earlyMouth.getId2() > finalMouth.getId2()) {
					OddPredict op = createOddPredict(m, mm, null, "亚赔下");

					op.setMatchCount(0);
					op.setPossibility(0.00f);

					op.setRemark("降盘略降水");
					result.add(op);

				}
			}

		}

		return result;
	}

	// 1、如果365终盘盘口比易胜博小半个盘时，365给出低水或超低水，那么上盘（主队）基本都能打出。
	public List<OddPredict> predict_asiaMouth1(Match m, MatchMouth mm) {

		List<OddPredict> result = new ArrayList<OddPredict>();
		// bet365 43, 易胜 38
		if (mm.getCompany() == 43 || mm.getCompany() == 38) {

			MatchMouth betMatchMouth = null;

			MatchMouth ewMatchMouth = null;

			if (mm.getCompany() == 43) {
				betMatchMouth = mm;
				ewMatchMouth = matchMouthDAO.findMatchMouthByMatchId(
						m.getId2(), 38);

			} else {
				ewMatchMouth = mm;
				betMatchMouth = matchMouthDAO.findMatchMouthByMatchId(
						m.getId2(), 43);

			}

			if (ewMatchMouth != null && betMatchMouth != null) {
				Property betAsiaUdMouth = propertyDAO
						.findPropertyById(betMatchMouth
								.getAsia_final_ud_mouth());
				Property ewAsiaUdMouth = propertyDAO
						.findPropertyById(ewMatchMouth.getAsia_final_ud_mouth());
				Double level = 0.00d;
				if ((betAsiaUdMouth.getId2() - ewAsiaUdMouth.getId2()) == -10) {
					level = betMatchMouth.getAsia_final_up();
					if ((level <= 0.85d)&&(!betAsiaUdMouth.getText().contains("-"))) {

						OddPredict op = createOddPredict(m, mm, null, "亚赔上");

						op.setMatchCount(0);
						op.setPossibility(0.00f);

						op.setRemark("365盘口小于易胜并低水");
						result.add(op);

					}

				}
			}

		}

		return result;
	}

	@Override
	public List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl) {

		List<OddPredict> result = new ArrayList<OddPredict>();

		if (m.getDate().before(new Date())) {

			result.addAll(predict_asiaMouth1(m, mm));
			result.addAll(predict_asiaMouth2(m, mm));
			result.addAll(predict_asiaMouth3(m, mm));

		}

		return result;

	}

	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}

	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}

	public IPropertyDAO getPropertyDAO() {
		return propertyDAO;
	}

	public void setPropertyDAO(IPropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}

}
