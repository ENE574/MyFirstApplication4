package com.jnu.student;
import java.util.ArrayList;
import java.util.List;
public class Reward {
    public static List<Reward> rewardList = new ArrayList<>();
    private String title;
    private String mark;
    private int type;
    private int complete = 0;
    public Reward(String title, String mark, int type){
        this.title = title;
        this.mark = mark;
        this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public String getMark() {
        return mark;
    }
    public int getType(){
        return type;
    }
    public void setComplete(int complete){
        this.complete = complete;
    }
    public int getComplete(){
        return this.complete;
    }
}
