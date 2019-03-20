package com.example.hiloldictionary.ui.item;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.hiloldictionary.R;
import com.example.hiloldictionary.repository.storage.db.Definition;

public class ItemActivity extends AppCompatActivity {
    private final String KEY = "item";
    private Definition item;
    private AppCompatTextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        tv = findViewById(R.id.tv);
        item = getIntent().getParcelableExtra(KEY);
        tv.setText(item.getDefinition());
    }
}
