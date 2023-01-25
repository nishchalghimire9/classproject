package com.rab3tech.admin.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rab3tech.dao.entity.EmployeeInfoEntity;

@Repository 
public interface  EmployeeInfoRepositoy  extends  JpaRepository<EmployeeInfoEntity, Integer> {
	
}

