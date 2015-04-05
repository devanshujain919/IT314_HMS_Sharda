package Controller.CMS;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.Main;
import Model.*;
import Model.CMS.Tests_Info;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller_Add_Tests implements Initializable
{
	private Stage stage;
	private Main mainApp;
	private boolean isDone = false;
	private Tests_Info tests_info;
	
	@FXML private TextArea test_type = new TextArea();
	
	@FXML private Label test_type_label = new Label();
	
	@FXML private Button btn_save = new Button();
	
	@FXML private Button btn_clear = new Button();
	
	@FXML private Button btn_cancel = new Button();
	
	@FXML
	private void handle_btn_save()
	{
		if(isInputValid())
		{
			isDone = true;
			tests_info.setName(test_type.getText());
			String id = generateID();
			
			System.out.println("Generated ID: " + id);
			
			tests_info.setID(id);
			
			System.out.println("Inserting into database...");
			
			//storeToDB(tests_info);
			
			System.out.println("Stored in the database");
			
			stage.close();
		}
	}
	
	private void storeToDB(Tests_Info tests_info)
	{
		try
		{
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://" + mainApp.getIP() + "\\SQLEXPRESS" + ":" + mainApp.getPort() + ";databaseName=" + mainApp.getDBName(), mainApp.getUserName(), mainApp.getPassword());
			
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Test_Info VALUES(\'" + tests_info.get_test_id() + "\', \'" + tests_info.get_test_name() + "\')";
			stmt.executeUpdate(query);
			
			conn.close();
		}
		catch(SQLException E)
		{
			E.printStackTrace();
		}
	}

	private String generateID() 
	{
		String retValue = "";
		
		try
		{
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://" + mainApp.getIP() + "\\SQLEXPRESS" + ":" + mainApp.getPort() + ";databaseName=" + mainApp.getDBName(), mainApp.getUserName(), mainApp.getPassword());
			
			Statement stmt = conn.createStatement();
			
			String q = "DELETE FROM Test_Info WHERE 1=1";
			stmt.executeUpdate(q);
			
			String query = "SELECT * FROM Test_Info ORDER BY test_id";
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 1;
			
			while(rs.next())
			{
				int test_id = Integer.parseInt(rs.getString("test_id"));
				String test_name = rs.getString("test_name");
				
				System.out.println(test_id + "\t" + test_name);
				if(test_id != i)
				{
					retValue = Integer.toString(i);
				}
				i ++;
			}
			
			conn.close(); 
		}
		catch(SQLException E)
		{
			E.printStackTrace();
		}
		
		return retValue;
	}

	@FXML
	private void handle_btn_clear()
	{
		test_type.clear();
	}
		
	@FXML
	private void handle_btn_cancel()
	{
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
	}
	
	public boolean isSaveClicked()
	{
		return isDone;
	}
	
	public void setTest(Tests_Info tests_info) 
	{
		System.out.println("Setting....");
		this.tests_info = tests_info;
		if(tests_info.get_test_name() != null)
		{
			test_type.setText(tests_info.get_test_name().getValue());
		}
	}
		
	public void setAppStage(Main mainApp, Stage stage)
	{
		System.out.println("Hi!! 2 time\n");
		this.mainApp = mainApp;
		this.stage = stage;
	}
	
	private boolean isInputValid()
	{
		System.out.println("Checking for error");
		String errorMsg = "";
		boolean retValue = true;
		if(test_type.getText() == null || test_type.getText().length() == 0)
		{
			errorMsg += "Test Type Name is empty\n";
			retValue = false;
		}
		
		if(errorMsg.length() > 0)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMsg);
			alert.showAndWait();
			
		}
		return retValue;
	}

}
