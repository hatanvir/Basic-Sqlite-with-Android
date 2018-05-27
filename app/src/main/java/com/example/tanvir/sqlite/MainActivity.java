package com.example.tanvir.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.jar.Attributes;

import static java.util.jar.Attributes.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public long countInsert = 0;
    Database mydb;
    EditText nameEditText,surnameEditText,marksEditText,idEditText;
    Button button,button2,button3,button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new Database(this);
        SQLiteDatabase sqLiteOpenHelper = mydb.getWritableDatabase();

        //finding editText
        nameEditText = findViewById(R.id.nameId);
        surnameEditText = findViewById(R.id.SurnameId);
        marksEditText = findViewById(R.id.marksId);
        idEditText = findViewById(R.id.idEditTextId);

        //finding button
        button = findViewById(R.id.buttonId);
        button2 = findViewById(R.id.buttonId2);
        button3 = findViewById(R.id.updateButtonId);
        button4 = findViewById(R.id.deleteButtonId);

        //listener for click
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String marks = marksEditText.getText().toString();
        String id = idEditText.getText().toString();

        if(v.getId() == R.id.buttonId){
           if (mydb.insertData(name,surname,marks)>=0){
                Toast.makeText(getApplicationContext(),"Successfully inserted",Toast.LENGTH_SHORT).show();
                countInsert++;
            }
        }
        if(v.getId()==R.id.buttonId2){
            Cursor cursor = mydb.display();
            if(cursor.getCount()==0){
                displayData("Sorry","Sorry");
            }
            //StringBuffer for aranging database values
            StringBuffer stringBuffer = new StringBuffer();
            while(cursor.moveToNext()){
                stringBuffer.append("Id :"+cursor.getString(0)+"\n");
                stringBuffer.append("Name :"+cursor.getString(1)+"\n");
                stringBuffer.append("Surname :"+cursor.getString(2)+"\n");
                stringBuffer.append("Marks :"+cursor.getString(3)+"\n\n");
            }
            displayData("Student Information",stringBuffer.toString());
        }
        if(v.getId()==R.id.updateButtonId){

            if(mydb.update(name,surname,marks,id)==true){
                Toast.makeText(getApplicationContext(),"Successfully updated",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Unable to updated",Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId() == R.id.deleteButtonId){
            if(mydb.delete(id)==true){
                Toast.makeText(getApplicationContext(),"Successfully deleted",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Unable to deleted",Toast.LENGTH_SHORT).show();
            }
        }
    }
    //function for aranging database values in AlertDialog Builder
    private void displayData(String student_information, String s) {
       AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
       alBuilder.setTitle(student_information);
       alBuilder.setMessage(s);
       alBuilder.setCancelable(true);
       alBuilder.show();
    }
}
