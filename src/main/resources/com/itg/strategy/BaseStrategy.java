package com.itg.strategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itg.dao.HistoryResult;
import com.itg.dao.IOddPredictDAO;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.OddPredictDAO;
import com.itg.dao.OddPredictPK;

public abstract class BaseStrategy implements IStrategy {

	private List<Map<MatchMouth, Date>> promptList = new ArrayList<Map<MatchMouth, Date>>();
	private String mailTo;
	private String ccTo;
	private IOddPredictDAO oddPredictDAO;
	private String strategyName;
	protected int warningBeforeMatchTime = 30;

	public int getWarningBeforeMatchTime() {
		return warningBeforeMatchTime;
	}

	public void setWarningBeforeMatchTime(int warningBeforeMatchTime) {
		this.warningBeforeMatchTime = warningBeforeMatchTime;
	}

	@Override
	public int matchMouthChanged(MatchMouth mm) {
		// TODO Auto-generated method stub

		oddPredictDAO.deleteByMatchMouth(mm);

		for (int i = 0; i < promptList.size(); i++) {

			for (MatchMouth o : promptList.get(i).keySet()) {

				if (o.getMatchMouthId() == (mm.getMatchMouthId())) {

					promptList.remove(i);

					return 1;

				}
			}

		}

		return 0;

	}

	protected int findMatchMouthIndexInList(MatchMouth mm) {

		for (int i = 0; i < promptList.size(); i++) {

			for (MatchMouth o : promptList.get(i).keySet()) {

				if (o.getMatchMouthId() == mm.getMatchMouthId()) {

					return i;
				}
			}

		}

		return -1;

	}

	protected OddPredict createOddPredict(Match m, MatchMouth mm,
			HistoryResult hr, String predict) {

		OddPredict op = new OddPredict();
		OddPredictPK pk = new OddPredictPK();
		pk.setPredict(predict);
		pk.setMatchMouthId(mm.getMatchMouthId());
		op.setPk(pk);
		op.setCompany(mm.getCompany());

		if (hr != null) {

			if (hr.getCountry() != 0) {
				op.getPk().setScope(2);
			} else

			if (hr.getChampionship() != 0) {
				op.getPk().setScope(3);
			} else {
				op.getPk().setScope(1);
			}
		} else {
			op.getPk().setScope(1);
		}

		op.setMatchId(mm.getMatchId());
		op.setDate(new Date());
		op.setScore(m.getScore());
		op.setStrategy(this);

		op.getPk().setStrategyName(getStrategyName());

		op.setAsia_final_bs_mouth(mm.getAsia_final_bs_mouth());
		op.setAsia_final_ud_mouth(mm.getAsia_final_ud_mouth());
		op.setAsia_final_big(mm.getAsia_final_big());
		op.setAsia_final_small(mm.getAsia_final_small());

		op.setAsia_final_up(mm.getAsia_final_up());
		op.setAsia_final_down(mm.getAsia_final_down());

		op.setEuro_final_win(mm.getEuro_final_win());
		op.setEuro_final_standoff(mm.getEuro_final_standoff());
		op.setEuro_final_loss(mm.getEuro_final_loss());

		return op;

	}

	protected void addToPromptList(MatchMouth mm) {

		if (findMatchMouthIndexInList(mm) == -1) {

			Map<MatchMouth, Date> r = new HashMap<MatchMouth, Date>();
			r.put(mm, new Date());
			promptList.add(r);

		}

	}

	protected abstract List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl);

	public List<OddPredict> predict(Match m, MatchMouth mm,
			List<HistoryResult> hl) {

		List<OddPredict> result = new ArrayList<OddPredict>();

		Long restTime = ((m.getDate().getTime()) - (new Date()).getTime());
		if (((restTime < warningBeforeMatchTime * 1000 * 60))
				&& (restTime > (-90 * 1000 * 60))) {

			if (((mm != null) && ((findMatchMouthIndexInList(mm) != -1)))) {
				return new ArrayList<OddPredict>();
			}
			String championList = ",49,50,53,55,57,64,1221,1272,1280,1955,6662,6663,6664,6693,6703,8312,";
			String championShip = "," + Integer.toString(m.getChampionship())
					+ ",";

			if (championList.contains(championShip)) {

				result = predict2(m, mm, hl);
			}

			if (result.size() > 0) {
				addToPromptList(mm);
			}
		}
		return result;

	}

	@Override
	public void removeExpiredMouth(int hours) {
		// TODO Auto-generated method stub

		Date now = new Date();
		Long diff;
		for (int i = promptList.size() - 1; i >= 0; i--) {

			for (MatchMouth o : promptList.get(i).keySet()) {
				diff = now.getTime() - promptList.get(i).get(o).getTime();
				if ((diff) >= 1000 * 60 * 60 * hours) {

					promptList.remove(i);
					break;

				}
			}

		}

	}

	@Override
	public String getCcTo() {
		// TODO Auto-generated method stub
		return ccTo;
	}

	@Override
	public String getMailTo() {
		// TODO Auto-generated method stub
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public void setCcTo(String ccTo) {
		this.ccTo = ccTo;
	}

	public IOddPredictDAO getOddPredictDAO() {
		return oddPredictDAO;
	}

	public void setOddPredictDAO(IOddPredictDAO oddPredictDAO) {
		this.oddPredictDAO = oddPredictDAO;
	}

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

}
