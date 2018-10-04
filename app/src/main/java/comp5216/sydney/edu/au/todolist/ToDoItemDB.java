package comp5216.sydney.edu.au.todolist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {ToDoListItem.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
/***
 * @author Akash
 *
 * get database instance
 */
public abstract class ToDoItemDB extends RoomDatabase {
    private static final String DATABASE_NAME = "todoitem_db";
    private static ToDoItemDB dbinstance;

    public static ToDoItemDB getDatabase(Context context) {
        if (dbinstance == null) {
            synchronized (MainActivity.class) {
                //fallbackToDestructiveMigration to delete old database when version of DB changes.
                dbinstance = Room.databaseBuilder(context.getApplicationContext(),
                        ToDoItemDB.class, DATABASE_NAME).fallbackToDestructiveMigration()
                        .build();

            }
        }
        return dbinstance;
    }

    public static void destroyInstance() {
        dbinstance = null;
    }

    public abstract ToDoItemDao toDoItemDao();
}
