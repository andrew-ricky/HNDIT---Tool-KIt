package com.hndit.academictoolkit;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CaPredictorActivity extends AppCompatActivity {

    private static final String[] TARGET_OPTIONS = {
            "Pass (Overall 40%)", "A Grade (Overall 75%)", "Custom Target"
    };
    private static final double[] TARGET_VALUES = {40.0, 75.0, -1};

    private static final double CA_WEIGHT_FRACTION = 0.40;
    private static final double FINAL_WEIGHT_FRACTION = 0.60;
    private static final double MIN_EXAM_PASS_MARK = 40.0;

    private Spinner spinnerTarget;
    private EditText edtCaScore, edtCustomTarget;
    private TextView txtStatus, txtResultDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_predictor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_ca_predictor));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        spinnerTarget = findViewById(R.id.spinnerTarget);
        edtCaScore = findViewById(R.id.edtCaScore);
        edtCustomTarget = findViewById(R.id.edtCustomTarget);
        txtStatus = findViewById(R.id.txtStatus);
        txtResultDetail = findViewById(R.id.txtResultDetail);

        setupTargetSpinner();

        findViewById(R.id.btnPredictMarks).setOnClickListener(v -> predictRequiredMarks());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupTargetSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, TARGET_OPTIONS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTarget.setAdapter(adapter);

        spinnerTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean isCustom = TARGET_VALUES[position] < 0;
                edtCustomTarget.setVisibility(isCustom ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void predictRequiredMarks() {
        String caScoreStr = edtCaScore.getText().toString().trim();
        if (caScoreStr.isEmpty()) {
            Toast.makeText(this, "Please enter your current Assignment score.", Toast.LENGTH_SHORT).show();
            return;
        }

        double caScore;
        try {
            caScore = Double.parseDouble(caScoreStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid Assignment score.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (caScore < 0 || caScore > 100) {
            Toast.makeText(this, "Assignment score must be between 0 and 100.", Toast.LENGTH_SHORT).show();
            return;
        }

        int targetIndex = spinnerTarget.getSelectedItemPosition();
        double targetOverall = TARGET_VALUES[targetIndex];

        if (targetOverall < 0) {
            String customStr = edtCustomTarget.getText().toString().trim();
            if (customStr.isEmpty()) {
                Toast.makeText(this, "Please enter your custom target mark.", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                targetOverall = Double.parseDouble(customStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid custom target mark.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (targetOverall < 0 || targetOverall > 100) {
                Toast.makeText(this, "Target mark must be between 0 and 100.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        double caContribution = caScore * CA_WEIGHT_FRACTION;
        double calculatedFinalMark = (targetOverall - caContribution) / FINAL_WEIGHT_FRACTION;
        double requiredFinalMark = Math.max(calculatedFinalMark, MIN_EXAM_PASS_MARK);

        if (requiredFinalMark <= 100) {
            txtStatus.setText("Achievable ✅");
            txtStatus.setTextColor(getColor(R.color.success_green));

            if (calculatedFinalMark < MIN_EXAM_PASS_MARK) {
                txtResultDetail.setText("Even though your Assignment score is high, you MUST score at least 40.0 in the Final Exam to pass the module.");
            } else {
                txtResultDetail.setText(String.format(
                        "You need at least %.1f out of 100 in the Final Written Exam to reach an overall mark of %.1f%%.",
                        requiredFinalMark, targetOverall));
            }
        } else {
            txtStatus.setText("Not Achievable ❌");
            txtStatus.setTextColor(getColor(R.color.error_red));
            txtResultDetail.setText(String.format(
                    "This target would require %.1f out of 100 in the Final Exam, which is above the maximum possible mark of 100.",
                    calculatedFinalMark));
        }
    }
}