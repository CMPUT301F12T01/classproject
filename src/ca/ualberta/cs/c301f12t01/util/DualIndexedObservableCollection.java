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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Implementation of {@link ObservableCollection}, using {@link LinkedHashMap}.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * @author padlesky
 * 
 */
public abstract class DualIndexedObservableCollection<Key, Element> extends
        ObservableCollection<Key, Element> {

    protected HashMap<Key, Element> primraryIndex = new HashMap<Key, Element>();
    protected List<Key> orderedIndex = new ArrayList<Key>();

    /**
     * Simple constructor for the dual-indexed observable collection.
     */
    public DualIndexedObservableCollection() {
        super();
    }

    protected boolean addNoNotify(Element element) {
        primraryIndex.put(getKey(element), element);
        orderedIndex.add(getKey(element));
        return true;
    }

    protected boolean replaceNoNotify(Element oldElement, Element newElement) {
        if (getKey(oldElement).equals(getKey(newElement))) {
            primraryIndex.put(getKey(oldElement), newElement);
            return true;
        }
        return false;
    }

    protected boolean removeNoNotify(Element element) {
        Key keyOfElement = getKey(element);
        /* Remove from both data structures. */
        primraryIndex.remove(keyOfElement);
        orderedIndex.remove(keyOfElement);
        return true;
    }

    /**
     * If the element itself contains the key, then this is the abstract method
     * for you! Simply return the key from the element and all will be dandy.
     * 
     * TODO: SHOULD THIS EVEN EXIST?
     * 
     * @param element
     * @return the key for the element.
     */
    public abstract Key getKey(Element element);

    /**
     * Gets the element of the first occurrence of the element.
     * 
     * @see ca.ualberta.cs.c301f12t01.util.ObservableCollection#getAt(int)
     */
    @Override
    public Element getAt(int position) {
        /* If position is out of bounds, return nothing. */
        if (position >= size() || position < 0) {
            return null;
        }

        Element element = primraryIndex.get(orderedIndex.get(position));
        return element;
    }

    /**
     * Access the taskCollection's iterator
     * 
     * @return Iterator from taskCollection
     */
    public Iterator<Element> iterator() {
        /* Two dots condoned by Bronte! */
        List<Element> element = new ArrayList<Element>();
        for (int i = 0; i < orderedIndex.size(); i++) {
            element.add(primraryIndex.get(orderedIndex.get(i)));
        }
        return element.iterator();
    }

    /**
     * 
     * @see ca.ualberta.cs.c301f12t01.util.ObservableCollection#get(java.lang.Object)
     */
    @Override
    public Element get(Key key) {
        return primraryIndex.get(key);
    }

    /**
     * @return The size of taskCollection
     */
    public int size() {
        return primraryIndex.size();
    }

    /**
     * Empties the entire collection.
     */
    public void clear() {
        primraryIndex.clear();
        orderedIndex.clear();
    }

    public boolean contains(Object thing) {
        return primraryIndex.containsValue(thing);
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
        return primraryIndex.isEmpty();
    }

    /**
     * Removes the element found with the key value.
     */
    public Element removeKey(Key key) {
        Element toBeRemoved = get(key);

        if (removeElement(toBeRemoved)) {
            return toBeRemoved;
        } else {
            return null;
        }

    }

    /**
     * Returns the first index of the specified element.
     * @param element
     * @return
     */
    public int grabIndex(Element element) {
        Key elementKey = getKey(element);
        return orderedIndex.indexOf(elementKey);
    }

    /**
     * @deprecated NOT IMPLEMENTED AND NEVER WILL BE.
     */
    public boolean retainAll(Collection<?> arg0) {
        return false;
    }

    /**
     * @deprecated NOT IMPLEMENTED AND NEVER WILL BE.
     */
    public Object[] toArray() {
        return null;
    }

    /**
     * @deprecated NOT IMPLEMENTED AND NEVER WILL BE.
     */
    public <T> T[] toArray(T[] array) {
        return null;
    }

    /**
     * @deprecated NOT IMPLEMENTED AND NEVER WILL BE.
     */
    public boolean removeAll(Collection<?> arg0) {
        return false;
    }

    /**
     * @deprecated NOT IMPLEMENTED AND NEVER WILL BE.
     */
    @SuppressWarnings("unchecked")
    public boolean remove(Object object) {
        return removeElement((Element) object);
    }

}
