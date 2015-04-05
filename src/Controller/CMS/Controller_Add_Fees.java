package Controller.CMS;

import java.net.URL;
import java.util.ResourceBundle;

import Model.*;
import Model.CMS.Fees_Info;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/*
 * This is a controller for the dialog to add new fees type.
 * Author: Devanshu Jain
 */

public class Controller_Add_Fees implements Initializable
{
	private Stage stage;
	private boolean isDone = false;
	private Fees_Info fees_info;
	
	@FXML private TextArea fees_type = new TextArea();
	
	@FXML private Label fees_type_label = new Label();
	
	@FXML private Button btn_save = new Button();
	
	@FXML private Button btn_clear = new Button();
	
	@FXML private Button btn_cancel = new Button();
	
	@FXML
	private void handle_btn_save()
	{
		if(isInputValid())
		{
			isDone = true;
			fees_info.setName(fees_type.getText());
			String id = generateID();
			fees_info.setID(id);
			storeToDB(fees_info);
			stage.close();
		}
	}
	
	private void storeToDB(Fees_Info fees_info2)
	{
		// TODO Auto-generated method stub
	}

	private String generateID() 
	{
		//TODO
		return "1";
	}

	@FXML
	private void handle_btn_clear()
	{
		fees_type.clear();
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
	
	public void setFees(Fees_Info fees_info) 
	{
		System.out.println("Setting....");
		this.fees_info = fees_info;
		if(fees_info.get_fees_name() != null)
		{
			fees_type.setText(fees_info.get_fees_name().getValue());
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
		if(fees_type.getText() == null || fees_type.getText().length() == 0)
		{
			errorMsg += "Fees Type Name is empty\n";
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
