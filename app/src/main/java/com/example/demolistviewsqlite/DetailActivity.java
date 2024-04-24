package com.example.demolistviewsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DetailActivity extends AppCompatActivity {
    EditText edtHo, edtTenDemLot, edtTen, edtLop, edtAddress, edtPhone, edtSinhNhat, edtMaSV;
    Button btnDelete, btnCapNhat, btnCancel;
    StudentDB studentDB;
    String maSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        studentDB = new StudentDB(this, "student9.db", null, 1);
        initViews();
        displayInfo();
        moDatePickerDialog();
        setupListeners();
    }

    private void initViews() {
        edtHo = findViewById(R.id.edtLastName);
        edtTenDemLot = findViewById(R.id.edtMiddleName);
        edtTen = findViewById(R.id.edtFirstName);
        edtLop = findViewById(R.id.edtClass);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        edtSinhNhat = findViewById(R.id.edtBirthDay);
        edtMaSV = findViewById(R.id.edtId);
        btnDelete = findViewById(R.id.btnDelete);
        btnCapNhat = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void displayInfo() {
        Intent intent = getIntent();
        maSV = intent.getStringExtra("maSv");
        edtHo.setText(intent.getStringExtra("ho"));
        edtTenDemLot.setText(intent.getStringExtra("tenDemLot"));
        edtTen.setText(intent.getStringExtra("ten"));
        edtLop.setText(intent.getStringExtra("lop"));
        edtAddress.setText(intent.getStringExtra("diaChi"));
        edtPhone.setText(intent.getStringExtra("soDienThoai"));
        edtSinhNhat.setText(intent.getStringExtra("sinhNhat"));
        edtMaSV.setText(intent.getStringExtra("maSv"));
    }

    private void setupListeners() {
        btnCapNhat.setOnClickListener(v -> {
            try {
                updateStudent();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        btnDelete.setOnClickListener(v -> deleteStudent());
        btnCancel.setOnClickListener(v -> finish());
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
        int ngay = Integer.parseInt(edtSinhNhat.getText().toString().substring(0, 2));
        int thang = Integer.parseInt(edtSinhNhat.getText().toString().substring(3, 5));
        int nam = Integer.parseInt(edtSinhNhat.getText().toString().substring(6));
        Log.i("Ngay", "Ngay: " + ngay);
        Log.i("Thang", "Thang: " + thang);
        Log.i("Nam", "Nam: " + nam);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtSinhNhat.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang - 1, ngay);
        datePickerDialog.show();
    }

    private void deleteStudent() {
        int IsSuccess = studentDB.deleteStudent(maSV);
        if (IsSuccess > 0) {
            setResult(Activity.RESULT_OK);
            Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Deleted failed", Toast.LENGTH_LONG).show();
        }
    }

    private void updateStudent() throws ParseException {
        Intent intent = getIntent();
        String newLop = edtLop.getText().toString();
        String newSoDienThoai = edtPhone.getText().toString();
        String newAddress = edtAddress.getText().toString();
        String newHo = edtHo.getText().toString();
        String newTenDemLot = edtTenDemLot.getText().toString();
        String newTen = edtTen.getText().toString();
        int newNamSinh = intent.getIntExtra("namSinh", 0);
        String birthday = edtSinhNhat.getText().toString(); // Assuming birthday is stored as string

        Student student = new Student(newHo, newTenDemLot, newTen, newLop, newNamSinh, birthday, maSV, newAddress, newSoDienThoai);
        int isSuccess = studentDB.updateStudent(student);
        Log.i("IsSuccess", "IsSuccess: " + isSuccess);

        if (isSuccess > 0) {
            setResult(Activity.RESULT_OK);
            Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Update failed", Toast.LENGTH_LONG).show();
        }
    }


}
