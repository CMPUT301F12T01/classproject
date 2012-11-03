package ca.ualberta.cs.c301f12t01.gui;

import java.util.UUID;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ca.ualberta.cs.c301f12t01.R;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.dummy.DummyTasks;

/**
 * Fragment that displays details about a Task. Uses the `fragment_task_detail`
 * for displaying an actual fragment, or if no fragment was specified, uses
 * `fragment_no_task_detail` for displaying the "sorry, t'ere ain't no task
 * to display" screen. layout.
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public class TaskDetailFragment extends Fragment {

    public static final String ARG_TASK_ID = "task_id";

    Task task;

    /** Constructs a new TaskDetailFragment. */
    public TaskDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Hopefully this fragment was initialized with some Task ID. Try to
         * retrieve the task ID and retrieve the Task in order to display it
         * later.
         */
        if (getArguments().containsKey(ARG_TASK_ID)) {
            UUID taskId = (UUID) getArguments().get(ARG_TASK_ID);

            task = DummyTasks.ITEM_MAP.get(taskId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        
        int detail_layout_resource_id;
        View rootView;

        /*
         * If given a Task, display one thing, else display "no task to display"
         * screen.
         */
        /*
         * Later, we'll use some sort of class to create the detail display.
         * Maybe.
         */
        /* TODO: refactor into new methods or entirely new classes. */

        if (task == null) {
            detail_layout_resource_id = R.layout.fragment_no_task_detail;
        } else {
            detail_layout_resource_id = R.layout.fragment_task_detail;
        }
        
        rootView = inflater.inflate(detail_layout_resource_id,
                container, false);

        /* We need to do a little bit more work for actual tasks. */
        if (task != null) {
            ((TextView) rootView.findViewById(R.id.task_detail))
                .setText(task.getDescription());
        }
        
        return rootView;
    }
}
