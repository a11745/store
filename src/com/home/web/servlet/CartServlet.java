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
 * ���ﳵģ��
 */
public class CartServlet extends BaseServlet {
	//��ȡ���ﳵ
	public Cart getCart(HttpServletRequest request){
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//�жϹ��ﳵ�Ƿ�Ϊ��
		if (cart==null) {
			//����һ��cart
			cart = new Cart();
			//��ӵ�session����
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	
    //��ӵ����ﳵ
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.��ȡpid ������
		String pid = request.getParameter("pid");
		int count = Integer.parseInt(request.getParameter("count"));
		//2.����productservice ͨ��pid��ȡһ����Ʒ
		ProductService ps = new ProductServiceImpl();
		Product product = ps.getById(pid);
		
		//3.��װ��cartItem
		CartItem cartItem = new CartItem(product,count);
		//4.��ӵ����ﳵ��
		Cart cart = getCart(request);
		cart.add2Cart(cartItem);
		//5.�ض���
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
		
	}
	
	//�ӹ��ﳵ���Ƴ�������
	public String remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.�Ȼ�ȡ��Ʒ��pid
		String pid = request.getParameter("pid");
		//2.���ù��ﳵ��remove����
		getCart(request).removeFromCart(pid);
		//3.�ض���
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
		
	}
	//��չ��ﳵ
	public String clearAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//2.���ù��ﳵ�ķ���
		getCart(request).clearCart();
		//3.�ض���
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
		
	}

}
