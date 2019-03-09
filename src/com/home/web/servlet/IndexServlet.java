package com.home.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.home.domain.Category;
import com.home.domain.Product;
import com.home.service.CategoryService;
import com.home.service.ProductService;
import com.home.service.impl.CategoryServiceImpl;
import com.home.service.impl.ProductServiceImpl;

/**
 *和首页相关的jsp
 */
public class IndexServlet extends BaseServlet {

	public String index(HttpServletRequest request, HttpServletResponse response) {
	/*	//1.调用CategoryService 查询所有的分类  返回值是list
		CategoryService cs = new CategoryServiceImpl();
		List<Category> clist = cs.findAll();
		//2.将返回值放入request域中
		request.setAttribute("clist", clist);

		*/
		
		//去数据库中查询最新商品和热门商品   将他们放入域中请求转发
		ProductService ps = new ProductServiceImpl();
		
		//查询最新商品
		List<Product> newList=null;
		List<Product> hotList =null;
		try {
			newList = ps.findNew();
			//查询热门商品
			hotList = ps.findHot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//将两个list放入域中
		request.setAttribute("nList", newList);
		request.setAttribute("hList", hotList);
		
	return "/jsp/index.jsp";
	}

}
