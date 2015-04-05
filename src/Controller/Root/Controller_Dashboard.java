/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Root;

import java.net.URL;
import java.util.ResourceBundle;

import Model.CMS.Employee_Info;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author hppc
 */
public class Controller_Dashboard implements Initializable 
{
	private Main mainApp;
	private Stage primaryStage;
	private Employee_Info employee_info;
	
    @FXML private Label employee_name;
    @FXML Image img_logo, img_account, img_search, img_add, img_patient;
    @FXML Button btn_edit_profile, btn_logout;
    @FXML Button btn_create_prescription, btn_search_prescription;
    @FXML Button btn_create_receipt, btn_search_receipt;
    @FXML Button btn_add_medication, btn_search_medication;
    @FXML Button btn_add_tests, btn_search_tests;
    @FXML Button btn_add_employee, btn_search_employee;
    @FXML Button btn_add_patient, btn_search_patient;
    @FXML Button btn_manage_account;
    @FXML Button btn_manage_fees, btn_manage_tests, btn_manage_medicines, btn_manage_remarks;
    
    
    @FXML
    private void edit_profile() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void logout() 
    {
        System.out.println("logout is clicked!!!");
    }
    @FXML
    private void add_patient() 
    {
        System.out.println("add patient is clicked!!!");
    }
    @FXML
    private void search_patient() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void add_employee() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void search_employee() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void manage_fees() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void manage_medicines() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void manage_tests() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void manage_remarks() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void manage_account() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void create_prescription() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void search_prescription() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void create_receipt() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void search_receipt() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void add_medication() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void search_medication() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void add_tests() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    @FXML
    private void search_tests() 
    {
        System.out.println("edit profile is clicked!!!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
    	img_logo = new Image("/Resources/img_logo.png");
    	img_add = new Image("/Resources/img_add.png");
    	img_patient = new Image("/Resources/img_patient_related_info.png");
    	img_account = new Image("/Resources/img_account_management.png");
    	img_search = new Image("/Resources/img_search.png");
    }
    
    public void setMainApp(Main mainApp, Employee_Info employee_info)
    {
    	this.mainApp = mainApp;
    	this.primaryStage = mainApp.getStage();
    	this.employee_info = employee_info;
    	String str = "Welcome, " + employee_info.getFirst_name().getValue() + " " + employee_info.getLast_name().getValue();
    	Text text = new Text(str);
    	double width = text.getLayoutBounds().getWidth();
    	employee_name.setMinWidth(width);
    	employee_name.setText(str);
    }
    
}
