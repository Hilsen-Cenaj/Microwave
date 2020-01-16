package com.example.microwave;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microwave.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class Tab1 extends Fragment {
    private final int REQ_CODE = 100;
    Program customProgram;
    ImageButton button_speech;
    Button button_quick,button_start ;
    TextView text_speech;
    private TextToSpeech textToSpeech;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab1, container, false);
        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getContext(), getActivity().getSupportFragmentManager() );
        button_start = root.findViewById(R.id.button_start);
        final ImageButton button_speaker=root.findViewById(R.id.button_speaker);
         button_speech=root.findViewById(R.id.button_speech);
        text_speech=root.findViewById(R.id.text_speech);
        customProgram=new Program();
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
                String data = "Για επιλογή προγράμματος πείτε έναρξη,για γρήγορο ζέσταμα 30 δευτερολέπτων πείτε γρήγορη έναρξη";
                Log.i("TTS", "button clicked: " + data);
                textToSpeech.setSpeechRate(0.90f);
                int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);

                if (speechStatus == TextToSpeech.ERROR) {
                    Log.e("TTS", "Error in converting Text to Speech!");
                }

            }
        });

        button_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
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


        button_quick=root.findViewById(R.id.button_quickstart);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text_speech.setText((CharSequence) result.get(0));
                    Log.e("Debug", String.valueOf(result.get(0)));
                    String data1=String.valueOf(result.get(0));
                    if(data1.equalsIgnoreCase("έναρξη")){
                        button_start.performClick();
                    }else if (data1.equalsIgnoreCase("Γρήγορη έναρξη")||data1.equalsIgnoreCase("Γρηγόρη έναρξη")){
                        button_quick.performClick();
                    }
                }
                break;
            }
        }
    }

}
