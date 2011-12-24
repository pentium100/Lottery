package com.itg.dao;

import java.util.List;


public interface IMatchMouthDAO {
	
	
	   public void insertMatchMouth(MatchMouth m);
	   public void modifyMatchMouth(MatchMouth m);
	   public void deleteMatchMouth(MatchMouth m);
	   public MatchMouth findMatchMouthByMatchId(Integer matchId, Integer company);
	   public MatchMouth findMatchMouthById(Integer matchMouthId);
	   public void setResult(MatchMouth mm, String score) ;
	   public Long getOddCounts(Integer matchId);
	   public void deleteUnuseMatchMouth();
	   public List<HistoryResult> checkMatchMouthHistory(MatchMouth mm, int championship, int country,  Integer inDays);
	   public List<MatchMouth> getMathcMouth(int start, int limit);
	   public int findMatchMouthIndexInList(List<MatchMouth> l, MatchMouth mm);
	   public List<MatchMouth> findMatchMouthByMatchId(int matchId2);
	   
	   
	   public void setMatchMouthInterval(MatchMouth mm) ;

}
