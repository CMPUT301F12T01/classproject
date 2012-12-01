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
package ca.ualberta.cs.c301f12t01.serverStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.StorageInterface;
import ca.ualberta.cs.c301f12t01.util.Message;

/**
 * Over-arching class for all
 * 
 * @author home
 * 
 */
public class ServerStorage implements StorageInterface, Observer {

	/**
	 * The server can be notified when a Task or Report has been
	 * remove/added/updated so that it can update itself as needed.
	 * 
	 * @param Observable
	 *            , Object message
	 */
	public void update(Observable obsv, Object message) {
		Message newMessage = (Message) message;

		if (newMessage.getPayload() instanceof Task) {

			Task newTask = (Task) newMessage.getPayload();

			switch (newMessage.getAction()) {
			case ADDED:
				storeTask(newTask);
				break;
			case REMOVED:
				removeTask(newTask);
				break;
			case MODIFIED:
				updateTask(newTask);
				break;
			}
		} else if (newMessage.getPayload() instanceof Report) {

			Report newReport = (Report) newMessage.getPayload();

			switch (newMessage.getAction()) {
			case ADDED:
				storeReport(newReport);
				break;
			case REMOVED:
				removeReport(newReport);
				break;
			case MODIFIED:
				updateReport(newReport);
				break;
			}
		}
	}

	/**
	 * No real need to implement this
	 */
	public void close() {

	}

	/**
	 * Stores a task on the server
	 * 
	 * @param taskToStore
	 *            The task we are storing
	 */
	public void storeTask(Task taskToStore) {
		TaskServerStorage.storeTask(taskToStore);
	}

	/**
	 * Works just be removing old task and adding new one
	 * 
	 * @param taskToUpdate
	 *            The task to update
	 */
	public void updateTask(Task taskToUpdate) {
		removeTask(taskToUpdate);
		storeTask(taskToUpdate);
	}

	public void removeTask(Task taskToRemove) {
		TaskServerRemove.remove(taskToRemove);
	}

	public HashMap<UUID, Task> getOwnTasks(UUID userid) {
		return TaskServerRetrieval.getUserTasks(userid);
	}

	public HashMap<UUID, Task> getLocalTasks() {
		return TaskServerRetrieval.getLocalTasks();
	}

	public HashMap<UUID, Task> getGlobalTasks() {
		return TaskServerRetrieval.getGlobalTasks();
	}

	public void storeReport(Report reportToStore) {
		ReportServerStorage.storeReport(reportToStore);
	}

	public void updateReport(Report reportToUpdate) {
		// TODO Auto-generated method stub

	}

	public void removeReport(Report reportToRemove) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Report> getReports(Task matchingTask) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Report> getLocalReports(Task matchingTask) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Report> getTaskCreatorReports(Task matchingTask) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Report> getGlobalReports(Task matchingTask) {
		// TODO Auto-generated method stub
		return null;
	}

}
