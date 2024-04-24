package com.example.demolistviewsqlite;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {
    private OnStudentClickListener listener;
    Activity context;
    int idLayout;
    ArrayList<Student> myList;

    public StudentAdapter(Activity context, int idLayout, ArrayList<Student> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(idLayout, parent, false);

        // Get the current student
        Student student = myList.get(position);

        // Get references to views
        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvAddress = convertView.findViewById(R.id.tv_address);
        // Set data to views
        tvName.setText(student.getFullName());
        tvAddress.setText(student.getAddress());

        // Button
        Button btnCall = convertView.findViewById(R.id.btn_call);
        Button btnEmail = convertView.findViewById(R.id.btn_email);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + student.getPhoneNumber()));
                context.startActivity(intent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] recipients = {"recipient@example.com"};
                String subject = "Subject of Email";
                String body = "Body of Email";

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);

                try {
                    context.startActivity(Intent.createChooser(intent, "Choose an email client"));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No email client installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set OnClickListener for the entire item view
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to switch to DetailActivity
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("maSv", student.getMaSV());
                intent.putExtra("namSinh", student.getNamsinh());
                intent.putExtra("ho", student.getHo());
                intent.putExtra("tenDemLot", student.getHolotSV());
                intent.putExtra("ten", student.getTen());
                intent.putExtra("lop", student.getLop());
                intent.putExtra("diaChi", student.getAddress());
                intent.putExtra("soDienThoai", student.getPhoneNumber());
                intent.putExtra("sinhNhat", student.getBirthday());
                // Start DetailActivity
                context.startActivityForResult(intent, 1);

            }
        });

        return convertView;
    }
    @Override
    public boolean isEnabled(int position) {
        //Set a Toast or Log over here to check.
        return true;
    }

}


