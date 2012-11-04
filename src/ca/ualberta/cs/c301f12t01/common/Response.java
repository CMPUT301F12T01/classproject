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
 * Response -- Part of the report which represents the fulfillment of one single
 * Request of a Task.
 * 
 * Its an interface for {@link AudioResponse} {@link PhotoResponse} {@link TextResponse}.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * 
 */
public interface Response {

    /** Returns the MediaType of this subclass of Response. */
    public abstract MediaType getMediaType();

}
