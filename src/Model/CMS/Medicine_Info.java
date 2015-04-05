package Model.CMS;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * This is a model file for storing data about the various types of medicines, that the doctor can prescribe to the patient.
 * Author: Devanshu Jain
 */

public class Medicine_Info 
{
	private StringProperty medicine_id;
	private StringProperty medicine_name; 
	private StringProperty medicine_company;
	private StringProperty other_remarks; // some remarks about the medicine to distinguish, in case of multiple medicines having same name
	
	/*
	 * Constructors
	 */
	
	public Medicine_Info()
	{
		
	}
	
	public Medicine_Info(String med_name, String med_cmpy, String remarks)
	{
		this.medicine_company = new SimpleStringProperty(med_cmpy);
		this.medicine_name = new SimpleStringProperty(med_name);
		this.other_remarks = new SimpleStringProperty(remarks);
	}
	
	/*
	 * Getters and setters
	 */
	
	public void set_med_id(String id)
	{
		this.medicine_id = new SimpleStringProperty(id);
	}
	
	public void set_med_name(String name)
	{
		this.medicine_name = new SimpleStringProperty(name);
	}
	
	public void set_med_cmpy(String med_cmpy)
	{
		this.medicine_company = new SimpleStringProperty(med_cmpy);
	}
	
	public void set_med_remarks(String med_remarks)
	{
		this.other_remarks = new SimpleStringProperty(med_remarks);
	}
	
	public StringProperty get_med_id()
	{
		return this.medicine_id;
	}
	
	public StringProperty get_med_name()
	{
		return this.medicine_name;
	}
	
	public StringProperty get_med_company()
	{
		return this.medicine_company;
	}
	
	public StringProperty get_med_remarks()
	{
		return this.other_remarks;
	}
}
