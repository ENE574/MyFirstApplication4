package com.jnu.student;
import static com.jnu.student.Task.taskList0;
import static com.jnu.student.Task.taskList1;
import static com.jnu.student.Task.taskList2;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
public class DayTaskFragment extends Fragment
        implements TaskAdapter.SignalListener, TaskAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    static TextView emptyTextView;
    static TextView marksTextView;
    static TaskAdapter adapter;
    private ActivityResultLauncher<Intent> addTaskLauncher;
    private ActivityResultLauncher<Intent> editTaskLauncher;
    public DayTaskFragment() {
    }
    public static DayTaskFragment newInstance() {
        DayTaskFragment fragment = new DayTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onItemClick(int position) {
        Intent editIntent = new Intent(this.getContext(), TaskItemActivity.class);
        editIntent.putExtra("id",position);
        editIntent.putExtra("title", taskList0.get(position).getTitle());
        editIntent.putExtra("mark", taskList0.get(position).getMark());
        editIntent.putExtra("times", taskList0.get(position).getTimes());
        editIntent.putExtra("type", taskList0.get(position).getType());
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
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);
        recyclerView = rootView.findViewById(R.id.recycle_view_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setLongClickable(true);
        adapter = new TaskAdapter(taskList0);
        recyclerView.setAdapter(adapter);
        adapter.setSignalListener(this);
        emptyTextView = rootView.findViewById(R.id.textView_task_empty);
        if (taskList0.size() == 0) {
            DayTaskFragment.emptyTextView.setVisibility(View.VISIBLE);
        }
        else{
            DayTaskFragment.emptyTextView.setVisibility(View.GONE);
        }
        registerForContextMenu(recyclerView);       //注册ContextMenu
        marksTextView = rootView.findViewById(R.id.textView_marks);
        marksTextView.setText(String.valueOf(Mark.marks));
        addTask();
        editTask();
        adapter.setOnItemClickListener(this);
        return rootView;
    }
    public void updateEmptyViewVisibility() {
        if (taskList0.size() == 0) {
            DayTaskFragment.emptyTextView.setVisibility(View.VISIBLE);
        }
        else{
            DayTaskFragment.emptyTextView.setVisibility(View.GONE);
        }
        if (taskList1.size() == 0){
            WeekTaskFragment.emptyTextView.setVisibility(View.VISIBLE);
        }
        else{
            WeekTaskFragment.emptyTextView.setVisibility(View.GONE);
        }
        if (taskList2.size() == 0){
            NormalTaskFragment.emptyTextView.setVisibility(View.VISIBLE);
        }
        else{
            NormalTaskFragment.emptyTextView.setVisibility(View.GONE);
        }
    }
    public void addTask(){
        if (addTaskLauncher != null) {
            addTaskLauncher.unregister();
        }
        addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String title = data.getStringExtra("title");
                            String mark = data.getStringExtra("mark");
                            int times = data.getIntExtra("times", 1);
                            int type = data.getIntExtra("type", 0);
                            if (type == 0) {
                                MainActivity.viewPager.setCurrentItem(0);
                                taskList0.add(new Task(title, mark, times, type));
                                DayTaskFragment.adapter.notifyItemInserted(taskList0.size());
                            }
                            else if (type == 1) {
                                MainActivity.viewPager.setCurrentItem(1);
                                taskList1.add(new Task(title, mark, times, type));
                                WeekTaskFragment.adapter.notifyItemInserted(taskList1.size());
                            }
                            else if (type == 2) {
                                MainActivity.viewPager.setCurrentItem(2);
                                taskList2.add(new Task(title, mark, times, type));
                                NormalTaskFragment.adapter.notifyItemInserted(taskList2.size());
                            }
                            updateEmptyViewVisibility();
                        }
                    }
                });
    }
    public void editTask(){
        if (editTaskLauncher != null) {
            editTaskLauncher.unregister();
        }
        editTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent data = result.getData();
                        if (data != null) {
                            String title = data.getStringExtra("title");
                            String mark = data.getStringExtra("mark");
                            int type = data.getIntExtra("type",0);
                            int times = data.getIntExtra("times",1);
                            int id = data.getIntExtra("id",0);
                            if (type == 0) {
                                MainActivity.viewPager.setCurrentItem(0);
                                taskList0.set(id, new Task(title, mark, times, type));
                                DayTaskFragment.adapter.notifyItemChanged(id);
                            }
                            else if (type == 1) {
                                MainActivity.viewPager.setCurrentItem(1);
                                taskList0.remove(id);
                                DayTaskFragment.adapter.notifyItemRemoved(id);
                                taskList1.add(new Task(title, mark, times, type));
                                WeekTaskFragment.adapter.notifyItemInserted(taskList1.size());
                            }
                            else if (type == 2) {
                                MainActivity.viewPager.setCurrentItem(2);
                                taskList0.remove(id);
                                DayTaskFragment.adapter.notifyItemRemoved(id);
                                taskList2.add(new Task(title, mark, times, type));
                                NormalTaskFragment.adapter.notifyItemInserted(taskList2.size());
                            }
                            updateEmptyViewVisibility();
                        }
                    }
                });
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int fragmentType = MainActivity.viewPager.getCurrentItem();
        if (fragmentType == 0) {
            taskList0.remove(item.getOrder());
            DayTaskFragment.adapter.notifyItemRemoved(item.getOrder());
        } else if (fragmentType == 1) {
            taskList1.remove(item.getOrder());
            WeekTaskFragment.adapter.notifyItemRemoved(item.getOrder());
        } else if (fragmentType == 2) {
            taskList2.remove(item.getOrder());
            NormalTaskFragment.adapter.notifyItemRemoved(item.getOrder());
        }
        updateEmptyViewVisibility();
        return super.onContextItemSelected(item);
    }
    @Override
    public void onSignalReceived() {
        DayTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
        WeekTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
        NormalTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.all_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_task) {
            Intent addIntent = new Intent(this.getContext(), InputTaskActivity.class);
            addTaskLauncher.launch(addIntent);
        }
        else if (item.getItemId() == R.id.action_sort) {
        }
        return super.onOptionsItemSelected(item);
    }
}