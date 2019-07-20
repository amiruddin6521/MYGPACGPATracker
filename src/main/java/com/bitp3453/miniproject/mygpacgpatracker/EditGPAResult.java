package com.bitp3453.miniproject.mygpacgpatracker;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class EditGPAResult extends AppCompatActivity {

    private String semId;
    private int totalGPAScore;

    Spinner spnGrade1, spnCredit1, spnGrade2, spnCredit2, spnGrade3, spnCredit3, spnGrade4,
            spnCredit4, spnGrade5, spnCredit5, spnGrade6, spnCredit6, spnGrade7, spnCredit7;
    EditText edtSub1, edtSub2, edtSub3, edtSub4, edtSub5, edtSub6, edtSub7;
    Double fnlGrade1, fnlGrade2, fnlGrade3, fnlGrade4, fnlGrade5, fnlGrade6, fnlGrade7;
    Double total1, total2, totalGPA;
    String strSubjName1, strSubjGrade1, strSubjCredit1,strSubjName2, strSubjGrade2, strSubjCredit2,
            strSubjName3, strSubjGrade3, strSubjCredit3,strSubjName4, strSubjGrade4, strSubjCredit4,
            strSubjName5, strSubjGrade5, strSubjCredit5,strSubjName6, strSubjGrade6, strSubjCredit6,
            strSubjName7, strSubjGrade7, strSubjCredit7, strGpaTotal, mySem;
    TextView scoreGPA, edtCurrSem;
    int id1, id2, id3, id4, id5, id6, id7, idTotal = 0;
    WebServiceCall wsc = new WebServiceCall();
    JSONObject jsnObj = new JSONObject();
    MyGPAdb dbMyGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gparesult);

        edtSub1 = (EditText) findViewById(R.id.edtSub1);edtSub2 = (EditText) findViewById(R.id.edtSub2);
        edtSub3 = (EditText) findViewById(R.id.edtSub3);edtSub4 = (EditText) findViewById(R.id.edtSub4);
        edtSub5 = (EditText) findViewById(R.id.edtSub5);edtSub6 = (EditText) findViewById(R.id.edtSub6);
        edtSub7 = (EditText) findViewById(R.id.edtSub7);
        spnGrade1 = (Spinner) findViewById(R.id.spnGrade1);spnGrade2 = (Spinner) findViewById(R.id.spnGrade2);
        spnGrade3 = (Spinner) findViewById(R.id.spnGrade3);spnGrade4 = (Spinner) findViewById(R.id.spnGrade4);
        spnGrade5 = (Spinner) findViewById(R.id.spnGrade5);spnGrade6 = (Spinner) findViewById(R.id.spnGrade6);
        spnGrade7 = (Spinner) findViewById(R.id.spnGrade7);
        spnCredit1 = (Spinner) findViewById(R.id.spnCredit1);spnCredit2 = (Spinner) findViewById(R.id.spnCredit2);
        spnCredit3 = (Spinner) findViewById(R.id.spnCredit3);spnCredit4 = (Spinner) findViewById(R.id.spnCredit4);
        spnCredit5 = (Spinner) findViewById(R.id.spnCredit5);spnCredit6 = (Spinner) findViewById(R.id.spnCredit6);
        spnCredit7 = (Spinner) findViewById(R.id.spnCredit7);
        edtCurrSem = (TextView) findViewById(R.id.edtCurrSem);
        scoreGPA = (TextView) findViewById(R.id.scoreGPA);

        dbMyGPA = new MyGPAdb(getApplication());
        Bundle b = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();
        semId = b.getString("keyid");
        totalGPAScore = b2.getInt("keyid2");
        edtCurrSem.setText(semId);
        getGPAtotal (totalGPAScore);
        getAllData();
    }

    public void fnUpdate(View vw){
        if(totalGPA == null){
            String str = "Please click calculate button first!";
            fnDisplayToastMsg(str);
        }else{
            updateGPArecord(id1,id2,id3,id4,id5,id6,id7,idTotal);
            returnHome();
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), ListViewGPAResult.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
        finish();
    }

    public void updateGPArecord(final int i1, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7, final int idt){

            Runnable run = new Runnable() {
                @Override
                public void run() {
                    Double dblGPA = Double.parseDouble(String.format("%.2f", totalGPA));

                    String subj1 = edtSub1.getText().toString();
                    String subj2 = edtSub2.getText().toString();
                    String subj3 = edtSub3.getText().toString();
                    String subj4 = edtSub4.getText().toString();
                    String subj5 = edtSub5.getText().toString();
                    String subj6 = edtSub6.getText().toString();
                    String subj7 = edtSub7.getText().toString();

                    String strGrd1 = spnGrade1.getSelectedItem().toString();
                    String strGrd2 = spnGrade2.getSelectedItem().toString();
                    String strGrd3 = spnGrade3.getSelectedItem().toString();
                    String strGrd4 = spnGrade4.getSelectedItem().toString();
                    String strGrd5 = spnGrade5.getSelectedItem().toString();
                    String strGrd6 = spnGrade6.getSelectedItem().toString();
                    String strGrd7 = spnGrade7.getSelectedItem().toString();

                    String strCrd1 = spnCredit1.getSelectedItem().toString();
                    String strCrd2 = spnCredit2.getSelectedItem().toString();
                    String strCrd3 = spnCredit3.getSelectedItem().toString();
                    String strCrd4 = spnCredit4.getSelectedItem().toString();
                    String strCrd5 = spnCredit5.getSelectedItem().toString();
                    String strCrd6 = spnCredit6.getSelectedItem().toString();
                    String strCrd7 = spnCredit7.getSelectedItem().toString();

                    String strSubQry0 = "UPDATE dbmygpa SET gpa_total = '" + dblGPA + "' WHERE gpa_id = " + idt + ";";
                    dbMyGPA.fnExecuteSql(strSubQry0, getApplicationContext());

                    String strSubQry1 = "UPDATE dbmysubj SET subj_name = '" + subj1 + "', subj_gred = '" + strGrd1 + "', subj_credit = '" + strCrd1 + "' WHERE subj_id = " + i1 + ";";
                    dbMyGPA.fnExecuteSql(strSubQry1, getApplicationContext());

                    String strSubQry2 = "UPDATE dbmysubj SET subj_name = '" + subj2 + "', subj_gred = '" + strGrd2 + "', subj_credit = '" + strCrd2 + "' WHERE subj_id = " + i2 + ";";
                    dbMyGPA.fnExecuteSql(strSubQry2, getApplicationContext());

                    String strSubQry3 = "UPDATE dbmysubj SET subj_name = '" + subj3 + "', subj_gred = '" + strGrd3 + "', subj_credit = '" + strCrd3 + "' WHERE subj_id = " + i3 + ";";
                    dbMyGPA.fnExecuteSql(strSubQry3, getApplicationContext());

                    String strSubQry4 = "UPDATE dbmysubj SET subj_name = '" + subj4 + "', subj_gred = '" + strGrd4 + "', subj_credit = '" + strCrd4 + "' WHERE subj_id = " + i4 + ";";
                    dbMyGPA.fnExecuteSql(strSubQry4, getApplicationContext());

                    String strSubQry5 = "UPDATE dbmysubj SET subj_name = '" + subj5 + "', subj_gred = '" + strGrd5 + "', subj_credit = '" + strCrd5 + "' WHERE subj_id = " + i5 + ";";
                    dbMyGPA.fnExecuteSql(strSubQry5, getApplicationContext());

                    String strSubQry6 = "UPDATE dbmysubj SET subj_name = '" + subj6 + "', subj_gred = '" + strGrd6 + "', subj_credit = '" + strCrd6 + "' WHERE subj_id = " + i6 + ";";
                    dbMyGPA.fnExecuteSql(strSubQry6, getApplicationContext());

                    String strSubQry7 = "UPDATE dbmysubj SET subj_name = '" + subj7 + "', subj_gred = '" + strGrd7 + "', subj_credit = '" + strCrd7 + "' WHERE subj_id = " + i7 + ";";
                    dbMyGPA.fnExecuteSql(strSubQry7, getApplicationContext());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String strMsg = "Information successfully updated!";
                            fnDisplayToastMsg(strMsg);
                        }
                    });
                }
            };
            Thread thrSave = new Thread(run);
            thrSave.start();
    }

    public void getAllData()
    {
        if (semId.equals("Year 1, Semester 1")){
            getGPAdata1 (1);id1 = 1;
            getGPAdata2 (2);id2 = 2;
            getGPAdata3 (3);id3 = 3;
            getGPAdata4 (4);id4 = 4;
            getGPAdata5 (5);id5 = 5;
            getGPAdata6 (6);id6 = 6;
            getGPAdata7 (7);id7 = 7;
            idTotal = 1;
        }else if (semId.equals("Year 1, Semester 2")){
            getGPAdata1 (8);id1 = 8;
            getGPAdata2 (9);id2 = 9;
            getGPAdata3 (10);id3 = 10;
            getGPAdata4 (11);id4 = 11;
            getGPAdata5 (12);id5 = 12;
            getGPAdata6 (13);id6 = 13;
            getGPAdata7 (14);id7 = 14;
            idTotal = 2;
        }else if (semId.equals("Year 2, Semester 1")){
            getGPAdata1 (15);id1 = 15;
            getGPAdata2 (16);id2 = 16;
            getGPAdata3 (17);id3 = 17;
            getGPAdata4 (18);id4 = 18;
            getGPAdata5 (19);id5 = 19;
            getGPAdata6 (20);id6 = 20;
            getGPAdata7 (21);id7 = 21;
            idTotal = 3;
        }else if (semId.equals("Year 2, Semester 2")){
            getGPAdata1 (22);id1 = 22;
            getGPAdata2 (23);id2 = 23;
            getGPAdata3 (24);id3 = 24;
            getGPAdata4 (25);id4 = 25;
            getGPAdata5 (26);id5 = 26;
            getGPAdata6 (27);id6 = 27;
            getGPAdata7 (28);id7 = 28;
            idTotal = 4;
        }else if (semId.equals("Year 3, Semester 1")){
            getGPAdata1 (29);id1 = 29;
            getGPAdata2 (30);id2 = 30;
            getGPAdata3 (31);id3 = 31;
            getGPAdata4 (32);id4 = 32;
            getGPAdata5 (33);id5 = 33;
            getGPAdata6 (34);id6 = 34;
            getGPAdata7 (35);id7 = 35;
            idTotal = 5;
        }else if (semId.equals("Year 3, Semester 2")){
            getGPAdata1 (36);id1 = 36;
            getGPAdata2 (37);id2 = 37;
            getGPAdata3 (38);id3 = 38;
            getGPAdata4 (39);id4 = 39;
            getGPAdata5 (40);id5 = 40;
            getGPAdata6 (41);id6 = 41;
            getGPAdata7 (42);id7 = 42;
            idTotal = 6;
        }else if (semId.equals("Year 3, Special Semester")){
            getGPAdata1 (43);id1 = 43;
            getGPAdata2 (44);id2 = 44;
            getGPAdata3 (45);id3 = 45;
            getGPAdata4 (46);id4 = 46;
            getGPAdata5 (47);id5 = 47;
            getGPAdata6 (48);id6 = 48;
            getGPAdata7 (49);id7 = 49;
            idTotal = 7;
        }else if (semId.equals("Year 4, Semester 1")){
            getGPAdata1 (50);id1 = 50;
            getGPAdata2 (51);id2 = 51;
            getGPAdata3 (52);id3 = 52;
            getGPAdata4 (53);id4 = 53;
            getGPAdata5 (54);id5 = 54;
            getGPAdata6 (55);id6 = 55;
            getGPAdata7 (56);id7 = 56;
            idTotal = 8;
        }
    }

    public int getGrdPos (String pos)
    {
        if (pos.equals("A"))
        {
            return 1;
        }else if (pos.equals("A-"))
        {
            return 2;
        }else if (pos.equals("B+"))
        {
            return 3;
        }else if (pos.equals("B"))
        {
            return 4;
        }else if (pos.equals("B-"))
        {
            return 5;
        }else if (pos.equals("C+"))
        {
            return 6;
        }else if (pos.equals("C"))
        {
            return 7;
        }else if (pos.equals("C-"))
        {
            return 8;
        }else if (pos.equals("D+"))
        {
            return 9;
        }else if (pos.equals("D"))
        {
            return 10;
        }else if (pos.equals("E"))
        {
            return 11;
        }else
        {
            return 0;
        }
    }

    public void fnCalculate(View vw)
    {
        String strGrade1 = spnGrade1.getSelectedItem().toString();
        String strGrade2 = spnGrade2.getSelectedItem().toString();
        String strGrade3 = spnGrade3.getSelectedItem().toString();
        String strGrade4 = spnGrade4.getSelectedItem().toString();
        String strGrade5 = spnGrade5.getSelectedItem().toString();
        String strGrade6 = spnGrade6.getSelectedItem().toString();
        String strGrade7 = spnGrade7.getSelectedItem().toString();

        final Double dblCredit1 = Double.parseDouble(spnCredit1.getSelectedItem().toString());
        final Double dblCredit2 = Double.parseDouble(spnCredit2.getSelectedItem().toString());
        final Double dblCredit3 = Double.parseDouble(spnCredit3.getSelectedItem().toString());
        final Double dblCredit4 = Double.parseDouble(spnCredit4.getSelectedItem().toString());
        final Double dblCredit5 = Double.parseDouble(spnCredit5.getSelectedItem().toString());
        final Double dblCredit6 = Double.parseDouble(spnCredit6.getSelectedItem().toString());
        final Double dblCredit7 = Double.parseDouble(spnCredit7.getSelectedItem().toString());

        final String dblGrade1 = getGradeValue(strGrade1);
        final String dblGrade2 = getGradeValue(strGrade2);
        final String dblGrade3 = getGradeValue(strGrade3);
        final String dblGrade4 = getGradeValue(strGrade4);
        final String dblGrade5 = getGradeValue(strGrade5);
        final String dblGrade6 = getGradeValue(strGrade6);
        final String dblGrade7 = getGradeValue(strGrade7);

        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("selectFn", "fnGetGrade"));

                try{
                    jsnObj = wsc.makeHttpRequest(wsc.fnGetURL(), "POST", params);
                    fnlGrade1 = jsnObj.getDouble(dblGrade1);
                    fnlGrade2 = jsnObj.getDouble(dblGrade2);
                    fnlGrade3 = jsnObj.getDouble(dblGrade3);
                    fnlGrade4 = jsnObj.getDouble(dblGrade4);
                    fnlGrade5 = jsnObj.getDouble(dblGrade5);
                    fnlGrade6 = jsnObj.getDouble(dblGrade6);
                    fnlGrade7 = jsnObj.getDouble(dblGrade7);

                } catch (Exception e){
                    //if fail to get from server, get from local mobile time
                    String strMsg = "No internet connection, please turn on your Mobile Data/WiFi!";
                    fnDisplayToastMsg(strMsg);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String strMsg = "Grade credit successfully retrieve and calculated!";
                        fnDisplayToastMsg(strMsg);

                        total1 = (fnlGrade1 * dblCredit1) + (fnlGrade2 * dblCredit2) + (fnlGrade3 * dblCredit3) +
                                (fnlGrade4 * dblCredit4) + (fnlGrade5 * dblCredit5) + (fnlGrade6 * dblCredit6) +
                                (fnlGrade7 * dblCredit7);
                        total2 = (dblCredit1 + dblCredit2 + dblCredit3 + dblCredit4 +
                                dblCredit5 + dblCredit6 + dblCredit7);
                        totalGPA = total1 / total2;

                        scoreGPA.setText("Your GPA is: " + String.format("%.2f", totalGPA));
                    }
                });
            }
        };
        Thread thr = new Thread(run);
        thr.start();
    }

    public void fnDisplayToastMsg (String strText)
    {
        Toast tst = Toast.makeText(getApplicationContext(), strText, Toast.LENGTH_SHORT);
        tst.show();
    }

    public String getGradeValue(String grade) {

        if (grade.equals("A")) {
            return "gradeA";
        } else if (grade.equals("A-")) {
            return "gradeA-";
        } else if (grade.equals("B+")) {
            return "gradeB+";
        } else if (grade.equals("B")) {
            return "gradeB";
        } else if (grade.equals("B-")) {
            return "gradeB-";
        } else if (grade.equals("C+")) {
            return "gradeC+";
        } else if (grade.equals("C")) {
            return "gradeC";
        } else if (grade.equals("C-")) {
            return "gradeC-";
        } else if (grade.equals("D+")) {
            return "gradeD+";
        } else if (grade.equals("D")) {
            return "gradeD";
        }  else {
            return "gradeE";
        }
    }

    public void toDeleteRecord()
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                String strQry1 = "DELETE FROM dbmysubj WHERE subj_sem = '"+semId+"';";
                String strQry2 = "DELETE FROM dbmygpa WHERE gpa_sem = '"+semId+"';";

                dbMyGPA.fnExecuteSql(strQry1, getApplicationContext());
                dbMyGPA.fnExecuteSql(strQry2, getApplicationContext());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast showSuccess = Toast.makeText(getApplicationContext(), "Information successfully deleted!\n", Toast.LENGTH_SHORT);
                        showSuccess.show();
                        returnHome();
                    }
                });
            }
        };
        Thread thrSave = new Thread(run);
        thrSave.start();
    }

    public void fnDelete(View vw)
    {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                Cursor c = dbMyGPA.getReadableDatabase().rawQuery("SELECT gpa_sem FROM dbmygpa;", null);

                if (c.moveToFirst())
                {
                    do
                    {
                        mySem = c.getString(c.getColumnIndex("gpa_sem"));
                    }while(c.moveToNext());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!mySem.equals(semId))
                        {
                            String str = "Please delete this record first! '" + mySem + "'";
                            fnDisplayToastMsg (str);
                        } else
                        {
                            toDeleteRecord();
                        }
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
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
                        edtSub1.setText(strSubjName1);
                        spnGrade1.setSelection(getGrdPos (strSubjGrade1));
                        spnCredit1.setSelection(Integer.parseInt(strSubjCredit1));
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
                        edtSub2.setText(strSubjName2);
                        spnGrade2.setSelection(getGrdPos (strSubjGrade2));
                        spnCredit2.setSelection(Integer.parseInt(strSubjCredit2));
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
                        edtSub3.setText(strSubjName3);
                        spnGrade3.setSelection(getGrdPos (strSubjGrade3));
                        spnCredit3.setSelection(Integer.parseInt(strSubjCredit3));
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
                        edtSub4.setText(strSubjName4);
                        spnGrade4.setSelection(getGrdPos (strSubjGrade4));
                        spnCredit4.setSelection(Integer.parseInt(strSubjCredit4));
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
                        edtSub5.setText(strSubjName5);
                        spnGrade5.setSelection(getGrdPos (strSubjGrade5));
                        spnCredit5.setSelection(Integer.parseInt(strSubjCredit5));
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
                        edtSub6.setText(strSubjName6);
                        spnGrade6.setSelection(getGrdPos (strSubjGrade6));
                        spnCredit6.setSelection(Integer.parseInt(strSubjCredit6));
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
                        edtSub7.setText(strSubjName7);
                        spnGrade7.setSelection(getGrdPos (strSubjGrade7));
                        spnCredit7.setSelection(Integer.parseInt(strSubjCredit7));
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
    }

    public void onBackPressed()
    {
        Intent home_intent = new Intent(getApplicationContext(), ListViewGPAResult.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
        finish();
    }
}
