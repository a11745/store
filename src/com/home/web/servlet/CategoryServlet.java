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

  //��ѯ���еķ���
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����CategoryService ��ѯ���еķ���  ����ֵ��list
		CategoryService cs = new CategoryServiceImpl();
		List<Category> clist = cs.findAll();
		//2.������ֵת��json��ʽ ���ص���ҳ��
		//request.setAttribute("clist", clist);
		//2.1.����jar��
		//2.2.���빤����
		String json = JsonUtil.list2json(clist);
		//3д��ȥ 
		//������Ӧ��������
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().println(json);
				
		
		
		return null;
	}

}
