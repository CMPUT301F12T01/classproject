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
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

/**
 * Task -- a mission to be fulfilled by users.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * 
 */
public class Task implements Iterable<Request> {

    private String summary = "";
    private String description = "";
    private Collection<Request> requests = new ArrayList<Request>();
    private Boolean shared = true;
    final private UUID id;
    private UUID user;

    /** Instantiates a new class for the specified user. */
    public Task(UUID user) {
        setUser(user);
        id = UUID.randomUUID();
    }
    
    public Task(UUID taskId, UUID user) {
    	setUser(user);
    	id = taskId;
    	
    }

    /**
     * @param object
     * @return
     * @see java.util.Collection#add(java.lang.Object)
     */
    public boolean addRequest(Request newRequest) {
        return requests.add(newRequest);
    }

    /**
     * @param object
     * @return
     * @see java.util.Collection#remove(java.lang.Object)
     */
    public boolean removeRequest(Object deadRequest) {
        return requests.remove(deadRequest);
    }

    /**
     * Iterates through the Requests in this Task.
     * 
     * @return
     * @see java.util.Collection#iterator()
     */
    public Iterator<Request> iterator() {
        return requests.iterator();
    }

    /**
     * @return the amount of Requests in this Task.
     * @see java.util.Collection#size()
     */
    public int requestCount() {
        return requests.size();
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return whether the Task is local to the device only.
     */
    public Boolean isLocal() {
        return shared == false;
    }

    /** Declare that the task must be stored locally on the device only. */
    public void setLocal() {
        shared = false;
    }

    /**
     * @return whether the Task is globally shared.
     */
    public Boolean isGlobal() {
        return shared == true;
    }

    /** Declare that the task must be globally shared. */
    public void setGlobal() {
        shared = true;
    }

    /**
     * @return the Task id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the Task creator's ID.
     */
    public UUID getUser() {
        return user;
    }

    /**
     * @param summary
     *            the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(UUID user) {
        this.user = user;
    }

    /**
     * Returns the UUID and the summary of the task.
     * 
     * @deprecated For debug use only!
     */
    @Override
    public String toString() {
        /* String concatenation: a sign of *wonderful* coding practices in Java! */
        return getId().toString() + ": " + getSummary();
    }

}
