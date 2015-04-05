package application;
	
import java.io.IOException;

import Controller.CMS.*;
import Controller.Employee.Controller_Add_Employee;
import Controller.Root.Controller_Dashboard;
import Controller.Root.Controller_Root_Layout;
import Model.CMS.Employee_Info;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main 
{
	private Stage primaryStage;
	private BorderPane root_layout;
	private Employee_Info employee_info;
	
	private String USER_NAME = "user";
	private String PASSWORD = "password";
	private String IP = "10.100.57.33";
	private String PORT = "1433";
	private String DBNAME = "sen";
	
	public String getUserName()
	{
		return this.USER_NAME;
	}
	
	public String getIP()
	{
		return this.IP;
	}
	
	public String getPort()
	{
		return this.PORT;
	}
	
	public String getDBName()
	{
		return this.DBNAME;
	}
	
	public String getPassword()
	{
		return this.PASSWORD;
	}
	
	public void setIP(String IP)
	{
		this.IP = IP;
	}
	
	public void setPort(String port)
	{
		this.PORT = port;
	}
	
	public void setDbName(String DBName)
	{
		this.DBNAME = DBName;
	}
	
	public void setUsername(String username)
	{
		this.USER_NAME = username;
	}
	
	public void setpassword(String password)
	{
		this.PASSWORD = password;
	}
	
	public void start(Stage primaryStage, Employee_Info employee_info) 
	{
		try 
		{
			this.primaryStage = primaryStage;
			initRootLayout();
			this.employee_info = employee_info;
			showDashboard();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void initRootLayout()
	{
		System.out.println("Hello");
		
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/Root/RootLayout.fxml"));
			root_layout = (BorderPane) loader.load();
			Controller_Root_Layout controller = loader.getController();
			Scene scene = new Scene(root_layout);
			primaryStage.setScene(scene);
			primaryStage.show();
			controller.setStage(this);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}

	public void showMedicines()
	{
		System.out.println("Showing Medicines");
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/CMS/Medicine_Info.fxml"));
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			root_layout.setCenter(anchor_pane);
			Controller_Manage_Medicine controller = loader.getController();
			controller.setMainApp(this);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	public void showRemarks()
	{
		System.out.println("Showing Remarks");
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/CMS/Remarks_Info.fxml"));
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			root_layout.setCenter(anchor_pane);
			Controller_Manage_Remarks controller = loader.getController();
			controller.setMainApp(this);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	public void showFees()
	{
		System.out.println("Showing Fees");
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/CMS/Fees_Info.fxml"));
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			root_layout.setCenter(anchor_pane);
			Controller_Manage_Fees controller = loader.getController();
			controller.setMainApp(this);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	public void showTests()
	{
		System.out.println("Showing Tests");
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/CMS/Tests_Info.fxml"));
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			root_layout.setCenter(anchor_pane);
			Controller_Manage_Tests controller = loader.getController();
			controller.setMainApp(this);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	public void showDashboard()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/Root/Dashboard.fxml"));
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			root_layout.setCenter(anchor_pane);
			Controller_Dashboard controller = loader.getController();
			controller.setMainApp(this, employee_info);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	public void showDatabase() 
	{
		System.out.println("Showing Database");
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/CMS/Database_Connectivity_Screen.fxml"));
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			root_layout.setCenter(anchor_pane);
			Controller_Manage_Database controller = loader.getController();
			controller.setMainApp(this);
			controller.setForm(IP, PORT, DBNAME, USER_NAME, PASSWORD);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	public boolean showAddPatient(Employee_Info patient_info) 
	{
		// TODO Auto-generated method stub
		System.out.println("Adding patient dialog");
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/Employee/Dialog_Add_Employee.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add New Employee");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			Controller_Add_Employee controller = loader.getController();
			System.out.println("Hi!!\n");
			controller.setStage(dialogStage);
			controller.setPatient(patient_info);
			dialogStage.showAndWait();
			return controller.isSaveClicked();
		}
		catch(IOException E)
		{
			E.printStackTrace();
		}
		return false;
	}

	public void showSearchPatient() 
	{
		// TODO Auto-generated method stub
		
	}

	public void createPrescription() 
	{
		// TODO Auto-generated method stub
		
	}

	public void searchPrescription() 
	{
		// TODO Auto-generated method stub
		
	}

	public void createReceipt() 
	{
		// TODO Auto-generated method stub
		
	}

	public void searchReceipt()
	{
		// TODO Auto-generated method stub
		
	}

	public void addMedications() 
	{
		// TODO Auto-generated method stub
		
	}

	public void searchMedications() 
	{
		// TODO Auto-generated method stub
		
	}

	public void addTests()
	{
		// TODO Auto-generated method stub
		
	}

	public void searchTests()
	{
		// TODO Auto-generated method stub
		
	}

	public void manageAccount() 
	{
		// TODO Auto-generated method stub
		
	}

	public void addEmployee() 
	{
		// TODO Auto-generated method stub
		
	}

	public void searchEmployee()
	{
		// TODO Auto-generated method stub
		
	}
	
	public Stage getStage()
	{
		return this.primaryStage;
	}
	
	public Main()
	{
		
	}
}
