package com.lmg.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.lmg.mybatis.bean.Employee;

public interface EmployeeMapper {
	
	//������¼��װһ��map: Map<Integer,Employee>:����������¼������,ֵ�Ƿ�װ���javaBean
	//����mybatis��װ��� map ��ʱ��ʹ���ĸ�������Ϊ map �� key
	@MapKey("lastName")
	public Map<Integer,Employee> getEmpByLastNameLikeReturnMap(String lastName);
	
	//����һ����¼��map,key��������,ֵ���Ƕ�Ӧ��ֵ
	public Map<String,Object> getEmpsByIdReturnMap(Integer id);
	
	public List<Employee> getEmpsByLastNameLike(String lastName);
	
	public Employee getEmpByMap(Map<String, Object> map);
	
	public Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);
	
	public Employee getEmpById(Integer id);
	
	public Long addEmp(Employee employee);
	
	public Boolean updateEmp(Employee employee);
	
	public void deleteEmpById(Integer id);
	
}
