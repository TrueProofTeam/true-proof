package com.trueproof.trueproof.logic;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class InputFilterMinMax implements InputFilter, View.OnFocusChangeListener {
    private final double min, max;

    public InputFilterMinMax(double min, double max) {
        if (min > max) {
            double mid = max;
            max = min;
            min = mid;
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        String destString = dest.toString();
        String inputString = destString.substring(0, dstart) + source.toString() + destString.substring(dstart);

        if (inputString.equalsIgnoreCase("-") && min < 0){
            Log.e("filter", "- ignore case");
            return null;
        }
        if (inputString.equalsIgnoreCase(".")) return null;

        try {
            double input = Double.parseDouble(inputString);
            if (mightBeInRange((int) input))
                return null;
        } catch (NumberFormatException nfe) {}

        return "";
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (!hasFocus) {
            if (v instanceof EditText) sanitizeValues((EditText) v);
        }
    }

    private boolean mightBeInRange(double value) {
        // Quick "fail"
        if (value >= 0 && value > max) return false;
        if (value >= 0 && value >= min) return true;
        if (value < 0 && value < min) return false;
        if (value < 0 && value <= max) return true;

        boolean negativeInput = value < 0;

        if (numberOfDigits((int) min) == numberOfDigits((int) max)) {
            if (!negativeInput) {
                if (numberOfDigits((int) value) >= numberOfDigits((int) min) && value < min) return false;
            } else {
                if (numberOfDigits((int) value) >= numberOfDigits((int) max) && value > max) return false;
            }
        }

        return true;
    }

    private int numberOfDigits(double n) {
        return String.valueOf(n).replace("-", "").length();
    }

    private void sanitizeValues(EditText valueText) {
        try {
            double value = Double.parseDouble(valueText.getText().toString());
            if (value < min) {
                value = min;
                valueText.setText(String.valueOf(value));
            } else if (value > max) {
                value = max;
                valueText.setText(String.valueOf(value));
            }
        } catch (NumberFormatException nfe) {
            valueText.setText("");
        }
    }
}
