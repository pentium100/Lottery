package com.itg.dao;

import java.util.Date;
import java.util.List;


public interface IWarningFilterDAO {
	
	   public void insertWarningFilter(WarningFilter w);
	   public void modifyWarningFilter(WarningFilter w);
	   public void deleteWarningFilter(WarningFilter w);
	   public List<WarningFilter> findWarningFilter(MatchMouth mm);
	   

}
