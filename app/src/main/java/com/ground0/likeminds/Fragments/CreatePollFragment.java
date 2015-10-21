package com.ground0.likeminds.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ground0.likeminds.R;

/**
 * Created by Arjun on 03-09-2015.
 */
public class CreatePollFragment extends Fragment {

    Context context;
    View rootView;


    class AnswerCloseListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            ((ViewGroup)v.getParent().getParent().getParent()).removeView((View) v.getParent().getParent());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_create_poll, container, false);




        rootView.findViewById(R.id.action_add_answer).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View view = inflater.inflate(R.layout.poll_answer_item,
                                        (ViewGroup)rootView.findViewById(R.id.add_answer_placeholder),
                                        false);
                        view.findViewById(R.id.close_btn).setOnClickListener(new AnswerCloseListener());
                        ((ViewGroup) rootView.findViewById(R.id.add_answer_placeholder)).addView(view);

                    }
                }
        );

        rootView.findViewById(R.id.action_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Event Posted", Toast.LENGTH_SHORT).show();
            }
        });


        return rootView;
    }
}
