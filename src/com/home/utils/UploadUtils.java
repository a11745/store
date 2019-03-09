package com.home.utils;

import java.util.UUID;

public class UploadUtils {
	/**
	 * ��ȡ�������
	 * @param realName ��ʵ����
	 * @return uuid
	 */
	public static String getUUIDName(String realName){
		//realname  ������  1.jpg   Ҳ������  1
		//��ȡ��׺��
		int index = realName.lastIndexOf(".");
		if(index==-1){
			return UUID.randomUUID().toString().replace("-", "").toUpperCase();
		}else{
			return UUID.randomUUID().toString().replace("-", "").toUpperCase()+realName.substring(index);
		}
	}
	
	/**
	 * ��ȡ�ļ���ʵ����
	 * @param name
	 * @return
	 */
	public static String getRealName(String name){
		// c:/upload/1.jpg    1.jpg
		//��ȡ���һ��"/"
		int index = name.lastIndexOf("\\");
		return name.substring(index+1);
	}
	
	/**
	 * ��ȡ�ļ�Ŀ¼
	 * @param name �ļ�����
	 * @return Ŀ¼
	 */
	public static String getDir(String name){
		int i = name.hashCode();
		String hex = Integer.toHexString(i);
		int j=hex.length();
		for(int k=0;k<8-j;k++){
			hex="0"+hex;
		}
		return "/"+hex.charAt(0)+"/"+hex.charAt(1);
	}
	
	public static void main(String[] args) {
		//String s="G:\\day17-������ǿ\\resource\\1.jpg";
		String s="1.jgp";
		String realName = getRealName(s);
		//System.out.println(realName);
		
		String uuidName = getUUIDName(realName);
		//System.out.println(uuidName);
		
		String dir = getDir(realName);
		System.out.println(dir);
		
		
	}
}
