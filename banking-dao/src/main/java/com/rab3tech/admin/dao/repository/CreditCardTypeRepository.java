package com.rab3tech.admin.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rab3tech.dao.entity.CreditCardTypeEntity;
@Repository
public  interface  CreditCardTypeRepository extends JpaRepository< CreditCardTypeEntity, Integer> { // enter entity class name and data types of primary key

	
}
