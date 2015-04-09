package Controller.Database.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.ObservableList;
import Controller.Database.DBSetupTask;
import Controller.Database.DBTask;
import Model.Employee.Employee_Info;

public class AddEmployeeTask extends DBTask<ObservableList<Employee_Info>>
{
	@Override 
	protected ObservableList<Employee_Info> call() throws Exception 
	{
		Employee_Info emp_info = (Employee_Info) super.object;
		try (Connection con = DBSetupTask.con) 
		{
			String id = emp_info.getId().getValue();
			String category = emp_info.getCategory().getValue();
			String First_Name = emp_info.getFirst_name().getValue();
			String Last_Name = emp_info.getLast_name().getValue();
			String Middle_Name = emp_info.getMiddle_name().getValue();
			String Birth_Date = emp_info.getBirth_day().getValue();
			String Marital_status = emp_info.getMarital_status().getValue();
			String salary = emp_info.getSalary().getValue();
			String date_of_joining = emp_info.getDate_of_joining().toString();
			String phone = emp_info.getContact_no().getValue();
			String address = emp_info.getAddress().getValue();
			String city = emp_info.getCity().getValue();
			String state = emp_info.getState().getValue();
			String emp_post = "";
			String username = emp_info.getUsername().getValue();
			String password = emp_info.getPassword().getValue();
			String insertEmployee_query = "INSERT INTO Employee VALUES(" + id + "," + category + "," + First_Name + "," + Middle_Name + "," + Last_Name + "," + Birth_Date + "," + Marital_status + "," + salary + "," + date_of_joining + "," + phone + "," + address + "," + city + "," + emp_post + "," + username + "," + password + "," + ");";
			System.out.println("query is: " + insertEmployee_query);
			addInfo(con, insertEmployee_query);
			return null;
		}
	}
	
	private void addInfo(Connection con, String query) throws SQLException 
	{
		Statement st = con.createStatement();      
		st.executeUpdate(query);
	}
		
}
