package com.rab3tech.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rab3tech.admin.dao.repository.EmployeeInfoRepositoy;
import com.rab3tech.admin.service.EmployeePersonalService;
import com.rab3tech.dao.entity.EmployeeInfoEntity;
import com.rab3tech.vo.EmployeeVO;

@Service
public class EmployeeInfoimpl implements EmployeePersonalService {

	@Autowired
	private EmployeeInfoRepositoy employeeInfoRepositoy;

	// LOGIC TO SAVE THE DATA INTO THE DATA BASE.
	@Override
	public void save(EmployeeVO employeeVO) {
		EmployeeInfoEntity employeeInfoEntity = new EmployeeInfoEntity();
		BeanUtils.copyProperties(employeeVO, employeeInfoEntity);
		employeeInfoRepositoy.save(employeeInfoEntity);
	}

	// LOGIC TO DELETE THE DATA
	@Override
	public void deleteById(int id) {
		employeeInfoRepositoy.deleteById(id);
	}

	// LOGIC TO FETCH THE RECORD BEFORE WE UPDATE THE DATA
	@Override
	public EmployeeVO findById(int id) { // it will return optional so we need get method
		EmployeeInfoEntity employeeInfoEn = employeeInfoRepositoy.findById(id).get();
		EmployeeVO employeeVO = new EmployeeVO();
		BeanUtils.copyProperties(employeeInfoEn, employeeVO);
		return employeeVO;
	}
	// LOGIC TO UPDATE THE DATA  AFTER WE FETCH MAKE UPDATE 
	@Override
	@Transactional // IT IS LOADING THE DATA INTO PERSISTNACE CONTEXT AND CHANGING THE STATE 
	public void updateById(EmployeeVO employeeVO) { // it will return optional so we need get method
		EmployeeInfoEntity employeeInfoEn = employeeInfoRepositoy.findById(employeeVO.getCid()).get();
		employeeInfoEn.setFirstname(employeeVO.getFirstname());
		employeeInfoEn.setLastname(employeeVO.getLastname());
		employeeInfoEn.setAddress(employeeVO.getAddress());
		employeeInfoEn.setContactnumber(employeeVO.getContactnumber());
		employeeInfoEn.setEmail(employeeVO.getEmail());
		employeeInfoEn.setGender(employeeVO.getGender());
		 
	}
	
	// THIS IS JAVA 8 TO RETRIVE THE DATA FROM THE DATA BASE
	@Override
	public List<EmployeeVO> findAll() {
		List<EmployeeInfoEntity> employeeinfoEntity = employeeInfoRepositoy.findAll();
		return employeeinfoEntity.stream().map(employeeInfoEn -> {
			EmployeeVO employeeVO = new EmployeeVO();
			BeanUtils.copyProperties(employeeInfoEn, employeeVO);
			return employeeVO;
		}).collect(Collectors.toList());
	}

	// THIS IS REGULAR METHOD WE CAN USE EITHER ONE.

	// THIS LOGIC IS TO RETRIVE THE DATA FROM THE DATA BASE AND TO SHOW INTO THE
	// FRONT END.
//	@Override
//	public List<EmployeeVO> showEmployeelist(){ 
//		List<EmployeeVO> employeeInfo = new ArrayList<>(); // this is A blank list.
//		EmployeeVO employeeVO = (null); // this is also blank list.
//		List<EmployeeInfoEntity>employeeEntity= employeeInfoRepositoy.findAll();//data will come here inside the list 
//		
//		for(EmployeeInfoEntity emply:employeeEntity ) {
//			
//			employeeVO = new EmployeeVO();
//			BeanUtils.copyProperties(emply, employeeVO);
//			employeeInfo.add(employeeVO);
//		}
//		return employeeInfo;	
//	}

}
