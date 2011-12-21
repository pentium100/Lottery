package com.itg.order;

import java.util.Date;
import java.util.List;

public interface IOrderManager {
	
	
   public void placeOrder(Order order);
   public Order cancelOrder(Order order);
   public Double getTtlStake(Date date);  //合计下注金额
   public Double getGrossWin(Date date);
   public List<Order> getOrders(Date date);
   
}
