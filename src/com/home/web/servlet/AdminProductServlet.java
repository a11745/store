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
	//后台商品管理
public class AdminProductServlet extends BaseServlet {

	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.调用servic 查询所有 返回list集合
		ProductService ps = new ProductServiceImpl();
		List<Product> list = ps.findAll();
		//2.将list放入request域中 请求妆发
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";
		}
	//跳转到添加商品的页面
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查询所有的分类返回一个list
		CategoryService cs = new CategoryServiceImpl();
		List<Category> cList = cs.findAll();
		//将list放入request域中
		request.setAttribute("clist", cList);
		return "/admin/product/add.jsp";
		}

}
