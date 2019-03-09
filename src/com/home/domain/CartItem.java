package com.home.domain;

import java.io.Serializable;
//���ﳵ��
public class CartItem implements Serializable{
	private Product product;//��Ʒ����
	private Integer count;//��������
	private Double subtotal =0.0;//С��
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSubtotal() {
		return product.getShop_price()*count;
	}
	public CartItem(Product product, Integer count) {
		super();
		this.product = product;
		this.count = count;
	}
	public CartItem() {
		super();
		
	}
	
	
	
}
