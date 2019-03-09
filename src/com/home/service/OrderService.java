package com.home.service;

import java.util.List;

import com.home.domain.Order;
import com.home.domain.PageBean;
import com.home.domain.User;

public interface OrderService {

	void add(Order order)throws Exception;

	PageBean<Order> findAllByPage(int currPage, int pageSize, User user)throws Exception;

	Order getById(String oid)throws Exception;

	void updateOrder(Order order)throws Exception;

	List<Order> findAll()throws Exception;

}
