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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;

public class NormalTaskFragment extends Fragment
        implements TaskAdapter.SignalListener, TaskAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    static TextView emptyTextView;
    static TextView marksTextView;
    static TaskAdapter adapter;
    private ActivityResultLauncher<Intent> addTaskLauncher;
    private ActivityResultLauncher<Intent> editTaskLauncher;

    public NormalTaskFragment() {
    }
    public static NormalTaskFragment newInstance() {
        NormalTaskFragment fragment = new NormalTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onItemClick(int position) {
        if (!adapter.isSortVisible) {
            Intent editIntent = new Intent(this.getContext(), TaskItemActivity.class);
            editIntent.putExtra("id", position);
            editIntent.putExtra("title", taskList2.get(position).getTitle());
            editIntent.putExtra("mark", taskList2.get(position).getMark());
            editIntent.putExtra("times", taskList2.get(position).getTimes());
            editIntent.putExtra("type", taskList2.get(position).getType());
            editTaskLauncher.launch(editIntent);
        }
        else{
            adapter.isSortVisible = false;
            adapter.notifyDataSetChanged();
            requireActivity().invalidateOptionsMenu();
        }
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
        adapter = new TaskAdapter(taskList2);
        recyclerView.setAdapter(adapter);
        adapter.setSignalListener(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags;
                if (adapter.isSortVisible) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }
                else {
                    dragFlags = 0;
                }
                int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition){
                    for (int i = fromPosition; i < toPosition; i++)
                    {
                        Collections.swap(adapter.taskList, i , i+1);
                    }
                }
                else {
                    for (int i = fromPosition; i > toPosition; i--)
                    {
                        Collections.swap(adapter.taskList, i , i-1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        emptyTextView = rootView.findViewById(R.id.textView_task_empty);
        if (taskList2.size() == 0){
            NormalTaskFragment.emptyTextView.setVisibility(View.VISIBLE);
        }
        else{
            NormalTaskFragment.emptyTextView.setVisibility(View.GONE);
        }
        registerForContextMenu(recyclerView);
        marksTextView = rootView.findViewById(R.id.textView_marks);
        if (Mark.marks < 0) {
            marksTextView.setTextColor(getResources().getColor(R.color.red, requireContext().getTheme()));
        }
        else {
            marksTextView.setTextColor(getResources().getColor(R.color.black, requireContext().getTheme()));
        }
        marksTextView.setText(String.valueOf(Mark.marks));
        addTask();
        editTask();
        adapter.setOnItemClickListener(this);
        return rootView;
    }
    public void updateEmptyViewVisibility() {
        if(DayTaskFragment.emptyTextView!=null){
            if (taskList0.size() == 0) {
                DayTaskFragment.emptyTextView.setVisibility(View.VISIBLE);
            }
            else{
                DayTaskFragment.emptyTextView.setVisibility(View.GONE);
            }
        }
        if(WeekTaskFragment.emptyTextView!=null){
            if (taskList1.size() == 0){
                WeekTaskFragment.emptyTextView.setVisibility(View.VISIBLE);
            }
            else{
                WeekTaskFragment.emptyTextView.setVisibility(View.GONE);
            }
        }
        if(NormalTaskFragment.emptyTextView!=null){
            if (taskList2.size() == 0){
                NormalTaskFragment.emptyTextView.setVisibility(View.VISIBLE);
            }
            else{
                NormalTaskFragment.emptyTextView.setVisibility(View.GONE);
            }
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
                                TaskSelectionFragment.viewPager.setCurrentItem(0);
                                taskList0.add(new Task(title, mark, times, type));
                                DayTaskFragment.adapter.notifyItemInserted(taskList0.size());
                            }
                            else if (type == 1) {
                                TaskSelectionFragment.viewPager.setCurrentItem(1);
                                taskList1.add(new Task(title, mark, times, type));
                                WeekTaskFragment.adapter.notifyItemInserted(taskList1.size());
                            }
                            else if (type == 2) {
                                TaskSelectionFragment.viewPager.setCurrentItem(2);
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
                                TaskSelectionFragment.viewPager.setCurrentItem(0);
                                taskList2.remove(id);
                                NormalTaskFragment.adapter.notifyItemRemoved(id);
                                taskList0.add(new Task(title, mark, times, type));
                                DayTaskFragment.adapter.notifyItemInserted(taskList0.size());
                            }
                            else if (type == 1) {
                                TaskSelectionFragment.viewPager.setCurrentItem(1);
                                taskList2.remove(id);
                                NormalTaskFragment.adapter.notifyItemRemoved(id);
                                taskList1.add(new Task(title, mark, times, type));
                                WeekTaskFragment.adapter.notifyItemInserted(taskList1.size());
                            }
                            else if (type == 2) {
                                TaskSelectionFragment.viewPager.setCurrentItem(2);
                                taskList2.set(id, new Task(title, mark, times, type));
                                NormalTaskFragment.adapter.notifyItemChanged(id);
                            }
                            updateEmptyViewVisibility();
                        }
                    }
                });
    }
    @Override
    public void onSignalReceived() {
        if (Mark.marks < 0) {
            if(DayTaskFragment.marksTextView!=null)
                DayTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.red, requireContext().getTheme()));
            if(WeekTaskFragment.marksTextView!=null)
                WeekTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.red, requireContext().getTheme()));
            if(NormalTaskFragment.marksTextView!=null)
                NormalTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.red, requireContext().getTheme()));
            if(RewardFragment.marksTextView!=null)
                RewardFragment.marksTextView.setTextColor(getResources().getColor(R.color.red, requireContext().getTheme()));
        }
        else {
            if(DayTaskFragment.marksTextView!=null)
                DayTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.black, requireContext().getTheme()));
            if(WeekTaskFragment.marksTextView!=null)
                WeekTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.black, requireContext().getTheme()));
            if(NormalTaskFragment.marksTextView!=null)
                NormalTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.black, requireContext().getTheme()));
            if(RewardFragment.marksTextView!=null)
                RewardFragment.marksTextView.setTextColor(getResources().getColor(R.color.black, requireContext().getTheme()));
        }
        if(DayTaskFragment.marksTextView!=null)
            DayTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
        if(WeekTaskFragment.marksTextView!=null)
            WeekTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
        if(NormalTaskFragment.marksTextView!=null)
            NormalTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
        if(RewardFragment.marksTextView!=null)
            RewardFragment.marksTextView.setText(String.valueOf(Mark.marks));
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.task_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_task) {
            Intent addIntent = new Intent(this.getContext(), InputTaskActivity.class);
            addTaskLauncher.launch(addIntent);
        }
        else if (item.getItemId() == R.id.action_sort) {
            adapter.isSortVisible = true;
            adapter.notifyDataSetChanged();
            requireActivity().invalidateOptionsMenu();
        }
        else if (item.getItemId() == R.id.action_sort_finish) {
            adapter.isSortVisible = false;
            adapter.notifyDataSetChanged();
            requireActivity().invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem sortFinishMenuItem = menu.findItem(R.id.action_sort_finish);
        MenuItem addMenuItem = menu.findItem(R.id.action_add_menu);
        sortFinishMenuItem.setVisible(adapter.isSortVisible);
        addMenuItem.setVisible(!adapter.isSortVisible);
    }
}