package com.home.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.home.dao.OrderDao;
import com.home.domain.Order;
import com.home.domain.OrderItem;
import com.home.domain.PageBean;
import com.home.domain.Product;
import com.home.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {
	//��Ӷ���
	//���ﲻ��DataSourceUtils.getDataSource()
	@Override
	public void add(Order order) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql,order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),
					order.getAddress(),order.getName(),order.getTelephone(),
					order.getUser().getUid());
		
	}
	//���һ��������   //�������б�
	@Override
	public void addItem(OrderItem oi) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(),sql,oi.getItemid(),oi.getCount(),oi.getSubtotal(),
				oi.getProduct().getPid(),oi.getOrder().getOid());
	}
	//��ѯ��ǰҳ����   ����id��ѯ�ҵĶ���
	@Override
	public List<Order> findAllByPage(int currPage, int pageSize, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid = ? order by ordertime desc limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class),uid,(currPage-1)*pageSize,pageSize);
		//������������ ��װÿ�������Ķ������б�
		sql="select * from orderitem oi,product p where oi.pid = p.pid and oi.oid = ?";
		for (Order order : list) {
			//��ǰ������������������
			List<Map<String, Object>> mList = qr.query(sql, new MapListHandler(),order.getOid());
			for (Map<String, Object> map : mList) {
				//��װproduct
				Product p = new Product();
				BeanUtils.populate(p, map);
				
				//��װorderitem
				OrderItem oi = new OrderItem();
				BeanUtils.populate(oi, map);
				
				
				oi.setProduct(p);
				//��װ��orderitemList  
				//��orderitem������ӵ�����Ӧ��order�����list������
				order.getItems().add(oi);
				
				
			}
			
			
		}
		return list;
	}
	//��ѯ������
	@Override
	public int getTotalCount(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(),uid)).intValue();
	}
	//�鿴��������    ͨ��id֧������
	@Override
	public Order getById(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where oid = ?";
		Order order = qr.query(sql, new BeanHandler<>(Order.class),oid);
		
		//��װorderitems
		sql = "select * from orderitem oi,product p where oi.pid = p.pid and oi.oid = ?";
		List<Map<String, Object>> query = qr.query(sql, new MapListHandler(),oid);
		for (Map<String, Object> map : query) {
			//��װ product 
			Product p = new Product();
			BeanUtils.populate(p, map);
			//��װorderitem
			OrderItem oi = new OrderItem();
			BeanUtils.populate(oi, map);
			oi.setProduct(p);
			//��orderitem���뵽order��items��
			order.getItems().add(oi);
			
		}
		return order;
	}
	//�޸Ķ���
	@Override
	public void update(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set state = ?,address = ?,name = ?,telephone = ? where oid = ?";
		qr.update(sql,order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
		
	}
	
	@Override
	public List<Order> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders";
		return qr.query(sql, new BeanListHandler<>(Order.class));
	}

}
