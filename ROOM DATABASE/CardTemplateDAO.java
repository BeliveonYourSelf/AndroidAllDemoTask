package com.cashmoney.calculate.report.manager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cashmoney.calculate.report.manager.dto.CardTemplate;

import java.util.List;

@Dao
public interface CardTemplateDAO  {

//    @Insert
//    void insertCardTemplate(CardTemplate cardTemplate);
//
//    @Update
//    void updateCardTemplate(CardTemplate cardTemplate);
//
//    @Delete
//    void deleteCardTemplate(CardTemplate cardTemplate);
//
//    @Query("SELECT * FROM CardTemplate")
//    List<CardTemplate> getAllCardTemplates();
//
//    @Query("SELECT * FROM CardTemplate WHERE id = :id")
//    CardTemplate getCardTemplateById(int id);


    @Query("SELECT * FROM CardTemplate WHERE id = :j")
    CardTemplate findById(long j);

    @Query("SELECT * FROM cardtemplate WHERE id = :i")
    CardTemplate findByIdbg(int i);

    @Query("SELECT * FROM cardtemplate")
    List<CardTemplate> getAllTemplates();
    @Query("SELECT * FROM cardtemplate WHERE  id =:i")
    List<CardTemplate> getAllTemplatesWhereDateGreaterThan(int i);


    @Insert
    long insert(CardTemplate cardTemplate);

    @Insert
    void insertAll(CardTemplate... cardTemplateArr);


    @Update
    void update(CardTemplate cardTemplate);

    @Delete
    void delete(CardTemplate cardTemplate);

}
