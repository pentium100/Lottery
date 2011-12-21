package com.itg.strategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.itg.dao.HistoryResult;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.OddPredictPK;
import com.itg.dao.Property;
import com.itg.dao.WarningFilter;

public class LargeNumStrategy extends BaseStrategy {

	private Float euroWarningPct = new Float(150);
	private Float asiaWarningPct = new Float(70);
	private int euroWarningCount = 30;

	private int asiaBigWarningCount = 10;
	private int asiaUpWarningCount = 10;
	private int asiaSmallWarningCount = 10;
	private int asiaDownWarningCount = 10;

	private String smallCompanyFilter = ";32;";
	private String downCompanyFilter = ";32;33;43;";

	private String upCompanyFilter = "*";
	private String bigCompanyFilter = "*";

	private String euroCompanyFilter = "*";
	
	

	@Override
	public List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl) {

		List<OddPredict> result = new ArrayList<OddPredict>();
		HistoryResult hr;
		

		for (int i = 0; i < hl.size(); i++) {

			hr = hl.get(i);
			if ((((hl.get(i).getWinPercent() - (100d/mm.getEuro_final_win())*0.9d) > euroWarningPct) 
					&& ((hl.get(i).getTotalMatches() >= euroWarningCount)))) {
				if (euroCompanyFilter.contains(mm.getCompany().toString())
						|| euroCompanyFilter.equals("*")) {

					OddPredict op = createOddPredict(m, mm, hl.get(i), "欧赔胜");

					op.setMatchCount(hr.getTotalMatches());
					op.setPossibility(hr.getWinPercent());

					op.setRemark(String.format("%5.2f",
							hr.getWinPercent() * mm.getEuro_final_win())
							.concat("%"));
					result.add(op);
				}

			}
			if ((((hl.get(i).getStandoffPercent() - (100.0d/mm.getEuro_final_standoff())*0.9d) > euroWarningPct) 
					&& ((hl.get(i).getTotalMatches() >= euroWarningCount)))) {
				if (euroCompanyFilter.contains(mm.getCompany().toString())
						|| euroCompanyFilter.equals("*")) {

					OddPredict op = createOddPredict(m, mm, hl.get(i), "欧赔平");
					op.setMatchCount(hr.getTotalMatches());
					op.setPossibility(hr.getStandoffPercent());

					op.setRemark(String.format("%5.2f",
							hr.getWinPercent() * mm.getEuro_final_win())
							.concat("%"));
					result.add(op);
				}

			}
			if ((((hl.get(i).getLossPercent() - (100.0d/mm.getEuro_final_loss())*0.9d) > euroWarningPct) 
					&& ((hl.get(i).getTotalMatches() >= euroWarningCount)))) {

				if (euroCompanyFilter.contains(mm.getCompany().toString())
						|| euroCompanyFilter.equals("*")) {
					OddPredict op = createOddPredict(m, mm, hl.get(i), "欧赔负");
					op.setMatchCount(hr.getTotalMatches());
					op.setPossibility(hr.getLossPercent());

					op.setRemark(String.format("%5.2f",
							hr.getLossPercent() * mm.getEuro_final_loss())
							.concat("%"));
					result.add(op);
				}

			}
			if (((hl.get(i).getBigPercent() >= asiaWarningPct) && ((hl.get(i)
					.getTotalUbMatches() >= asiaBigWarningCount)))) {

				if (bigCompanyFilter.contains(mm.getCompany().toString())
						|| bigCompanyFilter.equals("*")) {

					OddPredict op = createOddPredict(m, mm, hl.get(i), "大小大");
					op.setMatchCount(hr.getTotalUbMatches());
					op.setPossibility(hr.getBigPercent());
					op.setRemark("1B");
					result.add(op);
				}
 
			}
			boolean hasPredictedSmall = false;

			if (((hr.getSmallPercent() >= asiaWarningPct) && ((hr
					.getTotalUbMatches() >= asiaSmallWarningCount)))) {

				if (smallCompanyFilter.contains(mm.getCompany().toString())
						|| smallCompanyFilter.equals("*")) {

					OddPredict op = createOddPredict(m, mm, hr, "大小小");
					op.setMatchCount(hr.getTotalUbMatches());
					op.setPossibility(hr.getSmallPercent());
					op.setRemark("1B");
					hasPredictedSmall = true;
					result.add(op);

				}

			}

			if (((hl.get(i).getUpPercent() >= asiaWarningPct) && ((hl.get(i)
					.getTotalUbMatches() >= asiaUpWarningCount)))) {

				if (upCompanyFilter.contains(mm.getCompany().toString())
						|| upCompanyFilter.equals("*")) {
					OddPredict op = createOddPredict(m, mm, hr, "亚赔上");
					op.setMatchCount(hr.getTotalUbMatches());
					op.setPossibility(hr.getUpPercent());
					op.setRemark("1B");
					result.add(op);
				}

			}

			if (((hr.getDownPercent() >= asiaWarningPct) && ((hr
					.getTotalUbMatches() >= asiaDownWarningCount)))) {

				if (downCompanyFilter.contains(mm.getCompany().toString())
						|| downCompanyFilter.equals("*")) {

					OddPredict op = createOddPredict(m, mm, hr, "亚赔下");
					op.setMatchCount(hr.getTotalUbMatches());
					op.setPossibility(hr.getDownPercent());
					if(mm.getCompany()==33){
						op.setRemark("2B");
					}else{
						op.setRemark("1B");
					}
					result.add(op);
					
//					if((mm.getCompany()==33||(mm.getCompany()==32))&&(!hasPredictedSmall)){
//						
//						OddPredict op2 = createOddPredict(m, mm, hr, "大小小");
//						op2.setMatchCount(hr.getTotalUbMatches());
//						op2.setPossibility(hr.getSmallPercent());
//						op2.setRemark("+1B");
//						result.add(op2);
						
						
//					}
				}

			}

		}
		
		return result;

	}

	public Float getEuroWarningPct() {
		return euroWarningPct;
	}

	public void setEuroWarningPct(Float euroWarningPct) {
		this.euroWarningPct = euroWarningPct;
	}

	public Float getAsiaWarningPct() {
		return asiaWarningPct;
	}

	public void setAsiaWarningPct(Float asiaWarningPct) {
		this.asiaWarningPct = asiaWarningPct;
	}

	public int getEuroWarningCount() {
		return euroWarningCount;
	}

	public void setEuroWarningCount(int euroWarningCount) {
		this.euroWarningCount = euroWarningCount;
	}

	public int getAsiaBigWarningCount() {
		return asiaBigWarningCount;
	}

	public void setAsiaBigWarningCount(int asiaBigWarningCount) {
		this.asiaBigWarningCount = asiaBigWarningCount;
	}

	public int getAsiaUpWarningCount() {
		return asiaUpWarningCount;
	}

	public void setAsiaUpWarningCount(int asiaUpWarningCount) {
		this.asiaUpWarningCount = asiaUpWarningCount;
	}

	public int getAsiaSmallWarningCount() {
		return asiaSmallWarningCount;
	}

	public void setAsiaSmallWarningCount(int asiaSmallWarningCount) {
		this.asiaSmallWarningCount = asiaSmallWarningCount;
	}

	public int getAsiaDownWarningCount() {
		return asiaDownWarningCount;
	}

	public void setAsiaDownWarningCount(int asiaDownWarningCount) {
		this.asiaDownWarningCount = asiaDownWarningCount;
	}

	public String getSmallCompanyFilter() {
		return smallCompanyFilter;
	}

	public void setSmallCompanyFilter(String smallCompanyFilter) {
		this.smallCompanyFilter = smallCompanyFilter;
	}

	public String getDownCompanyFilter() {
		return downCompanyFilter;
	}

	public void setDownCompanyFilter(String downCompanyFilter) {
		this.downCompanyFilter = downCompanyFilter;
	}

	public String getUpCompanyFilter() {
		return upCompanyFilter;
	}

	public void setUpCompanyFilter(String upCompanyFilter) {
		this.upCompanyFilter = upCompanyFilter;
	}

	public String getBigCompanyFilter() {
		return bigCompanyFilter;
	}

	public void setBigCompanyFilter(String bigCompanyFilter) {
		this.bigCompanyFilter = bigCompanyFilter;
	}

	public String getEuroCompanyFilter() {
		return euroCompanyFilter;
	}

	public void setEuroCompanyFilter(String euroCompanyFilter) {
		this.euroCompanyFilter = euroCompanyFilter;
	}
}
