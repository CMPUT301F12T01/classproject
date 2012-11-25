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

public class PhotoResponse implements Response {

	private String photoData64;
	private String contentType;
    /**
     * 
     */
    public PhotoResponse(String photoData64, Object format) {
    	setPhoto(photoData64);
    	setFormatEnum(format);
    }
    
    public void setPhoto (String photoData64) {
    	this.photoData64 = photoData64;
    }
    
    public String getPhoto () {
    	return photoData64;
    }
    
    public void setFormatEnum(Object format) {
    	this.contentType = format.toString().toLowerCase();
    }
    
    public void setFormatString(String format) {
    	this.contentType = format;
    }
    
    public String getFormat() {
    	return contentType;
    }

    private class PhotoBundle {
    	public String photoData64;
    	public String contentType;
    	
    	public void setPhotoBundle (String photo, String format) {
    		this.photoData64 = photo;
    		this.contentType = format;
    	}
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
    	if(this.photoData64 != "") {
    		PhotoBundle photoBundle = new PhotoBundle();
    		photoBundle.setPhotoBundle(this.photoData64, this.contentType);
    		return (Serializable) photoBundle;
    	} else {
    		return null;
    	}
    }

    /* (non-Javadoc)
     * @see ca.ualberta.cs.c301f12t01.common.Response#setResponseData(java.io.Serializable)
     */
    public void setResponseData(Serializable newData) {
        PhotoBundle photoBundle = (PhotoBundle) newData;
        this.photoData64 = photoBundle.photoData64;
        this.contentType = photoBundle.contentType;
    }

}
