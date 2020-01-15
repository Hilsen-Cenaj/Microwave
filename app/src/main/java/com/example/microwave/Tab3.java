package com.example.microwave;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Tab3 extends Fragment {
    Program customProgram=new Program();
    TextView time,temp,type;
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab3, container, false);

        final Button button_finalS = root.findViewById(R.id.button_final_start);
        final Button button_toTab2 = root.findViewById(R.id.button_to_tab2);
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

        return root;
    }
    public void setProgram(Program p){

        customProgram=p;
        if(p.getMinutes()==1)time.setText("Χρόνος: "+String.valueOf(p.getMinutes())+" λεπτό "+String.valueOf(p.getSeconds())+" δευτερόλεπτα ");
        else time.setText("Χρόνος: "+String.valueOf(p.getMinutes())+" λεπτά "+String.valueOf(p.getSeconds())+" δευτερόλεπτα ");
        temp.setText("Θερμοκρασία: "+String.valueOf(p.getHeat()));
        type.setText("Είδος: "+p.getType());
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

