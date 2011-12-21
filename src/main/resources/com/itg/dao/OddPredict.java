package com.itg.dao;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.itg.strategy.IStrategy;

@Entity
@Table(name = "OddPredict")
@XmlRootElement(name = "oddPredict")
public class OddPredict {

	// @Column(length=3)
	// private String predict;
	// private Integer matchMouthId; //盘口id
	@EmbeddedId
	private OddPredictPK pk;
	private Date date; // 预测日期时间
	private Integer matchId; // 比赛id
	private Integer company; // 公司

	private String score;
	private Double profit; // 准确性
	private Float possibility; // 统计概率，百分比
	private String remark;
	private Integer matchCount; // 总场数
	private Integer historyResultId; // 历史结果ID.

	private Double euro_final_win;
	private Double euro_final_standoff;
	private Double euro_final_loss;

	private Integer asia_final_bs_mouth;
	private Integer asia_final_ud_mouth;
	private Double asia_final_big;
	private Double asia_final_small;
	private Double asia_final_up;
	private Double asia_final_down;

	@Transient
	private String homeTeam;
	@Transient
	private String awayTeam;
	@Transient
	private Boolean isInHome;
	@Transient
	private String championship;
	@Transient
	private String company_text;

	@Transient
	private Integer level;

	@Transient
	private Date matchDate;

	@Transient
	private IStrategy strategy;

	@Transient
	private String country;

	@Transient
	private String bsMouth;
	@Transient
	private String udMouth;

	@Transient
	private String actual;

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getPossibility() {
		return possibility;
	}

	public void setPossibility(Float possibility) {
		this.possibility = possibility;
	}

	public Integer getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(Integer matchCount) {
		this.matchCount = matchCount;
	}

	public Integer getHistoryResultId() {
		return historyResultId;
	}

	public void setHistoryResultId(Integer historyResultId) {
		this.historyResultId = historyResultId;
	}

	public Double getEuro_final_win() {
		return euro_final_win;
	}

	public void setEuro_final_win(Double euroFinalWin) {
		euro_final_win = euroFinalWin;
	}

	public Double getEuro_final_standoff() {
		return euro_final_standoff;
	}

	public void setEuro_final_standoff(Double euroFinalStandoff) {
		euro_final_standoff = euroFinalStandoff;
	}

	public Double getEuro_final_loss() {
		return euro_final_loss;
	}

	public void setEuro_final_loss(Double euroFinalLoss) {
		euro_final_loss = euroFinalLoss;
	}

	public Integer getAsia_final_bs_mouth() {
		return asia_final_bs_mouth;
	}

	public void setAsia_final_bs_mouth(Integer asiaFinalBsMouth) {
		asia_final_bs_mouth = asiaFinalBsMouth;
	}

	public Integer getAsia_final_ud_mouth() {
		return asia_final_ud_mouth;
	}

	public void setAsia_final_ud_mouth(Integer asiaFinalUdMouth) {
		asia_final_ud_mouth = asiaFinalUdMouth;
	}

	public OddPredictPK getPk() {
		return pk;
	}

	public void setPk(OddPredictPK pk) {
		this.pk = pk;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getAsia_final_big() {
		return asia_final_big;
	}

	public void setAsia_final_big(Double asiaFinalBig) {
		asia_final_big = asiaFinalBig;
	}

	public Double getAsia_final_small() {
		return asia_final_small;
	}

	public void setAsia_final_small(Double asiaFinalSmall) {
		asia_final_small = asiaFinalSmall;
	}

	public Double getAsia_final_up() {
		return asia_final_up;
	}

	public void setAsia_final_up(Double asiaFinalUp) {
		asia_final_up = asiaFinalUp;
	}

	public Double getAsia_final_down() {
		return asia_final_down;
	}

	public void setAsia_final_down(Double asiaFinalDown) {
		asia_final_down = asiaFinalDown;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Boolean getIsInHome() {
		return isInHome;
	}

	public void setIsInHome(Boolean isInHome) {
		this.isInHome = isInHome;
	}

	public String getChampionship() {
		return championship;
	}

	public void setChampionship(String championship) {
		this.championship = championship;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBsMouth() {
		return bsMouth;
	}

	public void setBsMouth(String bsMouth) {
		this.bsMouth = bsMouth;
	}

	public String getUdMouth() {
		return udMouth;
	}

	public void setUdMouth(String udMouth) {
		this.udMouth = udMouth;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {

		this.score = score;

	}

	public String getCompany_text() {
		return company_text;
	}

	public void setCompany_text(String companyText) {
		company_text = companyText;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getEuroWin() {

		Double r = -1.00;

		Pattern p = Pattern.compile("(\\d{1,2}):(\\d{1,2})");
		Matcher m = p.matcher(score);
		int score1 = 0, score2 = 0;
		if (m.find()) {
			score1 = Integer.parseInt(m.group(1));
			score2 = Integer.parseInt(m.group(2));
		}

		if (score1 > score2
				&& getPk().getPredict().subSequence(2, 3).equals("胜")) {
			r = getEuro_final_win();
		}

		if (score1 == score2
				&& getPk().getPredict().subSequence(2, 3).equals("平")) {
			r = getEuro_final_standoff();
		}
		if (score1 < score2
				&& getPk().getPredict().subSequence(2, 3).equals("负")) {
			r = getEuro_final_loss();
		}

		return r;
	}

	public Double getUdWin1(Double udMouth) {

		Double r = -1.00;

		Pattern p = Pattern.compile("(\\d{1,2}):(\\d{1,2})");
		Matcher m = p.matcher(getScore());
		int score1 = 0, score2 = 0;
		if (m.find()) {
			score1 = Integer.parseInt(m.group(1));
			score2 = Integer.parseInt(m.group(2));
		}

		if (getUdMouth().contains("-")) {
			int score3 = score1;
			score1 = score2;
			score2 = score3;
			udMouth = Math.abs(udMouth);
		}

		if ((score1 - score2) > udMouth
				&& getPk().getPredict().substring(2, 3).equals("上")) {
			r = 1.00;
		}

		if ((score1 - score2) == udMouth) {
			r = 0.00;
		}
		if ((score1 - score2) < udMouth
				&& getPk().getPredict().substring(2, 3).equals("下")) {
			r = 1.00;
		}
		return r;

	}

	public Double getUdWin(String udMouth) {

		Double r = 0.00;

		Pattern pattern = Pattern.compile("-?\\d\\.?\\d?");

		Matcher m = pattern.matcher(udMouth);
		Double odd = 0.00;
		// boolean find = m.find();

		int i = 0;
		while (m.find()) {
			i++;
			odd = Double.parseDouble(m.group(0));
			r += 0.5 * getUdWin1(odd);

		}

		if (i == 1) {
			r = r * 2;
		}

		if (r > 0) {
			r = r * 0.8;
		}
		return r;

	}

	public Double getBsWin1(Double udMouth) {

		Double r = -1.00;

		Pattern p = Pattern.compile("(\\d{1,2}):(\\d{1,2})");
		Matcher m = p.matcher(score);
		int score1 = 0, score2 = 0;
		if (m.find()) {
			score1 = Integer.parseInt(m.group(1));
			score2 = Integer.parseInt(m.group(2));
		}

		if ((score1 + score2) > udMouth
				&& getPk().getPredict().substring(2, 3).equals("大")) {
			r = 1.00;
		}

		if ((score1 + score2) == udMouth) {

			r = 0.00;

		}
		if ((score1 + score2) < udMouth
				&& getPk().getPredict().substring(2, 3).equals("小")) {
			r = 1.00;
		}

		return r;

	}

	public Double getBsWin(String udMouth) {

		Double r = 0.00;
		Pattern pattern = Pattern.compile("-?\\d\\.?\\d?");

		Matcher m = pattern.matcher(udMouth);
		Double odd = 0.00;
		// boolean find = m.find();

		int i = 0;
		while (m.find()) {
			i++;
			odd = Double.parseDouble(m.group(0));
			r += 0.5 * getBsWin1(odd);

		}

		if (i == 1) {
			r = r * 2;
		}

		if (r > 0) {
			r = r * 0.8;
		}
		
		return r;

	}

	public IStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}
	
	public String convetEuroToAsia(){
		
		if(getEuro_final_win()>=2.3d){
			return "+0";
			 
		}
		
		if(getEuro_final_win()>=1.90d){
			return "0/0.5";
		}

		if(getEuro_final_win()>=1.70d){
			return "0.5";
		}

		if(getEuro_final_win()>=1.60d){
			return "0.5/1";
		}

		if(getEuro_final_win()>=1.45d){
			return "1";
		}
		if(getEuro_final_win()>=1.35d){
			return "1/1.5";
		}
		if(getEuro_final_win()>=1.25d){
			return "1.5";
		}
		
		if(getEuro_final_win()>=1.15d){
			return "1.5/2";
		}


		return "2";
		
	}

}
