package com.home.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.home.domain.PageBean;
import com.home.domain.Product;
import com.home.service.ProductService;
import com.home.service.impl.ProductServiceImpl;

/**
 * ]��Ʒ
 */
public class ProductServlet extends BaseServlet {
	

	/**
	 * ͨ��id��ѯ������Ʒ����
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ��Ʒid
		String pid = request.getParameter("pid");
		
		//2.����service
		ProductService ps = new ProductServiceImpl();
		Product p = null;
		try {
			p = ps.getById(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//3.���������request����
		request.setAttribute("bean", p);
		return "/jsp/product_info.jsp";
	}
	//��ҳ��ѯ����
	public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��ȡ��� ��ǰҳ ����һ��pagesize
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 12;
		//2.����service ����ֵpagebean
		ProductService ps = new ProductServiceImpl();
		PageBean<Product> bean=null;
		try {
			bean = ps.findByPage(currPage,pageSize,cid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//3.���������request����
		request.setAttribute("pb", bean);
		return "/jsp/product_list.jsp";
	}

}
