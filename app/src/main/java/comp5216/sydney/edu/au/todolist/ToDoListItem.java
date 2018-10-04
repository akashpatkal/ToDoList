package comp5216.sydney.edu.au.todolist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "todolist")
// Table name
/***
 * @auther Akash
 *
 * ToDoListItem
 */
public class ToDoListItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "toDoItemID")
    private int toDoItemID;

    @ColumnInfo(name = "toDoItemMessage")
    private String information;

    @ColumnInfo(name = "lastUpdateTime")
    private Date lastUpdateTime;

    @ColumnInfo(name = "CreationTime")
    private Date creationTime;


    public ToDoListItem(String information, Date creationTime, Date lastUpdateTime) {
        this.information = information;
        this.creationTime = creationTime;
        this.lastUpdateTime = lastUpdateTime;

    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return information + '\t' +
                "Time:" + lastUpdateTime;
    }

    @NonNull
    public int getToDoItemID() {
        return toDoItemID;
    }

    public void setToDoItemID(@NonNull int toDoItemID) {
        this.toDoItemID = toDoItemID;
    }
}
