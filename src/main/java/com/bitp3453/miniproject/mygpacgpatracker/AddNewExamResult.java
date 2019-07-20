package com.bitp3453.miniproject.mygpacgpatracker;

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

public class AddNewExamResult extends AppCompatActivity {

    Spinner spnGrade1, spnCredit1, spnGrade2, spnCredit2, spnGrade3, spnCredit3, spnGrade4,
            spnCredit4, spnGrade5, spnCredit5, spnGrade6, spnCredit6, spnGrade7, spnCredit7;
    EditText edtSub1, edtSub2, edtSub3, edtSub4, edtSub5, edtSub6, edtSub7;
    Double fnlGrade1, fnlGrade2, fnlGrade3, fnlGrade4, fnlGrade5, fnlGrade6, fnlGrade7;
    Double total1, total2, totalGPA;
    String mySem;
    int id1, id2, id3, id4, id5, id6, id7, idGpa = 0;
    TextView scoreGPA, edtCurrSem;
    WebServiceCall wsc = new WebServiceCall();
    JSONObject jsnObj = new JSONObject();
    MyGPAdb dbMyGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exam_result);

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
                        if (mySem == null)
                        {
                            edtCurrSem.setText("Year 1, Semester 1");
                            setAllData("Year 1, Semester 1");
                        } else if (mySem.equals("Year 1, Semester 1"))
                        {
                            edtCurrSem.setText("Year 1, Semester 2");
                            setAllData("Year 1, Semester 2");
                        } else if (mySem.equals("Year 1, Semester 2"))
                        {
                            edtCurrSem.setText("Year 2, Semester 1");
                            setAllData("Year 2, Semester 1");
                        } else if (mySem.equals("Year 2, Semester 1"))
                        {
                            edtCurrSem.setText("Year 2, Semester 2");
                            setAllData("Year 2, Semester 2");
                        } else if (mySem.equals("Year 2, Semester 2"))
                        {
                            edtCurrSem.setText("Year 3, Semester 1");
                            setAllData("Year 3, Semester 1");
                        } else if (mySem.equals("Year 3, Semester 1"))
                        {
                            edtCurrSem.setText("Year 3, Semester 2");
                            setAllData("Year 3, Semester 2");
                        } else if (mySem.equals("Year 3, Semester 2"))
                        {
                            edtCurrSem.setText("Year 3, Special Semester");
                            setAllData("Year 3, Special Semester");
                        } else if (mySem.equals("Year 3, Special Semester"))
                        {
                            edtCurrSem.setText("Year 4, Semester 1");
                            setAllData("Year 4, Semester 1");

                        }
                    }
                });
            }
        };

        Thread thr = new Thread(run);
        thr.start();
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
                    String strMsg = "No internet connection, please turn on your Mobile Data/WiFi.";
                    fnDisplayToastMsg(strMsg);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String strMsg = "Grade credit value successfully retrieved and calculated!";
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

    public void fnDisplayToastMsg (String strText)
    {
        Toast tst = Toast.makeText(getApplicationContext(), strText, Toast.LENGTH_SHORT);
        tst.show();
    }

    public void fnSave(View vw){
        if(totalGPA == null){
            String str = "Please click calculate button first!";
            fnDisplayToastMsg(str);
        }else{
            saveGPArecord(id1,id2,id3,id4,id5,id6,id7,idGpa);
            finish();
        }
    }

    public void setAllData(String currSem)
    {
        if (currSem.equals("Year 1, Semester 1")){
            id1 = 1;
            id2 = 2;
            id3 = 3;
            id4 = 4;
            id5 = 5;
            id6 = 6;
            id7 = 7;
            idGpa = 1;
        }else if (currSem.equals("Year 1, Semester 2")){
            id1 = 8;
            id2 = 9;
            id3 = 10;
            id4 = 11;
            id5 = 12;
            id6 = 13;
            id7 = 14;
            idGpa = 2;
        }else if (currSem.equals("Year 2, Semester 1")){
            id1 = 15;
            id2 = 16;
            id3 = 17;
            id4 = 18;
            id5 = 19;
            id6 = 20;
            id7 = 21;
            idGpa = 3;
        }else if (currSem.equals("Year 2, Semester 2")){
            id1 = 22;
            id2 = 23;
            id3 = 24;
            id4 = 25;
            id5 = 26;
            id6 = 27;
            id7 = 28;
            idGpa = 4;
        }else if (currSem.equals("Year 3, Semester 1")){
            id1 = 29;
            id2 = 30;
            id3 = 31;
            id4 = 32;
            id5 = 33;
            id6 = 34;
            id7 = 35;
            idGpa = 5;
        }else if (currSem.equals("Year 3, Semester 2")){
            id1 = 36;
            id2 = 37;
            id3 = 38;
            id4 = 39;
            id5 = 40;
            id6 = 41;
            id7 = 42;
            idGpa = 6;
        }else if (currSem.equals("Year 3, Special Semester")){
            id1 = 43;
            id2 = 44;
            id3 = 45;
            id4 = 46;
            id5 = 47;
            id6 = 48;
            id7 = 49;
            idGpa = 7;
        }else if (currSem.equals("Year 4, Semester 1")){
            id1 = 50;
            id2 = 51;
            id3 = 52;
            id4 = 53;
            id5 = 54;
            id6 = 55;
            id7 = 56;
            idGpa = 8;
        }
    }

    public void saveGPArecord(final int i1, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7, final int id){
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                String strSem = edtCurrSem.getText().toString();
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

                String strQryGpa = "INSERT INTO dbmygpa VALUES('"+id+"','"+strSem+"','"+dblGPA+"');";
                dbMyGPA.fnExecuteSql(strQryGpa, getApplicationContext());

                String strQrySubj1 = "INSERT INTO dbmysubj VALUES('"+i1+"','"+subj1+"','"+strGrd1+"','"+strCrd1+"','"+strSem+"');";
                dbMyGPA.fnExecuteSql(strQrySubj1, getApplicationContext());

                String strQrySubj2 = "INSERT INTO dbmysubj VALUES('"+i2+"','"+subj2+"','"+strGrd2+"','"+strCrd2+"','"+strSem+"');";
                dbMyGPA.fnExecuteSql(strQrySubj2, getApplicationContext());

                String strQrySubj3 = "INSERT INTO dbmysubj VALUES('"+i3+"','"+subj3+"','"+strGrd3+"','"+strCrd3+"','"+strSem+"');";
                dbMyGPA.fnExecuteSql(strQrySubj3, getApplicationContext());

                String strQrySubj4 = "INSERT INTO dbmysubj VALUES('"+i4+"','"+subj4+"','"+strGrd4+"','"+strCrd4+"','"+strSem+"');";
                dbMyGPA.fnExecuteSql(strQrySubj4, getApplicationContext());

                String strQrySubj5 = "INSERT INTO dbmysubj VALUES('"+i5+"','"+subj5+"','"+strGrd5+"','"+strCrd5+"','"+strSem+"');";
                dbMyGPA.fnExecuteSql(strQrySubj5, getApplicationContext());

                String strQrySubj6 = "INSERT INTO dbmysubj VALUES('"+i6+"','"+subj6+"','"+strGrd6+"','"+strCrd6+"','"+strSem+"');";
                dbMyGPA.fnExecuteSql(strQrySubj6, getApplicationContext());

                String strQrySubj7 = "INSERT INTO dbmysubj VALUES('"+i7+"','"+subj7+"','"+strGrd7+"','"+strCrd7+"','"+strSem+"');";
                dbMyGPA.fnExecuteSql(strQrySubj7, getApplicationContext());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String strMsg = "Information successfully saved!";
                        fnDisplayToastMsg(strMsg);
                    }
                });
            }
        };
        Thread thrSave = new Thread(run);
        thrSave.start();
    }

    public void fnReset(View vw)
    {
        edtSub1.setText("");
        edtSub2.setText("");
        edtSub3.setText("");
        edtSub4.setText("");
        edtSub5.setText("");
        edtSub6.setText("");
        edtSub7.setText("");

        spnGrade1.setSelection(0);
        spnGrade2.setSelection(0);
        spnGrade3.setSelection(0);
        spnGrade4.setSelection(0);
        spnGrade5.setSelection(0);
        spnGrade6.setSelection(0);
        spnGrade7.setSelection(0);

        spnCredit1.setSelection(0);
        spnCredit2.setSelection(0);
        spnCredit3.setSelection(0);
        spnCredit4.setSelection(0);
        spnCredit5.setSelection(0);
        spnCredit6.setSelection(0);
        spnCredit7.setSelection(0);

        scoreGPA.setText("Your GPA is: 0.00");
    }
}
