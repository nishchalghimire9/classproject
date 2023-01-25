package com.rab3tech.admin.service;

import java.util.List;

import com.rab3tech.vo.AccountStatusVO;

public interface AccountStatusService {

	List<AccountStatusVO> findAll();

	void deleteById(int id);

	AccountStatusVO findById(int id);

	void updateById(AccountStatusVO accountStatusVO);



}
