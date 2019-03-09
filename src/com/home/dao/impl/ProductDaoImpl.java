package com.home.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.home.dao.ProductDao;
import com.home.domain.Product;
import com.home.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {
	//查询最新商品
	@Override
	public List<Product> findNew() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate limit 9";
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}
	//查询热门商品
	@Override
	public List<Product> findHot() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = 1 order by pdate limit 9";
		return qr.query(sql, new BeanListHandler<>(Product.class));
		
	}
	//通过pid查询商品
	@Override
	public Product getByPid(String pid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ? limit 1";
		
		return qr.query(sql, new BeanHandler<>(Product.class),pid);
	}
	
	//当前页数据
	@Override
	public List<Product> finByPage(int currPage, int pageSize, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "select * from product where cid = ? limit  ?,?";
		
		return qr.query(sql, new BeanListHandler<>(Product.class),cid,(currPage-1)*pageSize,pageSize);
	}
	//当前类别总条数
	@Override
	public int getTotalCount(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "select count(*) from product where cid = ?";
		
		return ((Long)qr.query(sql, new ScalarHandler(),cid)).intValue();
	
	}
	//更新商品的cid  为删除分类的时候准备
	@Override
	public void updateCid(String cid) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "update product set cid = null where cid = ?";
		qr.update(DataSourceUtils.getConnection(), sql, cid);
		
	}
	//后台查询所有商品
	@Override
	public List<Product> findAll() throws Exception {
QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "select * from product";
		
		return qr.query(sql, new BeanListHandler<>(Product.class));
	}
	//添加商品
	@Override
	public void add(Product p) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql,p.getPid(),p.getPname(),p.getMarket_price(),
					p.getShop_price(),p.getPimage(),p.getPdate(),
					p.getIs_hot(),p.getPdesc(),p.getPflag(),p.getCategory().getCid());
	}

}
