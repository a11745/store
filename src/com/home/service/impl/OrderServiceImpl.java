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
			//1.��������
			DataSourceUtils.startTransaction();
			//2.��orders�������һ������
			OrderDao od = new OrderDaoImpl();
			od.add(order);
			//3.��orderitems�����n������    //�������б�
			for (OrderItem oi : order.getItems()) {
				od.addItem(oi);
			}
			//4 ���ﴦ��(�ύ)
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//����ع�
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
		
	}
	//��ҳ��ѯ�ҵĶ���
	@Override
	public PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception {
		OrderDao od = new OrderDaoImpl();
		//��ѯ��ǰҳ����
		List<Order> list = od.findAllByPage(currPage,pageSize,user.getUid());
		
		//��ѯ������
		int totalCount = od.getTotalCount(user.getUid());
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}
	//�鿴��������    ͨ��id֧������
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
	//��ѯ���ж���
	@Override
	public List<Order> findAll() throws Exception {
		OrderDao od = new OrderDaoImpl();
		
		return od.findAll();
		
	}

}
