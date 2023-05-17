package com.gradebook.gradebook.models;

public class Classes {
    private int id;
    private int teacherId;
    private String name;
    private int num;

    public Classes(int id, String name, int num, int teacherId) {
        this.id = id;
        this.name = name;
        this.num = num;
        this.teacherId=teacherId;
    }

    public Classes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return
                id + '\t' + name + '\t' + num;
    }
}
