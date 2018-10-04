package comp5216.sydney.edu.au.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/***
 * @author Akash
 *
 *  To do item adapter
 */
public class ToDoItemArrayAdapter extends ArrayAdapter<ToDoListItem> {

    private Context mContext;
    private List<ToDoListItem> toDoItemsList = new ArrayList<>();

    public ToDoItemArrayAdapter(@NonNull Context context, ArrayList<ToDoListItem> list) {
        super(context, 0, list);
        mContext = context;
        toDoItemsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, parent, false);

        ToDoListItem currentToDoItem = toDoItemsList.get(position);

        TextView item = (TextView) listItem.findViewById(R.id.txtItem);
        item.setText(currentToDoItem.getInformation());
        TextView creationDate = (TextView) listItem.findViewById(R.id.creationDate);
        // convert Date to String and print on UI
        creationDate.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(currentToDoItem.getCreationTime()));
        if (currentToDoItem.getLastUpdateTime() != null) {
            TextView lastUpdateDate = (TextView) listItem.findViewById(R.id.lastUpDate);
            // convert Date to String and print on UI
            lastUpdateDate.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(currentToDoItem.getLastUpdateTime()));
        }
        return listItem;
    }
}