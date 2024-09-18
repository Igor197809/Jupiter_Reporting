// ReportDao.java
package com.jupiterreporting.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.jupiterreporting.data.entities.Report;

import java.util.List;

@Dao
public interface ReportDao {
    @Insert
    void insert(Report report);

    @Query("SELECT * FROM reports WHERE isSynced = 0")
    List<Report> getUnsyncedReports();

    @Update
    void update(Report report);

    @Delete
    void delete(Report report);
}
