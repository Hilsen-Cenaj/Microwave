package com.example.microwave;

import android.content.DialogInterface;
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
import android.widget.Toast;
import com.example.microwave.ui.main.CustomViewPager;
import com.example.microwave.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    CustomViewPager viewPager;
    Program customProgram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);


        for (View v: tabs.getTouchables())
            v.setClickable(false);
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
    }


    public Program getCustomProgram(){
        return customProgram;
    }
}