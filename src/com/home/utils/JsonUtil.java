package com.home.utils;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.xml.XMLSerializer;

/**
 * ����json���ݸ�ʽ�Ĺ�����
 * 
 * @Date 2013-3-31
 * @version 1.0
 */
public class JsonUtil {
	/**
	 * ������ת����String���͵�JSON���ݸ�ʽ
	 * 
	 * @param objects
	 * @return
	 */
	public static String array2json(Object[] objects){
		
		JSONArray jsonArray = JSONArray.fromObject(objects);
		return jsonArray.toString();
		
	}
	
	/**
	 * ��list����ת����String���͵�JSON���ݸ�ʽ
	 * 
	 * @param list
	 * @return
	 */
	public static String list2json(List list){
		
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
		
	}
	
	/**
	 * ��map����ת����String���͵�JSON���ݸ�ʽ
	 * 
	 * @param map
	 * @return
	 */
	public static String map2json(Map map){
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
		
	}
	
	/**
	 * ��Object����ת����String���͵�JSON���ݸ�ʽ
	 * 
	 * @param object
	 * @return
	 */
	public static String object2json(Object object){
		
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject.toString();
		
	}
	
	/**
	 * ��XML���ݸ�ʽת����String���͵�JSON���ݸ�ʽ
	 * 
	 * @param xml
	 * @return
	 */
	public static String xml2json(String xml){
		
		JSONArray jsonArray = (JSONArray) new XMLSerializer().read(xml);
		return jsonArray.toString();
		
	}
	
	/**
	  * ��ȥ�������ɵ��ֶΣ��ر��ʺ�ȥ�������Ķ���
	  *
	  * @param excludes
	  * @return
	*/
	public static JsonConfig configJson(String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		jsonConfig.setIgnoreDefaultExcludes(true);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		return jsonConfig;
	}
	
}
