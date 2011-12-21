package com.itg.dao;

import java.util.Date;
import java.util.List;


public interface IOddPredictDAO {

	   public void insertOddPredict(OddPredict p);
	   public void modifyOddPredict(OddPredict p);
	   public void deleteOddPredict(OddPredict p);
	   public void deleteByMatchMouth(MatchMouth mm);
	   public List<OddPredict> getOddPredicts(Date date, int inDays);
	   public List<String> findStrategyByMatchMouthId(int matchMouthId);
	
	
	
}
