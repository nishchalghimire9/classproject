package com.rab3tech.admin.service;

import java.util.List;

import com.rab3tech.vo.EmployeeVO;

public interface EmployeePersonalService {

		void save(EmployeeVO employeeVO);

		List<EmployeeVO> findAll();

		void deleteById(int id);

		EmployeeVO findById(int id);

		void updateById(EmployeeVO employeeVO);


			//	List<EmployeeVO> showEmployeelist();
		
		
	}
