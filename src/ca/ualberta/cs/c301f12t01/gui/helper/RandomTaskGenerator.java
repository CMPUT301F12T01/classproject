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
package ca.ualberta.cs.c301f12t01.gui.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.model.TaskCollection;

/**
 * Gets random tasks from the TaskManager.
 * 
 * @author easantos
 * 
 */
public class RandomTaskGenerator {

	private Random generator = new Random();
	private TaskCollection[] collections;

	/**
	 * Instantiates the RandomTaskGenerator from the given TaskCollections.
	 */
	public RandomTaskGenerator(TaskCollection... initialCollections) {
		if (initialCollections.length < 1) {
			throw new IllegalArgumentException(
					"Must have at least one initial collection");
		}

		collections = initialCollections;
	}

	/** Returns a random Task ID from the tracked TaskManager. */
	public UUID getRandomTaskId() {
		List<UUID> taskList = new ArrayList<UUID>();
		
		for (TaskCollection collection : collections) {
			for (Task task : collection) {
				taskList.add(task.getId());
			}
		}
		
		int index = generator.nextInt(taskList.size());
		
		return taskList.get(index);
	}

}
