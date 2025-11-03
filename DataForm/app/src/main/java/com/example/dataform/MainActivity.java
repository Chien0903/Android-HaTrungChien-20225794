package com.example.dataform;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private RadioGroup rgGender;
    private EditText etBirthday;
    private CalendarView calendarView;
    private View calendarContainer;
    private EditText etAddress;
    private EditText etEmail;
    private CheckBox cbAgree;
    private Button btnSelect;
    private Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataform);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        rgGender = findViewById(R.id.rgGender);
        etBirthday = findViewById(R.id.etBirthday);
        calendarView = findViewById(R.id.calendarView);
        calendarContainer = findViewById(R.id.calendarContainer);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        cbAgree = findViewById(R.id.cbAgree);
        btnSelect = findViewById(R.id.btnSelect);
        btnRegister = findViewById(R.id.btnRegister);

        // Ensure calendar is properly initialized
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendarView.setDate(calendar.getTimeInMillis(), false, false);
        calendarView.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        
        btnSelect.setOnClickListener(v -> toggleCalendarVisibility());

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String formatted = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
            etBirthday.setText(formatted);
            // Auto-hide calendar after date selection
            calendarContainer.setVisibility(View.GONE);
        });

        btnRegister.setOnClickListener(v -> validateAndMarkErrors());
    }

    private void toggleCalendarVisibility() {
        int currentVisibility = calendarContainer.getVisibility();
        
        if (currentVisibility == View.VISIBLE) {
            // Hide calendar
            calendarContainer.setVisibility(View.GONE);
        } else {
            // Show calendar - fully reinitialize
            java.util.Calendar cal = java.util.Calendar.getInstance();
            long currentTime = cal.getTimeInMillis();
            
            // Set date and properties
            calendarView.setFirstDayOfWeek(java.util.Calendar.MONDAY);
            calendarView.setDate(currentTime, false, false);
            
            // Show container
            calendarContainer.setVisibility(View.VISIBLE);
            calendarContainer.bringToFront();
            
            // Force calendar to render its content
            calendarContainer.post(new Runnable() {
                @Override
                public void run() {
                    // Ensure calendar is properly initialized
                    java.util.Calendar cal2 = java.util.Calendar.getInstance();
                    long time = cal2.getTimeInMillis();
                    calendarView.setDate(time, true, true);
                    
                    // Force layout
                    calendarView.requestLayout();
                    calendarView.invalidate();
                    calendarContainer.requestLayout();
                    calendarContainer.invalidate();
                }
            });
        }
    }

    private void validateAndMarkErrors() {
        boolean hasError = false;

        // Reset background colors first
        resetBackgroundColors();

        // Validate First Name
        if (isEmpty(etFirstName)) {
            etFirstName.setBackgroundColor(Color.RED);
            hasError = true;
        }

        // Validate Last Name
        if (isEmpty(etLastName)) {
            etLastName.setBackgroundColor(Color.RED);
            hasError = true;
        }

        // Validate Gender
        if (rgGender.getCheckedRadioButtonId() == -1) {
            rgGender.setBackgroundColor(Color.RED);
            hasError = true;
        }

        // Validate Birthday
        if (isEmpty(etBirthday)) {
            etBirthday.setBackgroundColor(Color.RED);
            hasError = true;
        }

        // Validate Address
        if (isEmpty(etAddress)) {
            etAddress.setBackgroundColor(Color.RED);
            hasError = true;
        }

        // Validate Email
        if (isEmpty(etEmail)) {
            etEmail.setBackgroundColor(Color.RED);
            hasError = true;
        }

        // Validate Terms Agreement
        if (!cbAgree.isChecked()) {
            cbAgree.setBackgroundColor(Color.RED);
            hasError = true;
        }

        // Show success message if no errors
        if (!hasError) {
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_LONG).show();
        }
    }

    private void resetBackgroundColors() {
        // Reset EditText backgrounds to default (transparent or default background)
        etFirstName.setBackgroundColor(Color.TRANSPARENT);
        etLastName.setBackgroundColor(Color.TRANSPARENT);
        etBirthday.setBackgroundColor(Color.TRANSPARENT);
        etAddress.setBackgroundColor(Color.TRANSPARENT);
        etEmail.setBackgroundColor(Color.TRANSPARENT);
        
        // Reset RadioGroup background
        rgGender.setBackgroundColor(Color.TRANSPARENT);
        
        // Reset CheckBox background
        cbAgree.setBackgroundColor(Color.TRANSPARENT);
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText() == null || editText.getText().toString().trim().isEmpty();
    }
}

