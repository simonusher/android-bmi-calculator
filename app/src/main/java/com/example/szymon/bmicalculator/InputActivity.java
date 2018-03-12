package com.example.szymon.bmicalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class InputActivity extends Activity {
    private Bmi bmiCounter;

    private TextView massTextView;
    private TextView heightTextView;
    private EditText massEditText;
    private EditText heightEditText;
    private Button calculateButton;
    private Switch unitSwitch;

    private String errorString;

    private String massLabelPounds;
    private String heightLabelInches;
    private String unitsSwitchLabelPoundsInches;

    private String massLabelKilograms;
    private String heightLabelMeters;
    private String unitsSwitchLabelMetersKilograms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        initializeFields();

        unitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean unitsInLbIn) {
                switchUnits(unitsInLbIn);
                clearTextFields();
            }
        });
        boolean unitsInLbIn = unitSwitch.isChecked();
        if(unitsInLbIn){
            bmiCounter = new BmiForLbIn();
        }else {
            bmiCounter = new BmiForKgM();
        }

        final Intent calculateResultIntent = new Intent(this, ResultActivity.class);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean textFieldsCorrect = setBmiCounterValuesFromTextFields();
                if(textFieldsCorrect){
                    try {
                        double result = bmiCounter.calculateBmi();
                        String resultString = String.format("%.2f", result);
                        calculateResultIntent.putExtra("result", resultString);
                        calculateResultIntent.putExtra("colorCode", bmiCounter.getColorForBmi(result));
                        startActivity(calculateResultIntent);
                    }catch (IllegalArgumentException e){
                        showErrorToast();
                    }
                } else {
                    showErrorToast();
                }
            }
        });

        readDataFromFile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuItemAboutMe:
                showAboutMeActivity();
                return true;
            case R.id.menuItemSaveToFile:
                saveDataToFile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeFields(){
        massTextView = findViewById(R.id.massTextView);
        heightTextView = findViewById(R.id.heightTextView);
        massEditText = findViewById(R.id.massEditText);
        heightEditText = findViewById(R.id.heightEditText);
        calculateButton = findViewById(R.id.calculateButton);
        unitSwitch = findViewById(R.id.unitsSwitch);
        errorString = getString(R.string.errorString);

        massLabelPounds = getString(R.string.massLabelPounds);
        heightLabelInches = getString(R.string.heightLabelInches);
        unitsSwitchLabelPoundsInches = getString(R.string.unitsSwitchLabelPoundsInches);

        massLabelKilograms = getString(R.string.massLabelKilograms);
        heightLabelMeters = getString(R.string.heightLabelMeters);
        unitsSwitchLabelMetersKilograms = getString(R.string.unitsSwitchLabelMetersKilograms);
    }

    private void switchUnits(boolean poundsAndInchesSelected){
        if(poundsAndInchesSelected){
            massTextView.setText(massLabelPounds);
            heightTextView.setText(heightLabelInches);
            unitSwitch.setText(unitsSwitchLabelPoundsInches);
            this.bmiCounter = new BmiForLbIn();
        } else {
            massTextView.setText(massLabelKilograms);
            heightTextView.setText(heightLabelMeters);
            unitSwitch.setText(unitsSwitchLabelMetersKilograms);
            this.bmiCounter = new BmiForKgM();
        }
    }

    private void clearTextFields(){
        massEditText.setText("");
        heightEditText.setText("");
    }

    private boolean setBmiCounterValuesFromTextFields(){
        String massString = massEditText.getText().toString();
        String heightString = heightEditText.getText().toString();
        if(!massString.equals("") && !heightString.equals("")){
            bmiCounter.setMass(Double.parseDouble(massString));
            bmiCounter.setHeigth(Double.parseDouble(heightString));
            return true;
        }
        return false;
    }

    private void showErrorToast(){
        Context context = getApplicationContext();
        Toast errorToast = Toast.makeText(context, errorString, Toast.LENGTH_SHORT);
        errorToast.show();
    }

    private void showToast(String text){
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showAboutMeActivity(){
        Intent showAboutMe = new Intent(this, AboutMeActivity.class);
        startActivity(showAboutMe);
    }

    private void saveDataToFile(){
        String massString = massEditText.getText().toString();
        String heightString = heightEditText.getText().toString();
        boolean areUnitsInLbIn = unitSwitch.isChecked();
        String fileContents = areUnitsInLbIn + "\n" + massString + "\n" + heightString;
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(getString(R.string.filename), Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
            showToast(getString(R.string.savedDataToFile));
        } catch (Exception e) {
            showToast(getString(R.string.errorWhileSavingFile));
        }
    }

    private boolean readDataFromFile(){
        File directory = getApplicationContext().getFilesDir();
        File file = new File(directory, getString(R.string.filename));

        if(file.exists()){
            try{
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String areUnitsInLbIn = reader.readLine();
                String mass = reader.readLine();
                String height = reader.readLine();
                unitSwitch.setChecked(Boolean.parseBoolean(areUnitsInLbIn));
                massEditText.setText(mass);
                heightEditText.setText(height);

            }catch (IOException e){
                showToast(getString(R.string.errorWhileLoadingFile));
                return false;
            }
            return true;
        }
        showToast(getString(R.string.errorWhileLoadingFile));
        return false;
    }
}
