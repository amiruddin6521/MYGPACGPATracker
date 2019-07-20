package com.bitp3453.miniproject.mygpacgpatracker;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class ViewGPAResult extends AppCompatActivity {

    private String semId;
    private int totalGPAScore;

    TextView edtSemester, edtSubj1, edtSubj2, edtSubj3, edtSubj4, edtSubj5, edtSubj6, edtSubj7, edtGrade1, edtGrade2,
            edtGrade3, edtGrade4, edtGrade5, edtGrade6, edtGrade7, edtCredit1, edtCredit2, edtCredit3, edtCredit4,
            edtCredit5, edtCredit6, edtCredit7, scoreGPA;
    Button btnEdit;
    String strSubjName1, strSubjGrade1, strSubjCredit1,strSubjName2, strSubjGrade2, strSubjCredit2,
            strSubjName3, strSubjGrade3, strSubjCredit3,strSubjName4, strSubjGrade4, strSubjCredit4,
            strSubjName5, strSubjGrade5, strSubjCredit5,strSubjName6, strSubjGrade6, strSubjCredit6,
            strSubjName7, strSubjGrade7, strSubjCredit7, strGpaTotal;
    MyGPAdb dbMyGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gparesult);

        edtSemester = (TextView) findViewById(R.id.edtSemester);
        edtSubj1 = (TextView) findViewById(R.id.edtSubj1); edtSubj2 = (TextView) findViewById(R.id.edtSubj2);
        edtSubj3 = (TextView) findViewById(R.id.edtSubj3); edtSubj4 = (TextView) findViewById(R.id.edtSubj4);
        edtSubj5 = (TextView) findViewById(R.id.edtSubj5); edtSubj6 = (TextView) findViewById(R.id.edtSubj6);
        edtSubj7 = (TextView) findViewById(R.id.edtSubj7);
        edtGrade1 = (TextView) findViewById(R.id.edtGrade1); edtGrade2 = (TextView) findViewById(R.id.edtGrade2);
        edtGrade3 = (TextView) findViewById(R.id.edtGrade3); edtGrade4 = (TextView) findViewById(R.id.edtGrade4);
        edtGrade5 = (TextView) findViewById(R.id.edtGrade5); edtGrade6 = (TextView) findViewById(R.id.edtGrade6);
        edtGrade7 = (TextView) findViewById(R.id.edtGrade7);
        edtCredit1 = (TextView) findViewById(R.id.edtCredit1); edtCredit2 = (TextView) findViewById(R.id.edtCredit2);
        edtCredit3 = (TextView) findViewById(R.id.edtCredit3); edtCredit4 = (TextView) findViewById(R.id.edtCredit4);
        edtCredit5 = (TextView) findViewById(R.id.edtCredit5); edtCredit6 = (TextView) findViewById(R.id.edtCredit6);
        edtCredit7 = (TextView) findViewById(R.id.edtCredit7);
        scoreGPA = (TextView) findViewById(R.id.scoreGPA);
        btnEdit = (Button) findViewById(R.id.fnEdit);

        dbMyGPA = new MyGPAdb(getApplication());
        Bundle b = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();
        semId = b.getString("keyid");
        totalGPAScore = b2.getInt("keyid2");
        edtSemester.setText(semId);
        getGPAtotal (totalGPAScore);

        if (semId.equals("Year 1, Semester 1")){
            getGPAdata1 (1);
            getGPAdata2 (2);
            getGPAdata3 (3);
            getGPAdata4 (4);
            getGPAdata5 (5);
            getGPAdata6 (6);
            getGPAdata7 (7);
        }else if (semId.equals("Year 1, Semester 2")){
            getGPAdata1 (8);
            getGPAdata2 (9);
            getGPAdata3 (10);
            getGPAdata4 (11);
            getGPAdata5 (12);
            getGPAdata6 (13);
            getGPAdata7 (14);
        }else if (semId.equals("Year 2, Semester 1")){
            getGPAdata1 (15);
            getGPAdata2 (16);
            getGPAdata3 (17);
            getGPAdata4 (18);
            getGPAdata5 (19);
            getGPAdata6 (20);
            getGPAdata7 (21);
        }else if (semId.equals("Year 2, Semester 2")){
            getGPAdata1 (22);
            getGPAdata2 (23);
            getGPAdata3 (24);
            getGPAdata4 (25);
            getGPAdata5 (26);
            getGPAdata6 (27);
            getGPAdata7 (28);
        }else if (semId.equals("Year 3, Semester 1")){
            getGPAdata1 (29);
            getGPAdata2 (30);
            getGPAdata3 (31);
            getGPAdata4 (32);
            getGPAdata5 (33);
            getGPAdata6 (34);
            getGPAdata7 (35);
        }else if (semId.equals("Year 3, Semester 2")){
            getGPAdata1 (36);
            getGPAdata2 (37);
            getGPAdata3 (38);
            getGPAdata4 (39);
            getGPAdata5 (40);
            getGPAdata6 (41);
            getGPAdata7 (42);
        }else if (semId.equals("Year 3, Special Semester")){
            getGPAdata1 (43);
            getGPAdata2 (44);
            getGPAdata3 (45);
            getGPAdata4 (46);
            getGPAdata5 (47);
            getGPAdata6 (48);
            getGPAdata7 (49);
        }else if (semId.equals("Year 4, Semester 1")){
            getGPAdata1 (50);
            getGPAdata2 (51);
            getGPAdata3 (52);
            getGPAdata4 (53);
            getGPAdata5 (54);
            getGPAdata6 (55);
            getGPAdata7 (56);
        }else{
            return;
        }
    }

    public void fnEdit (View vw)
    {
        Bundle b = new Bundle();
        Bundle b2 = new Bundle();
        b.putString("keyid", semId);
        b2.putInt("keyid2", totalGPAScore);
        Intent i = new Intent(ViewGPAResult.this,EditGPAResult.class);
        i.putExtras(b);
        i.putExtras(b2);
        startActivity(i);
        finish();
    }

    public void getGPAtotal (final int id ){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT * FROM dbmygpa WHERE gpa_id = "+ id +";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        strGpaTotal = c.getString(c.getColumnIndex("gpa_total"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scoreGPA.setText("Your GPA is: " + strGpaTotal);
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    public void getGPAdata1 (final int id ){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT subj_name,subj_gred,subj_credit FROM dbmysubj WHERE subj_id = "+ id +";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        strSubjName1 = c.getString(c.getColumnIndex("subj_name"));
                        strSubjGrade1 = c.getString(c.getColumnIndex("subj_gred"));
                        strSubjCredit1 = c.getString(c.getColumnIndex("subj_credit"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edtSubj1.setText(strSubjName1);
                        edtGrade1.setText(strSubjGrade1);
                        edtCredit1.setText(strSubjCredit1);
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    public void getGPAdata2 (final int id ){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT subj_name,subj_gred,subj_credit FROM dbmysubj WHERE subj_id = "+ id +";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        strSubjName2 = c.getString(c.getColumnIndex("subj_name"));
                        strSubjGrade2 = c.getString(c.getColumnIndex("subj_gred"));
                        strSubjCredit2 = c.getString(c.getColumnIndex("subj_credit"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edtSubj2.setText(strSubjName2);
                        edtGrade2.setText(strSubjGrade2);
                        edtCredit2.setText(strSubjCredit2);
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    public void getGPAdata3 (final int id ){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT subj_name,subj_gred,subj_credit FROM dbmysubj WHERE subj_id = "+ id +";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        strSubjName3 = c.getString(c.getColumnIndex("subj_name"));
                        strSubjGrade3 = c.getString(c.getColumnIndex("subj_gred"));
                        strSubjCredit3 = c.getString(c.getColumnIndex("subj_credit"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edtSubj3.setText(strSubjName3);
                        edtGrade3.setText(strSubjGrade3);
                        edtCredit3.setText(strSubjCredit3);
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    public void getGPAdata4 (final int id ){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT subj_name,subj_gred,subj_credit FROM dbmysubj WHERE subj_id = "+ id +";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        strSubjName4 = c.getString(c.getColumnIndex("subj_name"));
                        strSubjGrade4 = c.getString(c.getColumnIndex("subj_gred"));
                        strSubjCredit4 = c.getString(c.getColumnIndex("subj_credit"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edtSubj4.setText(strSubjName4);
                        edtGrade4.setText(strSubjGrade4);
                        edtCredit4.setText(strSubjCredit4);
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    public void getGPAdata5 (final int id ){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT subj_name,subj_gred,subj_credit FROM dbmysubj WHERE subj_id = "+ id +";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        strSubjName5 = c.getString(c.getColumnIndex("subj_name"));
                        strSubjGrade5 = c.getString(c.getColumnIndex("subj_gred"));
                        strSubjCredit5 = c.getString(c.getColumnIndex("subj_credit"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edtSubj5.setText(strSubjName5);
                        edtGrade5.setText(strSubjGrade5);
                        edtCredit5.setText(strSubjCredit5);
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    public void getGPAdata6 (final int id ){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT subj_name,subj_gred,subj_credit FROM dbmysubj WHERE subj_id = "+ id +";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        strSubjName6 = c.getString(c.getColumnIndex("subj_name"));
                        strSubjGrade6 = c.getString(c.getColumnIndex("subj_gred"));
                        strSubjCredit6 = c.getString(c.getColumnIndex("subj_credit"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edtSubj6.setText(strSubjName6);
                        edtGrade6.setText(strSubjGrade6);
                        edtCredit6.setText(strSubjCredit6);
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    public void getGPAdata7 (final int id ){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT subj_name,subj_gred,subj_credit FROM dbmysubj WHERE subj_id = "+ id +";", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        strSubjName7 = c.getString(c.getColumnIndex("subj_name"));
                        strSubjGrade7 = c.getString(c.getColumnIndex("subj_gred"));
                        strSubjCredit7 = c.getString(c.getColumnIndex("subj_credit"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edtSubj7.setText(strSubjName7);
                        edtGrade7.setText(strSubjGrade7);
                        edtCredit7.setText(strSubjCredit7);
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    @Override
    public void onBackPressed()
    {
        Intent home_intent = new Intent(getApplicationContext(), ListViewGPAResult.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
        finish();
    }
}
