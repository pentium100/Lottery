package com.itg.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "HistoryResult")
@XmlRootElement(name = "historyResult")
public class HistoryResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reportId;
	private boolean sent;

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	private Integer company;
	private Integer matchMouthId; // 盘口ID.
	private Integer matchId; // 网上数据的比赛 ID.
	private Integer country;

	public Integer getMatchMouthId() {
		return matchMouthId;
	}

	public void setMatchMouthId(Integer matchMouthId) {
		this.matchMouthId = matchMouthId;
	}

	private Integer inDays;
	private Date reportTime;

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	private Integer championship;
	private Integer totalMatches;
	private Integer totalUbMatches;

	private Integer winCount;
	private Integer standoffCount;
	private Integer lossCount;
	private Float winPercent;
	private Float standoffPercent;
	private Float lossPercent;

	private Integer bigCount;
	private Integer bsStandoffCount;
	private Integer smallCount;
	private Float bigPercent;
	private Float bsStandoffPercent;
	private Float smallPercent;

	private Integer upCount;
	private Integer udStandoffCount;
	private Integer downCount;
	private Float upPercent;
	private Float udStandoffPercent;
	private Float downPercent;

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Integer getInDays() {
		return inDays;
	}

	public void setInDays(Integer inDays) {
		this.inDays = inDays;
	}

	public Integer getChampionship() {
		return championship;
	}

	public void setChampionship(Integer championship) {
		this.championship = championship;
	}

	public Integer getTotalMatches() {
		return totalMatches;
	}

	public void setTotalMatches(Integer totalMatches) {
		this.totalMatches = totalMatches;
	}

	public Integer getWinCount() {
		return winCount;
	}

	public void setWinCount(Integer winCount) {
		this.winCount = winCount;
	}

	public Integer getStandoffCount() {
		return standoffCount;
	}

	public void setStandoffCount(Integer standoffCount) {
		this.standoffCount = standoffCount;
	}

	public Integer getLossCount() {
		return lossCount;
	}

	public void setLossCount(Integer lossCount) {
		this.lossCount = lossCount;
	}

	public Float getWinPercent() {
		return winPercent;
	}

	public void setWinPercent(Float winPercent) {
		this.winPercent = winPercent;
	}

	public Float getStandoffPercent() {
		return standoffPercent;
	}

	public void setStandoffPercent(Float standoffPercent) {
		this.standoffPercent = standoffPercent;
	}

	public Float getLossPercent() {
		return lossPercent;
	}

	public void setLossPercent(Float lossPercent) {
		this.lossPercent = lossPercent;
	}

	public Integer getBigCount() {
		return bigCount;
	}

	public void setBigCount(Integer bigCount) {
		this.bigCount = bigCount;
	}

	public Integer getBsStandoffCount() {
		return bsStandoffCount;
	}

	public void setBsStandoffCount(Integer bsStandoffCount) {
		this.bsStandoffCount = bsStandoffCount;
	}

	public Integer getSmallCount() {
		return smallCount;
	}

	public void setSmallCount(Integer smallCount) {
		this.smallCount = smallCount;
	}

	public Float getBigPercent() {
		return bigPercent;
	}

	public void setBigPercent(Float bigPercent) {
		this.bigPercent = bigPercent;
	}

	public Float getBsStandoffPercent() {
		return bsStandoffPercent;
	}

	public void setBsStandoffPercent(Float bsStandoffPercent) {
		this.bsStandoffPercent = bsStandoffPercent;
	}

	public Float getSmallPercent() {
		return smallPercent;
	}

	public void setSmallPercent(Float smallPercent) {
		this.smallPercent = smallPercent;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public Long getReportId() {
		return reportId;
	}

	public Integer getUpCount() {
		return upCount;
	}

	public void setUpCount(Integer upCount) {
		this.upCount = upCount;
	}

	public Integer getUdStandoffCount() {
		return udStandoffCount;
	}

	public void setUdStandoffCount(Integer udStandoffCount) {
		this.udStandoffCount = udStandoffCount;
	}

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}

	public Float getUpPercent() {
		return upPercent;
	}

	public void setUpPercent(Float upPercent) {
		this.upPercent = upPercent;
	}

	public Float getUdStandoffPercent() {
		return udStandoffPercent;
	}

	public void setUdStandoffPercent(Float udStandoffPercent) {
		this.udStandoffPercent = udStandoffPercent;
	}

	public Float getDownPercent() {
		
		return downPercent;
	}

	public void setDownPercent(Float downPercent) {
		this.downPercent = downPercent;
	}

	public Integer getTotalUbMatches() {
		
		if (totalUbMatches == null) {
			return 0;
		} else {

			return totalUbMatches;
		}
	}

	public void setTotalUbMatches(Integer totalUbMatches) {
		this.totalUbMatches = totalUbMatches;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

}
