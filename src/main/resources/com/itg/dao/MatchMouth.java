package com.itg.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.jdbc.support.rowset.SqlRowSet;

@Entity
@Table(name = "MatchMouth")
@XmlRootElement(name = "matchMouth")
public class MatchMouth {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int matchMouthId;
	
	
	@Transient
    private boolean changed;
	
	@Transient
    private String changedField;	
	
	private Date offerTime;
	
	private Integer matchId;  //网上数据的比赛 ID.
	private Integer company; // 公司

	private Double euro_final_win; // 欧终赔_胜
	private Double euro_final_loss; // 欧终赔_负
	private Double euro_final_standoff; // 欧终赔_平

	private Double euro_early_win; // 欧初赔_胜
	private Double euro_early_loss; // 欧初赔_负
	private Double euro_early_standoff; // 欧初赔_平

	private Double asia_final_up; // 亚终赔_上
	@Column(nullable=true)
	private Integer asia_final_ud_mouth; // 亚终赔_盘口
	private Double asia_final_down; // 亚终赔_下

	private Double asia_early_up; // 亚初赔_上
	@Column(nullable=true)
	private Integer asia_early_ud_mouth; // 亚初赔_盘口
	private Double asia_early_down; // 亚初赔_下

	private Double asia_final_big; // 亚终赔_大
	@Column(nullable=true)
	private Integer asia_final_bs_mouth; // 亚终赔_盘口
	private Double asia_final_small; // 亚终赔_小

	private Double asia_early_big; // 亚初赔_大
	@Column(nullable=true)
	private Integer asia_early_bs_mouth; // 亚初赔_盘口
	private Double asia_early_small; // 亚初赔_小

	private String big_small; // 大/小
	private String up_down; // 上/下
	private String win_standoff_loss; // 胜平负
	
    private String asia_win;   //亚赔赢盘
    
    @Column(length=2)
    private String asiaLevelChange;
    private Double asiaLevelChangeVol;
    @Column(length=2)
    private String asiaOddChange;
    private Integer asiaOddChangeVol;
    
    @Column(length=1)
    private String interval;
    @Column(length=1)
    private String water_level;
    @Column(length=1, columnDefinition="nvarchar")
    private String fen;
    @Column(length=1, columnDefinition="nvarchar")
    private String pan;
    
    @Column(length=1, columnDefinition="nvarchar")
    private String xing;    
    


	public String getXing() {
		return xing;
	}

	public void setXing(String xing) {
		this.xing = xing;
	}

	public String getFen() {
		return fen;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getWater_level() {
		return water_level;
	}

	public void setWater_level(String water_level) {
		this.water_level = water_level;
	}


	public String getAsiaLevelChange() {
		return asiaLevelChange;
	}

	public void setAsiaLevelChange(String asiaLevelChange) {
		this.asiaLevelChange = asiaLevelChange;
	}

	public Double getAsiaLevelChangeVol() {
		return asiaLevelChangeVol;
	}

	public void setAsiaLevelChangeVol(Double asiaLevelChangeVol) {
		this.asiaLevelChangeVol = asiaLevelChangeVol;
	}

	public String getAsiaOddChange() {
		return asiaOddChange;
	}

	public void setAsiaOddChange(String asiaOddChange) {
		this.asiaOddChange = asiaOddChange;
	}

	public Integer getAsiaOddChangeVol() {
		return asiaOddChangeVol;
	}

	public void setAsiaOddChangeVol(Integer asiaOddChangeVol) {
		this.asiaOddChangeVol = asiaOddChangeVol;
	}

	public int getMatchMouthId() {
		return matchMouthId;
	}

	public void setMatchMouthId(int matchMouthId) {
		this.matchMouthId = matchMouthId;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

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

	public Double getEuro_final_win() {
		return euro_final_win;
	}

	public void setEuro_final_win(Double euro_final_win) {
		if(!euro_final_win.equals(getEuro_final_win())){
			changedField=changedField+"#euro_final_win#";
			setChanged(true);
		}
		
		this.euro_final_win = euro_final_win;
	}

	public Double getEuro_final_loss() {
		return euro_final_loss;
	}

	public void setEuro_final_loss(Double euro_final_loss) {
		if(!euro_final_loss.equals(getEuro_final_loss())){
			changedField=changedField+"#euro_final_loss#";
			setChanged(true);
		}
		this.euro_final_loss = euro_final_loss;
	}

	public Double getEuro_final_standoff() {

		return euro_final_standoff;
	}

	public void setEuro_final_standoff(Double euro_final_standoff) {
		if(!euro_final_standoff.equals(getEuro_final_standoff())){
			changedField=changedField+"#euro_final_standoff#";
			setChanged(true);
		}

		this.euro_final_standoff = euro_final_standoff;
	}

	public Double getEuro_early_win() {
		return euro_early_win;
	}

	public void setEuro_early_win(Double euro_early_win) {
		if(!euro_early_win.equals(getEuro_early_win())){
			changedField=changedField+"#euro_early_win#";
			setChanged(true);
		}
		
		this.euro_early_win = euro_early_win;
	}

	public Double getEuro_early_loss() {
		return euro_early_loss;
	}

	public void setEuro_early_loss(Double euro_early_loss) {
		
		if(!euro_early_loss.equals(getEuro_early_loss())){
			changedField=changedField+"#euro_early_loss#";
			setChanged(true);
		}

		this.euro_early_loss = euro_early_loss;
	}

	public Double getEuro_early_standoff() {
		return euro_early_standoff;
	}

	public void setEuro_early_standoff(Double euro_early_standoff) {
		this.euro_early_standoff = euro_early_standoff;
	}

	public Double getAsia_final_up() {
		return asia_final_up;
	}

	public void setAsia_final_up(Double asia_final_up) {
		this.asia_final_up = asia_final_up;
	}

	public Integer getAsia_final_ud_mouth() {
		return asia_final_ud_mouth;
	}

	public void setAsia_final_ud_mouth(Integer asia_final_ud_mouth) {
		if((asia_final_ud_mouth==null)&&(getAsia_final_ud_mouth()!=null)){
			changedField=changedField+"#asia_final_ud_mouth#";
			setChanged(true);
		}
		if((asia_final_ud_mouth!=null)&&(!asia_final_ud_mouth.equals(getAsia_final_ud_mouth()))){
			changedField=changedField+"#asia_final_ud_mouth#";
			setChanged(true);
		}

		this.asia_final_ud_mouth = asia_final_ud_mouth==0?null:asia_final_ud_mouth;
	}

	public Double getAsia_final_down() {
		return asia_final_down;
	}

	public void setAsia_final_down(Double asia_final_down) {
		this.asia_final_down = asia_final_down;
	}

	public Double getAsia_early_up() {
		return asia_early_up;
	}

	public void setAsia_early_up(Double asia_early_up) {
		this.asia_early_up = asia_early_up;
	}

	public Integer getAsia_early_ud_mouth() {
		return asia_early_ud_mouth;
	}

	public void setAsia_early_ud_mouth(Integer asia_early_ud_mouth) {
		
		this.asia_early_ud_mouth = asia_early_ud_mouth==0?null:asia_early_ud_mouth;
		//if(asia_early_ud_mouth==0){
		//	this.asia_early_ud_mouth = null;
		//}
	}

	public Double getAsia_early_down() {
		return asia_early_down;
	}

	public void setAsia_early_down(Double asia_early_down) {
		this.asia_early_down = asia_early_down;
	}

	public Double getAsia_final_big() {
		return asia_final_big;
	}

	public void setAsia_final_big(Double asia_final_big) {
		this.asia_final_big = asia_final_big;
	}

	public Integer getAsia_final_bs_mouth() {
		return asia_final_bs_mouth;
	}

	public void setAsia_final_bs_mouth(Integer asia_final_bs_mouth) {
		if(!asia_final_bs_mouth.equals(getAsia_final_bs_mouth())){
			changedField=changedField+"#asia_final_bs_mouth#";
			setChanged(true);
		}
		this.asia_final_bs_mouth = asia_final_bs_mouth==0?null:asia_final_bs_mouth;
	}

	public Double getAsia_final_small() {
		return asia_final_small;
	}

	public void setAsia_final_small(Double asia_final_small) {
		this.asia_final_small = asia_final_small;
	}

	public Double getAsia_early_big() {
		return asia_early_big;
	}

	public void setAsia_early_big(Double asia_early_big) {
		this.asia_early_big = asia_early_big;
	}

	public Integer getAsia_early_bs_mouth() {
		return asia_early_bs_mouth;
	}

	public void setAsia_early_bs_mouth(Integer asia_early_bs_mouth) {
		this.asia_early_bs_mouth = asia_early_bs_mouth==0?null:asia_early_bs_mouth;
	}

	public Double getAsia_early_small() {
		return asia_early_small;
	}

	public void setAsia_early_small(Double asia_early_small) {
		this.asia_early_small = asia_early_small;
	}

	public String getBig_small() {
		return big_small;
	}

	public void setBig_small(String big_small) {
		this.big_small = big_small;
	}

	public String getUp_down() {
		return up_down;
	}

	public void setUp_down(String up_down) {
		this.up_down = up_down;
	}

	public String getWin_standoff_loss() {
		return win_standoff_loss;
	}

	public void setWin_standoff_loss(String win_standoff_loss) {
		this.win_standoff_loss = win_standoff_loss;
	}

	public String getAsia_win() {
		return asia_win;
	}

	public void setAsia_win(String asia_win) {
		this.asia_win = asia_win;
	}
	
	public MatchMouth(){
		setChanged(false);
		changedField = "";
	}
	public String isChangedField(String field){
		if(changedField.contains("#"+field+"#")){
			return "red";
		}else{
			return "black";
		}
		
		
	}

	public Date getOfferTime() {
		return offerTime;
	}

	public void setOfferTime(Date offerTime) {
		this.offerTime = offerTime;
	}
	


	

}
