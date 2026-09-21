package com.hndit.academictoolkit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardView cardGpa, cardTargetGpa, cardAttendance, cardCaPredictor;
    private TextView txtSyllabusInfo, txtFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        setupNavigation();
        populateSyllabusInfo();
        setupFooter();
    }

    private void bindViews() {
        cardGpa = findViewById(R.id.cardGpa);
        cardTargetGpa = findViewById(R.id.cardTargetGpa);
        cardAttendance = findViewById(R.id.cardAttendance);
        cardCaPredictor = findViewById(R.id.cardCaPredictor);
        txtSyllabusInfo = findViewById(R.id.txtSyllabusInfo);
        txtFooter = findViewById(R.id.txtFooter);
    }

    private void setupNavigation() {
        cardGpa.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, GpaActivity.class)));

        cardTargetGpa.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TargetGpaActivity.class)));

        cardAttendance.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AttendanceActivity.class)));

        cardCaPredictor.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CaPredictorActivity.class)));
    }

    private void populateSyllabusInfo() {
        StringBuilder sb = new StringBuilder();
        for (int semester = 1; semester <= 4; semester++) {
            List<Subject> subjects = HNDITData.getSubjectsForSemester(semester);
            int totalCredits = HNDITData.getTotalCreditsForSemester(semester);
            sb.append("Semester ").append(semester).append("\n");
            for (Subject s : subjects) {
                sb.append("  • ").append(s.getCode()).append(" - ").append(s.getName())
                        .append(" (").append(s.getCredits()).append(" cr)\n");
            }
            sb.append("  Total listed credits: ").append(totalCredits).append("\n\n");
        }
        txtSyllabusInfo.setText(sb.toString().trim());
    }

    private void setupFooter() {
        if (txtFooter != null) {
            txtFooter.setText("HNDIT 24th Batch");
        }
    }
}