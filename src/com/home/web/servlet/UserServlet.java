package com.home.web.servlet;

import java.io.IOException;
import java.util.Date;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.home.constant.Constant;
import com.home.domain.User;
import com.home.myconventer.MyConventer;
import com.home.service.UserService;
import com.home.service.impl.UserServiceImpl;
import com.home.utils.MD5Utils;
import com.home.utils.UUIDUtils;

/**
 * ���û���صķ���
 */
public class UserServlet extends BaseServlet {
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("userServlet��add����ִ����");
		return null;
		
	}
	
	/**
	 * //��ת��ע�����
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		return "/jsp/register.jsp";
		
	}
	/**
	 * �û�ע��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.��װ����
		User user = new User();
		
		//ע���Զ���ת����
		ConvertUtils.register(new MyConventer(),Date.class);
		
		BeanUtils.populate(user, request.getParameterMap());
		//1.1�����û�id
		user.setUid(UUIDUtils.getId());
		//1.2�����û�������
		user.setCode(UUIDUtils.getCode());
		//1.3MD5��������
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		//2.����service
		UserService s = new UserServiceImpl();
		
		s.regist(user);
		
		//3.ҳ������ת��
		request.setAttribute("msg", "�û�ע���ѳɹ�����ȥ���伤���");
		return "/jsp/msg.jsp";
		
	}
	/**
	 * �û�����
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.��ȡ������
		String code = request.getParameter("code");
		//2.����service��ɼ���
		UserService s=new UserServiceImpl();
		User user = s.active(code);
		
		if (user == null) {
			//ͨ��������û���ҵ��û�
			request.setAttribute("msg", "����ʧ��,�����¼���");
		}else {
			//�����Ϣ
			request.setAttribute("msg", "����ɹ�");
		}
		//3.����ת��msg.jsp
		
		return "/jsp/msg.jsp";
		
	}
	//��ת����¼����
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		return "/jsp/login.jsp";
		
	}
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.��ȡ��¼��������
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = MD5Utils.md5(password);
		//2.����serive��ɵ�¼ ����user
		UserService s= new UserServiceImpl();
		User user = s.login(username,password);
		//3.�ж��û�
		if (user==null) {
			//�û��������벻ƥ��
			request.setAttribute("msg", "�û��������벻ƥ��");
			
			return "/jsp/login.jsp";
		}else {
			if (Constant.USER_IS_ACTIVE!=user.getState()) {
				//�û�δ����
				request.setAttribute("msg", "�û�δ����");
				
				return "/jsp/login.jsp";
			}
		}
		
		//4.��user����session�� �ض���  (���û�ӭĳĳ)
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/");// ��ͬ/store
		return null;
	
	}
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.�ɵ�session
		request.getSession().invalidate();
		//2.�ض���
		response.sendRedirect(request.getContextPath());
		//�����Զ���¼
		
		return null;
		
	}
}
