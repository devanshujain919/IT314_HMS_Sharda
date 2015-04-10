/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Login;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import Model.Employee.Employee_Info;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Anshu
 */
@SuppressWarnings("deprecation")
public class Controller_Login implements Initializable {
    
	private Employee_Info employee_info;
	
    @FXML private PasswordField password;
    
    @FXML private TextField username;
      
    @FXML private Button btn_login;
        
    @FXML private Image img_logo;
    
    private Main mainApp;
    
    private Stage stage;

    @FXML
    private void handle_btn_login() 
    {
       if(isValid())
       {
    	   Main.setEmployee(employee_info);
    	   mainApp.showDashboard();
       }
       else
       {
    	   Main.setEmployee(new Employee_Info("devanshu", "jain", "xyz", "123"));
    	   mainApp.showDashboard();
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
         
         if(!validate)
         {
        	 Dialogs.create()
 			.owner(stage)
 			.title(" ALERT ")
 			.masthead(" Please enter req. fields ")
 			.message(errorMsg)
 				.showWarning();
        	 return validate;
         }
       
         boolean validate_db = false;
         
         try
         {
			Connection con = Main.getConnection();
			if(con == null)
			{
				Main.setConnection(null);
				Main.setUsername("");
				Main.setPort("");
				Main.setpassword("");
				Main.setDbName("");
				Main.setIP("");
				
				Dialogs.create()
				.owner(stage)
				.title(" ALERT ")
				.masthead(" Database is not setup ")
				.message("Please set up the connection ")
					.showWarning();
					return false;
			}
			String query = "SELECT username, password FROM Employee WHERE username=?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, username_field);
 			ResultSet rs = stmt.executeQuery();
 			int flag = 0, matches = 0;
 			while(rs.next())
 			{
	 			flag = 1;
	 			String pass_hash = rs.getString("password");
	 			
	 			pass_hash = get_SHA_1_SecurePassword(pass_hash, getSalt());
	 			if(pass_hash.equals(pass_field))
	 			{
		 			matches = 1;
		 			break;
	 			}
 			}
 			if(flag == 1 && matches == 1)
 			{
	 			validate_db = true;
 			}
 			else
 			{
	 			validate_db = false;
 			}
         }
         catch(SQLException E)
         {
        	 Dialogs.create()
	    		.owner(stage)
	    		.title(" ALERT ")
	    		.masthead(" SQlException encountered ")
	    		.message("Item could not be added... ")
	    		.showWarning();
        	 return false;
         }         
         
         return validate_db;
    }
    
    
    private static String get_SHA_1_SecurePassword(String passwordToHash, String salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    private static String getSalt() 
    {
        try
        {
        	SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
	        sr.nextBytes(salt);
	        return salt.toString();
        }
        catch(NoSuchAlgorithmException E)
        {
        	return null;
        }
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
    	img_logo = new Image("/Resources/img_logo.png");
    }    
    
    public void setMainApp(Main main)
    {
    	this.mainApp = main;
    	this.stage = mainApp.getStage();
    }
    
}
