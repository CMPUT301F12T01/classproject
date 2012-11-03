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
package ca.ualberta.cs.c301f12t01.common;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Response -- part of the report which represents the fulfillment of one single
 * Request of a Task.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * 
 */
public abstract class Response {

    private Timestamp timestamp;

    /**
     * Construct a new Response with a Timestamp set to the current time. This
     * constructor should not be used, except from inherited classes.
     */
    protected Response() {
        /* Get a timestamp for the current time. */
        setTimestamp(new Timestamp(new Date().getTime()));
    }

    /**
     * Construct a new Response with the given Timestamp. This constructor
     * should not be used, except from inherited classes.
     * 
     * @param timestamp
     */
    protected Response(Timestamp timestamp) {
        setTimestamp(timestamp);
    }

    /**
     * @return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     *            the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /** Returns the MediaType of this subclass of Response. */
    public abstract MediaType getMediaType();

}
