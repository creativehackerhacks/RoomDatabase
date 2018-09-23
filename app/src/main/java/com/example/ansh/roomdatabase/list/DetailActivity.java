package com.example.ansh.roomdatabase.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ansh.roomdatabase.R;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_DATE_AND_TIME = "EXTRA_DATE_AND_TIME";
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String EXTRA_COLOR = "EXTRA_COLOR";

    private TextView mDateAndTime, mMessage;
    private ImageView mColoredBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDateAndTime = findViewById(R.id.lbl_date_and_time_header);
        mMessage = findViewById(R.id.lbl_message_body);
        mColoredBackground = findViewById(R.id.imv_colored_background);

        Intent i = getIntent();
        String dateAndTimeExtra = i.getStringExtra(EXTRA_DATE_AND_TIME);
        String message = i.getStringExtra(EXTRA_MESSAGE);
        int colorResourceExtra = i.getIntExtra(EXTRA_COLOR, 0);

        mDateAndTime.setText(dateAndTimeExtra);
        mMessage.setText(message);
        mColoredBackground.setBackgroundResource(colorResourceExtra);

    }
}
