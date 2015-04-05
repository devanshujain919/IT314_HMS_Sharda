/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Login;

import java.net.URL;
import java.util.ResourceBundle;

import Model.CMS.Employee_Info;
import application.Login;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Anshu
 */
public class Controller_Login implements Initializable {
    
	private Employee_Info employee_info;
	
    @FXML private PasswordField password;
    
    @FXML private TextField username;
      
    @FXML private Button btn_login;
        
    @FXML private Image img_logo;
    
    private Login mainApp;
    
    private Stage stage;

    @FXML
    private void handle_btn_login() 
    {
       if(isValid())
       {
    	   mainApp.goToMain(employee_info);
       }
    }
    
    private boolean isValid()
    {
    	 String username_field= username.getText();
         String pass_field=password.getText();
         String errorMsg = "";
         boolean validate = true;
         
         if(username_field.equals("") && pass_field.equals(""))
         {
         	errorMsg += "Enter username and password\n";
         	validate = false;
         }
         if(!username_field.equals("") && pass_field.equals(""))
         {
         	errorMsg += "Enter password\n";
         	validate = false;
         }
         if(username_field.equals("") && !pass_field.equals(""))
         {
         	errorMsg += "Enter username\n";
         	validate = false;
         }
         
         // TODO: check from database
         if(validate)
         {
        	 employee_info = new Employee_Info("Devanshu", "Jain", "abc", "xyz");
         }
         
         if(!validate)
     	 {
         	Alert alert = new Alert(AlertType.ERROR);
 	 		alert.initOwner(stage);
 			alert.setTitle("Invalid Fields");
 			alert.setHeaderText("Fields can't be left empty");
 			alert.setContentText(errorMsg);
 			alert.showAndWait();
     	 }
         
         return validate;
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
    	img_logo = new Image("/Resources/img_logo.png");
    }    
    
    public void setMainApp(Login main)
    {
    	this.mainApp = main;
    	this.stage = mainApp.getStage();
    }
    
}
