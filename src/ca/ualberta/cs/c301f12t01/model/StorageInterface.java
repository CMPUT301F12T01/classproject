package ca.ualberta.cs.c301f12t01.model;

import java.util.Collection;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Response;
import ca.ualberta.cs.c301f12t01.common.Task;

/*
 *Class to interface between the model and the storage
 *@author Mitchell Home
 *@author Neil Borle
 */

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
	
	// Storage and retrieval of requests
	public void storeRequest(Request requestToStore);
	
	public Collection<Request> getRequests(UUID taskId);
	
	// Storage and retrieval of responses
	public void storeResponse(Response responseToStore);
	
	public Collection<Response> getResponses(UUID reportId);

}
