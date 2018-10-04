package comp5216.sydney.edu.au.todolist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
/***
 * @author Akash
 *
 * ToDoItemDao
 */
@Dao
public interface ToDoItemDao {
    // sorted by last updated Time
    @Query("SELECT * FROM todolist ORDER BY lastUpdateTime DESC")
    List<ToDoListItem> listAll();

    @Insert
    void insert(ToDoListItem toDoItem);


    @Query("DELETE FROM todolist")
    void deleteAll();

}
