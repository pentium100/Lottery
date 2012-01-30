package com.itg.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.itg.restful.DateAdapter;


@Entity
@Table(name = "Matches")
@XmlRootElement(name="matches")
public class Match {
	private int id;
	private int id2;   //网上数据的比赛ID号.
	public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}
	private Date date; //比赛日期
	private int championship; //赛事
	private int company; //公司
	
	
	private String score; //比分
	
	@Column(nullable=true)
	private Integer level;
	
	@Transient
	private Date offerTime;



    @Transient
    private String interval;
    
    @Transient
    private String water_level;
    
    @Transient
    private String fen;
    
    @Transient
    private String pan;


    @Transient
    private String xing;

    
    public String getXing() {
		return xing;
	}
	public void setXing(String xing) {
		this.xing = xing;
	}
	@Transient
    public String getFen() {
		return fen;
	}
	public void setFen(String fen) {
		this.fen = fen;
	}
	@Transient
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	private String home_power;
    
    private String away_power;
    
    @Column(length=65535)
    private String remark;
    
    @Transient
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	@Transient
	public String getWater_level() {
		return water_level;
	}
	public void setWater_level(String water_level) {
		this.water_level = water_level;
	}
	public String getHome_power() {
		return home_power;
	}
	public void setHome_power(String home_power) {
		this.home_power = home_power;
	}
	public String getAway_power() {
		return away_power;
	}
	public void setAway_power(String away_power) {
		this.away_power = away_power;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private Boolean isInHome;

    private Double euro_final_win;  //欧终赔_胜
    private Double euro_final_loss; //欧终赔_负
    private Double euro_final_standoff; //欧终赔_平
    
    private Double euro_early_win;  //欧初赔_胜
    private Double euro_early_loss; //欧初赔_负
    private Double euro_early_standoff; //欧初赔_平
    
    private Double asia_final_up;  //亚终赔_上
    private int asia_final_ud_mouth; //亚终赔_盘口
    private Double asia_final_down; //亚终赔_下
    
    private Double asia_early_up;  //亚初赔_上
    private int asia_early_ud_mouth; //亚初赔_盘口
    private Double asia_early_down; //亚初赔_下
    
    private Double asia_final_big;  //亚终赔_大
    private int asia_final_bs_mouth; //亚终赔_盘口
    private Double asia_final_small; //亚终赔_小
    
    private Double asia_early_big;  //亚初赔_大
    private int asia_early_bs_mouth; //亚初赔_盘口
    private Double asia_early_small; //亚初赔_小
    
    private int homeTeam;   //主队
    private int awayTeam;   //客队
    
    private Integer homeTeamPosition;
    private Integer awayTeamPosition;
    private Integer rankDiff;
    
    private String big_small;  			//大/小
    private String up_down;    			//上/下
    private String win_standoff_loss;   //胜平负
    private String asia_win;   //亚赔赢盘
    

    
    private Integer country;
    
	public String getAsia_win() {
		return asia_win;
	}
	public void setAsia_win(String asia_win) {
		this.asia_win = asia_win;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	public Double getEuro_final_win() {
		return euro_final_win;
	}
	public void setEuro_final_win(Double euro_final_win) {
		this.euro_final_win = euro_final_win;
	}
	public Double getEuro_final_loss() {
		return euro_final_loss;
	}
	public void setEuro_final_loss(Double euro_final_loss) {
		this.euro_final_loss = euro_final_loss;
	}
	public Double getEuro_final_standoff() {
		return euro_final_standoff;
	}
	public void setEuro_final_standoff(Double euro_final_standoff) {
		this.euro_final_standoff = euro_final_standoff;
	}
	public Double getEuro_early_win() {
		return euro_early_win;
	}
	public void setEuro_early_win(Double euro_early_win) {
		this.euro_early_win = euro_early_win;
	}
	public Double getEuro_early_loss() {
		return euro_early_loss;
	}
	public void setEuro_early_loss(Double euro_early_loss) {
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
	public int getAsia_final_ud_mouth() {
		return asia_final_ud_mouth;
	}
	public void setAsia_final_ud_mouth(int asia_final_ud_mouth) {
		this.asia_final_ud_mouth = asia_final_ud_mouth;
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
	public int getAsia_early_ud_mouth() {
		return asia_early_ud_mouth;
	}
	public void setAsia_early_ud_mouth(int asia_early_ud_mouth) {
		this.asia_early_ud_mouth = asia_early_ud_mouth;
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
	public int getAsia_final_bs_mouth() {
		return asia_final_bs_mouth;
	}
	public void setAsia_final_bs_mouth(int asia_final_bs_mouth) {
		this.asia_final_bs_mouth = asia_final_bs_mouth;
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
	public int getAsia_early_bs_mouth() {
		return asia_early_bs_mouth;
	}
	public void setAsia_early_bs_mouth(int asia_early_bs_mouth) {
		this.asia_early_bs_mouth = asia_early_bs_mouth;
	}
	public Double getAsia_early_small() {
		return asia_early_small;
	}
	public void setAsia_early_small(Double asia_early_small) {
		this.asia_early_small = asia_early_small;
	}
	public int getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(int homeTeam) {
		this.homeTeam = homeTeam;
	}
	public int getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(int awayTeam) {
		this.awayTeam = awayTeam;
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
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public int getChampionship() {
		return championship;
	}
	public void setChampionship(int championship) {
		this.championship = championship;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Boolean getIsInHome() {
		return isInHome;
	}
	public void setIsInHome(Boolean isInHome) {
		this.isInHome = isInHome;
	}   
	
	
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	public Integer getHomeTeamPosition() {
		return homeTeamPosition;
	}
	public void setHomeTeamPosition(Integer homeTeamPosition) {
		this.homeTeamPosition = homeTeamPosition;
	}
	public Integer getAwayTeamPosition() {
		return awayTeamPosition;
	}
	public void setAwayTeamPosition(Integer awayTeamPosition) {
		this.awayTeamPosition = awayTeamPosition;
	}
	public Integer getRankDiff() {
		return rankDiff;
	}
	public void setRankDiff(Integer rankDiff) {
		this.rankDiff = rankDiff;
	}
	@XmlJavaTypeAdapter(DateAdapter.class)  
	public Date getOfferTime() {
		return offerTime;
	}
	public void setOfferTime(Date offerTime) {
		this.offerTime = offerTime;
	}

 
    
    
    
    

}
