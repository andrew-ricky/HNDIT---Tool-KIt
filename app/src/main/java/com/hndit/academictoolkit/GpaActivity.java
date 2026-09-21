package com.hndit.academictoolkit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class GpaActivity extends AppCompatActivity {

    private Spinner spinnerSemester;
    private LinearLayout llSubjectsContainer;
    private TextView txtResult;

    private final List<Spinner> gradeSpinners = new ArrayList<>();
    private List<Subject> currentSubjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_gpa));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spinnerSemester = findViewById(R.id.spinnerSemester);
        llSubjectsContainer = findViewById(R.id.llSubjectsContainer);
        txtResult = findViewById(R.id.txtResult);
        findViewById(R.id.btnCalculate).setOnClickListener(v -> calculateGpa());

        setupSemesterSpinner();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupSemesterSpinner() {
        String[] semesters = {"Semester 1", "Semester 2", "Semester 3", "Semester 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, semesters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(adapter);

        spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadSubjectsForSemester(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        loadSubjectsForSemester(1);
    }

    private void loadSubjectsForSemester(int semester) {
        currentSubjects = HNDITData.getSubjectsForSemester(semester);
        llSubjectsContainer.removeAllViews();
        gradeSpinners.clear();
        txtResult.setText("");

        LayoutInflater inflater = LayoutInflater.from(this);
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, HNDITData.GRADE_LABELS);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (Subject subject : currentSubjects) {
            View row = inflater.inflate(R.layout.item_subject_grade, llSubjectsContainer, false);

            TextView txtName = row.findViewById(R.id.txtSubjectName);
            TextView txtCredits = row.findViewById(R.id.txtSubjectCredits);
            Spinner spinnerGrade = row.findViewById(R.id.spinnerGrade);

            txtName.setText(subject.getCode() + " - " + subject.getName());
            txtCredits.setText("Credits: " + subject.getCredits());
            spinnerGrade.setAdapter(gradeAdapter);

            llSubjectsContainer.addView(row);
            gradeSpinners.add(spinnerGrade);
        }
    }

    private void calculateGpa() {
        double totalWeightedPoints = 0.0;
        int totalCredits = 0;

        for (int i = 0; i < currentSubjects.size(); i++) {
            Subject subject = currentSubjects.get(i);
            Spinner gradeSpinner = gradeSpinners.get(i);
            int selectedIndex = gradeSpinner.getSelectedItemPosition();

            double gradePoint = 0.0;
            if (selectedIndex > 0) {
                gradePoint = HNDITData.GRADE_POINTS[selectedIndex];
            }

            totalWeightedPoints += subject.getCredits() * gradePoint;
            totalCredits += subject.getCredits();
        }

        if (totalCredits == 0) {
            Toast.makeText(this, "No subjects found.", Toast.LENGTH_SHORT).show();
            return;
        }

        double gpa = totalWeightedPoints / totalCredits;
        txtResult.setText(String.format("Semester GPA: %.2f  (Total Credits: %d)", gpa, totalCredits));
    }
}