package Controller.Employee;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import Model.CMS.Employee_Info;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.application.Application;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class Controller_Add_Employee implements Initializable 
{
	
	private Employee_Info patient_info;
	
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
    		storeToDB();
    		stage.close();
    	}
    }
    
    private void storeToDB()
    {
		// TODO Auto-generated method stub
		
	}

	@FXML
    private void handle_btn_cancel()
    {
    	stage.close();
    }
    
    private boolean isValid() 
    {
    	boolean retValue = true;
    	
        int count=0;
        
        String pass = password.getText();
        String pass_confirm = password_confirm.getText();

        if(!pass.equals(pass_confirm))
        {
           password_label.setText("*Passwords do not match"); 
           retValue = false;
        }
        else
        {
        	count++;
        }
        
        int len = contact_no.getText().length();
        
        if(!(len==10))
        {
             contact_no_label.setText("*Enter 10 digits Mobile number"); 
             retValue = false;
        }
        else
        {
        	count++;
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
        
        if(firstName.equals("") || lastName.equals("") || stateName.equals("") || cityName.equals("") || addressVal.equals("") || categoryName.equals("") || dateOfJoin.equals("") || birthDay.equals("") || birthMonth.equals("") || birthYear.equals("") || designationVal.equals("") || salaryVal.equals(""))
        {
        	retValue = false;
        	Dialogs.create()
        		.owner(stage)
        		.title(" ALERT ")
        		.masthead(" Missing out something ")
        		.message("Mandatory field is empty ")
        		.showWarning();
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

	public void setPatient(Employee_Info patient_info)
	{
		this.patient_info = patient_info;
		first_name.setText(patient_info.getFirst_name().getValue());
		middle_name.setText(patient_info.getMiddle_name().getValue());
		last_name.setText(patient_info.getLast_name().getValue());
		
		username.setText(patient_info.getUsername().getValue());
		password.setText(patient_info.getPassword().getValue());
		
		designation.setText(patient_info.getDesignation().getValue());
		salary.setText(patient_info.getSalary().getValue());
		category.setValue(patient_info.getCategory().getValue());
		
		birth_year.setText(patient_info.getBirth_year().getValue());
		birth_day.setValue(patient_info.getBirth_day().getValue());
		birth_month.setValue(patient_info.getBirth_month().getValue());
		
		marital_status.setValue(patient_info.getMarital_status().getValue());
		contact_no.setText(patient_info.getContact_no().getValue());
		state.setValue(patient_info.getState().getValue());
		city.setText(patient_info.getCity().getValue());
		address.setText(patient_info.getAddress().getValue());
		
		date_of_joining.setValue(patient_info.getDate_of_joining());
		
	}

	public boolean isSaveClicked() 
	{
		return false;
	} 
}
