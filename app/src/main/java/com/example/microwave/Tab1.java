package com.example.microwave;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microwave.ui.main.SectionsPagerAdapter;

public class Tab1 extends Fragment {
    Program customProgram;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab1, container, false);
        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getActivity().getSupportFragmentManager() );
        final Button button_start = root.findViewById(R.id.button_start);

        customProgram=new Program();
        //TO DO: make quick button clicked right
        final Button button_quick=root.findViewById(R.id.button_quickstart);
        button_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)getActivity()).getViewPager().setCurrentItem(1);
            }
        });

        button_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customProgram = new Program(0, 30, "Ζεστό", "Γρήγορο Ζέσταμα");
                ((MainActivity) getActivity()).setCustomProgram(customProgram);

                ((MainActivity) getActivity()).getViewPager().setCurrentItem(2);
            }
        });
        return root;
    }



}
