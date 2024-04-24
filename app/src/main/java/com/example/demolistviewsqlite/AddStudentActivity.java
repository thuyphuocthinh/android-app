package com.example.demolistviewsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddStudentActivity extends AppCompatActivity {
    EditText edtHo, edtTenDemLot, edtTen, edtLop, edtAddress, edtPhone, edtSinhNhat, edtMaSV;
    Button btnAdd, btnCancel;
    StudentDB studentDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        studentDB = new StudentDB(this, "student9.db", null, 1);
        initViews();
        moDatePickerDialog();
        setUpListeners();
    }

    private void initViews() {
        edtHo = findViewById(R.id.edtAddLastName);
        edtTenDemLot = findViewById(R.id.edtAddMiddleName);
        edtTen = findViewById(R.id.edtAddFirstName);
        edtLop = findViewById(R.id.edtAddClass);
        edtAddress = findViewById(R.id.edtAddAddress);
        edtPhone = findViewById(R.id.edtAddPhone);
        edtSinhNhat = findViewById(R.id.edtAddBirthDay);
        edtMaSV = findViewById(R.id.edtAddId);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancelAdd);
    }

    private void setUpListeners() {
        btnCancel.setOnClickListener(v -> finish());
        btnAdd.setOnClickListener(v -> {
            try {
                addStudent();
                setResult(RESULT_OK);
                Toast.makeText(getApplicationContext(), "Added a new student successfully", Toast.LENGTH_LONG).show();
                finish();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void moDatePickerDialog() {
        edtSinhNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });
    }

    private  void chonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtSinhNhat.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
    private void addStudent() throws ParseException {
        String ho = edtHo.getText().toString();
        String tenDemLot = edtTenDemLot.getText().toString();
        String ten = edtTen.getText().toString();
        String lop = edtLop.getText().toString();
        String address = edtAddress.getText().toString();
        String phone = edtPhone.getText().toString();
        String birthday = edtSinhNhat.getText().toString();
        String maSV = edtMaSV.getText().toString();
        Student student = new Student(ho, tenDemLot, ten, lop, Integer.parseInt(birthday.substring(6)), birthday, maSV, address, phone);
        studentDB.addStudent(student);
    }
}