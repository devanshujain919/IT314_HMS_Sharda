package application;
	
import java.io.IOException;
import java.sql.Connection;

import Controller.CMS.*;
import Controller.Employee.Controller_Add_Employee;
import Controller.Root.Controller_Dashboard;
import Controller.Root.Controller_Root_Layout;
import Controller.Login.Controller_Login;
import Controller.Prescription.Controller_Search_Patient;
import Model.Employee.Employee_Info;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application
{
	private static Stage primaryStage;
	private static BorderPane root_layout;
	private static Employee_Info employee_info;
	
	private static String USER_NAME = "";
	private static String PASSWORD = "";
	private static String IP = "";
	private static String PORT = "";
	private static String DBNAME = "";
	
	private static Connection conn = null;
	
	public static Employee_Info getEmployee()
	{
		return Main.employee_info;
	}
	
	public static void setEmployee(Employee_Info emp_info)
	{
		Main.employee_info = emp_info;
	}
	
	public static BorderPane getRootLayout()
	{
		return root_layout;
	}
	
	public static Connection getConnection()
	{
		return conn;
	}
	
	public static void setConnection(Connection con)
	{
		conn = con;
	}
	
	public static String getUserName()
	{
		return USER_NAME;
	}
	
	public static String getIP()
	{
		return IP;
	}
	
	public static String getPort()
	{
		return PORT;
	}
	
	public static String getDBName()
	{
		return DBNAME;
	}
	
	public static String getPassword()
	{
		return PASSWORD;
	}
	
	public static void setIP(String IP)
	{
		Main.IP = IP;
	}
	
	public static void setPort(String port)
	{
		Main.PORT = port;
	}
	
	public static void setDbName(String DBName)
	{
		Main.DBNAME = DBName;
	}
	
	public static void setUsername(String username)
	{
		Main.USER_NAME = username;
	}
	
	public static void setpassword(String password)
	{
		Main.PASSWORD = password;
	}
	
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			Main.primaryStage = primaryStage;
			initRootLayout();
			showLogin();
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
	
	public void showLogin()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(); 
			loader.setLocation(getClass().getResource("/View/Auth/Login.fxml"));
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			root_layout.setCenter(anchor_pane);
			Controller_Login controller = loader.getController();
			controller.setMainApp(this);
		}
		catch(IOException E)
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
		if(Main.employee_info == null)
		{
			return ;
		}
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
	
	public void showAddPatient() 
	{
		// TODO:
	}

	public void showSearchPatient() 
	{
		System.out.println("Showing Database");
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/Search/Search_Patient.fxml"));
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			root_layout.setCenter(anchor_pane);
			Controller_Search_Patient controller = loader.getController();
			controller.setMainApp(this);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}		
	}

	public void createPrescription() 
	{
		//TODO	
		
	}

	public void searchPrescription() 
	{
		System.out.println("Choose the patient");
		try
		{
			FXMLLoader loader = new FXMLLoader();
			System.out.println("1");
			loader.setLocation(Main.class.getResource("/View/Prescription/Search_Patient.fxml"));
			System.out.println("2");
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			root_layout.setCenter(anchor_pane);
			Controller_Search_Patient controller = loader.getController();
			controller.setMainApp(this);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}			
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

	public boolean addEmployee(Employee_Info emp_info) 
	{
		// TODO Auto-generated method stub
		System.out.println("Adding employee dialog");
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/Employee/Dialog_Add_Employee.fxml"));
			System.out.println("hello2");
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
			controller.setEmployee(emp_info);
			dialogStage.showAndWait();
			return controller.isSaveClicked();
		}
		catch(IOException E)
		{
			E.printStackTrace();
		}
		return false;
	}

	public void searchEmployee()
	{
		// TODO Auto-generated method stub
		
	}
	
	public Stage getStage()
	{
		return Main.primaryStage;
	}
	
	public Main()
	{
		
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
}
