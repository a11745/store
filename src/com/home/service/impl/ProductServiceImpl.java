package com.home.service.impl;

import java.util.List;

import com.home.dao.ProductDao;
import com.home.dao.impl.ProductDaoImpl;
import com.home.domain.PageBean;
import com.home.domain.Product;
import com.home.service.ProductService;

public class ProductServiceImpl implements ProductService{
	//查询最新商品
	@Override
	public List<Product> findNew() throws Exception {
		ProductDao pdao = new ProductDaoImpl();
		
		return pdao.findNew();
	}
	//插叙热门
	@Override
	public List<Product> findHot() throws Exception {
		ProductDao pdao = new ProductDaoImpl();
		return pdao.findHot();
	}
	
	//通过pid查询单个商品详情
	@Override
	public Product getById(String pid) throws Exception {
			ProductDao pdao = new ProductDaoImpl();
			
		return pdao.getByPid(pid);
	}
	//分类分页显示商品
	@Override
	public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		ProductDao pdao = new ProductDaoImpl();
		//当前页数据
		List<Product> list = pdao.finByPage(currPage,pageSize,cid);
		
		//总条数
		int totalCount = pdao.getTotalCount(cid);
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}
	//后台展示所有商品
	@Override
	public List<Product> findAll() throws Exception {
		//1.
		ProductDao pdao = new ProductDaoImpl();
		
		return pdao.findAll();
	}
	//添加商品
	@Override
	public void add(Product p) throws Exception {
		ProductDao pdao = new ProductDaoImpl();
		pdao.add(p);
	}

}
