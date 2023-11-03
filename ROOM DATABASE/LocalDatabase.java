package com.cashmoney.calculate.report.manager.Databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.cashmoney.calculate.report.manager.R;
import com.cashmoney.calculate.report.manager.dao.CardTemplateDAO;
import com.cashmoney.calculate.report.manager.dao.ViewInCardDAO;
import com.cashmoney.calculate.report.manager.dto.CardTemplate;
import com.cashmoney.calculate.report.manager.dto.ViewInCard;

@Database(entities = {CardTemplate.class, ViewInCard.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract CardTemplateDAO cardTemplateDAO();

    public abstract ViewInCardDAO viewInCardDAO();

    public static LocalDatabase getInstance(Context context) {
        return (LocalDatabase) Room.databaseBuilder(context, LocalDatabase.class, context.getString(R.string.database)).build();
    }
}
