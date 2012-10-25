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
 * MediaType -- identify what kind of media a request requests or what kind of
 * media is attached to a response.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * 
 * As of now, the types that exist are text, audio, or photos. More may
 * be added.
 */
public enum MediaType {
    TEXT, AUDIO, PHOTO;
}
