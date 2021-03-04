package com.example.lesson9;

import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;


public class EditFragment extends Fragment {
    private EditText name;
    private EditText description;
    private EditText date;
    private Button buttonSave;
    private boolean isLand;
    private DatePickerDialog picker;

    public static EditFragment newInstance(int position) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putInt("key", position);
        fragment.setArguments(args);
        return fragment;
    }


    private void initView(View view) {
        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        date.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, year, month, day);

            picker.show();
        });
        buttonSave = view.findViewById(R.id.button_save);

        buttonSave.setOnClickListener(v -> {
            if (getArguments().getInt("key") == -1) {
                NoteDataClass note = new NoteDataClass(name.getText().toString(), description.getText().toString(), date.getText().toString());
                ((MainActivity) getActivity()).list.add(note);
                Bundle args = new Bundle();
                args.putInt("key", ((MainActivity) getActivity()).list.size() - 1);
                this.setArguments(args);
            } else {
                ((MainActivity) getActivity()).list.get(getArguments().getInt("key")).date = date.getText().toString();
                ((MainActivity) getActivity()).list.get(getArguments().getInt("key")).description = description.getText().toString();
                ((MainActivity) getActivity()).list.get(getArguments().getInt("key")).name = name.getText().toString();
            }

            if (!isLand) {
                FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
                DetaileFragment dataFragment = DetaileFragment.newInstance(
                        ((MainActivity) getActivity()).list.get(getArguments().getInt("key")).name,
                        ((MainActivity) getActivity()).list.get(getArguments().getInt("key")).description,
                        ((MainActivity) getActivity()).list.get(getArguments().getInt("key")).date,
                        getArguments().getInt("key")
                );
//                fragmentTr.addToBackStack("");
                fragmentTr.replace(R.id.Fr_container, dataFragment);
                fragmentTr.commit();
            } else {
                FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().findViewById(R.id.Fr_container2).setVisibility(View.VISIBLE);
                DetaileFragment dataFragment = DetaileFragment.newInstance(
                        ((MainActivity) getActivity()).list.get(getArguments().getInt("key")).name,
                        ((MainActivity) getActivity()).list.get(getArguments().getInt("key")).description,
                        ((MainActivity) getActivity()).list.get(getArguments().getInt("key")).date,
                        getArguments().getInt("key")
                );
                fragmentTr.replace(R.id.Fr_container2, dataFragment);
                fragmentTr.commit();

            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_fragmet, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getInt("key") == -1) {

            } else {
                name.setText(((MainActivity) getActivity()).list.get(getArguments().getInt("key")).name);
                description.setText(((MainActivity) getActivity()).list.get(getArguments().getInt("key")).description);
                date.setText(((MainActivity) getActivity()).list.get(getArguments().getInt("key")).date);
            }


        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}