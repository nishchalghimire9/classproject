package com.rab3tech.admin.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.rab3tech.dao.entity.DebitCardTypeEntity2;
@Repository
public  interface  DebitCardTypeRepository2 extends JpaRepository< DebitCardTypeEntity2, Integer> { //ENTER ENTITY CLASS NAME AND DATA TYPES  OF PRIMARY KEY


	
}
