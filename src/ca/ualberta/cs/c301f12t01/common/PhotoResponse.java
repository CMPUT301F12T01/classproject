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
 * 
 * @author padlesky
 */

public class PhotoResponse implements Response, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1658994885795864110L;
	
	private String type;
	private String photoData64;
    /**
     * 
     */
    public PhotoResponse(String photoData64, String photoType) {
    	setPhoto(photoData64);
    	setTypeS(photoType);
    }
    
    public void setPhoto (String photoData64) {
    	this.photoData64 = photoData64;
    }
    
    public void setTypeS (String photoType) {
    	this.type = photoType;
    }
    
    public void setType (Object obj) {
    	this.type = obj.toString().toLowerCase();
    }
    
    public String getPhoto () {
    	return photoData64;
    }
    
    public String getType() {
    	return type;
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
    	return this;
    }

    /* (non-Javadoc)
     * @see ca.ualberta.cs.c301f12t01.common.Response#setResponseData(java.io.Serializable)
     */
    public void setResponseData(Serializable newData) {
    	setTypeS(((PhotoResponse) newData).getType());
    	setPhoto(((PhotoResponse) newData).getPhoto());
    }
}
