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
 * ]商品
 */
public class ProductServlet extends BaseServlet {
	

	/**
	 * 通过id查询单个商品详情
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取商品id
		String pid = request.getParameter("pid");
		
		//2.调用service
		ProductService ps = new ProductServiceImpl();
		Product p = null;
		try {
			p = ps.getById(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//3.将结果放入request域中
		request.setAttribute("bean", p);
		return "/jsp/product_info.jsp";
	}
	//分页查询数据
	public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取类别 当前页 设置一个pagesize
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 12;
		//2.调用service 返回值pagebean
		ProductService ps = new ProductServiceImpl();
		PageBean<Product> bean=null;
		try {
			bean = ps.findByPage(currPage,pageSize,cid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//3.将结果放入request域中
		request.setAttribute("pb", bean);
		return "/jsp/product_list.jsp";
	}

}
