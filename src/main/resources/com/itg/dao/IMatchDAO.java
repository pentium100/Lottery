package com.itg.dao;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IMatchDAO {
	
	
	   public void insertMatch(Match m);
	   public void modifyMatch(Match m);
	   public void deleteMatch(Match m);
	   public Match findMatchById(Integer id);
	   public Match findMatchById2(Integer id2);
	   public List<Match> getMatches(Date fromDate, Date toDate, int company,
			int championShip, int start , int limit);

	   public List<Match> getMatchMouth(List<FilterItem>fl, int start , int limit, String sort, String dir) throws ParseException;

	   public Long getMatchMouthCount(List<FilterItem>fl) throws ParseException;
	   
	   public Long getMatchesCount(Date fromDate, Date toDate, int company,
				int championShip);
	   public List analyzeEuroMouthMix(Date fromDate, Date toDate, int company,
			double euro_mouth, int asia_big_small_mouth);
	   public List test(Date fromDate, Date toDate, int company,
			double euro_mouth, int asia_big_small_mouth);
	   public List analyzeEuroMouth(Date fromDate, Date toDate, int company,
			double euro_mouth);
	   
	   public List<Match> getMatchMouthByCondition(int company, Double euroWinFrom, Double euroWinTo, Double euroStandFrom, Double euroStandTo, Double euroLossFrom, Double euroLossTo, String[] matchMouthIdList)throws ParseException ;

	   

}
