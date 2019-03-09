package com.home.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.home.domain.Category;
import com.home.service.CategoryService;
import com.home.service.impl.CategoryServiceImpl;
import com.home.utils.UUIDUtils;
	//后台分类管理模块
public class AdminCategoryServlet extends BaseServlet {
	//展示所有分类
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.调用categoryservice 查询所有的分类信息 返回值 list
		CategoryService cs = new CategoryServiceImpl();
		List<Category> list = cs.findAll();
		//2.将list放入request域中 请求转发
		request.setAttribute("list", list);
		return "/admin/category/list.jsp";
	}
	//跳转到添加页面上
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/admin/category/add.jsp";
	}
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.接受cname
		String cname = request.getParameter("cname");
		
		//2.封装category
		Category c = new Category();
		c.setCid(UUIDUtils.getId());
		c.setCname(cname);
		//3.调用service完成添加操作
		CategoryService cs = new CategoryServiceImpl();
		cs.add(c);
		//4.重定向 查询所有操作
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.接受id
		String cid = request.getParameter("cid");
		//2.调用service完成查询操作 返回值category
		CategoryService cs = new CategoryServiceImpl();
		Category c = cs.getById(cid);
		//3.将category放入request域中 请求妆发/admin/category/edit.jsp
		request.setAttribute("bean", c);
		return "/admin/category/edit.jsp";
	}
	//更改分类信息
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取cid cname
		
		//2.封装参数
		Category c = new Category();
		c.setCid(request.getParameter("cid"));
		c.setCname(request.getParameter("cname"));
		//3.调用service  完成更新操作
		CategoryService sc = new CategoryServiceImpl();
		sc.update(c);
		//4.重定向到 查询所有
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	//删除分类
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取cid
		String cid = request.getParameter("cid");
		//调用service 完成删除
		CategoryService cs = new  CategoryServiceImpl();
		cs.delete(cid);
		//重定向
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		
		return null;
	}
	
		
}
