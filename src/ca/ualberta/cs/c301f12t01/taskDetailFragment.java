package ca.ualberta.cs.c301f12t01;

import ca.ualberta.cs.c301f12t01.dummy.DummyContent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class taskDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    DummyContent.DummyItem mItem;

    public taskDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.task_detail)).setText(mItem.content);
        }
        return rootView;
    }
}
