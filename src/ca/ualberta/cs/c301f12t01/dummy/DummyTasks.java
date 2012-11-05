package ca.ualberta.cs.c301f12t01.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ca.ualberta.cs.c301f12t01.common.MediaType;
import ca.ualberta.cs.c301f12t01.common.Request;
import ca.ualberta.cs.c301f12t01.common.Task;

public class DummyTasks {

    /*
     * As this has been adapted from the Model-Detail flow template, there was
     * an embedded class, the DummyItem that existed here. Now we're using the
     * common Task class.
     * 
     * THIS CLASS SHOULD BE REPLACED WITH A TASKCOLLECTION INTSTANCE.
     * 
     * ...once that class is working, of course.
     */

    /* Besides, the following is really poor practice. */
    public static List<Task> ITEMS = new ArrayList<Task>();
    public static Map<UUID, Task> ITEM_MAP = new HashMap<UUID, Task>();
    public static final UUID DUMMY_USER = UUID.randomUUID();

    /* Initialize this dummy task collection with some dummy tasks. */
    static {
        Task[] dummyTasks = {
          /* new Task(DUMMY_USER)  {{
               setSummary("700 pictures of poop.");
               setDescription("All races welcome ;D");
               addRequest(new Request(MediaType.PHOTO));
               setGlobal();
           }},
           new Task(DUMMY_USER)  {{
               setSummary("Buy full-body dalmation suit; describe its texture");
               addRequest(new Request(MediaType.TEXT));
               setLocal();
           }},
           new Task(DUMMY_USER)  {{
               setSummary("Play 'Dancing Queen' on tuba");
               addRequest(new Request(MediaType.AUDIO));
               setLocal();
           }}, */
        };
                
        for (Task task : dummyTasks) {
            addTask(task);
        }
    }

    public static void addTask(Task newTask) {
        ITEMS.add(newTask);
        ITEM_MAP.put(newTask.getId(), newTask);
    }
}
