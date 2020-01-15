package com.example.microwave;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.microwave.ui.main.CustomViewPager;
import com.example.microwave.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    CustomViewPager viewPager;
    Program customProgram;
    TextView connected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        connected=findViewById(R.id.connected);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        TabLayout tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);


        for (View v: tabs.getTouchables())
            v.setClickable(false);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setMessage("Write your message here.");
                builder1.setCancelable(true);

                builder1.setNeutralButton(
                        "OK",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });



                AlertDialog alert11 = builder1.create();

                alert11.show();
                alert11.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });


    }

    public ViewPager getViewPager() {
        if (null == viewPager) {
            viewPager =  findViewById(R.id.view_pager);
        }
        return viewPager;
    }

    public void setCustomProgram(Program p){
        customProgram=p;

        String tag = "android:switcher:" + R.id.view_pager + ":" + 2;

        Tab3 f = (Tab3) getSupportFragmentManager().findFragmentByTag(tag);
        f.setProgram(p);
    }


    public Program getCustomProgram(){
        return customProgram;
    }
    private class AsyncTaskRunner extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog;


        protected String doInBackground (String... params){
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result){
            Drawable img = getResources().getDrawable( R.drawable.ic_stat_name );
            img.setBounds( 0, 0, 60, 60 );
            connected.setCompoundDrawables( img, null, null, null );
            connected.setText(getResources().getString(R.string.connected));
            progressDialog.dismiss();

        }

        protected void onPreExecute(){

            progressDialog=new ProgressDialog(MainActivity.this,R.style.AppCompatAlertDialogStyle);
            progressDialog.setTitle("Σύνδεση με Φούρνο Μικροκυμάτων");
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

        protected void onProgressUpdate(String... text ){

        }
    }


}