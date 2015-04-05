package application;

import java.io.IOException;

import Controller.Login.Controller_Login;
import Model.CMS.Employee_Info;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Login extends Application 
{
	private Stage primaryStage;
	
	private Main mainApp = new Main();
	
	private AnchorPane anchor_pane;
	
	public static void main(String[] args)
	{
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}
		catch (ClassNotFoundException E) 
		{
			System.out.println("Driver not found");
			E.printStackTrace();
		}
		launch(args);
	}
	
	public Stage getStage()
	{
		return this.primaryStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Sharda HMS");
		initLogin();
  	}
	
	private void initLogin()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(); 
			loader.setLocation(getClass().getResource("/View/Auth/Login.fxml"));
			anchor_pane = (AnchorPane) loader.load();
			Controller_Login controller = loader.getController();
			Scene scene = new Scene(anchor_pane);
			primaryStage.setScene(scene);
			primaryStage.show();
			controller.setMainApp(this);
		}
		catch(IOException E)
		{
			E.printStackTrace();
		}
	}
	
	public void goToMain(Employee_Info employee_info)
	{
		mainApp.start(primaryStage, employee_info);
	}

}
