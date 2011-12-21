package com.itg.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "WarningFilter")
@XmlRootElement(name = "warningFilter")
public class WarningFilter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	
	private Integer company; //
	
	private Double euro_final_win; //
	
	private Double euro_final_loss; //
	
	private Double euro_final_standoff; //
	
	private Double euro_early_win; //
	
	private Double euro_early_loss; //
	
	private Double euro_early_standoff; // 
	
	private Double asia_final_up; // 
	
	private Integer asia_final_ud_mouth; //
	
	private Double asia_final_down; //
	
	private Double asia_early_up; // 
	
	private Integer asia_early_ud_mouth; // 
	
	private Double asia_early_down; // 
	
	private Double asia_final_big; // 
	private Integer asia_final_bs_mouth; //
	
	private Double asia_final_small; // 
	
	private Double asia_early_big; //
	private Integer asia_early_bs_mouth; //
	
	private Double asia_early_small; // 
	
	private String big_small; // 
	
	private String up_down; //
	
	private String win_standoff_loss; // 
	
    private String asia_win;   //
    @Column(nullable=true)
	public String getAsia_win() {
		return asia_win;
	}
	public void setAsia_win(String asia_win) {
		this.asia_win = asia_win;
	}
	
	@Column(nullable=true)
	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}
	@Column(nullable=true)
	public Double getEuro_final_win() {
		
		
		return euro_final_win;
	}
	
	public void setEuro_final_win(Double euro_final_win) {
		
		
		this.euro_final_win = euro_final_win;
	}
	@Column(nullable=true)
	public Double getEuro_final_loss() {
		return euro_final_loss;
	}

	public void setEuro_final_loss(Double euro_final_loss) {

		this.euro_final_loss = euro_final_loss;
	}
	@Column(nullable=true)
	public Double getEuro_final_standoff() {
		return euro_final_standoff;
	}

	public void setEuro_final_standoff(Double euro_final_standoff) {
		
		this.euro_final_standoff = euro_final_standoff;
	}
	@Column(nullable=true)
	public Double getEuro_early_win() {
		return euro_early_win;
	}

	public void setEuro_early_win(Double euro_early_win) {
		this.euro_early_win = euro_early_win;
	}
	@Column(nullable=true)
	public Double getEuro_early_loss() {
		return euro_early_loss;
	}

	public void setEuro_early_loss(Double euro_early_loss) {
		this.euro_early_loss = euro_early_loss;
	}
	@Column(nullable=true)
	public Double getEuro_early_standoff() {
		return euro_early_standoff;
	}

	public void setEuro_early_standoff(Double euro_early_standoff) {
		this.euro_early_standoff = euro_early_standoff;
	}
	@Column(nullable=true)
	public Double getAsia_final_up() {
		return asia_final_up;
	}

	public void setAsia_final_up(Double asia_final_up) {
		this.asia_final_up = asia_final_up;
	}
	@Column(nullable=true)
	public int getAsia_final_ud_mouth() {
				
		return asia_final_ud_mouth;
	}

	public void setAsia_final_ud_mouth(int asia_final_ud_mouth) {
		this.asia_final_ud_mouth = asia_final_ud_mouth;
	}
	@Column(nullable=true)
	public Double getAsia_final_down() {
		return asia_final_down;
	}

	public void setAsia_final_down(Double asia_final_down) {
		this.asia_final_down = asia_final_down;
	}
	@Column(nullable=true)
	public Double getAsia_early_up() {
		return asia_early_up;
	}

	public void setAsia_early_up(Double asia_early_up) {
		this.asia_early_up = asia_early_up;
	}
	@Column(nullable=true)
	public int getAsia_early_ud_mouth() {
		return asia_early_ud_mouth;
	}

	public void setAsia_early_ud_mouth(int asia_early_ud_mouth) {
		this.asia_early_ud_mouth = asia_early_ud_mouth;
	}
	@Column(nullable=true)
	public Double getAsia_early_down() {
		return asia_early_down;
	}

	public void setAsia_early_down(Double asia_early_down) {
		this.asia_early_down = asia_early_down;
	}
	@Column(nullable=true)
	public Double getAsia_final_big() {
		return asia_final_big;
	}

	public void setAsia_final_big(Double asia_final_big) {
		this.asia_final_big = asia_final_big;
	}
	@Column(nullable=true)
	public int getAsia_final_bs_mouth() {
		return asia_final_bs_mouth;
	}

	public void setAsia_final_bs_mouth(int asia_final_bs_mouth) {
		

		this.asia_final_bs_mouth = asia_final_bs_mouth;
	}
	@Column(nullable=true)
	public Double getAsia_final_small() {
		return asia_final_small;
	}

	public void setAsia_final_small(Double asia_final_small) {
		this.asia_final_small = asia_final_small;
	}
	@Column(nullable=true)
	public Double getAsia_early_big() {
		return asia_early_big;
	}

	public void setAsia_early_big(Double asia_early_big) {
		this.asia_early_big = asia_early_big;
	}
	@Column(nullable=true)
	public int getAsia_early_bs_mouth() {
		return asia_early_bs_mouth;
	}

	public void setAsia_early_bs_mouth(int asia_early_bs_mouth) {
		this.asia_early_bs_mouth = asia_early_bs_mouth;
	}
	@Column(nullable=true)
	public Double getAsia_early_small() {
		return asia_early_small;
	}

	public void setAsia_early_small(Double asia_early_small) {
		this.asia_early_small = asia_early_small;
	}

	@Column(nullable=true)
	public String getBig_small() {
		return big_small;
	}

	public void setBig_small(String big_small) {
		this.big_small = big_small;
	}
	@Column(nullable=true)
	public String getUp_down() {
		return up_down;
	}

	public void setUp_down(String up_down) {
		this.up_down = up_down;
	}
	@Column(nullable=true)
	public String getWin_standoff_loss() {
		return win_standoff_loss;
	}

	public void setWin_standoff_loss(String win_standoff_loss) {
		this.win_standoff_loss = win_standoff_loss;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

    
}
