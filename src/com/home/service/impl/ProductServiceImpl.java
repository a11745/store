package com.home.service.impl;

import java.util.List;

import com.home.dao.ProductDao;
import com.home.dao.impl.ProductDaoImpl;
import com.home.domain.PageBean;
import com.home.domain.Product;
import com.home.service.ProductService;

public class ProductServiceImpl implements ProductService{
	//��ѯ������Ʒ
	@Override
	public List<Product> findNew() throws Exception {
		ProductDao pdao = new ProductDaoImpl();
		
		return pdao.findNew();
	}
	//��������
	@Override
	public List<Product> findHot() throws Exception {
		ProductDao pdao = new ProductDaoImpl();
		return pdao.findHot();
	}
	
	//ͨ��pid��ѯ������Ʒ����
	@Override
	public Product getById(String pid) throws Exception {
			ProductDao pdao = new ProductDaoImpl();
			
		return pdao.getByPid(pid);
	}
	//�����ҳ��ʾ��Ʒ
	@Override
	public PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception {
		ProductDao pdao = new ProductDaoImpl();
		//��ǰҳ����
		List<Product> list = pdao.finByPage(currPage,pageSize,cid);
		
		//������
		int totalCount = pdao.getTotalCount(cid);
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}
	//��̨չʾ������Ʒ
	@Override
	public List<Product> findAll() throws Exception {
		//1.
		ProductDao pdao = new ProductDaoImpl();
		
		return pdao.findAll();
	}
	//�����Ʒ
	@Override
	public void add(Product p) throws Exception {
		ProductDao pdao = new ProductDaoImpl();
		pdao.add(p);
	}

}
