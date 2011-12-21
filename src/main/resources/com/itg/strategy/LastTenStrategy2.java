package com.itg.strategy;

import java.util.ArrayList;
import java.util.List;

import com.itg.dao.HistoryResult;
import com.itg.dao.IPropertyDAO;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.Property;

public class LastTenStrategy2 extends LastTenStrategy {
	
	private IPropertyDAO propertyDAO;
	
	
	@Override
	public List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl) {

		List<OddPredict> result = new ArrayList<OddPredict>();
		
		Property p = propertyDAO.findPropertyById(m.getChampionship());
		
		if((p.getText().contains("附")||p.getText().contains("盃")||p.getText().contains("杯"))&&(!p.getText().equals("FOX盃"))){
			return result;
		}

		return super.predict2(m, mm, hl);
	}


	public IPropertyDAO getPropertyDAO() {
		return propertyDAO;
	}


	public void setPropertyDAO(IPropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}
	
}
