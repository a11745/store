package com.home.service;

import java.util.List;

import com.home.domain.Category;

public interface CategoryService {

	List<Category> findAll() throws Exception;

	void add(Category c) throws Exception;

	Category getById(String cid)throws Exception;

	void update(Category c)throws Exception;

	void delete(String cid)throws Exception;
	
}
