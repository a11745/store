package com.home.service.impl;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import com.home.dao.CategoryDao;
import com.home.dao.ProductDao;
import com.home.dao.impl.CategoryDaoImpl;
import com.home.dao.impl.ProductDaoImpl;
import com.home.domain.Category;
import com.home.service.CategoryService;
import com.home.utils.DataSourceUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService{
	//��ѯ���еķ���
	@Override
	public List<Category> findAll() throws Exception {
		//1.�������������
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//2.��ȡָ���Ļ���
		Cache cache = cm.getCache("categoryCache");
		//3.��ȡ����    ���Խ�cache����һ��list����
		Element element = cache.get("clist");
		List<Category> list  = null;
		//4.�ж�����
		if (element==null) {
			//�����ݿ��л�ȡ
			CategoryDao cd = new CategoryDaoImpl();
			list = cd.findAll();
			//��list���뻺��
			cache.put(new Element("clist", list));
			
			//System.out.println("������û�����ݣ���ȥ���ݿ��л�ȡ");
		}else {
			//ֱ�ӷ���
			list = (List<Category>) element.getObjectValue();
			//System.out.println("������������");
		}
		/*CategoryDao cd = new CategoryDaoImpl();
		cd.findAll();
		*/
		return list;
	}
	//��ӷ���
	@Override
	public void add(Category c) throws Exception {
		//��ʱ�Ȼ�ȡdao
		CategoryDao cd = new CategoryDaoImpl();
		
		
		cd.add(c);
		//���»���
		//1.�������������
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//2.��ȡָ���Ļ���
		Cache cache = cm.getCache("categoryCache");
		//3.��ջ���
		cache.remove("clist");
				
	}
	//ͨ��cid��ȡһ���������
	@Override
	public Category getById(String cid) throws Exception {
CategoryDao cd = new CategoryDaoImpl();
		
		
	return cd.getById(cid);
		
	}
	//���·���
	@Override
	public void update(Category c) throws Exception {
		//1.����dao
		CategoryDao cd = new CategoryDaoImpl();
		cd.update(c);
		//���»���
				//1.�������������
				CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
				//2.��ȡָ���Ļ���
				Cache cache = cm.getCache("categoryCache");
				//3.��ջ���
				cache.remove("clist");
	}
	//ɾ������
	@Override
	public void delete(String cid) throws Exception {
		
		try {
			//1.��������
			DataSourceUtils.startTransaction();
			//2.������Ʒ
			ProductDao pd = new ProductDaoImpl();
			pd.updateCid(cid);
			//3.ɾ������
			CategoryDao cd = new CategoryDaoImpl();
			cd.delete(cid);
			//4.��������
			DataSourceUtils.commitAndClose();
			
			//��ջ���
			//1.�������������
			CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
			//2.��ȡָ���Ļ���
			Cache cache = cm.getCache("categoryCache");
			//3.��ջ���
			cache.remove("clist");
		} catch (Exception e) {
			
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
		
	}
	

}
