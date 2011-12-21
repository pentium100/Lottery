package com.itg.strategy;

import java.util.ArrayList;
import java.util.List;

import com.itg.dao.HistoryResult;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;

public class UnusualStrategy extends BaseStrategy {

	

	private String companyFilter = "*";
	@Override
	public List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl) {
      
		List<OddPredict> result = new ArrayList<OddPredict>();
		
		if (!companyFilter.contains(mm.getCompany().toString())){
			return result;
		}
		
		if((m.getHomeTeamPosition() != null)&&(m.getAwayTeamPosition()!=null)&&m.getHomeTeamPosition()<m.getAwayTeamPosition()&&(mm.getEuro_final_win()>mm.getEuro_final_loss())){

			OddPredict op = createOddPredict(m, mm, null, "亚赔下");

			op.setMatchCount(0);
			op.setPossibility(0.00f);

			op.setRemark("非正常赔率");
			result.add(op);

		}
		
			
		return result;

	}
	public String getCompanyFilter() {
		return companyFilter;
	}
	public void setCompanyFilter(String companyFilter) {
		this.companyFilter = companyFilter;
	}


}
