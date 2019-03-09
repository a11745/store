package com.home.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;



public class Cart implements Serializable{
	//存放购物车顶的集合 key：商品的id cartitem：购物车顶   使用map集合删除单个购物项
	private Map<String, CartItem> map = new LinkedHashMap<>();
	//总金额
	private Double total=0.0;
	

	//获取所有的购物车项
	public Collection<CartItem> getItems(){
		return map.values();
	}
	
	//添加到购物车
	public void add2Cart(CartItem item) {
		//1.判断购物车中有无该商品
		//1.1先获取商品id
		String pid = item.getProduct().getPid();
		if (map.containsKey(pid)) {
			//you
			//设置购买数量 需要获取该商品之前的购买数量  + 现在的购买数量
			//先获取原来购物车中的购物车项
			CartItem oItem = map.get(pid);
			oItem.setCount(oItem.getCount()+item.getCount());
			
		}else {
			//没有
			//将购物车项添加进去
			map.put(pid, item);
		}
		//2.添加完成之后  修改金额
		total+=item.getSubtotal();
	}
	//从购物车中删除
	public void removeFromCart(String pid) {
		//1.从集合中删除
		CartItem item = map.remove(pid);
		//2.修改金额
		total-=item.getSubtotal();
		//
	}
	//清空购物车
	public void clearCart() {
		//1.将map置空
		map.clear();
		//2.金额为0
		total=0.0;
	}
	
	public Map<String, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
}
