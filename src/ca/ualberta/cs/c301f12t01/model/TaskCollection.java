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

import java.util.Collection;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.util.DualIndexedObservableCollection;
import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * ObservableCollection that handles task.
 * 
 * Instantiate one for local and global tasks.
 * 
 * @author Mitchell Home
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * @author Aaron Padlesky <padlesky@ualberta.ca>
 */
public class TaskCollection extends DualIndexedObservableCollection<UUID, Task> {

    /**
     * Constructs a TaskCollection from the given plain collection of tasks.
     */
    public TaskCollection(Collection<Task> tasks) {
        super();
        
        System.err.println("Populating...");

        for (Task task : tasks) {
        	System.err.println(task);
            addNoNotify(task);
        }
    }

    /** Blank constructor. It's recommended that the TaskCollection is constructed using 
     * {@link #TaskCollection(Collection)}
     */
    public TaskCollection(){
        
    }
    
    /**
     * Adds a task. The UUID is determined automatically.
     * 
     * @param task
     *            The task to be added
     * @return Whether the value was succesfully
     *         added.
     */
    @Override
    public boolean add(Task task) {
        /*
         * This is just here so that I can document this method. I'll let the
         * compiler/JIT handle the optimization of this code.
         */
        return super.add(task);
    }

    public boolean modify(Task oldTask, Task newTask) {
    	return super.replace(oldTask, newTask);
    }
    /**
     * Does the same as {@link removeKey}.
     */
    public boolean remove(UUID taskId) {
        return removeKey(taskId) != null;
    }

    /**
     * Does the same thing as {@link removeKey}(task.getId()).
     */
    public boolean remove(Task task) {
        return super.removeElement(task);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ca.ualberta.cs.c301f12t01.util.LinkedOPCollection#getKey(java.lang.Object
     * )
     */
    @Override
    public UUID getKey(Task task) {
        return task.getId();
    }

}
