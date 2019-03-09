package com.home.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.home.domain.Category;
import com.home.domain.Product;
import com.home.service.ProductService;
import com.home.service.impl.ProductServiceImpl;
import com.home.utils.UUIDUtils;
import com.home.utils.UploadUtils;


public class AddProductServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			//0.创建map 用来放入前台传递的数据
			HashMap<String, Object> map = new HashMap<>();
			//创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//创建核心上传对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析request
			List<FileItem> list = upload.parseRequest(request);
			//遍历集合
			for (FileItem fi : list) {
				//判断是否是普通的上传组件
				if (fi.isFormField()) {
					//普通上传组件
					map.put(fi.getFieldName(), fi.getString("utf-8"));
				}else {
					//文件上传组件
					//获取文件名称
					String name = fi.getName();
					//获取文件真实名称
					String realName = UploadUtils.getRealName(name);
					//获取随机名称
					String uuidName = UploadUtils.getUUIDName(realName);
					
					//获取文件的存放路径
					String path = this.getServletContext().getRealPath("/products/1");
					
					
					//获取文件流
					InputStream is = fi.getInputStream();
					//保存图片
					FileOutputStream os = new FileOutputStream(new File(path,uuidName));
					IOUtils.copy(is, os);
					
					os.close();
					is.close();
					//删除临时文件
					fi.delete();
					//在map中设置文件的路径
					map.put(fi.getFieldName(), "products/1/"+uuidName);
					
				}
			}
			
			
			
			//1.封装参数
			Product p = new Product();
			BeanUtils.populate(p, map);
			//1.1商品id
			p.setPid(UUIDUtils.getId());
			//1.2商品时间
			//Data data = new Data();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String format = sdf.format(new Date());
			p.setPdate(format);
			//1.3封装category
			Category c = new Category();
			c.setCid((String)map.get("cid"));
			p.setCategory(c);
			//2调用service 完成添加操作
			ProductService ps = new ProductServiceImpl();
			ps.add(p);
			//3.页面重定向
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "商品添加失败");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
