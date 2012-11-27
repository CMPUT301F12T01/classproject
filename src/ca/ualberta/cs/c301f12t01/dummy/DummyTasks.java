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

/**
 * 
 * @author Bronte Lee <bronte@ualberta.ca>, Eddie Antonio Santos <easantos@ualberta.ca>
 *
 */


package ca.ualberta.cs.c301f12t01.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;

public class DummyTasks {

	/*
	 * As this has been adapted from the Model-Detail flow template, there was
	 * an embedded class, the DummyItem that existed here. Now we're using the
	 * common Task class.
	 *
	 * THIS CLASS SHOULD BE REPLACED WITH A TASKCOLLECTION INTSTANCE.
	 *
	 * ...once that class is working, of course.
	 */

	/* Besides, the following is really poor practice. */
	public static List<Task> ITEMS = new ArrayList<Task>();
	public static Map<UUID, Task> ITEM_MAP = new HashMap<UUID, Task>();
	public static final String DUMMY_USER = "";

	/* Initialize this dummy task collection with some dummy tasks. */
	static {
		Task[] dummyTasks = {
				new Task(DUMMY_USER) {{
					setSummary("Go create more tasks!");
					setDescription("Dummy tasks have returned!");
					addRequest(new Request(MediaType.TEXT));
					setGlobal();
				}},
		};

		for (Task task : dummyTasks) {
			addItem(task);
		}
	}

	public static void addItem(Task newTask) {
		ITEMS.add(newTask);
		ITEM_MAP.put(newTask.getId(), newTask);
	}
}


