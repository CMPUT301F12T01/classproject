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

/**
 * Request -- Represents one of (possibly many) request of a Task.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * 
 * @see Task Not to be confused with a Response.
 */
public class Request {

    /**
     * The media that the request requires.
     * 
     * @see MediaType
     */
    private MediaType type;
    /** A short description of this specific request. */
    private String description;
    /** The quantity of the media requested. */
    private int quantity;

    /**
     * Constructs a new Request with only its MediaType.
     * 
     * @param type
     */
    public Request(MediaType type) {
        this(type, "");

    }
    
    /**
     * Constructs a new Request with only its MediaType.
     * 
     * @param type
     * @param description
     */
    public Request(MediaType type, String newDescription) {
        this(type, newDescription, 0);

    }

    /**
     * Constructs a new Request with the most useful fields.
     * 
     * @param type
     *            the initial MediaType
     * @param description
     *            the description of the Request
     * @param quantity
     *            the quantity desired, as an int.
     */
    public Request(MediaType type, String description, int quantity) {
        setType(type);
        setDescription(description);
        setQuantity(quantity);
    }

    /**
     * @return the type
     */
    public MediaType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(MediaType type) {
        this.type = type;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
