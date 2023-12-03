package com.jnu.student;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
public class InputRewardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText titleEditText;
    private EditText markEditText;
    private Spinner rewardTypeSpinner;
    private final static String[] rewardTypeArray = {"单次","不限"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_reward);
        markEditText = findViewById(R.id.editText_Mark);
        titleEditText = findViewById(R.id.edit_text_title);
        rewardTypeSpinner = findViewById(R.id.spinner_rewardType);
        Button addButton = findViewById(R.id.button_ok);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(titleEditText.getText())){
                    Toast.makeText(InputRewardActivity.this,"请输入标题",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(markEditText.getText())){
                    Toast.makeText(InputRewardActivity.this,"请输入成就点数",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent resultIntent = new Intent();
                resultIntent.putExtra("title", titleEditText.getText().toString());
                resultIntent.putExtra("mark", markEditText.getText().toString());
                resultIntent.putExtra("type", rewardTypeSpinner.getSelectedItemPosition());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ArrayAdapter<String> startAdapter = new ArrayAdapter<>(this,R.layout.item_select,rewardTypeArray);
        rewardTypeSpinner.setAdapter(startAdapter);
        rewardTypeSpinner.setSelection(0);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}