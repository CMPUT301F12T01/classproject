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

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.util.OPCollection;

/**
 * Task collection class	
 * @author Mitchell Home
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public class TaskCollection extends OPCollection<UUID, Task>{

	LinkedHashMap<UUID, Task> taskCollection = new LinkedHashMap<UUID, Task>();

	/* (non-Javadoc)
     * @see ca.ualberta.cs.c301f12t01.util.OPCollection#addNoNotify(java.lang.Object)
     */
    @Override
    protected boolean addNoNotify(Task task) {
        // TODO Auto-generated method stub
        taskCollection.put(task.getId(), task);
        return true;
    }

    /**
     * WARNING! O(n) operation ahead. CONTINUE AT YOUR OWN RISK.
     * 
     * NEEDS TO BE SUPER TESTED.
     * 
     * @see ca.ualberta.cs.c301f12t01.util.OPCollection#getAt(int)
     */
    @Override
    public Task getAt(int position) {
        /* If position is out of bounds, return nothing. */
        if (position >= size() || position < 0) {
            return null;
        }
        
        Iterator<Task> iter = taskCollection.values().iterator();
        
        for (int i = 0; i < position; i++)
            iter.next();
        
        return iter.next();
    }

    /**
	 * Adds a task. The UUID is determined automatically.
	 * @param t
	 * 			The task to be added 
	 * @return True because collection says so
	 */
	public boolean add(Task task) {
	    /* This is just here so that I can document this method. I'll let the
	     * compiler/JIT handle the optimization of this code.
	     */
	    return super.add(task);
	}


    /**
	 * Access the taskCollection's iterator
	 * @return Iterator from taskCollection
	 */
	public Iterator<Task> iterator() {
	    /* Two dots condoned by Bronte! */
		return taskCollection.values().iterator();
	}

	/**
    
     * @see ca.ualberta.cs.c301f12t01.util.OPCollection#get(java.lang.Object)
     */
    @Override
    public Task get(UUID taskId) {
        return taskCollection.get(taskId);
    }

    /**
     * @return The size of taskCollection
     */
    public int size() {
    	return taskCollection.size();
    }

    public void clear() {
        taskCollection.clear();
    }

    public boolean contains(Object thing) {
        return taskCollection.containsValue(thing);
    }

    public boolean containsAll(Collection<?> things) {
        for (Object thing : things) {
            if (!contains(thing)) {
                return false;
            }
        }
        
        return true;
    }

    public boolean isEmpty() {
        return taskCollection.isEmpty();
    }

    public Task removeKey(UUID taskId) {
        return taskCollection.remove(taskId);
    }

    /**
     * Does the same as {@link removeKey}.
     * @return
     */
    public boolean remove(UUID key) {
        return removeKey(key) != null;
    }

    /**
     * @deprecated NOT IMPLEMENTED AND NEVER WILL BE.
     */
    public boolean retainAll(Collection<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @deprecated NOT IMPLEMENTED AND NEVER WILL BE.
     */
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @deprecated NOT IMPLEMENTED AND NEVER WILL BE.
     */
    public <T> T[] toArray(T[] array) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @deprecated NOT IMPLEMENTED AND NEVER WILL BE.
     */
    public boolean removeAll(Collection<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @deprecated NOT IMPLEMENTED AND NEVER WILL BE.
     */
    public boolean remove(Object object) {
        // TODO Auto-generated method stub
        return false;
    }


}
