package com.cashmoney.calculate.report.manager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cashmoney.calculate.report.manager.dto.ViewInCard;

import java.util.List;

@Dao
public interface ViewInCardDAO {

    @Delete
    void delete(ViewInCard viewInCard);

    @Query("SELECT * FROM viewincard WHERE id = :j")
    ViewInCard findById(long j);

    @Query("SELECT * FROM viewincard WHERE id = :j")
    ViewInCard findByIdbg(long j);

    @Query("SELECT * FROM viewincard")
    List<ViewInCard> getAllViews();

    @Query("SELECT * FROM viewincard WHERE id = :j")
    List<ViewInCard> getAllViewsWhereThemeEquals(long j);

    @Insert
    void insert(ViewInCard viewInCard);

    @Insert
    void insertAll(List<ViewInCard> list);

    @Insert
    void insertAll(ViewInCard... viewInCardArr);

    @Update
    void update(ViewInCard viewInCard);

    @Update
    void updateAll(List<ViewInCard> list);

}
