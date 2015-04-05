package Controller.CMS;

import java.net.URL;
import java.util.ResourceBundle;

import Model.CMS.Medicine_Info;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Controller_Add_Medicine implements Initializable
{
	private Stage stage;
	private boolean isDone = false;
	private Medicine_Info medicine_details;
	
	@FXML private TextArea medicine_name = new TextArea();
	
	@FXML private TextArea medicine_company = new TextArea();
	
	@FXML private TextArea medicine_remarks = new TextArea();
	
	@FXML private Label med_name_label = new Label();
	
	@FXML private Label med_company_label = new Label();
	
	@FXML private Label med_remarks_label = new Label();
	
	@FXML private Button btn_save = new Button();
	
	@FXML private Button btn_clear = new Button();
	
	@FXML private Button btn_cancel = new Button();
	
	@FXML
	private void handle_btn_save()
	{
		if(isInputValid())
		{
			isDone = true;
			medicine_details.set_med_name(medicine_name.getText());
			medicine_details.set_med_cmpy(medicine_company.getText());
			medicine_details.set_med_remarks(medicine_remarks.getText());
			String id = generateID();
			medicine_details.set_med_id(id);
			storeToDB(medicine_details);
			stage.close();
		}
	}
	
	private void storeToDB(Medicine_Info medicine_details2) 
	{
		// TODO Auto-generated method stub
	}

	private String generateID()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@FXML
	private void handle_btn_clear()
	{
		medicine_name.clear();
		medicine_company.clear();
		medicine_remarks.clear();
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
	
	public void setMedicine(Medicine_Info med_info) 
	{
		System.out.println("Setting....");
		this.medicine_details = med_info;
		if(medicine_details.get_med_name() != null)
		{
			medicine_name.setText(medicine_details.get_med_name().getValue());
		}
		if(medicine_details.get_med_company() != null)
		{
			medicine_company.setText(medicine_details.get_med_company().getValue());
		}
		if(medicine_details.get_med_remarks() != null)
		{
			medicine_remarks.setText(medicine_details.get_med_remarks().getValue());		
		}
	}
		
	public void setStage(Stage stage)
	{
		System.out.println("Hi!! 2 time\n");
		this.stage = stage;
	}
	
	private boolean isInputValid()
	{
		System.out.println("Checking for error");
		String errorMsg = "";
		boolean retValue = true;
		if(medicine_name.getText() == null || medicine_name.getText().length() == 0)
		{
			
			errorMsg += "Medicine Name is empty\n";
			retValue = false;
		}
		System.out.println("1");
		if(medicine_company.getText() == null || medicine_company.getText().length() == 0)
		{
			errorMsg += "Medicine Company Name is empty\n";
			retValue = false;
		}
		System.out.println("2");
		if(medicine_remarks.getText() == null || medicine_remarks.getText().length() == 0)
		{
			errorMsg += "Remarks for Medicine is empty\n";
			retValue = false;
		}
		System.out.println("nananana" + errorMsg + "\n");
		
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
