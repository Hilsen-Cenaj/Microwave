package com.example.microwave;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class Tab2 extends Fragment {
    Program customProgram;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab2, container, false);
        customProgram=new Program();
        final Button button_defrost = root.findViewById(R.id.button_defrost);
        final Button button_meal = root.findViewById(R.id.button_meal);
        final Button button_drinks = root.findViewById(R.id.button_drinks);
        final ImageButton button_up=root.findViewById(R.id.button_up);
        final ImageButton button_down=root.findViewById(R.id.button_down);
        final EditText minutes= root.findViewById(R.id.mins);
        final EditText seconds= root.findViewById(R.id.secs);
        final SeekBar seekBar=root.findViewById(R.id.seekBar);
        final Button button_next=root.findViewById(R.id.button_to_tab3);
        button_up.setOnClickListener(new View.OnClickListener()
        {
            int mins,secs;
            public void show(){
                String string_mins="";
                String string_secs="";
                if(mins<10){
                    string_mins="0"+String.valueOf(mins);
                }else{
                    string_mins=String.valueOf(mins);
                }
                if(secs<10){
                    string_secs="0"+String.valueOf(secs);
                }else{
                    string_secs=String.valueOf(secs);
                }
                minutes.setText(string_mins);
                seconds.setText(string_secs);
            }

            @Override
            public void onClick(View v)
            {
                if(minutes.getText().toString().isEmpty()) {
                    minutes.setText("00");


                }
                if(seconds.getText().toString().isEmpty() ) {

                    seconds.setText("00");
                }
                    mins =Integer.parseInt(minutes.getText().toString());
                    secs=Integer.parseInt(seconds.getText().toString());
                    if(minutes.isFocused()){
                        if(mins>=59){
                            mins=0;
                        }else{
                            mins+=1;
                        }
                    }else {
                        if (secs >= 59) {
                            secs = 0;
                            if(mins>=59){
                                mins=0;
                            }else {
                                mins += 1;
                            }
                        } else {
                            secs += 1;
                        }
                    }
                    show();

                }



        });

        button_down.setOnClickListener(new View.OnClickListener()
        {
            int mins,secs;
            public void show(){
                String string_mins="";
                String string_secs="";
                if(mins<10){
                    string_mins="0"+String.valueOf(mins);
                }else{
                    string_mins=String.valueOf(mins);
                }
                if(secs<10){
                    string_secs="0"+String.valueOf(secs);
                }else{
                    string_secs=String.valueOf(secs);
                }
                minutes.setText(string_mins);
                seconds.setText(string_secs);
            }

            @Override
            public void onClick(View v)
            {
                if(minutes.getText().toString().isEmpty()) {
                    minutes.setText("00");


                }
                if(seconds.getText().toString().isEmpty() ) {

                    seconds.setText("00");
                }
                    mins =Integer.parseInt(minutes.getText().toString());
                    secs=Integer.parseInt(seconds.getText().toString());
                    if(minutes.isFocused()){
                        if(mins==0){
                            mins=59;
                        }else{
                            mins-=1;
                        }
                    }else {
                        if (secs ==0) {
                            secs = 59;
                            if(mins==0){
                                mins=0;
                            }else {
                                mins -= 1;
                            }
                        } else {
                            secs -= 1;
                        }
                    }
                    show();

                }

        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).getViewPager().setCurrentItem(2);
            }
        });

        return root;
    }


}
