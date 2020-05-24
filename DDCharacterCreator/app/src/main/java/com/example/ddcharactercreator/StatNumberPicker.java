package com.example.ddcharactercreator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;

public class StatNumberPicker extends NumberPicker {
    public StatNumberPicker(Context context) {
        super(context);
    }

    public StatNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttributeSet(attrs);
    }

    public StatNumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        processAttributeSet(attrs);
    }
    private void processAttributeSet(AttributeSet attrs) {
        this.setMinValue(attrs.getAttributeIntValue(null, "min", 0));
        this.setMaxValue(attrs.getAttributeIntValue(null, "max", 0));
    }
}
