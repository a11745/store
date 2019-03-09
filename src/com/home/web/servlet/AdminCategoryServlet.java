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
	//��̨�������ģ��
public class AdminCategoryServlet extends BaseServlet {
	//չʾ���з���
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����categoryservice ��ѯ���еķ�����Ϣ ����ֵ list
		CategoryService cs = new CategoryServiceImpl();
		List<Category> list = cs.findAll();
		//2.��list����request���� ����ת��
		request.setAttribute("list", list);
		return "/admin/category/list.jsp";
	}
	//��ת�����ҳ����
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/admin/category/add.jsp";
	}
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����cname
		String cname = request.getParameter("cname");
		
		//2.��װcategory
		Category c = new Category();
		c.setCid(UUIDUtils.getId());
		c.setCname(cname);
		//3.����service�����Ӳ���
		CategoryService cs = new CategoryServiceImpl();
		cs.add(c);
		//4.�ض��� ��ѯ���в���
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����id
		String cid = request.getParameter("cid");
		//2.����service��ɲ�ѯ���� ����ֵcategory
		CategoryService cs = new CategoryServiceImpl();
		Category c = cs.getById(cid);
		//3.��category����request���� ����ױ��/admin/category/edit.jsp
		request.setAttribute("bean", c);
		return "/admin/category/edit.jsp";
	}
	//���ķ�����Ϣ
	public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.��ȡcid cname
		
		//2.��װ����
		Category c = new Category();
		c.setCid(request.getParameter("cid"));
		c.setCname(request.getParameter("cname"));
		//3.����service  ��ɸ��²���
		CategoryService sc = new CategoryServiceImpl();
		sc.update(c);
		//4.�ض��� ��ѯ����
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		return null;
	}
	//ɾ������
	public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.��ȡcid
		String cid = request.getParameter("cid");
		//����service ���ɾ��
		CategoryService cs = new  CategoryServiceImpl();
		cs.delete(cid);
		//�ض���
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		
		return null;
	}
	
		
}
