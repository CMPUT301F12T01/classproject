package ca.ualberta.cs.c301f12t01.model;

import java.util.Collection;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;

/*
 *Class to interface between the model and the storage
 *@author Mitchell Home
 *@author Neil Borle
 */

// TODO: Javadoc the rest of this interface.

public interface StorageInterface {
	
	// Storage and retrieval of Tasks
	public void storeTask(Task taskToStore);
	
	public Collection<Task> getOwnTasks(UUID userid);
	
	public Collection<Task> getLocalTasks();
	
	public Collection<Task> getGlobalTasks();
	
	// Storage and retrieval of reports
	public void storeReport(Report reportToStore);
	
	public Collection<Report> getLocalReports(UUID taskid);
	
	public Collection<Report> getGlobalReports(UUID taskid);

}
