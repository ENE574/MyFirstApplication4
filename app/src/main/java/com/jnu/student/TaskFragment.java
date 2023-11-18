package com.jnu.student;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
public class TaskFragment extends Fragment implements TaskAdapter.SignalListener, TaskAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private TextView coinsTextView;
    private TaskAdapter adapter;
    private ActivityResultLauncher<Intent> addTaskLauncher;
    private ActivityResultLauncher<Intent> editTaskLauncher;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private static List<Task> taskList = new ArrayList<>();
    public TaskFragment() {
    }
    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onItemClick(int position) {
        Intent editIntent = new Intent(this.getContext(), TaskItemActivity.class);
        editIntent.putExtra("id",position);
        editIntent.putExtra("title", taskList.get(position).getTitle());
        editIntent.putExtra("mark", taskList.get(position).getMark());
        editIntent.putExtra("times", taskList.get(position).getTimes());
        editIntent.putExtra("type", taskList.get(position).getType());
        editTaskLauncher.launch(editIntent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        View rootView2 = inflater.inflate(R.layout.fragment_task, container, false);
        viewPager = rootView.findViewById(R.id.viewPager);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        recyclerView = rootView2.findViewById(R.id.recycle_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setLongClickable(true);
        adapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(adapter);
        adapter.setSignalListener(this);
        registerForContextMenu(recyclerView);
        coinsTextView = rootView.findViewById(R.id.textView_marks);
        coinsTextView.setText(String.valueOf(Mark.marks));
        addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String title = data.getStringExtra("title");
                            String coin = data.getStringExtra("mark");
                            int times = data.getIntExtra("times", 1);
                            int type = data.getIntExtra("type", 0);
                            taskList.add(new Task(title, coin, times, type));
                            adapter.notifyItemInserted(taskList.size());
                        }
                    }
                });
        editTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent data = result.getData();
                        if (data != null) {
                            String title = data.getStringExtra("title");
                            String coin = data.getStringExtra("mark");
                            int type = data.getIntExtra("type",0);
                            int times = data.getIntExtra("times",1);
                            int id = data.getIntExtra("id",0);
                            taskList.set(id, new Task(title, coin, times, type));
                            adapter.notifyItemChanged(id);
                        }
                    }
                });
        adapter.setOnItemClickListener(this);
        return rootView;
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                break;
            case 1:
                taskList.remove(item.getOrder());
                adapter.notifyItemRemoved(item.getOrder());
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    @Override
    public void onSignalReceived() {
        coinsTextView.setText(String.valueOf(Mark.marks));
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.all_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_task) {
            Intent addIntent = new Intent(this.getContext(), InputTaskActivity.class);
            addTaskLauncher.launch(addIntent);
        }
        else if (item.getItemId() == R.id.action_join_duty) {
        }
        else if (item.getItemId() == R.id.action_sort) {
        }
        else if (item.getItemId() == R.id.navigation_home) {
            TaskFragment.newInstance();
        }
        else if (item.getItemId() == R.id.navigation_dashboard) {
            RewardFragment.newInstance();
        }
        else if (item.getItemId() == R.id.navigation_notifications) {
            MineFragment.newInstance();
        }
        return super.onOptionsItemSelected(item);
    }
}