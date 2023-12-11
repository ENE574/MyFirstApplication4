package com.jnu.student;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
public class InputTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText titleEditText;
    private EditText markEditText;
    private EditText timesEditText;
    private Spinner taskTpyeSpinner;
    private final static String[] taskTpyeArray = {"每日任务","每周任务","普通任务"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);
        markEditText = findViewById(R.id.editText_Mark);
        titleEditText = findViewById(R.id.edit_text_title);
        timesEditText = findViewById(R.id.editText_Number);
        taskTpyeSpinner = findViewById(R.id.spinner_taskType);
        Button addButton = findViewById(R.id.button_ok);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(titleEditText.getText())){
                    Toast.makeText(InputTaskActivity.this,"请输入标题",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(markEditText.getText())){
                    Toast.makeText(InputTaskActivity.this,"请输入成就点数",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", titleEditText.getText().toString());
                resultIntent.putExtra("mark", markEditText.getText().toString());
                if (!(timesEditText.getText().toString().isEmpty())){
                    resultIntent.putExtra("times", Integer.parseInt(timesEditText.getText().toString()));
                }
                resultIntent.putExtra("type", taskTpyeSpinner.getSelectedItemPosition());
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
        taskTpyeSpinner.setSelection(0);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onSupportNavigateUp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}