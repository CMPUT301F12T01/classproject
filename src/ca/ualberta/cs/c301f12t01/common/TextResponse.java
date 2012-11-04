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
 * TextResponse -- A response containing text.
 * 
 * This should be instantiated when you want to give a text response to a given
 * task.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 * 
 */
public class TextResponse implements Response {

    private String text;

    /**
     * Takes in a string of text and calls setText with that text.
     * This is used by the GUI.
     */
    public TextResponse(String text) {
        setText(text);
    }

    /**
     * Gets the text entered into the text response.
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the TextRepsonse to the text passed into it.
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ca.ualberta.cs.c301f12t01.common.Response#getMediaType()
     */
    public MediaType getMediaType() {
        return MediaType.TEXT;
    }
    
    /* TODO: Test the following methods (verify with getText). */

    /* (non-Javadoc)
     * @see ca.ualberta.cs.c301f12t01.common.Response#getResponseData()
     */
    public Serializable getResponseData() {
        return getText();
    }

    /* (non-Javadoc)
     * @see ca.ualberta.cs.c301f12t01.common.Response#setResponseData(java.io.Serializable)
     */
    public void setResponseData(Serializable newData) {
        setText((String) newData); 
    }

}
