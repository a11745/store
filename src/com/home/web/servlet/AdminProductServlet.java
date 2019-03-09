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
	//��̨��Ʒ����
public class AdminProductServlet extends BaseServlet {

	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����servic ��ѯ���� ����list����
		ProductService ps = new ProductServiceImpl();
		List<Product> list = ps.findAll();
		//2.��list����request���� ����ױ��
		request.setAttribute("list", list);
		return "/admin/product/list.jsp";
		}
	//��ת�������Ʒ��ҳ��
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//��ѯ���еķ��෵��һ��list
		CategoryService cs = new CategoryServiceImpl();
		List<Category> cList = cs.findAll();
		//��list����request����
		request.setAttribute("clist", cList);
		return "/admin/product/add.jsp";
		}

}
