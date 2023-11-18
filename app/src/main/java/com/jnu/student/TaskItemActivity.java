package com.jnu.student;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
public class TaskItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText titleEditText;
    private EditText markEditText;
    private EditText timesEditText;
    private Spinner taskTpyeSpinner;
    private final static String[] taskTpyeArray = {"每日任务","每周任务","普通任务","副本任务"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);
        markEditText = findViewById(R.id.editText_Mark);
        titleEditText = findViewById(R.id.edit_text_title);
        timesEditText = findViewById(R.id.editText_Number);
        taskTpyeSpinner = findViewById(R.id.spinner_taskType);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        String title = intent.getStringExtra("title");
        String mark = intent.getStringExtra("mark");
        int times = intent.getIntExtra("times",1);
        int type = intent.getIntExtra("type",0);
        titleEditText.setText(title);
        markEditText.setText(mark);
        timesEditText.setText(String.valueOf(times));
        Button addButton = findViewById(R.id.button_ok);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", titleEditText.getText().toString());
                resultIntent.putExtra("mark", markEditText.getText().toString());
                resultIntent.putExtra("times", Integer.parseInt(timesEditText.getText().toString()));
                resultIntent.putExtra("id",id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ArrayAdapter<String> startAdapter = new ArrayAdapter<>(this,R.layout.item_select,taskTpyeArray);
        taskTpyeSpinner.setAdapter(startAdapter);
        taskTpyeSpinner.setSelection(type);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}