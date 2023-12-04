package com.jnu.student;
import static com.jnu.student.Mark.marks;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class RewardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Reward> rewardList;
    private static SignalListener signalListener;
    private OnItemClickListener onItemClickListener;
    boolean isSortVisible = false;
    public void setSignalListener(SignalListener listener) {
        signalListener = listener;
    }
    public RewardAdapter(List<Reward> rewardList) {
        this.rewardList = rewardList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item_layout, parent, false);
        return new RewardViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RewardViewHolder rewardViewHolder = (RewardViewHolder) holder;
        Reward reward = rewardList.get(position);
        rewardViewHolder.bind(reward);
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
        return rewardList.size();
    }
    public class RewardViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener{
        private final TextView textViewMark;
        private final TextView textViewRewardTitle;
        private final ImageView sortImageView;
        private final TextView textViewTimes;
        public RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMark = itemView.findViewById(R.id.text_view_mark);
            textViewRewardTitle = itemView.findViewById(R.id.text_view_reward_title);
            sortImageView = itemView.findViewById(R.id.imageView_sort);
            textViewTimes = itemView.findViewById(R.id.textView_times);
            itemView.setOnCreateContextMenuListener(this);
        }
        public void bind(Reward reward) {
            textViewMark.setText("-" + reward.getMark());
            textViewRewardTitle.setText(reward.getTitle());
            if (reward.getType() == 0) {
                textViewTimes.setText(reward.getComplete() + "/1");
            } else {
                textViewTimes.setText(reward.getComplete() + "/∞");
            }
            if (isSortVisible) {
                sortImageView.setVisibility(View.VISIBLE);
            } else {
                sortImageView.setVisibility(View.GONE);
            }
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View view,
                                        ContextMenu.ContextMenuInfo contextMenuInfo) {
            if (!isSortVisible) {
                menu.add(0, 0, this.getAdapterPosition(), "编辑");
                menu.add(0, 1, this.getAdapterPosition(), "删除");
            }
        }
    }
    public interface SignalListener {
        void onSignalReceived();
    }
}
