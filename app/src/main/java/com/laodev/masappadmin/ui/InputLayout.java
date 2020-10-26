package com.laodev.masappadmin.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.laodev.masappadmin.R;

public class InputLayout extends LinearLayout {

    private int resourceID;
    private String hintStr;
    private String inputStr;

    private EditText txt_input;

    public InputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.InputLayout);
        this.resourceID = arr.getResourceId(R.styleable.InputLayout_src, 0);
        this.hintStr = arr.getString(R.styleable.InputLayout_hint);
        this.inputStr = arr.getString(R.styleable.InputLayout_inputType);
        arr.recycle();

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.ui_input_layout, this, true);

        initUIView();
    }

    private void initUIView() {
        ImageView img_input = findViewById(R.id.img_input);
        img_input.setImageResource(resourceID);

        txt_input = findViewById(R.id.txt_input);
        txt_input.setHint(hintStr);
        switch (inputStr) {
            case "Number" :
                txt_input.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "Email" :
                txt_input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case "Phone" :
                txt_input.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case "Pass" :
                txt_input.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }
    }

    public String getInputText() {
        return txt_input.getText().toString();
    }

    public void setInputText(String str) {
        txt_input.setText(str);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        txt_input.setEnabled(enabled);
    }

}
