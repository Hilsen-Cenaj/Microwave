package com.example.microwave;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

public class Tab2 extends Fragment {
    Program customProgram;
    int mins=0,secs=0;
    String type="",heat="Ζεστό";
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
        final Button back_button=root.findViewById(R.id.button_to_tab1);
        minutes.setText("01");
        final EditText seconds= root.findViewById(R.id.secs);
        seconds.setText(("00"));
        final SeekBar seekBar=root.findViewById(R.id.seekBar);
        final Button button_next=root.findViewById(R.id.button_to_tab3);

        seekBar.setProgress(1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress()==1) heat="Ζεστό";
                else if(seekBar.getProgress()==0) heat="Λίγο ζεστό";
                else if (seekBar.getProgress()==2) heat="Πολύ ζεστό";
                Toast.makeText(getActivity(), "Current value is " + heat, Toast.LENGTH_SHORT).show();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getViewPager().setCurrentItem(0);
            }
        });

        button_up.setOnClickListener(new View.OnClickListener()
        {

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
                mins=Integer.parseInt(String.valueOf(minutes.getText()));
                secs=Integer.parseInt(String.valueOf(seconds.getText()));
                setUpType();
                if(type!="") {
                    customProgram = new Program(mins, secs, heat, type);
                    ((MainActivity) getActivity()).setCustomProgram(customProgram);
                    ((MainActivity) getActivity()).getViewPager().setCurrentItem(2);
                }else{
                    Snackbar snackbar = Snackbar
                            .make(getView(), "Παρακαλώ επιλέξτε είδος", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            private void setUpType() {
                if(button_defrost.isPressed()) type="Ξεπάγωμα";
                else if(button_drinks.isPressed()) type="Ποτό";
                else if(button_meal.isPressed()) type="Φαγητό";

            }
        });

        button_defrost.setOnTouchListener((new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                button_defrost.setPressed(true);
                if(button_drinks.isPressed()){
                    button_drinks.setPressed(false);
                }
                if(button_meal.isPressed()){
                    button_meal.setPressed(false);
                }
                return true;
            }
        }));

        button_meal.setOnTouchListener((new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                button_meal.setPressed(true);
                if(button_drinks.isPressed()){
                    button_drinks.setPressed(false);
                }
                if(button_defrost.isPressed()){
                    button_defrost.setPressed(false);
                }
                return true;
            }
        }));

        button_drinks.setOnTouchListener((new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                button_drinks.setPressed(true);
                if(button_defrost.isPressed()){
                    button_defrost.setPressed(false);
                }
                if(button_meal.isPressed()){
                    button_meal.setPressed(false);
                }
                return true;
            }
        }));




        return root;

    }


}
