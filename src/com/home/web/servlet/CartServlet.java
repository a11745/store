package com.home.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.naming.factory.BeanFactory;

import com.home.domain.Cart;
import com.home.domain.CartItem;
import com.home.domain.Product;
import com.home.service.ProductService;
import com.home.service.impl.ProductServiceImpl;

/**
 * 购物车模块
 */
public class CartServlet extends BaseServlet {
	//获取购物车
	public Cart getCart(HttpServletRequest request){
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//判断购物车是否为空
		if (cart==null) {
			//创建一个cart
			cart = new Cart();
			//添加到session域中
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	
    //添加到购物车
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取pid 和数量
		String pid = request.getParameter("pid");
		int count = Integer.parseInt(request.getParameter("count"));
		//2.调用productservice 通过pid获取一个商品
		ProductService ps = new ProductServiceImpl();
		Product product = ps.getById(pid);
		
		//3.组装成cartItem
		CartItem cartItem = new CartItem(product,count);
		//4.添加到购物车中
		Cart cart = getCart(request);
		cart.add2Cart(cartItem);
		//5.重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
		
	}
	
	//从购物车中移除购物项
	public String remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.先获取商品的pid
		String pid = request.getParameter("pid");
		//2.调用购物车的remove方法
		getCart(request).removeFromCart(pid);
		//3.重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
		
	}
	//清空购物车
	public String clearAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//2.调用购物车的方法
		getCart(request).clearCart();
		//3.重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
		
	}

}
