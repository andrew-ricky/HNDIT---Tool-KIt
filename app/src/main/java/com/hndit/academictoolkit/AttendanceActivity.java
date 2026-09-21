package com.hndit.academictoolkit;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AttendanceActivity extends AppCompatActivity {

    private EditText edtTotalDays, edtAttendedDays;
    private Button btnCheckEligibility;
    private TextView txtStatus, txtResultDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Attendance & Eligibility");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        toolbar.setNavigationOnClickListener(v -> finish());

        bindViews();

        btnCheckEligibility.setOnClickListener(v -> checkEligibility());
    }

    private void bindViews() {
        edtTotalDays = findViewById(R.id.edtTotalDays);
        edtAttendedDays = findViewById(R.id.edtAttendedDays);
        btnCheckEligibility = findViewById(R.id.btnCheckEligibility);
        txtStatus = findViewById(R.id.txtStatus);
        txtResultDetail = findViewById(R.id.txtResultDetail);
    }

    private void checkEligibility() {
        String totalStr = edtTotalDays.getText().toString().trim();
        String attendedStr = edtAttendedDays.getText().toString().trim();

        if (totalStr.isEmpty() || attendedStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double total = Double.parseDouble(totalStr);
        double attended = Double.parseDouble(attendedStr);

        if (total <= 0) {
            Toast.makeText(this, "Total lectures must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        if (attended > total) {
            Toast.makeText(this, "Attended lectures cannot exceed total lectures", Toast.LENGTH_SHORT).show();
            return;
        }

        double percentage = (attended / total) * 100.0;
        StringBuilder detailMessage = new StringBuilder();

        detailMessage.append(String.format("Current Attendance: %.1f%%\n\n", percentage));

        if (percentage >= 70.0) {
            txtStatus.setText("Eligible for Exam");
            txtStatus.setTextColor(Color.parseColor("#2E7D32"));

            int canMiss = (int) Math.floor((attended - 0.70 * total) / 0.70);
            if (canMiss > 0) {
                detailMessage.append(String.format("• You can miss up to %d consecutive lectures and still remain eligible.", canMiss));
            } else {
                detailMessage.append("• You are eligible, but missing even 1 more lecture will drop your attendance below 70%.");
            }

        } else if (percentage >= 60.0) {
            txtStatus.setText("Medical Certificate Required");
            txtStatus.setTextColor(Color.parseColor("#E65100"));

            int needToAttend70 = (int) Math.ceil((0.70 * total - attended) / 0.30);
            detailMessage.append("• You can submit a valid Medical Certificate to get exam eligibility.\n")
                    .append(String.format("• Or, to reach 70%% eligibility without a medical, you must attend the next %d consecutive lectures.", needToAttend70));

        } else {
            txtStatus.setText("Not Eligible");
            txtStatus.setTextColor(Color.parseColor("#C62828"));

            int needToAttend60 = (int) Math.ceil((0.60 * total - attended) / 0.40);
            int needToAttend70 = (int) Math.ceil((0.70 * total - attended) / 0.30);

            detailMessage.append(String.format("• To reach 60%% (Medical Eligibility): Attend next %d consecutive lectures.\n", needToAttend60))
                    .append(String.format("• To reach 70%% (Full Eligibility): Attend next %d consecutive lectures.", needToAttend70));
        }

        txtResultDetail.setText(detailMessage.toString());
    }
}