package com.jnu.student;
import static com.jnu.student.Reward.rewardList;
import static com.jnu.student.Mark.marks;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
public class RewardFragment extends Fragment
        implements RewardAdapter.SignalListener,RewardAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    static TextView emptyTextView;
    static TextView marksTextView;
    static RewardAdapter adapter2;
    private ActivityResultLauncher<Intent> addRewardLauncher;
    private ActivityResultLauncher<Intent> editRewardLauncher;
    public RewardFragment() {
    }
    @Override
    public void onItemClick(int position) {
        if (!adapter2.isSortVisible) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("满足奖励")
                    .setMessage("确定花费"+adapter2.rewardList.get(position).getMark()+"点成就来满足你的奖励？")
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Reward reward = adapter2.rewardList.get(position);
                            reward.setComplete(reward.getComplete() + 1);
                            marks=marks-Integer.parseInt(adapter2.rewardList.get(position).getMark());
                            onSignalReceived();
                            if (reward.getType()==0) {
                                rewardList.remove(reward);
                            }
                            adapter2.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            adapter2.isSortVisible = false;
            adapter2.notifyDataSetChanged();
            requireActivity().invalidateOptionsMenu();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (editRewardLauncher != null) {
            editRewardLauncher.unregister();
        }
        editRewardLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String title = data.getStringExtra("title");
                            String mark = data.getStringExtra("mark");
                            int type = data.getIntExtra("type", 0);
                            int id = data.getIntExtra("id", 0);
                            rewardList.set(id, new Reward(title, mark, type));
                            adapter2.notifyItemChanged(id);
                            if (rewardList.size() == 0) {
                                RewardFragment.emptyTextView.setVisibility(View.VISIBLE);
                            } else {
                                RewardFragment.emptyTextView.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        recyclerView = rootView.findViewById(R.id.recycle_view_rewards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setLongClickable(true);
        adapter2 = new RewardAdapter(rewardList);
        recyclerView.setAdapter(adapter2);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags;
                if (adapter2.isSortVisible) {
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
                        Collections.swap(adapter2.rewardList, i , i+1);
                    }
                }
                else {
                    for (int i = fromPosition; i > toPosition; i--)
                    {
                        Collections.swap(adapter2.rewardList, i , i-1);
                    }
                }
                adapter2.notifyItemMoved(fromPosition, toPosition);
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        emptyTextView = rootView.findViewById(R.id.textView_reward_empty);
        if (rewardList.size() == 0) {
            RewardFragment.emptyTextView.setVisibility(View.VISIBLE);
        }
        else{
            RewardFragment.emptyTextView.setVisibility(View.GONE);
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
        addReward();
        adapter2.setOnItemClickListener(this);
        return rootView;
    }
    public void updateEmptyViewVisibility() {
        if (rewardList.size() == 0) {
            RewardFragment.emptyTextView.setVisibility(View.VISIBLE);
        }
        else{
            RewardFragment.emptyTextView.setVisibility(View.GONE);
        }
    }
    public void addReward(){
        if (addRewardLauncher != null) {
            addRewardLauncher.unregister();
        }
        addRewardLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String title = data.getStringExtra("title");
                            String mark = data.getStringExtra("mark");
                            int type = data.getIntExtra("type", 0);
                            rewardList.add(new Reward(title, mark, type));
                            adapter2.notifyItemInserted(rewardList.size());
                            if (rewardList.size() == 0) {
                                RewardFragment.emptyTextView.setVisibility(View.VISIBLE);
                            } else {
                                RewardFragment.emptyTextView.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }
    public void editReward(){
        Intent intent = new Intent(getActivity(), RewardItemActivity.class);
        editRewardLauncher.launch(intent);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int fragmentType = MainActivity.bottomViewPager.getCurrentItem();
        if (fragmentType == 1) {
            switch (item.getItemId()) {
                case 0:
                    editReward();
                    RewardFragment.adapter2.notifyItemRemoved(item.getOrder());
                    break;
                case 1:
                    rewardList.remove(item.getOrder());
                    RewardFragment.adapter2.notifyItemRemoved(item.getOrder());
                    break;
                default:
                    return super.onContextItemSelected(item);
            }
        }
        updateEmptyViewVisibility();
        return super.onContextItemSelected(item);
    }
    @Override
    public void onSignalReceived() {
        if (Mark.marks < 0) {
            DayTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.red, requireContext().getTheme()));
            WeekTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.red, requireContext().getTheme()));
            NormalTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.red, requireContext().getTheme()));
            RewardFragment.marksTextView.setTextColor(getResources().getColor(R.color.red, requireContext().getTheme()));
        }
        else {
            DayTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.black, requireContext().getTheme()));
            WeekTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.black, requireContext().getTheme()));
            NormalTaskFragment.marksTextView.setTextColor(getResources().getColor(R.color.black, requireContext().getTheme()));
            RewardFragment.marksTextView.setTextColor(getResources().getColor(R.color.black, requireContext().getTheme()));
        }
        DayTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
        WeekTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
        NormalTaskFragment.marksTextView.setText(String.valueOf(Mark.marks));
        RewardFragment.marksTextView.setText(String.valueOf(Mark.marks));
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear(); 
        inflater.inflate(R.menu.reward_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_reward) {
            Intent addIntent = new Intent(this.getContext(), InputRewardActivity.class);
            addRewardLauncher.launch(addIntent);
        }
        else if (item.getItemId() == R.id.action_sort) {
            adapter2.isSortVisible = true;
            adapter2.notifyDataSetChanged();
            requireActivity().invalidateOptionsMenu();
        }
        else if (item.getItemId() == R.id.action_sort_finish) {
            adapter2.isSortVisible = false;
            adapter2.notifyDataSetChanged();
            requireActivity().invalidateOptionsMenu(); 
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem sortFinishMenuItem = menu.findItem(R.id.action_sort_finish);
        MenuItem addMenuItem = menu.findItem(R.id.action_add_menu);
        sortFinishMenuItem.setVisible(adapter2.isSortVisible);
        addMenuItem.setVisible(!adapter2.isSortVisible);
    }
}