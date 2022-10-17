package webapp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webapp.dao.EmployeeDAO;
import webapp.dao.EmployeeDAOImpl;
import webapp.model.Employee;

@WebServlet("/")
public class EmployeeController extends HttpServlet {
	
	EmployeeDAO employeeDAO = null;
	
	public EmployeeController() {
		employeeDAO = new EmployeeDAOImpl();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if(action == null) {
			action = "LIST";
		}
		
		switch(action) {
			
			case "LIST":
				listEmployee(request, response);
				break;
				
			case "EDIT":
				getSingleEmployee(request, response);
				break;
				
			case "DELETE":
				deleteEmployee(request, response);
				break;
				
			default:
				listEmployee(request, response);
				break;
				
		}
		
	}

	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
	
		if(employeeDAO.delete(email)) {
			request.setAttribute("NOTIFICATION", "Employee Deleted Successfully!");
		}
		
		listEmployee(request, response);
	}

	private void getSingleEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String email = request.getParameter("email");
		
		Employee theEmployee = employeeDAO.get(email);
		
		request.setAttribute("employee", theEmployee);
		
		request.getRequestDispatcher("/views/employee-form.jsp").forward(request, response);
		
	}

	private void listEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Employee> theList = employeeDAO.get();
		
		request.setAttribute("list", theList);
		
		request.getRequestDispatcher("/views/employee-list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		
		Employee e = new Employee();
		e.setName(request.getParameter("name"));
		e.setDepartment(request.getParameter("department"));
		e.setDob(request.getParameter("dob"));
		
		if(email.isEmpty() || email == null) {
			
			if(employeeDAO.save(e)) {
				request.setAttribute("NOTIFICATION", "Employee Saved Successfully!");
			}
		
		}else {
			
			e.setEmail(email);
			if(employeeDAO.update(e)) {
				request.setAttribute("NOTIFICATION", "Employee Updated Successfully!");
			}
			
		}
		
		listEmployee(request, response);
	}

}