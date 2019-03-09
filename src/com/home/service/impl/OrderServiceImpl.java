package com.home.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.home.dao.OrderDao;
import com.home.dao.impl.OrderDaoImpl;
import com.home.domain.Order;
import com.home.domain.OrderItem;
import com.home.domain.PageBean;
import com.home.domain.User;
import com.home.service.OrderService;
import com.home.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {
	//
	@Override
	public void add(Order order) throws Exception  {
		
		try {
			//1.开启事物
			DataSourceUtils.startTransaction();
			//2.向orders表中添加一条数据
			OrderDao od = new OrderDaoImpl();
			od.add(order);
			//3.向orderitems中添加n条数据    //订单项列表
			for (OrderItem oi : order.getItems()) {
				od.addItem(oi);
			}
			//4 事物处理(提交)
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//事物回滚
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
		
	}
	//分页查询我的订单
	@Override
	public PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception {
		OrderDao od = new OrderDaoImpl();
		//查询当前页数据
		List<Order> list = od.findAllByPage(currPage,pageSize,user.getUid());
		
		//查询总条数
		int totalCount = od.getTotalCount(user.getUid());
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}
	//查看订单详情    通过id支付订单
	@Override
	public Order getById(String oid) throws Exception {
		OrderDao od = new OrderDaoImpl();
		Order order = od.getById(oid);
		return order;
	}
	
	
	@Override
	public void updateOrder(Order order) throws Exception {
		OrderDao od = new OrderDaoImpl();
		od.update(order);
		
	}
	//查询所有订单
	@Override
	public List<Order> findAll() throws Exception {
		OrderDao od = new OrderDaoImpl();
		
		return od.findAll();
		
	}

}
