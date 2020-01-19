package com.example.microwave;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class Tab3 extends Fragment {
    Button button_finalS ,button_toTab2;
    private final int REQ_CODE =5;
    private TextToSpeech textToSpeech;
    ImageButton button_speech;
    Program customProgram=new Program();
    TextView time,temp,type;
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab3, container, false);
        final ImageButton button_speaker=root.findViewById(R.id.button_speaker);
        button_speech=root.findViewById(R.id.button_speech);
        button_finalS = root.findViewById(R.id.button_final_start);
        button_toTab2 = root.findViewById(R.id.button_to_tab2);
        time =root.findViewById(R.id.Chosen_Time);
        temp=root.findViewById(R.id.Chosen_Temp);
        type=root.findViewById(R.id.Chosen_Program);
        button_finalS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner=new AsyncTaskRunner();
                runner.execute();
            }
        });

        button_toTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).getViewPager().setCurrentItem(1);
            }
        });

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
                String data = "Το πρόγραμμα που έχετε επιλέξει είναι: "+type.getText()+" ."+time.getText()+" ."
                        +temp.getText()+" ."+"Πατήστε το κουμπί το μικροφώνου και πείτε έναρξη για " +
                        "έναρξη του προγράμματος ή  πείτε πίσω για μετάβαση στο προηγούμενο στάδιο ";
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
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
    public void setProgram(Program p){

        customProgram=p;
        if(p.getMinutes()==1)time.setText("Χρόνος: "+String.valueOf(p.getMinutes())+" λεπτό "+String.valueOf(p.getSeconds())+" δευτερόλεπτα ");
        else time.setText("Χρόνος: "+String.valueOf(p.getMinutes())+" λεπτά "+String.valueOf(p.getSeconds())+" δευτερόλεπτα ");
        temp.setText("Θερμοκρασία: "+String.valueOf(p.getHeat()));
        type.setText("Είδος: "+p.getType());
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

                    Log.e("Debug", String.valueOf(result.get(0)));
                    String data1=String.valueOf(result.get(0));
                    if(data1.equalsIgnoreCase("έναρξη")){
                        button_finalS.performClick();
                    }
                    else if(data1.equalsIgnoreCase("πίσω")){
                        button_toTab2.performClick();
                    }else{
                        Toast.makeText(getContext(),"Παρακαλώ επαναλάβετε",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
        }
    }
    public Program getCustomProgram(){
        return customProgram;
    }
    private class AsyncTaskRunner extends AsyncTask<String,Integer,String> {
        ProgressDialog progressDialog;


        protected String doInBackground (String... params){

            int milsec=customProgram.getMinutes()*60000+customProgram.getSeconds()*1000;
            Log.e("Mins", String.valueOf(milsec));
            for(int i=1000;i<milsec;i+=1000) {
                try {
                    Thread.sleep(1000);
                    Log.e("Secs", String.valueOf(i));
                    publishProgress(i*100/milsec);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(String result){

            progressDialog.dismiss();

        }

        protected void onPreExecute(){

            progressDialog=new ProgressDialog(getContext(),R.style.AppCompatAlertDialogStyle);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("Έναρξη Προγράμματος");
            progressDialog.setMessage("Παρακαλώ κρατήστε τη συσκευή κοντά στον φούρνο μικροκυμάτων");

            //Setting a " Cancel" Button
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ακύρωση", new DialogInterface.OnClickListener(){
                // Set a click listener for progress dialog cancel button
                @Override
                public void onClick(DialogInterface dialog, int which){
                    // dismiss the progress dialog
                    progressDialog.dismiss();
                    cancel(true);
                }
            });

            progressDialog.show();
        }

        protected void onProgressUpdate(Integer... text ){
            progressDialog.setProgress(text[0]);
        }
    }
}

