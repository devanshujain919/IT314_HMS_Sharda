package Controller.CMS;

import java.net.URL;
import java.util.ResourceBundle;

import Model.CMS.Remarks_Info;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Controller_Add_Remarks implements Initializable
{
	private Stage stage;
	private boolean isDone = false;
	private Remarks_Info remark_info;
	
	@FXML private TextArea remark_eng = new TextArea();
	
	@FXML private TextArea remark_guj = new TextArea();
	
	@FXML private TextArea remark_trans = new TextArea();
	
	@FXML private TextArea remark_context = new TextArea();
	
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
			remark_info.setEnglishText(remark_eng.getText());
			remark_info.setGujaratiText(remark_guj.getText());
			remark_info.setContext(remark_context.getText());
			String id = generateID();
			remark_info.setRemarkID(id);
			storeToDB(remark_info);
			stage.close();
		}
	}
	
	private void storeToDB(Remarks_Info remark_info) 
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
		remark_eng.clear();
		remark_guj.clear();
		remark_trans.clear();
		remark_context.clear();
	}
		
	@FXML
	private void handle_btn_cancel()
	{
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		Font gujju_font = Font.loadFont(getClass().getResourceAsStream("/Resources/Gujrati-Saral-1.ttf"), 20);
		remark_trans.setFont(gujju_font);
		remark_trans.setEditable(false);
	}
	
	public boolean isSaveClicked()
	{
		return isDone;
	}
	
	public void setRemark(Remarks_Info remark_info) 
	{
		System.out.println("Setting....");
		this.remark_info = remark_info;
		if(remark_info.get_english_text() != null)
		{
			remark_eng.setText(remark_info.get_english_text().getValue());
		}
		if(remark_info.get_gujarati_text() != null)
		{
			remark_guj.setText(remark_info.get_gujarati_text().getValue());
			remark_trans.setText(remark_info.get_gujarati_text().getValue());
		}
		if(remark_info.get_context() != null)
		{
			remark_context.setText(remark_info.get_context().getValue());		
		}
	}
	
	@FXML
	private void handle_guj_change()
	{
		remark_trans.setText(remark_guj.getText());
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
		if(remark_eng.getText() == null || remark_eng.getText().length() == 0)
		{
			
			errorMsg += "Remark English is empty\n";
			retValue = false;
		}
		System.out.println("1");
		if(remark_guj.getText() == null || remark_guj.getText().length() == 0)
		{
			errorMsg += "Remark Gujarati is empty\n";
			retValue = false;
		}
		System.out.println("2");
		if(remark_context.getText() == null || remark_context.getText().length() == 0)
		{
			errorMsg += "Remark context is empty\n";
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
