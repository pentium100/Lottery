package com.itg.strategy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class CombineStrategy extends BaseStrategy {

	private int company1;
	private int company2;

	private Double euro_final_win1;
	private Double euro_final_win2;

	private Double euro_final_standoff1;
	private Double euro_final_standoff2;

	private Double euro_final_loss1;
	private Double euro_final_loss2;

	private List<String> checkList;
	private String remarks;

	private IMatchDAO matchDAO;
    
	private boolean isMatch(Match m, MatchMouth mm, Map<String, String> cl,
			String no) {

		String no2 = null;
		if (no.equals("1")){
			no2 = "2";
		}else{
			no2 = "1";
		}
		

		List<Match> l = null;
		if ((mm.getCompany().equals(Integer.parseInt(cl.get("company"+no))))
				&& (mm.getEuro_final_win().equals(Double.parseDouble(cl.get("euro_final_win"+no))))
				&& (mm.getEuro_final_standoff().equals(Double.parseDouble(cl.get("euro_final_standoff"+no))))
				&& (mm.getEuro_final_loss().equals(Double.parseDouble(cl.get("euro_final_loss"+no))))

		) {

			List<FilterItem> fl = new ArrayList<FilterItem>();
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "company",
					new String[] { String.valueOf(cl.get("company"+no2)) }));
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "euro_final_win",
					new String[] { String.valueOf(cl.get("euro_final_win"+no2)) }));
			fl.add(FilterItemOpt.makeFilter("numeric", "eq",
					"euro_final_standoff", new String[] { String
							.valueOf(cl.get("euro_final_standoff"+no2)) }));
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "euro_final_loss",
					new String[] { String.valueOf(cl.get("euro_final_loss"+no2)) }));
			fl.add(FilterItemOpt.makeFilter("numeric", "eq", "id2",
					new String[] { String.valueOf(m.getId2()) }));
			
			try {
				l = matchDAO.getMatchMouth(fl, 0, 10, null, null);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			

		}
		if(l!=null&&l.size()>0){
			return true;
		}else{
			return false;
		}

	}

	@Override
	public List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl) {

		List<OddPredict> result = new ArrayList<OddPredict>();

		for (String cl2 : checkList) {
			
			Map<String, String> cl = new HashMap<String, String>();
			
			String[] cl3 = cl2.split(";");
			cl.put("company1", cl3[0]);
			cl.put("euro_final_win1", cl3[1]);
			cl.put("euro_final_standoff1", cl3[2]);
			cl.put("euro_final_loss1", cl3[3]);
			
			cl.put("company2", cl3[4]);
			cl.put("euro_final_win2", cl3[5]);
			cl.put("euro_final_standoff2", cl3[6]);
			cl.put("euro_final_loss2", cl3[7]);

			if (isMatch(m, mm, cl, "1") || isMatch(m, mm, cl, "2")) {

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

	public List<String> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<String> checkList) {
		this.checkList = checkList;
	}


}
