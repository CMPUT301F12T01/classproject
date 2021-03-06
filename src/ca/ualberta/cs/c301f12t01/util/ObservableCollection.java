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
import java.util.Observable;

/**
 * Observable, Positional Collection -- Intended to be a collection that can be
 * accessed randomly with a key, can be iterated in a predictable order, and can
 * be observed.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * @author padlesky
 */
public abstract class ObservableCollection<Key, Element> extends Observable
        implements Iterable<Element>, Collection<Element> {

    public abstract Element get(Key key);

    public abstract Element getAt(int position);

    protected abstract boolean addNoNotify(Element element);

    protected abstract boolean replaceNoNotify(Element oldElement,
            Element newElement);

    protected abstract boolean removeNoNotify(Element element);

    /**
     * Adds the specified element to the collection and notifies all observers.
     */
    public boolean add(Element element) {
        if (addNoNotify(element)) {
            setChanged();
            Message message = Message.makeAdded(element);
            notifyObservers(message);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Replaces the specified element in the collection with the new element and
     * notifies all observers.
     */
    public boolean replace(Element oldElement, Element newElement) {
        if (replaceNoNotify(oldElement, newElement)) {
            setChanged();
            Message message = Message.makeModified(newElement);
            notifyObservers(message);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Removes the specified element from the collection and notifies all observers.
     * @param element
     * @return
     */
    public boolean removeElement(Element element) {
        if (removeNoNotify(element)) {
            setChanged();
            Message message = Message.makeRemoved(element);
            notifyObservers(message);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Adds elements from the given collection to this collection.
     * Notifies observers on each element added.
     */
    public boolean addAll(Collection<? extends Element> elements) {
        for (Element element : elements) {
            if (!add(element)) {
                return false;
            }
        }

        return true;
    }

    public abstract Element removeKey(Key key);

}
