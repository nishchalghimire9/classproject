package com.rab3tech.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab3tech.admin.dao.repository.DebitCardTypeRepository2;
import com.rab3tech.admin.service.DebitCardTypeService;
import com.rab3tech.dao.entity.DebitCardTypeEntity2;
import com.rab3tech.vo.DebitCardTypeVO;

@Service
public class DebitCardTypeServiceImpl implements DebitCardTypeService {
	@Autowired
	private DebitCardTypeRepository2 debitCardTypeRepository2; // here we autowired Debitcard reopsitory.

	@Override
	public void save(DebitCardTypeVO debitCardTypeVO) {
		DebitCardTypeEntity2 debitcardtypeEntity = new DebitCardTypeEntity2(); // here creating an object of entity
																				// class
		BeanUtils.copyProperties(debitCardTypeVO, debitcardtypeEntity); // copy data from debitcardVO to entity
		debitCardTypeRepository2.save(debitcardtypeEntity);

	}

	// when we retrieve data from the data base this is first line to be created
	// this code is for retrieving the data
	
	@Override
	public List<DebitCardTypeVO> showDebitCardTypeList() {
		
		List<DebitCardTypeVO> debitcardtypeList = new ArrayList<>(); // this is blank list.
		DebitCardTypeVO cardTypeVO = (null); // again create another blank object.
		List<DebitCardTypeEntity2> cardTypeEntity = debitCardTypeRepository2.findAll(); // data will come here inside the  cardTypeEntity
																			
		for (DebitCardTypeEntity2 entity : cardTypeEntity) {
			cardTypeVO = new DebitCardTypeVO(); // create aobject of Debit card vo
			BeanUtils.copyProperties(entity, cardTypeVO);
			debitcardtypeList.add(cardTypeVO);
		}
		return debitcardtypeList;
	}

}
