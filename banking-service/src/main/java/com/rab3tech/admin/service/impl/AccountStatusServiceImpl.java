package com.rab3tech.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.admin.dao.repository.AccountStatusRepository;
import com.rab3tech.admin.service.AccountStatusService;
import com.rab3tech.dao.entity.AccountStatus;
import com.rab3tech.vo.AccountStatusVO;

@Service
public class AccountStatusServiceImpl implements AccountStatusService {
	@Autowired
	private AccountStatusRepository accountStatusRepository;

	// this is a code to edit the the input.
	// fetching the data to edit first
	@Override
	public AccountStatusVO findById(int id) {
		AccountStatus accountStatus = accountStatusRepository.findById(id).get();
		AccountStatusVO accountStatusVO = new AccountStatusVO();
		BeanUtils.copyProperties(accountStatus, accountStatusVO);
		return accountStatusVO;
	}

	@Override // after making change we need to update,
	@Transactional
	public void updateById(AccountStatusVO accountStatusVO ) {
		AccountStatus accountstatus = accountStatusRepository.findById(accountStatusVO.getId()).get();
		accountstatus.setCode(accountStatusVO.getCode());
		accountstatus.setDescription(accountStatusVO.getDescription());
		accountstatus.setName(accountStatusVO.getName()); 
		
	}

	@Override
	public void deleteById(int id) {
		accountStatusRepository.deleteById(id);

	}

	@Override
	public List<AccountStatusVO> findAll() {

		List<AccountStatus> accountStatusList = accountStatusRepository.findAll(); // now we are converting
																					// AccountStatus to AccountStatusVO
		return accountStatusList.stream().map(accountStatus -> {
			AccountStatusVO accountStatusVO = new AccountStatusVO();
			BeanUtils.copyProperties(accountStatus, accountStatusVO);
			return accountStatusVO;
		}).collect(Collectors.toList());

	}

}
