package com.hndit.academictoolkit;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TargetGpaActivity extends AppCompatActivity {

    private static final int TOTAL_PROGRAM_CREDITS = 80;

    private EditText edtCurrentCgpa, edtCompletedCredits, edtRemainingCredits, edtTargetCgpa;
    private TextView txtStatus, txtResultDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_gpa);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_target_gpa));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtCurrentCgpa = findViewById(R.id.edtCurrentCgpa);
        edtCompletedCredits = findViewById(R.id.edtCompletedCredits);
        edtRemainingCredits = findViewById(R.id.edtRemainingCredits);
        edtTargetCgpa = findViewById(R.id.edtTargetCgpa);
        txtStatus = findViewById(R.id.txtStatus);
        txtResultDetail = findViewById(R.id.txtResultDetail);

        findViewById(R.id.btnPredict).setOnClickListener(v -> predict());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void predict() {
        String currentCgpaStr = edtCurrentCgpa.getText().toString().trim();
        String completedCreditsStr = edtCompletedCredits.getText().toString().trim();
        String remainingCreditsStr = edtRemainingCredits.getText().toString().trim();
        String targetCgpaStr = edtTargetCgpa.getText().toString().trim();

        if (currentCgpaStr.isEmpty() || completedCreditsStr.isEmpty() || targetCgpaStr.isEmpty()) {
            Toast.makeText(this, "Please fill Current CGPA, Completed Credits and Target CGPA.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double currentCgpa;
        int completedCredits;
        double targetCgpa;
        try {
            currentCgpa = Double.parseDouble(currentCgpaStr);
            completedCredits = Integer.parseInt(completedCreditsStr);
            targetCgpa = Double.parseDouble(targetCgpaStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            return;
        }

        int remainingCredits;
        if (remainingCreditsStr.isEmpty()) {
            remainingCredits = TOTAL_PROGRAM_CREDITS - completedCredits;
        } else {
            try {
                remainingCredits = Integer.parseInt(remainingCreditsStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number for remaining credits.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (remainingCredits <= 0) {
            txtStatus.setText("No remaining credits left to calculate.");
            txtStatus.setTextColor(getColor(R.color.warning_orange));
            txtResultDetail.setText("You have completed (or exceeded) the total program credits, " +
                    "so your final CGPA is your current CGPA.");
            return;
        }

        double totalFuturePoints = (targetCgpa * (completedCredits + remainingCredits))
                - (currentCgpa * completedCredits);
        double requiredGpa = totalFuturePoints / remainingCredits;

        if (requiredGpa <= 0) {
            txtStatus.setText("Target Already Achieved ✅");
            txtStatus.setTextColor(getColor(R.color.success_green));
            txtResultDetail.setText("Even with a 0.00 GPA in your remaining " + remainingCredits +
                    " credits, your CGPA will stay at or above " + String.format("%.2f", targetCgpa) + ".");
        } else if (requiredGpa <= 4.0) {
            txtStatus.setText("Reachable ✅");
            txtStatus.setTextColor(getColor(R.color.success_green));
            txtResultDetail.setText("You need an average GPA of " + String.format("%.2f", requiredGpa) +
                    " across your remaining " + remainingCredits + " credits to reach a CGPA of " +
                    String.format("%.2f", targetCgpa) + ".");
        } else {
            txtStatus.setText("Unreachable ❌");
            txtStatus.setTextColor(getColor(R.color.error_red));
            txtResultDetail.setText("This target would require an average GPA of " +
                    String.format("%.2f", requiredGpa) + " in your remaining " + remainingCredits +
                    " credits, which is above the maximum possible GPA of 4.00.");
        }
    }
}