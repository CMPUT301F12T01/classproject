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

/* WARNING: I did not write this code in Eclipse. I do that sometimes. */

/**
 * An generic message sent to observers.
 * 
 * @author Eddie Antonio Santos
 *
 * A <code>Message</code> encodes an action and the object that has been
 * acted upon. This is useful when Observers not only need to know what
 * changed their Observable, but also <em>how</em> it changed. Instances
 * of this class are intended to be sent via the {@link
 * java.util.Observable#notifyObservers(java.lang.Object)} method of
 * Observable subclasses. The Observers can then assume that their
 * update method has a second argument of type <code>Message</code>.
 *
 * The specific semantics of a Message are undefined, however, messages
 * provide two things:
 *  - An "action" enumeration; and
 *  - a payload
 *
 * The action is supposed to have happened to the object. One can work
 * off these basic semantics and make their own, site-specific
 * semantics.
 *
 * Also note that this base class is immutable. Once the constructor has
 * made the Message, it is done. None of its fields can be modified in a
 * conventional manner. However, as the fields are references, they can
 * be modified, so be aware of that.
 *
 * Not to be confused with
 * {@link http://developer.android.com/reference/android/os/Message.html}
 * though they are very, VERY similar!
 *
 */
public class Message {

    /** Suggested actions that can be done to payloads. */
    public static enum MessageAction {
        NONE,
        ADDED,
        REMOVED,
        MODIFIED
    }

    private MessageAction action = MessageAction.NONE;
    private Object payload;

    /**
     * Creates a new Message class with all required fields provided.
     *
     * Direct use of this constructor is not recommended. Instead use
     * one of the provided static factory methods.
     *
     * @see Message#makeAdded(java.lang.Object)
     * @see Message#makeRemoved(java.lang.Object)
     * @see Message#makeModified(java.lang.Object)
     */
    public Message(MessageAction initialAction, Object initialPayload) {
        action = initialAction;
        payload = initialPayload;
    }


    /** Creates a new "added" Message with the given payload. */
    public static Message makeAdded(Object payload) {
        return new Message(MessageAction.ADDED, payload);
    }
    /** Creates a new "removed" Message with the given payload. */
    public static Message makeRemoved(Object payload) {
        return new Message(MessageAction.REMOVED, payload);
    }
    /** Creates a new "modified" Message with the given payload. */
    public static Message makeModified(Object payload) {
        return new Message(MessageAction.MODIFIED, payload);
    }


    /** Gets the payload attached to the Message. */
    public Object getPayload() {
        return payload;
    }

    /** Gets the action associated with the payload. */
    public MessageAction getAction() {
        return action;
    }


}
