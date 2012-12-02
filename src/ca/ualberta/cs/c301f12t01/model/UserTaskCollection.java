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
package ca.ualberta.cs.c301f12t01.model;

import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.util.Message;

/**
 * Aggregates users tasks from the given TaskCollections. Observes
 * TaskCollections.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public class UserTaskCollection extends TaskCollection implements Observer {

	private String userID;
	private TaskCollection collections[];

	/** When constructed, aggregates tasks from the given collections. */
	public UserTaskCollection(String initialUserID,
			TaskCollection... initialCollections) {
		userID = initialUserID;
		collections = initialCollections;

		for (TaskCollection collection : collections) {
			/* We'll observe it. */
			collection.addObserver(this);
			addAllAppropriateTasks(collection);
		}
		setChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable observable, Object data) {
		/* Check if we got a Message instance. */
		if (!(data instanceof Message)) {
			return;
		}

		/* Furthermore, check that that Message was carrying a Task. */
		Message message = (Message) data;
		Object messagePayload = message.getPayload();
		if (!(messagePayload instanceof Task)) {
			return;
		}

		Task task = (Task) messagePayload;

		/* Now see if we even have to care about it. */
		if (!taskBelongsToTrackedUser(task)) {
			return;
		}

		/* Finally, we can check its status. */
		switch (message.getAction()) {
		case ADDED:
			add(task);
			break;
		case REMOVED:
			remove(task);
			break;
		case MODIFIED:
			/* The task in this case is that task that is being replaced.
			 * We want to replace the old value with the new value. */
			Task oldTask = get(task.getId());
			
			replace(oldTask, task);
			break;
		}

	}

	public boolean taskBelongsToTrackedUser(Task task) {
		return task.getUser().equals(userID);
	}

	/**
	 * Adds all user tasks from collection.
	 * 
	 * @param collection
	 */
	private void addAllAppropriateTasks(TaskCollection collection) {
		for (Task task : collection) {
			if (taskBelongsToTrackedUser(task)) {
				addNoNotify(task);
			}
		}
	}

}
