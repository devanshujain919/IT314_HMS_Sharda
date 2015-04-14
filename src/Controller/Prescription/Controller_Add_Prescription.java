package Controller.Prescription;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import application.Main;
import Model.Patient.Prescription;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class Controller_Add_Prescription implements Initializable
{
	private Stage stage;
	private boolean isDone = false;
	private Prescription pres_info, original_pres_info;
	private Integer ADD = 0, EDIT = 1, OTHER = 2;
	private Integer mode = OTHER;

	
	@FXML private Button btn_save = new Button();
	@FXML private Button btn_clear = new Button();
	@FXML private Button btn_cancel = new Button();
	
	@FXML private TextArea pres_date = new TextArea();
	@FXML private TextArea pres_time = new TextArea();
	@FXML private DatePicker pres_follow_date = new DatePicker();
	@FXML private TextArea pres_disease = new TextArea();
	@FXML private TextArea pres_remarks = new TextArea();
	
	@FXML 
	private void handle_btn_save()
	{
		if(isInputValid())
		{
			isDone = true;
			pres_info.setDisease(new SimpleStringProperty(pres_disease.getText()));
			pres_info.setRemarks(new SimpleStringProperty(pres_remarks.getText()));
			pres_info.setFollow_up_date(pres_follow_date.getValue());
			isDone = storeToDB();
			stage.close();
		}
	}
	
	@FXML
	private void handle_btn_clear()
	{
		if(mode == ADD)
		{
			pres_disease.clear();
			pres_remarks.clear();
		}
		else
		{
			pres_disease.setText(original_pres_info.getDisease().getValue());
			pres_remarks.setText(original_pres_info.getRemarks().getValue());
		}
	}
	
	@FXML
	private void handle_btn_cancel()
	{
		stage.close();
	}
	
	private boolean storeToDB()
	{
		System.out.println("Saving....");
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
		
		if(mode == ADD)
		{
			try
			{
				String query = "INSERT INTO Prescription VALUES(?, ?, ?, ?, ?, ?)";
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, pres_info.getPat_id().getValue());
				stmt.setString(2, pres_info.getDate().toString());
				stmt.setString(3, pres_info.getTime().getValue());
				stmt.setString(4, pres_info.getFollow_up_date().toString());
				stmt.setString(5, pres_info.getDisease().getValue());
				stmt.setString(6, pres_info.getRemarks().getValue());
				System.out.println(stmt.toString());
				int no = stmt.executeUpdate();
				original_pres_info.setDisease(pres_info.getDisease());
				original_pres_info.setFollow_up_date(pres_info.getFollow_up_date());
				original_pres_info.setRemarks(pres_info.getRemarks());
				System.out.println("No of rows updated: " + no);
				stmt.close();
			}
			catch(SQLException E)
			{
				E.printStackTrace();
				Dialogs.create()
	    		.owner(stage)
	    		.title(" ALERT ")
	    		.masthead(" SQlException encountered ")
	    		.message("Item could not be added... ")
	    		.showWarning();
				return false;
			}
		}
		else if(mode == EDIT)
		{
			try
			{
				String query = "UPDATE Prescription SET follow_up_date=?, disease=?, remarks=? WHERE pat_ID=? AND date=? AND time=?;";
				PreparedStatement stmt = con.prepareStatement(query);
				System.out.println(pres_info.getPat_id().getValue());
				stmt.setString(4, pres_info.getPat_id().getValue());
				System.out.println(pres_info.getDate().toString());
				stmt.setString(5, pres_info.getDate().toString());
				System.out.println(pres_info.getTime().getValue());
				stmt.setString(6, pres_info.getTime().getValue());
				System.out.println(pres_info.getFollow_up_date().toString());
				stmt.setString(1, pres_info.getFollow_up_date().toString());
				System.out.println(pres_info.getDisease().getValue());
				stmt.setString(2, pres_info.getDisease().getValue());
				System.out.println(pres_info.getRemarks().getValue());
				stmt.setString(3, pres_info.getRemarks().getValue());
				System.out.println("Query: " + stmt.toString());
				int no = stmt.executeUpdate();
				System.out.println("hello");
				original_pres_info.setDisease(pres_info.getDisease());
				original_pres_info.setFollow_up_date(pres_info.getFollow_up_date());
				original_pres_info.setRemarks(pres_info.getRemarks());
				System.out.println("No of rows updated: " + no);
				stmt.close();
			}
			catch(SQLException E)
			{
				E.printStackTrace();
				Dialogs.create()
	    		.owner(stage)
	    		.title(" ALERT ")
	    		.masthead(" SQlException encountered ")
	    		.message("Information could not be saved... ")
	    		.showWarning();
				return false;
			}
		}
		else
		{
			System.out.println("Mode is not valid...");
			return false;
		}
		return true;
	}
	
	public boolean isSaveClicked()
	{
		return isDone;
	}
	
	public void setStage(Stage dialogStage) 
	{
		this.stage = dialogStage;
	}

	public void setPrescription(Prescription pres_info, int mode)
	{
		System.out.println("Setting....");
		this.original_pres_info = pres_info;
		this.pres_info = Prescription.clone(original_pres_info);
		
		pres_date.setText(pres_info.getDate().toString());
		pres_time.setText(pres_info.getTime().getValue()); 
		
		if(pres_info.getDisease() != null)
		{
			pres_disease.setText(pres_info.getDisease().getValue());
		}
		if(pres_info.getRemarks() != null)
		{
			pres_remarks.setText(pres_info.getRemarks().getValue());
		}
		if(pres_info.getFollow_up_date() != null)
		{
			pres_follow_date.setValue(pres_info.getFollow_up_date());
		}
		this.mode = mode;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		pres_date.setEditable(false);
		pres_time.setEditable(false);
	}
	
	private boolean isInputValid()
	{
		System.out.println("Checking for error");
		String errorMsg = "";
		boolean retValue = true;
		if(pres_disease.getText() == null || pres_disease.getText().length() == 0)
		{
			errorMsg += "Disease field is empty\n";
			retValue = false;
		}
		if(pres_remarks.getText() == null || pres_remarks.getText().length() == 0)
		{
			errorMsg += "Remarks field is empty\n";
			retValue = false;
		}
		if(pres_follow_date.getValue().toString() == null || pres_follow_date.getValue().toString().length() == 0)
		{
			errorMsg += "Follow Up date is empty\n";
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
