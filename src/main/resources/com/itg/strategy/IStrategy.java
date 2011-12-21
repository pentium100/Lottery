package com.itg.strategy;

import java.util.List;

import com.itg.dao.HistoryResult;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;

public interface IStrategy {

	List<OddPredict> predict(Match m, MatchMouth mm, List<HistoryResult> hl);
	int matchMouthChanged(MatchMouth mm);
	
	void removeExpiredMouth(int hours);
	String getMailTo();
	String getCcTo();
	String getStrategyName();
}
