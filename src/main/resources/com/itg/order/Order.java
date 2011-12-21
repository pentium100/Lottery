package com.itg.order;

import java.util.Date;
import java.util.List;

import com.itg.dao.Property;


public class Order {

	
	private int id;  //订单id
	private Integer matchId;  //比赛id
	private List<Integer> matchMouthIds;  //盘口id列表
	private Date placeTime; //下注时间
	private Date matchTime;
	public static enum Type {euro, asia, bigSmall}//下注类型
	private Type type; 
	private Double stake;  //下注金额
	private String direction; //下注 方向 
	private Property mouth; //盘口
	private String matchResult; //比赛结果
	private Double profit; //赢亏
	private Double price;   //赔率
	private Integer cancelOrder;  //取消订单号
}
