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
	//查询所有的分类
	@Override
	public List<Category> findAll() throws Exception {
		//1.创建缓存管理器
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//2.获取指定的缓存
		Cache cache = cm.getCache("categoryCache");
		//3.获取数据    可以将cache看成一个list集合
		Element element = cache.get("clist");
		List<Category> list  = null;
		//4.判断数据
		if (element==null) {
			//从数据库中获取
			CategoryDao cd = new CategoryDaoImpl();
			list = cd.findAll();
			//将list放入缓存
			cache.put(new Element("clist", list));
			
			//System.out.println("缓存中没有数据，已去数据库中获取");
		}else {
			//直接返回
			list = (List<Category>) element.getObjectValue();
			//System.out.println("缓存中有数据");
		}
		/*CategoryDao cd = new CategoryDaoImpl();
		cd.findAll();
		*/
		return list;
	}
	//添加分类
	@Override
	public void add(Category c) throws Exception {
		//暂时先获取dao
		CategoryDao cd = new CategoryDaoImpl();
		
		
		cd.add(c);
		//更新缓存
		//1.创建缓存管理器
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//2.获取指定的缓存
		Cache cache = cm.getCache("categoryCache");
		//3.清空缓存
		cache.remove("clist");
				
	}
	//通过cid获取一个分类对象
	@Override
	public Category getById(String cid) throws Exception {
CategoryDao cd = new CategoryDaoImpl();
		
		
	return cd.getById(cid);
		
	}
	//更新分类
	@Override
	public void update(Category c) throws Exception {
		//1.调用dao
		CategoryDao cd = new CategoryDaoImpl();
		cd.update(c);
		//更新缓存
				//1.创建缓存管理器
				CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
				//2.获取指定的缓存
				Cache cache = cm.getCache("categoryCache");
				//3.清空缓存
				cache.remove("clist");
	}
	//删除分类
	@Override
	public void delete(String cid) throws Exception {
		
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			//2.更新商品
			ProductDao pd = new ProductDaoImpl();
			pd.updateCid(cid);
			//3.删除分类
			CategoryDao cd = new CategoryDaoImpl();
			cd.delete(cid);
			//4.更新事务
			DataSourceUtils.commitAndClose();
			
			//清空缓存
			//1.创建缓存管理器
			CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
			//2.获取指定的缓存
			Cache cache = cm.getCache("categoryCache");
			//3.清空缓存
			cache.remove("clist");
		} catch (Exception e) {
			
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
		
	}
	

}
