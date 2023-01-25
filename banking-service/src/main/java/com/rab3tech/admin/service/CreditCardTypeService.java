package com.rab3tech.admin.service;

import java.util.List;

import com.rab3tech.vo.CreditCardTypeVO;

public interface CreditCardTypeService {

	void save(CreditCardTypeVO creditCardTypeVO);

	List<CreditCardTypeVO> showCreditCardTypeList();

}
