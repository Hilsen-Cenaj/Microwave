package com.example.microwave;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class Tab2 extends Fragment {
    private final int REQ_CODE = 7;
    Program customProgram;
    ImageButton button_speech;
    Button button_next,back_button;
    EditText minutes,seconds;
    private TextToSpeech textToSpeech;
    int mins=0,secs=0;
    String type="",heat="Ζεστό";
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab2, container, false);
        customProgram=new Program();
        final ImageButton button_speaker=root.findViewById(R.id.button_speaker);
        button_speech=root.findViewById(R.id.button_speech);
        final Button button_defrost = root.findViewById(R.id.button_defrost);
        final Button button_meal = root.findViewById(R.id.button_meal);
        final Button button_drinks = root.findViewById(R.id.button_drinks);
        final ImageButton button_up=root.findViewById(R.id.button_up);
        final ImageButton button_down=root.findViewById(R.id.button_down);
        minutes= root.findViewById(R.id.mins);
         back_button=root.findViewById(R.id.button_to_tab1);
        minutes.setText("01");
        seconds= root.findViewById(R.id.secs);
        seconds.setText(("00"));
        final SeekBar seekBar=root.findViewById(R.id.seekBar);
        button_next=root.findViewById(R.id.button_to_tab3);

        textToSpeech=new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale aLocale = Locale.forLanguageTag("el-GR");
                    int ttsLang = textToSpeech.setLanguage(aLocale);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");

                } else {
                    Toast.makeText(getContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "Πατήστε το κουμπί του μικροφώνου και πείτε φαγητό ή ποτό ή ξεπάγωμα και πόσα λεπτά θέλετε. Για παράδειγμα Φαγητό 2 λεπτά και 30 δευτερόλεπτα";
                Log.i("TTS", "button clicked: " + data);

                textToSpeech.setSpeechRate(0.90f);
                int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);

                if (speechStatus == TextToSpeech.ERROR) {
                    Log.e("TTS", "Error in converting Text to Speech!");
                }

            }
        });

        button_speech.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.forLanguageTag("el-GR").getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                Toast.makeText(getActivity(), "Επιλέξατε " + heat, Toast.LENGTH_SHORT).show();
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();

        }
    }
    public boolean checkVoiceCommands(String [] com) {

        if (com.length >= 3) {
            if (com[0].equalsIgnoreCase("φαγητό") ||
                    com[0].equalsIgnoreCase("ποτό") ||
                    com[0].equalsIgnoreCase("ξεπάγωμα")) {


                    if (isInteger(com[1]) && (com[2].equalsIgnoreCase("λεπτά")||com[2].equalsIgnoreCase("λεπτό"))) {
                        if(com[1].equalsIgnoreCase("ένα")) mins=1;
                         else   mins = Integer.parseInt(com[1]);
                        minutes.setText(String.valueOf(mins));
                        secs=0;
                        seconds.setText(String.valueOf(secs));
                    } else if (isInteger(com[1]) && (com[2].equalsIgnoreCase("δευτερόλεπτα")||com[2].equalsIgnoreCase("δευτερόλεπτο"))) {
                        if(com[2].equalsIgnoreCase("ένα")) secs=1;
                        else   secs = Integer.parseInt(com[1]);

                        minutes.setText(String.valueOf(0));
                        seconds.setText(String.valueOf(secs));
                    } else
                        return false;
                    if (com.length >= 5) {
                        if(com[3].equalsIgnoreCase("και")) {
                            if (isInteger(com[4]) &&( com[5].equalsIgnoreCase("δευτερόλεπτα")||com[5].equalsIgnoreCase("δευτερόλεπτο"))) {
                                if(com[4].equalsIgnoreCase("ένα"))secs=1;
                                else   secs = Integer.parseInt(com[4]);
                                Log.e("secs", String.valueOf(secs));
                                seconds.setText(String.valueOf(secs));
                                return true;
                            } else return false;
                        }
                    } else {

                        return true;
                    }

            }
        }
        return false;
    }


    public static boolean isInteger(String s) {
        try {
            if(s.equalsIgnoreCase("ένα")) return true;
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    Log.e("Debug", String.valueOf(result.get(0)));
                    String data1=String.valueOf(result.get(0));
                    String [] splitdata=data1.split(" ");
                    if(data1.equalsIgnoreCase("πίσω")) {
                        back_button.performClick();

                    }
                    else if(checkVoiceCommands(splitdata)){
                        type=splitdata[0];
                        button_next.performClick();
                    }else{
                        Toast.makeText(getContext(),"Παρακαλώ επαναλάβετε",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
        }
    }

}
