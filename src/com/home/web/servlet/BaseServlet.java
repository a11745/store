package com.home.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ͨ�õ�servlet
 * ͨ�������ȡ����servlet  ����дͨ�÷���
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//��дservice
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		//1.��ȡ����    ����������ߵ��������ʱ��  this��������������
		Class clazz = this.getClass();
		//System.out.println(this);
		//2.��ȡ����ķ���
		String m = request.getParameter("method");
		if (m==null) {
			m="index";
		}
		
		//System.out.println(m);
		//3.��ȡ��������
		
		Method method = clazz.getMethod(m, HttpServletRequest.class,HttpServletResponse.class);
		
		//4.�÷���ִ��  ����ֵΪ����ת����·��
		String s = (String) method.invoke(this, request,response);
		//5.�ж�s�Ƿ�Ϊ��
		if (s!=null) {
			request.getRequestDispatcher(s).forward(request, response);
		} 
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}
	
}
