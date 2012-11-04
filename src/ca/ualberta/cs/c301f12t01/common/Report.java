/**
 * Copyright 2012 Neil Borle, Mitchell Home, Bronte Lee, Aaron
 * Padlesky, Eddie Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ca.ualberta.cs.c301f12t01.common;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;


/**
 * Report -- Contains Responses that fulfill Tasks.
 * 
 * This should be instantiated when a user makes a report for a task.
 * A report is given a timestamp upon creation and is given a 
 * collection of responses.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * 
 */
public class Report implements Iterable<Response> {

    final private UUID id = UUID.randomUUID();
    private UUID taskID;
    private Collection<Response> responses;
    private Timestamp timestamp;

    /** 
     * Construct a new Report for the given Task. It also 
     * handles the timestamp. This is for the GUI to call
     * where it doesn't have a timestamp yet.
     * 
     *  @param task
     */
    public Report(Task task) {
        this(task, new Timestamp(new Date().getTime()));
    }
    
    /**
     * Construct a Report from storage. This is what storage would
     * call to reconstruct a Report from stored data.
     *  
     * @param task
     * @param timestamp
     */
    public Report(Task task, Timestamp timestamp) {
    	setTaskID(task.getId());
    	setTimestamp(timestamp);
    }

    
    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the task ID
     */
    public UUID getTaskID() {
        return taskID;
    }

    /**
     * @return the responses
     */
    public Collection<Response> getResponses() {
        return responses;
    }

    /**
     * @param taskID the taskID to set
     */
    public void setTaskID(UUID taskID) {
        this.taskID = taskID;
    }

    /**
     * @param response the response to add
     * @return
     * @see java.util.Collection#add(java.lang.Object)
     */
    public boolean addResponse(Response response) {
        return responses.add(response);
    }

    /**
     * @param deadResponse the response to kill
     * @return
     * @see java.util.Collection#remove(java.lang.Object)
     */
    public boolean removeResponse(Response deadResponse) {
        return responses.remove(deadResponse);
    }

    /**
     * @return the amount of Responses in this Report.
     * @see java.util.Collection#size()
     */
    public int responseCount() {
        return responses.size();
    }


    /**
     * Iterate through this Report's Responses.
     * @return
     * @see java.util.Collection#iterator()
     */
    public Iterator<Response> iterator() {
        return responses.iterator();
    }


	
	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp()
	{
	
		return timestamp;
	}


	
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp)
	{
	
		this.timestamp = timestamp;
	}

}
