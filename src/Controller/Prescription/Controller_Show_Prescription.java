package Controller.Prescription;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import Model.Patient.Prescription;

public class Controller_Show_Prescription implements Initializable 
{
	private Prescription pres = new Prescription();

	@FXML TextArea pres_date = new TextArea();
	@FXML TextArea pres_follow = new TextArea();
	@FXML TextArea pres_symptoms = new TextArea();
	@FXML TextArea pres_remarks = new TextArea();
	
	public void setPrescription(Prescription value)
	{
		this.pres = value;
		this.pres_date.setText(value.getDate().toString() + ", at, " + value.getTime().getValue());
		this.pres_follow.setText(value.getFollow_up_date().toString());
		this.pres_symptoms.setText(value.getSymptoms().getValue());
		this.pres_remarks.setText(value.getRemarks().getValue());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
	
}
