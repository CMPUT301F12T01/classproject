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

import java.util.UUID;

import ca.ualberta.cs.c301f12t01.util.DualIndexedObservableCollection;
import ca.ualberta.cs.c301f12t01.common.Task;

/**
 * Task collection class
 * 
 * @author Mitchell Home
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public class TaskCollection extends DualIndexedObservableCollection<UUID, Task> {

    /**
     * Adds a task. The UUID is determined automatically.
     * 
     * @param task
     *            The task to be added
     * @return True because collection says so
     */
    @Override
    public boolean add(Task task) {
        /*
         * This is just here so that I can document this method. I'll let the
         * compiler/JIT handle the optimization of this code.
         */
        return super.add(task);
    }

    /**
     * Does the same as {@link removeKey}.
     * 
     * @return
     */
    public boolean remove(UUID taskId) {
        return removeKey(taskId) != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ca.ualberta.cs.c301f12t01.util.LinkedOPCollection#getKey(java.lang.Object)
     */
    @Override
    public UUID getKey(Task task) {
        return task.getId();
    }

}
