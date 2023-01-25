package com.rab3tech.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab3tech.admin.dao.repository.CreditCardTypeRepository;
import com.rab3tech.admin.service.CreditCardTypeService;
import com.rab3tech.dao.entity.CreditCardTypeEntity;
import com.rab3tech.vo.CreditCardTypeVO;

@Service
public class CreditCardTypeServiceImpl implements CreditCardTypeService  {

	@Autowired
	private CreditCardTypeRepository creditCardTypeRepository;
	
	@Override
	public void save(CreditCardTypeVO creditCardTypeVO) { 
		CreditCardTypeEntity creditCardTypeEntity = new CreditCardTypeEntity(); // here we create object of entity to copy from Vo
		BeanUtils.copyProperties
		(creditCardTypeVO, creditCardTypeEntity); //
		creditCardTypeRepository.save(creditCardTypeEntity);
		
	}
		
	@Override
		public List<CreditCardTypeVO> showCreditCardTypeList(){
			List<CreditCardTypeVO> creditCardTypeList=new ArrayList<>();
			//CreditCardTypeVO cardTypeVO=null;
			 
			List<CreditCardTypeEntity> cardTypeEntity=creditCardTypeRepository.findAll();
			for(CreditCardTypeEntity entity:cardTypeEntity) {
				CreditCardTypeVO cardTypeVO =new CreditCardTypeVO(); ///
				BeanUtils.copyProperties(entity, cardTypeVO);
				creditCardTypeList.add(cardTypeVO);
			}
			return creditCardTypeList;
		}

}