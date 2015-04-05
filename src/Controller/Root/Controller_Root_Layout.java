package Controller.Root;

import java.net.URL;
import java.util.ResourceBundle;

import Model.CMS.Employee_Info;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class Controller_Root_Layout implements Initializable 
{
	private Main mainApp;
	
	@FXML MenuBar menu_bar = new MenuBar();
	
	@FXML Menu menu_general = new Menu();
	@FXML MenuItem dashboard_menu = new MenuItem();
	@FXML MenuItem database_menu = new MenuItem();

	@FXML Menu menu_cms = new Menu();	
	@FXML MenuItem manage_remarks_menu = new MenuItem();
	@FXML MenuItem manage_medicines_menu = new MenuItem();
	@FXML MenuItem manage_fees_menu = new MenuItem();
	@FXML MenuItem manage_tests_menu = new MenuItem();
	
	@FXML Menu menu_patient = new Menu();
	@FXML MenuItem add_patient_menu = new MenuItem();
	@FXML MenuItem search_patient_menu = new MenuItem();
	@FXML MenuItem add_prescription_menu = new MenuItem();
	@FXML MenuItem search_prescription_menu = new MenuItem();
	@FXML MenuItem add_receipt_menu = new MenuItem();
	@FXML MenuItem search_receipt_menu = new MenuItem();
	@FXML MenuItem add_medication_menu = new MenuItem();
	@FXML MenuItem search_medication_menu = new MenuItem();
	@FXML MenuItem add_test_menu = new MenuItem();
	@FXML MenuItem search_test_menu = new MenuItem();
	
	@FXML Menu menu_account = new Menu();
	@FXML MenuItem manage_account_menu = new MenuItem();
	
	@FXML Menu menu_employee_mgmt = new Menu();
	@FXML MenuItem add_employee_menu = new MenuItem();
	@FXML MenuItem search_employee_menu = new MenuItem();

	public void setStage(Main mainApp)
	{
		this.mainApp = mainApp;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		EventHandler<ActionEvent> action = handle_event();
		
		dashboard_menu.setOnAction(action);
		database_menu.setOnAction(action);
		
		manage_remarks_menu.setOnAction(action);
		manage_medicines_menu.setOnAction(action);
		manage_fees_menu.setOnAction(action);
		manage_tests_menu.setOnAction(action);
		
		add_patient_menu.setOnAction(action);
		search_patient_menu.setOnAction(action);
		add_prescription_menu.setOnAction(action);
		search_prescription_menu.setOnAction(action);
		add_receipt_menu.setOnAction(action);
		search_receipt_menu.setOnAction(action);
		add_medication_menu.setOnAction(action);
		search_medication_menu.setOnAction(action);
		add_test_menu.setOnAction(action);
		search_test_menu.setOnAction(action);
		
		manage_account_menu.setOnAction(action);
		
		add_employee_menu.setOnAction(action);
		search_employee_menu.setOnAction(action);
	}

	@FXML
	private EventHandler<ActionEvent> handle_event() 
	{
		return new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				MenuItem mItem = (MenuItem) event.getSource();
				String mitem_str = mItem.getText();
				System.out.println("String is : " + mitem_str);
				if(mitem_str.equalsIgnoreCase("Go to dashboard"))
				{
					System.out.println("Calling it");
					mainApp.showDashboard();
				}
				else if(mitem_str.equalsIgnoreCase("Connect to database"))
				{
					mainApp.showDatabase();
				}
				
				else if(mitem_str.equalsIgnoreCase("Manage Remarks"))
				{
					mainApp.showRemarks();
				}
				else if(mitem_str.equalsIgnoreCase("Manage Medicines"))
				{
					mainApp.showMedicines();
				}
				else if(mitem_str.equalsIgnoreCase("Manage Fees"))
				{
					mainApp.showFees();
				}
				else if(mitem_str.equalsIgnoreCase("Manage Tests"))
				{
					mainApp.showTests();
				}
				
				else if(mitem_str.equalsIgnoreCase("Add a Patient"))
				{
					System.out.println("Calling...");
					mainApp.showAddPatient(new Employee_Info("devanshu", "jain", "xyz", "abc"));
				}
				else if(mitem_str.equalsIgnoreCase("Search for a Patient"))
				{
					mainApp.showSearchPatient();
				}
				else if(mitem_str.equalsIgnoreCase("Create a Prescription"))
				{
					mainApp.createPrescription();
				}
				else if(mitem_str.equalsIgnoreCase("Search for a Prescription"))
				{
					mainApp.searchPrescription();
				}
				else if(mitem_str.equalsIgnoreCase("Create a Receipt"))
				{
					mainApp.createReceipt();
				}
				else if(mitem_str.equalsIgnoreCase("Search for a Receipt"))
				{
					mainApp.searchReceipt();
				}
				else if(mitem_str.equalsIgnoreCase("Add Medications"))
				{
					mainApp.addMedications();
				}
				else if(mitem_str.equalsIgnoreCase("Search for Medications"))
				{
					mainApp.searchMedications();
				}
				else if(mitem_str.equalsIgnoreCase("Add Tests"))
				{
					mainApp.addTests();
				}
				else if(mitem_str.equalsIgnoreCase("Search for Tests"))
				{
					mainApp.searchTests();
				}
				
				else if(mitem_str.equalsIgnoreCase("Manage Account"))
				{
					mainApp.manageAccount();
				}
				
				else if(mitem_str.equalsIgnoreCase("Add an Employee"))
				{
					mainApp.addEmployee();
				}
				else if(mitem_str.equalsIgnoreCase("Search for an Employee"))
				{
					mainApp.searchEmployee();
				}
				
				else
				{
					System.out.println("False alarm...");
				}
			}
		};
	}

}
