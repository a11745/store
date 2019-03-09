package com.home.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.home.domain.Category;
import com.home.service.CategoryService;
import com.home.service.impl.CategoryServiceImpl;
import com.home.utils.JsonUtil;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {

  //查询所有的分类
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.调用CategoryService 查询所有的分类  返回值是list
		CategoryService cs = new CategoryServiceImpl();
		List<Category> clist = cs.findAll();
		//2.将返回值转成json格式 返回到首页上
		//request.setAttribute("clist", clist);
		//2.1.导入jar包
		//2.2.导入工具类
		String json = JsonUtil.list2json(clist);
		//3写回去 
		//设置响应中文乱码
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(json);
				
		
		
		return null;
	}

}
