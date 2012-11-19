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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Task -- A quest to be fulfilled by users.
 * 
 * This should be instantiated when you create a Task or reconstruct a task from storage.
 * Each task has its own id and a creator id to distinguish between various tasks. Tasks
 * are made up of Summary, Description, Requests and whether it is shared. The summary is a
 * brief description of the task. The description is a lengthy description of the task.
 * The requests are one or more of the following three types, Text, Audio and Photo {@link Request}.
 * The shared is a boolean true or false whether to be shared or not.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * @author Neil Borle
 * 
 */
public class Task implements Iterable<Request> {

    private String summary = "";
    private String description = "";
    private List<Request> requests = new ArrayList<Request>();
    private Sharing shared = Sharing.GLOBAL;
    final private UUID id;
    private UUID user;

    /**
     * Constructs a new task. This is used by the GUI.
     * It takes in a user ID to keep track of who created the task.
     * @param user
     */
    public Task(UUID user) {
        setUser(user);
        id = UUID.randomUUID();
    }
    
    /** 
     * Constructs a new task given a pre-existing task id. 
     * This will be done from storage side of code.
     * @param taskId
     * @param user
     */
    public Task(UUID taskId, UUID user) {
    	setUser(user);
    	id = taskId;
    }

    /**
     * This will add a new request to the task.
     * @param object
     * @return If it is successful it will return True, otherwise False.
     * @see java.util.Collection#add(java.lang.Object)
     */
    public boolean addRequest(Request newRequest) {
        return requests.add(newRequest);
    }

    /**
     * This will remove a request from the task.
     * @param object
     * @return If it is successful it will return True, otherwise False.
     * @see java.util.Collection#remove(java.lang.Object)
     */
    public boolean removeRequest(Object deadRequest) {
        return requests.remove(deadRequest);
    }

    /**
     * Iterates through the Requests in this Task.
     * @return It will return a collection of the objects iterator over.
     * @see java.util.Collection#iterator()
     */
    public Iterator<Request> iterator() {
        return requests.iterator();
    }

    /**
     * Used to return a request from the list given its position.
     * @param position
     * @return Returns a request given its position.
     */
    public Request getRequest(int position) {
    	return requests.get(position);
    }
    
    /**
     * Used to see how many requests are in a Task.
     * @return Returns the amount of Requests in this Task.
     * @see java.util.Collection#size()
     */
    public int requestCount() {
        return requests.size();
    }

    /**
     * Used to get the summary of a given task. The summary is
     * a brief description of the given task.
     * @return Returns the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Used to get the description of a given task. The description is
     * a lengthy response describing in full detail what the task requires
     * in terms of what type of text audio or photo responses be required.
     * @return Returns the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Used to check whether the task is local.
     * @return Returns whether the Task is local to the device only.
     */
    public Boolean isLocal() {
        return shared.isLocal();
    }

    /**
     * Sets the task to local by changing shared to false.
     */
    public void setLocal() {
        shared = Sharing.LOCAL;
    }

    /**
     * Used to check whether the task is global.
     * @return Returns whether the Task is globally shared.
     */
    public Boolean isGlobal() {
        return shared.isGlobal();
    }

    /**
     * Set the task to global by changing shared to true.
     */
    public void setGlobal() {
        shared = Sharing.GLOBAL;
    }

    /**
     * Used to get the Task id.
     * @return Returns the Task id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Used to get the Task creator's ID.
     * @return Returns the Task creator's ID.
     */
    public UUID getUser() {
        return user;
    }

    /**
     * Sets the summary of the task.
     * @param summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Sets the description of the task.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the user to the Task.
     * @param user
     */
    public void setUser(UUID user) {
        this.user = user;
    }

    /**
     * Will create a new string of id concatenated with the summary.
     * @return Returns the UUID and the summary of the task as a string.
     * @deprecated For debug use only!
     */
    @Override
    public String toString() {
        /* String concatenation: a sign of *wonderful* coding practices in Java! */
        return getId().toString() + ": " + getSummary();
    }

}
