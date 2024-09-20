package com.jupiterreporting.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reports")
public class Report {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String field1;
    private String field2;
    private boolean isSynced;

    // Конструктор по умолчанию
    public Report() {}

    // Конструктор с параметрами
    public Report(String field1, String field2, boolean isSynced) {
        this.field1 = field1;
        this.field2 = field2;
        this.isSynced = isSynced;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }
}
