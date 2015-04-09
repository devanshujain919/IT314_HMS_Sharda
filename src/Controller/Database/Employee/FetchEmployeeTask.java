package Controller.Database.Employee;

import java.awt.image.DataBufferShort;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Controller.Database.DBSetupTask;
import Controller.Database.DBTask;
import Model.Employee.Employee_Info;

class FetchEmployeeTask extends DBTask<ObservableList<Employee_Info>>
{
	@Override 
	protected ObservableList<Employee_Info> call() throws Exception 
	{
		try (Connection con = DBSetupTask.con) 
		{
			String fetchEmployee_query = "SELECT * FROM Employee;";
			return fetchInfo(con, fetchEmployee_query);
		}
	}
	
	private ObservableList<Employee_Info> fetchInfo(Connection con, String query) throws SQLException 
	{
		ObservableList<Employee_Info> info = FXCollections.observableArrayList();
		
		Statement st = con.createStatement();      
		ResultSet rs = st.executeQuery(query);
		while (rs.next()) 
		{
			Employee_Info emp_info = new Employee_Info(rs.getString("First_Name"), rs.getString("Last_Name"), rs.getString("username"), rs.getString("password"));
			emp_info.setId(new SimpleStringProperty(rs.getString("emp_id")));
			info.add(emp_info);
		}
				
		return info;
	}
	
}