package com.jnu.student;
public class Task {
    private String title;
    private String mark;
    private int type;
    private int pin = 0;
    private int complete = 0;
    private int times = 1;
    private String tags;
    public Task(String title, String mark, int times, int type) {
        this.title = title;
        this.mark = mark;
        this.times = times;
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
    public boolean isPinned(){
        if (pin == 0){
            return false;
        }
        else {
            return true;
        }
    }
    public void changePinned(boolean ispinned) {
        if (ispinned){
            this.pin = 0;
        }
        else {
            this.pin = 1;
        }
    }
    public boolean isCompleted() {
        if (this.complete == this.times){
            return true;
        }
        else {
            return false;
        }
    }
    public void setCompleted(boolean checked) {
        if (checked){
            this.complete++;
        }
        else {
            this.complete--;
        }
    }
    public int getComplete(){
        return this.complete;
    }
    public int getTimes(){
        return this.times;
    }
    public void setTags(String tag){
        this.tags = tag;
    }
}
