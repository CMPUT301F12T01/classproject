package ca.ualberta.cs.c301f12t01.model;

/*
 *Class to interface between the model and the storage
 *@author Mitchell Home
 *@author Neil Borle
 */

public interface StorageInterface {
	
	public void storeTask();
	
	public void getLocalTasks();
	
	public void getGlobalTasks();
	
	public void storeReport();
	
	public void getLocalReports();
	
	public void getGlobalReports();

}
