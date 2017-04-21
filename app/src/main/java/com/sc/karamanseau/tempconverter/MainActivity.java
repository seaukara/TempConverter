package com.sc.karamanseau.tempconverter;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.SharedPreferences.Editor;
import java.text.DecimalFormat;

public class MainActivity extends Activity
        implements OnEditorActionListener {


    private EditText tempInput;
    private TextView celsius;

    private String fahrenheit = "32";
    private SharedPreferences savedValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets
        tempInput = (EditText) findViewById(R.id.tempInput);
        celsius = (TextView) findViewById(R.id.celsius);

        // set the listeners
        tempInput.setOnEditorActionListener(this);

        // get SharedPreferences object
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = savedValues.edit();
        editor.putString("fahrenheit", "");
        editor.apply();

        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();

        // get the instance variables
        fahrenheit = savedValues.getString("fahrenheit", "0");


        // set the bill amount on its widget
        tempInput.setText(fahrenheit);

        // calculate and display
        calculateAndDisplay();
    }

    public void calculateAndDisplay() {

        // get the temp in degrees fahrenheit
        fahrenheit = tempInput.getText().toString();
        double celsiusValue;
        String tempOut;
        // calculate celsius
        if (fahrenheit.equals("")) {

            tempOut = "0";
        }
        else {
            celsiusValue = ((Double.parseDouble(fahrenheit) - 32) / 1.8);

            try {
                DecimalFormat numberFormat = new DecimalFormat("#.00");


                // display the other results with formatting
                tempOut = Double.toString(celsiusValue);
                if (tempOut.contains(".")) {
                    tempOut = numberFormat.format(tempOut);
                }

            } catch (Exception e) {
                tempOut = Double.toString(celsiusValue);
            }

        }
        celsius.setText(tempOut);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;
    }



}
