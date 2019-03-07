package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import bean.Employee;
import manager.HibernateManager;

public class Emp_Management_App {

	static Employee emp = new Employee();
	public static HibernateManager manager = new HibernateManager();
	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		System.out.println("Employee management app.");
		int choice = -1;

		while (true) {
			System.out.println("1. Add Employee\n2. Update Employee\n3. View Employees\n4. Validate User\n5. Delete User\n0. Exit\n");
			System.out.println("Enter the choice");
			choice = Integer.parseInt(reader.readLine());

			switch (choice) {
			case 1:
				addEmployee();
				break;
			case 2:
				updateEmployee();
				break;
			case 3:
				viewEmployee();
				break;
			case 4:
				validateUser();
				break;
			case 5:
				deleteEmployee();
				break;
			case 0:
				System.exit(0);
				break;
			default:
				break;
			}
		}

	}

	public static void addEmployee() throws Exception {
		String ename, designation;
		int eid, salary, experience;
		System.out.println("Enter Employee Id");
		eid = Integer.parseInt(reader.readLine());

		System.out.println("Enter Employee name");
		ename = reader.readLine();

		System.out.println("Enter Employee designation");
		designation = reader.readLine();

		System.out.println("Enter Employee experience");
		experience = Integer.parseInt(reader.readLine());

		System.out.println("Enter Employee salary");
		salary = Integer.parseInt(reader.readLine());

		emp.setId(eid);
		emp.setDesignation(designation);
		emp.setExperience(experience);
		emp.setName(ename);
		emp.setSalary(salary);

		manager.save(emp);

	}

	public static void updateEmployee() throws Exception {
		String userNameUpdate;
		int userid = 0;

		System.out.println("Enter the user id to update");
		userid = Integer.parseInt(reader.readLine());

		System.out.println("Enter the user name to update");
		userNameUpdate = reader.readLine();

		manager.updateUser(userid, userNameUpdate);

	}

	public static void viewEmployee() {
		System.out.println("Viewing Employees");
		manager.connect();
		List<Employee> empAll = manager.getAll();
		for (int i = 0; i < empAll.size(); i++) {
			System.out.println(empAll.get(i).getId());
			System.out.println(empAll.get(i).getName());
			System.out.println(empAll.get(i).getDesignation());
			System.out.println(empAll.get(i).getSalary());
			System.out.println(empAll.get(i).getExperience());
			System.out.println("\n");
		}
	}

	public static void deleteEmployee() throws Exception {
		int deleteID;
		System.out.println("Enter the ID to be deleted");
		deleteID = Integer.parseInt(reader.readLine());
		manager.deleteUser(deleteID);

	}

	public static void validateUser() throws Exception {
		String validate;
		System.out.println("Enter the username to validate");
		validate = reader.readLine();

		if (manager.isUserValid(validate) == true) {
			System.out.println("User is present in the db");
		} else {
			System.out.println("User is not present");
		}

	}

}
