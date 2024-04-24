package com.example.demolistviewsqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.ParseException;

public class StudentDB extends SQLiteOpenHelper {
    static final String dbName= "studentDB";
    static final String sinhvienTable = "Sinhvien";
    static final String colID = "Sinhvien_ID";
    static final String colHo = "Sinhvien_ho";
    static final String colHoLot = "Sinhvien_holot";
    static final String colTen = "Sinhvien_Ten";
    static final String colNamsinh = "Sinhvien_namsinh";
    static final String colMaSinhVien = "Sinhvien_Ma";
    static final String colLop = "Sinhvien_Lop";
    static final String colPhoneNumber = "Sinhvien_phoneNumber";
    static final String colAdd = "Sinhvien_address";
    static final String colBirthday = "Sinhvien_birthday";
    String sql = "CREATE TABLE "+sinhvienTable+" ("+colID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            colHo+ " TEXT, "+ colHoLot+" TEXT, "+ colTen+" TEXT, "+
            colLop+" TEXT, "+colNamsinh+" Integer, "+colBirthday+" TEXT, "+colMaSinhVien+" TEXT, "+colPhoneNumber +" TEXT, "+colAdd + " TEXT );";
    public StudentDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }


    public Cursor searchStudentByName(String studentName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + sinhvienTable + " WHERE " + colTen + " LIKE ?";
        String[] selectionArgs = {"%" + studentName + "%"};
        return db.rawQuery(query, selectionArgs);
    }
    public void addStudent(Student student) {
        Log.i("MaSV", student.getMaSV());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colHo, student.getHo());
        cv.put(colHoLot, student.getHolot());
        cv.put(colTen, student.getTen());
        cv.put(colNamsinh, student.getNamsinh());
        cv.put(colMaSinhVien, student.getMaSV());
        cv.put(colAdd, student.getAddress());
        cv.put(colPhoneNumber, student.getPhoneNumber());
        cv.put(colLop, student.getLop());
        cv.put(colBirthday, student.getBirthday());
        db.insert(sinhvienTable, null, cv);
        db.close();
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colHo, student.getHo());
        cv.put(colHoLot, student.getHolot());
        cv.put(colTen, student.getTen());
        cv.put(colPhoneNumber, student.getPhoneNumber());
        cv.put(colLop, student.getLop());
        cv.put(colBirthday, student.getBirthday());
        cv.put(colMaSinhVien, student.getMaSV());
        cv.put(colNamsinh, student.getNamsinh());
        cv.put(colAdd, student.getAddress());

        // Using placeholders and passing values separately to prevent SQL injection
        String whereClause = colMaSinhVien + "=?";
        String[] whereArgs = {String.valueOf(student.getMaSV())};

        // Performing the update
        return db.update(sinhvienTable, cv, whereClause, whereArgs);
    }


    Cursor getAllStudents()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cur = db.query(sinhvienTable, new String[]{}, null, null, null, null, null);
        return cur;
    }

    @SuppressLint("Range")
    public Student getSVFromCursor(Cursor c) throws ParseException {
        Student student = new Student();
        if(c!= null){
            student.setHo(c.getString(c.getColumnIndex(colHo)));
            student.setHolot(c.getString(c.getColumnIndex(colHoLot)));
            student.setTen(c.getString(c.getColumnIndex(colTen)));
            student.setLop(c.getString(c.getColumnIndex(colLop)));
            student.setNamsinh(c.getInt(c.getColumnIndex(colNamsinh)));
            student.setMaSV(c.getString(c.getColumnIndex(colMaSinhVien)));
            student.setAddress(c.getString(c.getColumnIndex(colAdd)));
            student.setPhoneNumber(c.getString(c.getColumnIndex(colPhoneNumber)));
            student.setBirthday(c.getString(c.getColumnIndex(colBirthday)));
        }
        Log.i("ten", student.getFullName());
        return student;
    }


    Cursor getStudent(String code) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur= db.rawQuery("SELECT * FROM "+ sinhvienTable + " Where " + colMaSinhVien + " = " + code,null);
        cur.moveToFirst();
        Student s = getSVFromCursor(cur);
        return cur;
    }

    public int deleteStudent(String maSv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = colMaSinhVien + " = ?";
        String[] whereArgs = {maSv};
        return db.delete(sinhvienTable, whereClause, whereArgs);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
