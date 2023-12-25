package com.jnu.student;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Task> taskList;
    private Context mcontext;
    private OnItemClickListener onItemClickListener;
    boolean isSortVisible = false;
    public TaskAdapter(List<Task> taskList,Context context) {
        this.taskList = taskList;
        this.mcontext = context;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false);
        return new TaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
        Task task = taskList.get(position);
        if(task!=null)
            taskViewHolder.bind(task);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return taskList.size();
    }
    public class TaskViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener{
        private final CheckBox checkBox;
        private final TextView textViewMark;
        private final TextView textViewTaskTitle;
        private final ImageButton pinImageButton;
        private final ImageView sortImageView;
        private final TextView textViewTimes;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            textViewMark = itemView.findViewById(R.id.text_view_mark);
            textViewTaskTitle = itemView.findViewById(R.id.text_view_task_title);
            pinImageButton = itemView.findViewById(R.id.imageButton_pin);
            sortImageView = itemView.findViewById(R.id.imageView_sort);
            textViewTimes = itemView.findViewById(R.id.textView_times);
            itemView.setOnCreateContextMenuListener(this);
        }
        public void bind(Task task) {
            textViewMark.setText("+"+task.getMark());
            textViewTaskTitle.setText(task.getTitle());
            textViewTimes.setText(task.getComplete() +"/"+ task.getTimes());
            if (task.isPinned()) {
                pinImageButton.setImageResource(R.drawable.pin_fill);
            } else {
                pinImageButton.setImageResource(R.drawable.pin);
            }
            pinImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isSortVisible) {
                        task.changePinned(task.isPinned());
                        if (task.isPinned()) {
                            pinImageButton.setImageResource(R.drawable.pin_fill);
                        } else {
                            pinImageButton.setImageResource(R.drawable.pin);
                        }
                    }
                }
            });
            checkBox.setChecked(task.isCompleted());
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()){
                        Mark.marks = Mark.marks + Integer.parseInt(task.getMark());
                        task.setComplete(task.getComplete()+1);
                    }
                    else {
                        Mark.marks = Mark.marks - Integer.parseInt(task.getMark());
                        task.setComplete(task.getComplete()-1);
                    }
                    if (Mark.marks < 0) {
                        DayTaskFragment.marksTextView.setTextColor(mcontext.getResources().getColor(R.color.red, mcontext.getTheme()));
                        WeekTaskFragment.marksTextView.setTextColor(mcontext.getResources().getColor(R.color.red, mcontext.getTheme()));
                        NormalTaskFragment.marksTextView.setTextColor(mcontext.getResources().getColor(R.color.red, mcontext.getTheme()));
                        RewardFragment.marksTextView.setTextColor(mcontext.getResources().getColor(R.color.red, mcontext.getTheme()));
                    }
                    else {
                        DayTaskFragment.marksTextView.setTextColor(mcontext.getResources().getColor(R.color.black, mcontext.getTheme()));
                        WeekTaskFragment.marksTextView.setTextColor(mcontext.getResources().getColor(R.color.black, mcontext.getTheme()));
                        NormalTaskFragment.marksTextView.setTextColor(mcontext.getResources().getColor(R.color.black, mcontext.getTheme()));
                        RewardFragment.marksTextView.setTextColor(mcontext.getResources().getColor(R.color.black, mcontext.getTheme()));
                    }
                    DayTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
                    WeekTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
                    NormalTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
                    RewardFragment.marksTextView.setText(String.valueOf(Mark.marks));
                    checkBox.setChecked(task.isCompleted());
                    textViewTimes.setText(task.getComplete() +"/"+ task.getTimes());
                }
            });
            if (isSortVisible) {
                sortImageView.setVisibility(View.VISIBLE);
            } else {
                sortImageView.setVisibility(View.GONE);
            }
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            if (!isSortVisible) {
                menu.add(0, 1, this.getAdapterPosition(), "删除");
            }
        }
    }
}
