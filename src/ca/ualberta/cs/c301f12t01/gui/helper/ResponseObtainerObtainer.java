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
package ca.ualberta.cs.c301f12t01.gui.helper;

import java.lang.reflect.InvocationTargetException;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.MediaType;

/**
 * Contains a factory method for getting the appropriate ResponseObatiner.
 * Sorry about the name; I couldn't help myself!
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 *
 */
public class ResponseObtainerObtainer {

    
    public static ResponseObtainer getResponseObtainer(
            MediaType media, LayoutInflater inflater, ViewGroup root) {
        ResponseObtainer obtainer = null;
        int layout_id = -1;
        ViewGroup view = null;
        Class<? extends ResponseObtainer> obtainerClass = null;
        
        switch (media) {
            case TEXT:
                layout_id = R.layout.fragment_fulfill_text;
                obtainerClass = TextResponseObtainer.class;
                break;
            case AUDIO:
                layout_id = R.layout.fragment_fulfill_audio;
                obtainerClass = AudioResponseObtainer.class;
                break;
            case PHOTO:
                layout_id = R.layout.fragment_fulfill_photo;
                obtainerClass = PhotoResponseObtainer.class;
                break;
            default:
                // err....
                // TODO: Give an error response obtainer? 
        }
        
        view = (ViewGroup) inflater.inflate(layout_id, root);
        try {
            obtainer = obtainerClass.getConstructor(ViewGroup.class)
                    .newInstance(view);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Set up the proper fields for fulfilling the request (ex: buttons)
        obtainer.setupFulfillRequest();
        
        return obtainer;
    }
}
