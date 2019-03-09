package com.home.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.home.domain.Order;
import com.home.domain.OrderItem;
import com.home.domain.PageBean;
import com.home.service.OrderService;
import com.home.service.impl.OrderServiceImpl;
import com.home.utils.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class AdminOrderServlet extends BaseServlet {
	//������ж���
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrderService os = new OrderServiceImpl();
		
		List<Order> bean = os.findAll();
		request.setAttribute("orderbean", bean);
		return "/admin/order/list.jsp";
		
	}
	
	public String getDetailByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		//1.����oid
		String oid = request.getParameter("oid");
		//2.����service ��ѯ�������� ����ֵlist<OrderItem>
		OrderService os = new OrderServiceImpl();
		List<OrderItem> items = os.getById(oid).getItems();
		
		//3.��listת��json д��
		//�����ų�����д��ȥ������
		JsonConfig config = JsonUtil.configJson(new String[]{"class","itemid","order"});
		JSONArray json = JSONArray.fromObject(items, config);
		System.out.println(json);
		response.getWriter().println(json);
		return null;
		
	}
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.����oid��state
		String oid = request.getParameter("oid");
		//String state = request.getParameter("state");
		//2.����service
		OrderService os = new OrderServiceImpl();
		Order order = os.getById(oid);
		order.setState(2);
		
		os.updateOrder(order);
		
		//3.ҳ���ض���
		response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAll");
		return null;
		
	}

}
