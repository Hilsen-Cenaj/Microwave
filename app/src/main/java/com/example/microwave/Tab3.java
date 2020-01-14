package com.example.microwave;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import javax.sql.StatementEvent;

public class Tab3 extends Fragment {
    Program p ;
    TextView txt;
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab3, container, false);
        //TO DO : make correct TEXT VIEWS
        txt=root.findViewById(R.id.section_label);
        return root;
    }


    public void setProgram(Program customProgram){
        p=customProgram;
        txt.setText(p.getType());

    }

}

