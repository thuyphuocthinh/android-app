package com.example.demolistviewsqlite;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity{
    private static final int REQUEST_CODE_UPDATE_STUDENT = 1;
    ArrayList<Student> studentArrayList;
    ArrayAdapter<Student> studentAdapter;
    ListView lv;
    StudentDB studentDB;
    EditText edtSearch;

    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.fltBtnAdd);
        lv = findViewById(R.id.listView2);
        studentArrayList = new ArrayList<>();
        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
        studentDB = new StudentDB(MainActivity.this, "student9.db", null, 1);
        loadStudents();
        openAddStudentActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_STUDENT && resultCode == RESULT_OK) {
            Log.i("Result Code", "Result code: " + resultCode);
            // Handle the case where a student has been updated
            // Refresh the list of students from the database and update the UI
            studentArrayList = new ArrayList<>();
            loadStudents();
            studentAdapter.notifyDataSetChanged();
        }
    }

    private void openAddStudentActivity() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void loadStudents() {
        try (Cursor cursor = studentDB.getAllStudents()) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Create a Student object from cursor data
                    Student student = studentDB.getSVFromCursor(cursor);
                    // Add student to the list
                    studentArrayList.add(student);
                    // Print student information for testing
                    Log.d("MainActivity", "Student Info: " + student.getLop());
                } while (cursor.moveToNext());
            } else {
                // Handle case where no students are found
                Log.d("MainActivity", "No students found");
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during database operations
            Log.e("MainActivity", "Error accessing database", e);
            Toast.makeText(this, "Error accessing database", Toast.LENGTH_SHORT).show();
        }
        studentAdapter = new StudentAdapter(MainActivity.this, R.layout.layout, studentArrayList);
        lv.setAdapter(studentAdapter);
    }
}

