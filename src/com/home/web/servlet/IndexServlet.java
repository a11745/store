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
 *����ҳ��ص�jsp
 */
public class IndexServlet extends BaseServlet {

	public String index(HttpServletRequest request, HttpServletResponse response) {
	/*	//1.����CategoryService ��ѯ���еķ���  ����ֵ��list
		CategoryService cs = new CategoryServiceImpl();
		List<Category> clist = cs.findAll();
		//2.������ֵ����request����
		request.setAttribute("clist", clist);

		*/
		
		//ȥ���ݿ��в�ѯ������Ʒ��������Ʒ   �����Ƿ�����������ת��
		ProductService ps = new ProductServiceImpl();
		
		//��ѯ������Ʒ
		List<Product> newList=null;
		List<Product> hotList =null;
		try {
			newList = ps.findNew();
			//��ѯ������Ʒ
			hotList = ps.findHot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//������list��������
		request.setAttribute("nList", newList);
		request.setAttribute("hList", hotList);
		
	return "/jsp/index.jsp";
	}

}
