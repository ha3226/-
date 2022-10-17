package webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.BoardDAO;
import webapp.model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {
	
	Connection connection = null;
	ResultSet resultSet = null;
	Statement statement = null;
	PreparedStatement preparedStatement = null;
	
	@Override
	public List<Employee> get() {
		List<Employee> list = new ArrayList<>();
		Employee employee = null;
		
		try {			
			String sql = "SELECT * FROM tbl_employee";
			connection = BoardDAO.getCon();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				employee = new Employee();
				employee.setName(resultSet.getString("email"));
				employee.setName(resultSet.getString("name"));
				employee.setDepartment(resultSet.getString("department"));
				employee.setDob(resultSet.getString("dob"));
				list.add(employee);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Employee get(String email) {
		Employee employee = null;
		try {
			employee = new Employee();
			String sql = "SELECT * FROM tbl_employee where email="+ email;
			connection = BoardDAO.getCon();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				employee.setEmail(resultSet.getString("email"));
				employee.setName(resultSet.getString("name"));
				employee.setDepartment(resultSet.getString("department"));
				employee.setDob(resultSet.getString("dob"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public boolean save(Employee e) {
		boolean flag = false;
		try {
			String sql = " INSERT INTO tbl_employee(email, name, department, dob) VALUES"
					+ "('\"+e.getEmail()+\"','"+e.getName()+"', '"+e.getDepartment()+"', '"+e.getDob()+"')";
			connection = BoardDAO.getCon();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
			flag = true;
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean delete(String email) {
		boolean flag = false;
		try {
			String sql = "DELETE FROM tbl_employee where email="+email;
			connection = BoardDAO.getCon();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
			flag = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean update(Employee employee) {
		boolean flag = false;
		try {
			String sql = "UPDATE tbl_employee SET name = '"+employee.getName()+"', "
					+ "department = '"+employee.getDepartment()+"', dob = '"+employee.getDob()+"' where id="+employee.getId();
			connection = BoardDAO.getCon();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
			flag = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Employee get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}

	

