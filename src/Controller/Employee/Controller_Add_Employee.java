package Controller.Employee;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import application.Main;
import Model.Employee.Employee_Info;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class Controller_Add_Employee implements Initializable 
{
	
	private Employee_Info employee_info;
	
	private static int ADD=1, EDIT=2, OTHER=3;
	private static int mode = OTHER;
	
	@FXML private TextField first_name, middle_name, last_name;
	
	@FXML private TextField username;
	@FXML private PasswordField password, password_confirm;
	
	@FXML private ComboBox category;
	
	@FXML private DatePicker date_of_joining;
	
	@FXML private ComboBox birth_month, birth_day;
	
	@FXML private TextField birth_year;
	
	@FXML private ComboBox marital_status;
	
	@FXML TextField designation, salary, contact_no;
	
	@FXML private ComboBox state;
	
	@FXML private TextField city;
	
	@FXML private TextArea address;
	
	@FXML private Button btn_save, btn_clear, btn_cancel;
	
	@FXML private Label password_label;
	@FXML private Label contact_no_label;
	    
	private Stage stage;  

	@FXML
    public void handle_btn_clear(ActionEvent event) 
    {
		Action response = Dialogs.create()
			.owner(stage)
	        .title("Confirmation ")
	        .masthead(" This will clear all the data inputed by you ")
	        .message("Are you sure you want to clear the data? ")
	        .actions(Dialog.ACTION_YES, Dialog.ACTION_NO)
	        .showConfirm();

		if (response == Dialog.ACTION_YES) 
		{
			first_name.setText("");
			middle_name.setText("");
			last_name.setText("");
			
			username.setText("");
			password.setText("");
			password_confirm.setText("");
			
			birth_day.setValue(null);
			birth_month.setValue(null);
			birth_year.setText("");
			
			date_of_joining.setValue(null);
			category.setValue(null);
			
			marital_status.setValue(null);
			state.setValue(null);
			city.setText("");
			designation.setText("");
			salary.setText("");
			contact_no.setText("");
			
			address.setText("");
			
			password_label.setText("");
			contact_no.setText("");
		}
	    else 
	    {
	    	// Do nothing
	    }        
    }
	
    @FXML
    private void handle_btn_save()
    {
    	if(isValid())
    	{
    		this.employee_info.setFirst_name(new SimpleStringProperty(first_name.getText()));
    		this.employee_info.setMiddle_name(new SimpleStringProperty(middle_name.getText()));
    		this.employee_info.setLast_name(new SimpleStringProperty(last_name.getText()));
    		this.employee_info.setAddress(new SimpleStringProperty(address.getText()));
    		this.employee_info.setBirth_day(new SimpleStringProperty(birth_day.getValue().toString()));
    		this.employee_info.setMarital_status(new SimpleStringProperty(marital_status.getValue().toString()));
    		this.employee_info.setSalary(new SimpleStringProperty(salary.getText()));
    		this.employee_info.setDate_of_joining(date_of_joining.getValue());
    		this.employee_info.setContact_no(new SimpleStringProperty(contact_no.getText()));
    		this.employee_info.setCity(new SimpleStringProperty(city.getText()));
    		this.employee_info.setState(new SimpleStringProperty(state.getValue().toString()));
    		this.employee_info.setUsername(new SimpleStringProperty(username.getText()));
    		this.employee_info.setPassword(new SimpleStringProperty(password.getText()));
    		this.employee_info.setId(new SimpleStringProperty("1"));
    		
    		boolean isDone = storeToDB();
    		
    		stage.close();
    	}
    }
    
    private boolean storeToDB()
    {
    	String id = employee_info.getId().getValue();
		String category = employee_info.getCategory().getValue();
		String First_Name = employee_info.getFirst_name().getValue();
		String Last_Name = employee_info.getLast_name().getValue();
		String Middle_Name = employee_info.getMiddle_name().getValue();
		String Birth_Date = employee_info.getBirth_day().getValue();
		String Marital_status = employee_info.getMarital_status().getValue();
		String salary = employee_info.getSalary().getValue();
		String date_of_joining = employee_info.getDate_of_joining().toString();
		String phone = employee_info.getContact_no().getValue();
		String address = employee_info.getAddress().getValue();
		String city = employee_info.getCity().getValue();
		String state = employee_info.getState().getValue();
		String emp_post = "";
		String username = employee_info.getUsername().getValue();
		String password = employee_info.getPassword().getValue();
		
		if(mode == ADD)
		{
			String insertEmployee_query = "INSERT INTO Employee VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			System.out.println("query is: " + insertEmployee_query);
			
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
				PreparedStatement stmt = con.prepareStatement(insertEmployee_query);
				
				stmt.setString(1, id);
				stmt.setString(2, category);
				stmt.setString(3, First_Name);
				stmt.setString(4, Birth_Date);
				stmt.setString(5, Marital_status);
				stmt.setString(6, salary);
				stmt.setString(7, date_of_joining);
				stmt.setString(8, phone);
				stmt.setString(9, address);
				stmt.setString(10, city);
				stmt.setString(11, state);
				stmt.setString(12, emp_post);
				stmt.setString(13, username);
				
				String salt = getSalt();
				password = get_SHA_1_SecurePassword(password, salt);
				
				stmt.setString(14, password);
				
				stmt.executeUpdate();
			}
			catch(Exception E)
			{
				Dialogs.create()
	    		.owner(stage)
	    		.title(" ALERT ")
	    		.masthead(" SQlException encountered ")
	    		.message("Item could not be added... ")
	    		.showWarning();
				return false;
			}
			return true;
    	}
		else if(mode == EDIT)
		{
			String insertEmployee_query = "UPDATE Employee SET 	category=?, Name=?, Birth_Date=?, Marital_status=?, salary=?, date_of_joining=?, phone=?, address=?, city=?, state=?, emp_post=?, username=?, password=? WHERE emp_id=?);";
			
			System.out.println("query is: " + insertEmployee_query);
			
			try
			{
				Connection con = Main.getConnection();
				if(con == null)
				{
					
					if(password.length() >= 1)
					{
						String salt1 = getSalt();
						String pass_hash = get_SHA_1_SecurePassword(password, salt1);
						password = pass_hash;
					}
					else
					{
						password = employee_info.getPassword().getValue();
					}
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
				PreparedStatement stmt = con.prepareStatement(insertEmployee_query);
				
				stmt.setString(14, id);
				stmt.setString(1, category);
				stmt.setString(2, First_Name);
				stmt.setString(3, Birth_Date);
				stmt.setString(4, Marital_status);
				stmt.setString(5, salary);
				stmt.setString(6, date_of_joining);
				stmt.setString(7, phone);
				stmt.setString(8, address);
				stmt.setString(9, city);
				stmt.setString(10, state);
				stmt.setString(11, emp_post);
				stmt.setString(12, username);
				stmt.setString(13, password);
				
				stmt.executeUpdate();
			}
			catch(Exception E)
			{
				Dialogs.create()
	    		.owner(stage)
	    		.title(" ALERT ")
	    		.masthead(" SQlException encountered ")
	    		.message("Item could not be added... ")
	    		.showWarning();
				return false;
			}
			return true;
		}
		else
		{
			return false;
		}
    }

	@FXML
    private void handle_btn_cancel()
    {
    	stage.close();
    }
    
    private boolean isValid() 
    {
    	boolean retValue = true;
    	
        String pass = password.getText();
        String pass_confirm = password_confirm.getText();

        if(!pass.equals(pass_confirm))
        {
           password_label.setText("*Passwords do not match"); 
           retValue = false;
        }
        
        int len = contact_no.getText().length();
        
        if(!(len==10))
        {
             contact_no_label.setText("*Enter 10 digits Mobile number"); 
             retValue = false;
        }
        
        String firstName = first_name.getText();
        String lastName = last_name.getText();
        String userName = username.getText();
        
        String stateName = (String) state.getValue();
        String cityName = city.getText();
        String addressVal = address.getText();
        
        String categoryName = (String) category.getValue();
        LocalDate dateOfJoin = date_of_joining.getValue();
        
        String birthMonth = (String) birth_month.getValue();
        String birthDay = (String) birth_day.getValue();
        String birthYear = birth_year.getText();
        
        String designationVal = designation.getText();
        String salaryVal = salary.getText();
        
        if(userName.equals("") || firstName.equals("") || stateName.equals("") || cityName.equals("") || addressVal.equals("") || categoryName.equals("") || dateOfJoin.equals("") || birthDay.equals("") || birthMonth.equals("") || birthYear.equals("") || designationVal.equals("") || salaryVal.equals(""))
        {
        	retValue = false;
        	Dialogs.create()
        		.owner(stage)
        		.title(" ALERT ")
        		.masthead(" Missing out something ")
        		.message("Mandatory field is empty ")
        		.showWarning();
        }
        if(password.equals("") && mode == ADD)
        {
        	retValue = false;
        }
       
        return retValue;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
       
    }

	public void setStage(Stage dialogStage) 
	{
		this.stage = dialogStage;
	}

	public void setEmployee(Employee_Info employee_info)
	{
		
		if(employee_info == null)
		{
			mode = ADD;
		}
		else
		{
			mode = EDIT;
			System.out.println("Hello1");
			this.employee_info = employee_info;
			
			first_name.setText(employee_info.getFirst_name().getValue());
			middle_name.setText(employee_info.getMiddle_name().getValue());
			last_name.setText(employee_info.getLast_name().getValue());
			
			username.setText(employee_info.getUsername().getValue());
			password.setText("");
			
			designation.setText(employee_info.getDesignation().getValue());
			salary.setText(employee_info.getSalary().getValue());
			category.setValue(employee_info.getCategory().getValue());
			
			birth_year.setText(employee_info.getBirth_year().getValue());
			birth_day.setValue(employee_info.getBirth_day().getValue());
			birth_month.setValue(employee_info.getBirth_month().getValue());
			
			marital_status.setValue(employee_info.getMarital_status().getValue());
			contact_no.setText(employee_info.getContact_no().getValue());
			state.setValue(employee_info.getState().getValue());
			city.setText(employee_info.getCity().getValue());
			address.setText(employee_info.getAddress().getValue());
			
			date_of_joining.setValue(employee_info.getDate_of_joining());
		}
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
    		E.printStackTrace();
    	}
    	return null;
    }

	public boolean isSaveClicked() 
	{
		return false;
	} 
}
