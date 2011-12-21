package com.itg.strategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.itg.dao.HistoryResult;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.OddPredictPK;
import com.itg.dao.Property;
import com.itg.dao.WarningFilter;

public class LastTenStrategy extends BaseStrategy {

	private Float euroWarningPct = new Float(150);
	private Float asiaWarningPct1 = new Float(70);
	private Float asiaWarningPct2 = new Float(70);
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

	
	private NamedParameterJdbcTemplate jdbcTemplate;

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl) {

		
		List<OddPredict> result = new ArrayList<OddPredict>();
		
		
	
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select sum(case when mm.up_down = '下' then 1 else 0 end) as downCount,");
		sql.append("  	   sum(case when mm.big_small = '小' then 1 else 0 end) as smallCount,"); 

		sql.append("       sum(case when mm.up_down = '上' then 1 else 0 end) as upCount,");
		sql.append("  	   sum(case when mm.big_small = '大' then 1 else 0 end) as bigCount,"); 
		
		sql.append("       count(*) as ttlCount");
		sql.append(" from matchmouth mm");
		sql.append(" where matchmouthid in (");
		sql.append(" select top 10 matchmouthid ");
		sql.append("		from matchMouth mm3 , matches m3");
		sql.append("		where  ");
		sql.append("		 	 mm3.matchid = m3.id2");
		sql.append(" and m3.date <= dateadd(hour, -2, :date)");
		sql.append("		 and mm3.company = :company");
		sql.append("		 and mm3.euro_final_win = :euro_final_win");
		sql.append("		 and mm3.euro_final_standoff = :euro_final_standoff");
		sql.append("		 and mm3.euro_final_loss = :euro_final_loss");
		sql.append("		 and mm3.asia_final_bs_mouth=:asia_final_bs_mouth");
		sql.append("		 and mm3.asia_final_ud_mouth=:asia_final_ud_mouth");
		sql.append("         and mm3.big_small is not null");
		sql.append("         and mm3.up_down is not null");
		sql.append("		order by m3.date desc");
		sql.append("	)");
		
		Map<String, Object> params = new HashMap();
		
		params.put("euro_final_win", mm.getEuro_final_win());
		params.put("euro_final_standoff", mm.getEuro_final_standoff());
		params.put("euro_final_loss", mm.getEuro_final_loss());
		params.put("asia_final_bs_mouth", mm.getAsia_final_bs_mouth());
		params.put("asia_final_ud_mouth", mm.getAsia_final_ud_mouth());
		params.put("date", m.getDate());
		params.put("company", mm.getCompany());
		
		
		

		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql.toString(), params);
		while(rs.next()){
			if(rs.getInt("ttlCount")==10&&rs.getInt("downCount")>=(asiaWarningPct1/10)&&rs.getInt("downCount")<=(asiaWarningPct2/10)
			&&(downCompanyFilter.contains(mm.getCompany().toString())||(downCompanyFilter.equals("*")))		
			){
				
				OddPredict op = createOddPredict(m, mm, null, "亚赔下");

				op.setMatchCount(10);
				op.setPossibility(new Float(rs.getInt("downCount")*10.0));
				op.setRemark("1C");
				result.add(op);
				
//				op = createOddPredict(m, mm, null, "大小小");
//
//				op.setMatchCount(10);
//				op.setPossibility(new Float(rs.getInt("smallCount")*10.0));
//				op.setRemark("2C");
//				result.add(op);
				
			}
			

			
			if(rs.getInt("ttlCount")==10&&rs.getInt("upCount")>=(asiaWarningPct1/10)&&rs.getInt("upCount")<=(asiaWarningPct2/10)
					&&(upCompanyFilter.contains(mm.getCompany().toString())||(upCompanyFilter.equals("*")))
					){
				
				OddPredict op = createOddPredict(m, mm, null, "亚赔上");

				op.setMatchCount(10);
				op.setPossibility(new Float(rs.getInt("upCount")*10.0));
				op.setRemark("1C");
				result.add(op);
				
				
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

	public Float getAsiaWarningPct1() {
		return asiaWarningPct1;
	}

	public void setAsiaWarningPct1(Float asiaWarningPct1) {
		this.asiaWarningPct1 = asiaWarningPct1;
	}

	public Float getAsiaWarningPct2() {
		return asiaWarningPct2;
	}

	public void setAsiaWarningPct2(Float asiaWarningPct2) {
		this.asiaWarningPct2 = asiaWarningPct2;
	}
}
