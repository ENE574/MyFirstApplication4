package com.jnu.student;
import android.annotation.SuppressLint;
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
    private static SignalListener signalListener;
    private OnItemClickListener onItemClickListener;
    boolean isSortVisible = false;
    public void setSignalListener(SignalListener listener) {

        signalListener = listener;
    }
    public TaskAdapter(List<Task> taskList) {

        this.taskList = taskList;
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
            textViewTaskTitle = itemView.findViewById(R.id.text_view_reward_title);
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
                    if (signalListener != null) {
                        signalListener.onSignalReceived();
                    }
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
                menu.add(0, 0, this.getAdapterPosition(), "添加提醒");
                menu.add(0, 1, this.getAdapterPosition(), "删除");
            }
        }
    }
    public interface SignalListener {
        void onSignalReceived();
    }
}
