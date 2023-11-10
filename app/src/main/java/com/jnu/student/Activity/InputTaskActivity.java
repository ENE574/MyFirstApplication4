package com.jnu.student.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jnu.student.R;

public class InputTaskActivity extends AppCompatActivity {
    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);
        position= this.getIntent().getIntExtra("position",0);
        String title=this.getIntent().getStringExtra("title");
        EditText editTextTitle=findViewById(R.id.edittext_task_title);
        if(null!=title)
        {
            editTextTitle.setText(title);
        }
//        ImageButton menuButton = findViewById(R.id.menuButton);  // 找到加号按钮
//
//        menuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupMenu(v);
//            }
//        });
    }

}