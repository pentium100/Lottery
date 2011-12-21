package com.itg.dao;

import java.util.Date;
import java.util.List;


public interface IHistoryResultDAO {
	
	

	   public void insertHistoryResult(HistoryResult h);
	   public void modifyHistoryResult(HistoryResult h);
	   public void deleteHistoryResult(HistoryResult h);
	   public HistoryResult findHistoryResultById(Integer Id);
	   public List<HistoryResult> findHistoryResultByMatchId(Integer matchId, Integer companyId);
	   public List<HistoryResult> findHistoryResult(Integer matchMouthId, Date reportFrom, Date reportTo);
	   public List<HistoryResult> findHistoryResult(Date date, int summaryHistoryDays);
	   //public String getPrompt(HistoryResult h , String mouth, Boolean inFilter);
	   
	   
	   

}
