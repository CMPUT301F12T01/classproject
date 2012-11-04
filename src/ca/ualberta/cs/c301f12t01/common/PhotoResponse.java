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

import java.io.Serializable;


/**
 * NOT IMPLEMENTED
 * TODO IMPLEMENT THIS CLASS
 */

public class PhotoResponse implements Response {

    /**
     * 
     */
    public PhotoResponse() {
    }

    /* (non-Javadoc)
     * @see ca.ualberta.cs.c301f12t01.common.Response#getMediaType()
     */
    public MediaType getMediaType() {
        return MediaType.PHOTO;
    }

    /* (non-Javadoc)
     * @see ca.ualberta.cs.c301f12t01.common.Response#getResponseData()
     */
    public Serializable getResponseData() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see ca.ualberta.cs.c301f12t01.common.Response#setResponseData(java.io.Serializable)
     */
    public void setResponseData(Serializable newData) {
        // TODO Auto-generated method stub
        
    }

}
