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
package ca.ualberta.cs.c301f12t01.gui;

import ca.ualberta.cs.c301f12t01.localStorage.DeviceStorage;
import ca.ualberta.cs.c301f12t01.localStorage.ReportObserver;
import ca.ualberta.cs.c301f12t01.localStorage.TaskObserver;
import ca.ualberta.cs.c301f12t01.model.StorageInterface;
import ca.ualberta.cs.c301f12t01.model.TaskManager;
import android.app.Application;

/**
 * 
 * @author easantos
 *
 */
public class TaskSourceApplication extends Application {
    
    private TaskManager manager = null;
    private TaskObserver tObsv = null;
    private ReportObserver rObsv = null;
    
    /** Returns the Task Manager.  */ 
    public TaskManager getTaskManager() {
        /* Lazily loads the manager. */
        if (manager == null) {
            StorageInterface localStorage = new DeviceStorage(getApplicationContext());
            /* TODO: Get the server interface working! */
            StorageInterface serverStorage = null;
            
           
            
            manager = TaskManager.getInstance();
            /**
             * TODO not sure where to tell the observers
             * where localStorage is, I put it here for now
             */
            tObsv = new TaskObserver();
            manager.addObserver(tObsv); //add our observer
            tObsv.setLocal(localStorage);
            tObsv.setServer(serverStorage);
            
            rObsv = new ReportObserver();
            manager.addObserver(rObsv);//add our observer
            rObsv.setLocal(localStorage);
            rObsv.setServer(serverStorage);
            //manager.setLocal(localStorage);
            //manager.setLocal(serverStorage);
            
        }
        
        return manager;
    }

}
