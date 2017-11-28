package com.colinries.eqsolver;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searchRoots(View view) {

        // Assign all the views required to gather and display information

        EditText inputA = (EditText) findViewById(R.id.inputA);
        EditText inputB = (EditText) findViewById(R.id.inputB);
        EditText inputC = (EditText) findViewById(R.id.inputC);
        TextView outputX1 = (TextView) findViewById(R.id.outputX1);
        TextView outputX2 = (TextView) findViewById(R.id.outputX2);

        // Get the values for a, b and c from the respective EditText Fields

        float a = Float.valueOf(inputA.getText().toString());
        float b = Float.valueOf(inputB.getText().toString());
        float c = Float.valueOf(inputC.getText().toString());

        // Calculate the discriminant (delta) using the classic formula

        float discriminant = b*b - 4 * a * c;

        // Make sure that the script only continues if it is a second degree equation (a =/= 0) and the discriminant is positive

        if (discriminant >= 0 && a != 0) {

            // Calculate roots and round them to 4 decimal places

            double root1 = roundN((-b - sqrt(discriminant)) / (2 * a), 4);
            double root2 = roundN((-b + sqrt(discriminant)) / (2 * a), 4);

            // Check that the smaller root will be displayed as x1, the larger one as x2

            if (root1 <= root2) {
                outputX1.setText(Double.toString(root1));
                outputX2.setText(Double.toString(root2));
            } else {
                outputX1.setText(Double.toString(root2));
                outputX2.setText(Double.toString(root1));
            }

        } else if (a == 0) {  // If it's not a second degree equation, then solve it as a linear equation

            AlertDialog negativeDelta = new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("The equation appears not to be a second degree equation, so only 1 root exists: x=" + Double.toString(roundN((c/b), 4)))
                    .show();

            outputX1.setText(Double.toString(roundN((c/b), 4)));
            outputX2.setText("NaN");

        } else if (discriminant < 0) { // If the discriminant is negative, display a warning and set the root outputs to "NaN" (Not a Number)

            AlertDialog negativeDelta = new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("The equation entered has a negative discriminant and thus has no roots!")
                    .show();

            outputX1.setText("NaN");
            outputX2.setText("NaN");

        }
    }

    
    // Function for rounding decimal numbers to n decimal places
    // Set factor to nth power of 10
    // Move the comma so that the last desired decimal place is the unit, round it to unit, then shift the comma back to original position
    /* Example: 3.14159265 to 4 dec places.
                factor = 10000
                3.14159265 * 10000 = 31415.9265
                round -> 31416
                31416 / 10000 = 3.1416
                return 3.1416
     */

    public double roundN(double input, int n) {
        double factor = pow(10, n);
        return Math.round(input*factor)/factor;
    }
}
