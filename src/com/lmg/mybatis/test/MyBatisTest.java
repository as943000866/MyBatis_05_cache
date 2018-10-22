package com.lmg.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.lmg.mybatis.bean.Department;
import com.lmg.mybatis.bean.Employee;
import com.lmg.mybatis.dao.DepartmentMapper;
import com.lmg.mybatis.dao.EmployeeMapper;

public class MyBatisTest {
	
	private SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-conf.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	/**
	 * ��������
	 * һ������:(���ػ���):sqlSession ����Ļ��档һ��������һֱ�����ġ�SqlSession �����һ�� Map
	 * 		�����ݿ�ͬһ�λỰ�ڼ��ѯ�������ݻ�ŵ����ػ�����
	 * 		�Ժ������Ҫ��ȡ��ͬ������, ֱ�Ӵӻ�������, û��Ҫ��ȥ��ѯ���ݿ�
	 * 
	 * 		һ������ʧЧ��� (û��ʹ�õ���ǰһ��������,Ч������,����Ҫ�������ݿⷢ����ѯ)
	 * 		1.sqlSession ��ͬ
	 * 		2.sqlSession ��ͬ,��ѯ������ͬ.(��ǰһ�������л�û���������)
	 * 		3.sqlSession ��ͬ,���β�ѯ֮��ִ������ɾ�Ĳ���(�����ɾ�Ŀ��ܶԵ�ǰ������Ӱ��)
	 * 		4.sqlSession ��ͬ,�ֶ������һ������ (�������)
	 * ��������: (ȫ�ֻ���) : ���� namespace ����Ļ���; һ�� namespace ��Ӧһ����������
	 * 		��������:
	 * 		1.һ���Ự,��ѯһ������,������ݾͻᱻ���ڵ�ǰ�Ự��һ��������;
	 * 		2.����Ự�ر�;һ�������е����ݻᱻ���浽����������;�µĻỰ��ѯ��Ϣ,�Ϳ��Բ��ն������������;
	 * 		3.sqlSession==EmployeeMapper==>Employee
	 * 						DepartmentMapper==>Department
	 * 			��ͬ namespace  ��������ݻᱻ�����Լ���Ӧ�Ļ����� (Map)
	 * 			Ч��:���ݻ�Ӷ��������л�ȡ
	 * 				��������ݶ���Ĭ���ȷ���һ�������С�
	 * 				ֻ�лỰ�ύ���߹ر��Ժ�,һ�������е����ݲŻ�ת�嵽����������
	 * 		ʹ��:
	 * 			1).����ȫ�ֶ�����������:	<setting name="cacheEnabled" value="true"/>
	 * 			2).ȥ mapper.xml ������ʹ�ö�������
	 * 				<cache></cache>
	 * 			3).���� POJO ��Ҫʵ�����л��ӿ�
	 * 
	 * �ͻ����йص�����/����:
	 * 			1).cacheEnable=true;false:�رջ���(��������ر�)(һ������һֱ���õ�)
	 * 			2).ÿ�� select ��ǩ���� useCache="true":
	 * 					false:��ʹ�û���(һ��������Ȼʹ��,�������治ʹ��)
	 * 			3).��ÿ����ɾ�ı�ǩ��:flushCache="true";(һ�������涼�����)��
	 * 					��ɾ��ִ����ɺ�ͻ��������
	 * 					����:flushCache="true": һ������������;��������Ҳ�����
	 * 					��ѯ��ǩ:flushCache="false"
	 * 						��� flushCache="true";ÿ�β�ѯ֮�󶼻���ջ���;������û�б�ʹ�õ�
	 * 			4).sqlSession.clearCache();ֻ�������ǰ session ��һ������;
	 * 			5).localCacheScope:���ػ���������: (һ������ SESSION); ��ǰ�Ự���������ݱ����ڻỰ������;
	 * 								STATEMENT:���Խ���һ������
	 * ��������������:
	 * 		1).������������������
	 * 		2).������������������ϵ������;�ٷ���
	 * 		3).mapper.xml��ʹ���Զ��建��
	 */
	
	@Test
	public void testSecondLevelCache02() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		SqlSession openSession2 = sqlSessionFactory.openSession();
		
		try {
			DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
			DepartmentMapper mapper2 = openSession2.getMapper(DepartmentMapper.class);
			
			Department emp01 = mapper.getDeptById(1);
			System.out.println(emp01);
			openSession.close();
			
			
			//�ڶ��β�ѯ�ǴӶ����������õ�������,��û�з��� sql
			Department emp02 = mapper2.getDeptById(1);
			System.out.println(emp02);
			openSession2.close();
			
		} finally {
		}
	}
	
	@Test
	public void testSecondLevelCache() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		SqlSession openSession2 = sqlSessionFactory.openSession();
		
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);
			
			Employee emp01 = mapper.getEmpById(2);
			System.out.println(emp01);
			openSession.close();
			//mapper2.addEmp(new Employee(null, "aa", "aa@qq.com", "1"));
			//�ڶ��β�ѯ�ǴӶ����������õ�������,��û�з��� sql
			//openSession2.clearCache();
			Employee emp02 = mapper2.getEmpById(2);
			System.out.println(emp02);
			openSession2.close();
			
		} finally {
		}
	}
	@Test
	public void testFirstLevelCache() throws IOException{
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee emp01 = mapper.getEmpById(2);
			System.out.println(emp01);
			
			//1.sqlSession ��ͬ
//			SqlSession openSession2 = sqlSessionFactory.openSession();
//			EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);
			
			//2.sqlSession��ͬ,��ѯ������ͬ
			
			//3.sqlSession ��ͬ,���β�ѯ֮��ִ������ɾ�Ĳ���(�����ɾ�Ŀ��ܶԵ�ǰ������Ӱ��)
			mapper.addEmp(new Employee(null,"testCache","cache","1"));
			System.out.println("������ӳɹ�");
			
//			4.sqlSession ��ͬ,�ֶ������һ������ (�������)
			//openSession.clearCache();
			
			Employee emp02 = mapper.getEmpById(2);
			System.out.println(emp02);
			System.out.println(emp01==emp02);
		} finally {
			openSession.close();
		}
	}
}
