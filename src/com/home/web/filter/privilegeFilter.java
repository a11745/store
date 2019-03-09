package com.home.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.home.domain.User;

public class privilegeFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//1.ǿת
		HttpServletRequest request  = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		//2.ҵ���߼�
		//��session�л�ȡuser �ж�user�Ƿ�Ϊ�� ��Ϊ�� ����ת��
		User user = (User) request.getSession().getAttribute("user");
		if (user==null) {
			request.setAttribute("msg", "����û�е�¼,���ȵ�¼");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return;
		}
		
		//3.����
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
