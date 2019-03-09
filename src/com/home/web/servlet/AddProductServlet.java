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
			//0.����map ��������ǰ̨���ݵ�����
			HashMap<String, Object> map = new HashMap<>();
			//���������ļ����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//���������ϴ�����
			ServletFileUpload upload = new ServletFileUpload(factory);
			//����request
			List<FileItem> list = upload.parseRequest(request);
			//��������
			for (FileItem fi : list) {
				//�ж��Ƿ�����ͨ���ϴ����
				if (fi.isFormField()) {
					//��ͨ�ϴ����
					map.put(fi.getFieldName(), fi.getString("utf-8"));
				}else {
					//�ļ��ϴ����
					//��ȡ�ļ�����
					String name = fi.getName();
					//��ȡ�ļ���ʵ����
					String realName = UploadUtils.getRealName(name);
					//��ȡ�������
					String uuidName = UploadUtils.getUUIDName(realName);
					
					//��ȡ�ļ��Ĵ��·��
					String path = this.getServletContext().getRealPath("/products/1");
					
					
					//��ȡ�ļ���
					InputStream is = fi.getInputStream();
					//����ͼƬ
					FileOutputStream os = new FileOutputStream(new File(path,uuidName));
					IOUtils.copy(is, os);
					
					os.close();
					is.close();
					//ɾ����ʱ�ļ�
					fi.delete();
					//��map�������ļ���·��
					map.put(fi.getFieldName(), "products/1/"+uuidName);
					
				}
			}
			
			
			
			//1.��װ����
			Product p = new Product();
			BeanUtils.populate(p, map);
			//1.1��Ʒid
			p.setPid(UUIDUtils.getId());
			//1.2��Ʒʱ��
			//Data data = new Data();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String format = sdf.format(new Date());
			p.setPdate(format);
			//1.3��װcategory
			Category c = new Category();
			c.setCid((String)map.get("cid"));
			p.setCategory(c);
			//2����service �����Ӳ���
			ProductService ps = new ProductServiceImpl();
			ps.add(p);
			//3.ҳ���ض���
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msg", "��Ʒ���ʧ��");
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
