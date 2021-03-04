package com.example.lesson9;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class DetaileFragment extends Fragment {
    private TextView name;
    private TextView description;
    private TextView date;
    private Button buttonEdit;
    private boolean isLand;
    private int position;


    public static DetaileFragment newInstance(String name, String description, String date, int position) {
        DetaileFragment fragment = new DetaileFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("description", description);
        args.putString("date", date);
        fragment.setArguments(args);
        fragment.position = position;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detaile, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        buttonEdit = view.findViewById(R.id.button_save);

        buttonEdit.setOnClickListener(v -> {
            if (!isLand) {
                FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
                EditFragment fragment = EditFragment.newInstance(position);
//                fragmentTr.addToBackStack("");
                fragmentTr.replace(R.id.Fr_container, fragment);
                fragmentTr.commit();
            } else {
                FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().findViewById(R.id.Fr_container2).setVisibility(View.VISIBLE);
                EditFragment fragment = EditFragment.newInstance(position);
                fragmentTr.replace(R.id.Fr_container2, fragment);
                fragmentTr.commit();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            name.setText(getArguments().getString("name"));
            description.setText(getArguments().getString("description"));
            date.setText(getArguments().getString("date"));

        }
    }
}