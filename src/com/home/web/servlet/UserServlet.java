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
 * 和用户相关的方法
 */
public class UserServlet extends BaseServlet {
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("userServlet的add方法执行了");
		return null;
		
	}
	
	/**
	 * //跳转到注册界面
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
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.封装数据
		User user = new User();
		
		//注册自定义转换器
		ConvertUtils.register(new MyConventer(),Date.class);
		
		BeanUtils.populate(user, request.getParameterMap());
		//1.1设置用户id
		user.setUid(UUIDUtils.getId());
		//1.2设置用户激活码
		user.setCode(UUIDUtils.getCode());
		//1.3MD5加密密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		//2.调用service
		UserService s = new UserServiceImpl();
		
		s.regist(user);
		
		//3.页面请求转发
		request.setAttribute("msg", "用户注册已成功，请去邮箱激活吧");
		return "/jsp/msg.jsp";
		
	}
	/**
	 * 用户激活
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取激活码
		String code = request.getParameter("code");
		//2.调用service完成激活
		UserService s=new UserServiceImpl();
		User user = s.active(code);
		
		if (user == null) {
			//通过激活码没有找到用户
			request.setAttribute("msg", "激活失败,请重新激活");
		}else {
			//添加信息
			request.setAttribute("msg", "激活成功");
		}
		//3.请求转发msg.jsp
		
		return "/jsp/msg.jsp";
		
	}
	//跳转到登录界面
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		return "/jsp/login.jsp";
		
	}
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取登录名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		password = MD5Utils.md5(password);
		//2.调用serive完成登录 返回user
		UserService s= new UserServiceImpl();
		User user = s.login(username,password);
		//3.判断用户
		if (user==null) {
			//用户名和密码不匹配
			request.setAttribute("msg", "用户名和密码不匹配");
			
			return "/jsp/login.jsp";
		}else {
			if (Constant.USER_IS_ACTIVE!=user.getState()) {
				//用户未激活
				request.setAttribute("msg", "用户未激活");
				
				return "/jsp/login.jsp";
			}
		}
		
		//4.将user放入session中 重定向  (设置欢迎某某)
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath()+"/");// 等同/store
		return null;
	
	}
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.干掉session
		request.getSession().invalidate();
		//2.重定向
		response.sendRedirect(request.getContextPath());
		//处理自动登录
		
		return null;
		
	}
}
