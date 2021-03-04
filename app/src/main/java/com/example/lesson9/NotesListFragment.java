package com.example.lesson9;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A fragment representing a list of Items.
 */
public class NotesListFragment extends Fragment implements MyNoteDataClassRecyclerViewAdapter.OnItemClickListener {


    boolean isLand;
    FloatingActionButton floatingActionButton;

    public static NotesListFragment newInstance() {
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> {
            if (!isLand) {
                FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
                EditFragment fragment = EditFragment.newInstance(-1);
                fragmentTr.addToBackStack("");
                fragmentTr.replace(R.id.Fr_container, fragment);
                fragmentTr.commit();
            } else {
                FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
                getActivity().findViewById(R.id.Fr_container2).setVisibility(View.VISIBLE);
                EditFragment fragment = EditFragment.newInstance(-1);
                fragmentTr.replace(R.id.Fr_container2, fragment);
                fragmentTr.commit();
            }
        });

        // Set the adapter
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

               MyNoteDataClassRecyclerViewAdapter dataAdapter = new MyNoteDataClassRecyclerViewAdapter(((MainActivity)getActivity()).list);
                dataAdapter.SetOnItemClickListener(this);
            recyclerView.setAdapter(dataAdapter);

        return view;

    }

    public void onItemClick(View view, int position)  {
        if (!isLand) {
            FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
            DetaileFragment dataFragment = DetaileFragment.newInstance(
                    ((MainActivity)getActivity()).list.get(position).name,
                    ((MainActivity)getActivity()).list.get(position).description,
                    ((MainActivity)getActivity()).list.get(position).date,
                    position
            );
            fragmentTr.addToBackStack("");
            fragmentTr.replace(R.id.Fr_container, dataFragment);
            fragmentTr.commit();
        } else {
            FragmentTransaction fragmentTr = getActivity().getSupportFragmentManager().beginTransaction();
            getActivity().findViewById(R.id.Fr_container2).setVisibility(View.VISIBLE);
            DetaileFragment dataFragment = DetaileFragment.newInstance(
                    ((MainActivity)getActivity()).list.get(position).name,
                    ((MainActivity)getActivity()).list.get(position).description,
                    ((MainActivity)getActivity()).list.get(position).date,
                    position
            );
            fragmentTr.replace(R.id.Fr_container2, dataFragment);
            fragmentTr.commit();
        }
    }
}