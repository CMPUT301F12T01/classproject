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
package ca.ualberta.cs.c301f12t01.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;


/**
 * Implementation of {@link OPCollection}, using {@link LinkedHashMap}.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * 
 */
public abstract class LinkedOPCollection<Key, Element> extends
        OPCollection<Key, Element> {

    protected LinkedHashMap<Key, Element> linkedCollection = new LinkedHashMap<Key, Element>();

    /**
     * 
     */
    public LinkedOPCollection() {
        super();
    }

    public boolean addNoNotify(Element element) {
        linkedCollection.put(getKey(element), element);
        return true;
    }

    /**
     * If the element itself contains the key, then this is the abstract method
     * for you! Simply return the key from the element and all will be dandy.
     * 
     * @param element
     * @return the key for the element.
     */
    public abstract Key getKey(Element element);

    /**
     * WARNING! O(n) operation ahead. CONTINUE AT YOUR OWN RISK.
     * 
     * NEEDS TO BE SUPER TESTED.
     * 
     * @see ca.ualberta.cs.c301f12t01.util.OPCollection#getAt(int)
     */
    @Override
    public Element getAt(int position) {
        /* If position is out of bounds, return nothing. */
        if (position >= size() || position < 0) {
            return null;
        }

        Iterator<Element> iter = linkedCollection.values().iterator();

        for (int i = 0; i < position; i++)
            iter.next();

        return iter.next();
    }

    /**
     * Access the taskCollection's iterator
     * 
     * @return Iterator from taskCollection
     */
    public Iterator<Element> iterator() {
        /* Two dots condoned by Bronte! */
        return linkedCollection.values().iterator();
    }

    /**
     * 
     * @see ca.ualberta.cs.c301f12t01.util.OPCollection#get(java.lang.Object)
     */
    @Override
    public Element get(Key key) {
        return linkedCollection.get(key);
    }

    /**
     * @return The size of taskCollection
     */
    public int size() {
        return linkedCollection.size();
    }

    public void clear() {
        linkedCollection.clear();
    }

    public boolean contains(Object thing) {
        return linkedCollection.containsValue(thing);
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
        return linkedCollection.isEmpty();
    }

    public Element removeKey(Key key) {
        return linkedCollection.remove(key);
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