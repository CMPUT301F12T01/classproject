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

import java.util.Random;
import java.util.UUID;

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

		/* Not gonna lie: this method is faulty. :/ */

		TaskCollection collectionToPickFrom;
		/* Pick a random collection. */
		int collectionIndex = generator.nextInt(collections.length);
		collectionToPickFrom = collections[collectionIndex];

		if (collectionToPickFrom.isEmpty()) {
			/* DAMNIT! Just return null in this case. */
			return null;
		}

		int randomIndex = generator.nextInt(collectionToPickFrom.size());

		return collectionToPickFrom.getAt(randomIndex).getId();

	}

}
