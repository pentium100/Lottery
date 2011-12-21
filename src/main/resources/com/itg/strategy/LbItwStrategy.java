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
import com.itg.dao.MatchDAO;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.OddPredictPK;
import com.itg.dao.Property;
import com.itg.dao.WarningFilter;

public class LbItwStrategy extends BaseStrategy {

	
	private int company1;
	private int company2;
	
	private Double euro_final_win1;
	private Double euro_final_win2;

	private Double euro_final_standoff1;
	private Double euro_final_standoff2;

	private Double euro_final_loss1;
	private Double euro_final_loss2;
	private String remarks;
	
	private IMatchDAO matchDAO;
	@Override
	public List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl) {
      
		List<OddPredict> result = new ArrayList<OddPredict>();

		
		if( 
			(mm.getCompany().equals(getCompany1()))
			&&(mm.getEuro_final_win().equals(getEuro_final_win1()))
			&&(mm.getEuro_final_standoff().equals(getEuro_final_standoff1()))
			&&(mm.getEuro_final_loss().equals(getEuro_final_loss1()))
			
		   ){
			
			List<FilterItem> fl = new ArrayList<FilterItem>();
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "company", new String[]{String.valueOf(getCompany2())}));
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "euro_final_win", new String[]{String.valueOf(getEuro_final_win2())}));
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "euro_final_standoff", new String[]{String.valueOf(getEuro_final_standoff2())}));
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "euro_final_loss", new String[]{String.valueOf(getEuro_final_loss2())}));
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "id2", new String[]{String.valueOf(m.getId2())}));
			List<Match> l = null;
			try {
				l = matchDAO.getMatchMouth(fl, 0, 10, null, null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(l.size()>0){
				
				OddPredict op = createOddPredict(m, mm, null, "亚赔下");

				op.setMatchCount(0);
				op.setPossibility(0.00f);

				op.setRemark(getRemarks());
				result.add(op);

			}
			
		}
		
		return result;

	}
	public int getCompany1() {
		return company1;
	}
	public void setCompany1(int company1) {
		this.company1 = company1;
	}
	public int getCompany2() {
		return company2;
	}
	public void setCompany2(int company2) {
		this.company2 = company2;
	}
	public Double getEuro_final_win1() {
		return euro_final_win1;
	}
	public void setEuro_final_win1(Double euroFinalWin1) {
		euro_final_win1 = euroFinalWin1;
	}
	public Double getEuro_final_win2() {
		return euro_final_win2;
	}
	public void setEuro_final_win2(Double euroFinalWin2) {
		euro_final_win2 = euroFinalWin2;
	}
	public Double getEuro_final_standoff1() {
		return euro_final_standoff1;
	}
	public void setEuro_final_standoff1(Double euroFinalStandoff1) {
		euro_final_standoff1 = euroFinalStandoff1;
	}
	public Double getEuro_final_standoff2() {
		return euro_final_standoff2;
	}
	public void setEuro_final_standoff2(Double euroFinalStandoff2) {
		euro_final_standoff2 = euroFinalStandoff2;
	}
	public Double getEuro_final_loss1() {
		return euro_final_loss1;
	}
	public void setEuro_final_loss1(Double euroFinalLoss1) {
		euro_final_loss1 = euroFinalLoss1;
	}
	public Double getEuro_final_loss2() {
		return euro_final_loss2;
	}
	public void setEuro_final_loss2(Double euroFinalLoss2) {
		euro_final_loss2 = euroFinalLoss2;
	}
	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}
	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
