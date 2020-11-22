package ca.oneroof.oneroof.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.oneroof.oneroof.R;
import ca.oneroof.oneroof.viewmodel.HouseViewModel;

// reference: https://guides.codepath.com/android/using-the-recyclerview
public class RoommateNameAdapter extends RecyclerView.Adapter<RoommateNameAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.roommate_name);
        }
    }

    private HouseViewModel viewmodel;
    private List<String> roommates;

    public RoommateNameAdapter() {
        // TODO: error handling if this is null???
        //roommates = viewmodel.house.data.getValue().data.roommate_names;
        roommates = new ArrayList<String>();
        roommates.add("Alyssa");
        roommates.add("Maddie");
    }

    @Override
    public RoommateNameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View roommateNameView = inflater.inflate(R.layout.item_roommate_name, parent, false);
        ViewHolder viewHolder = new ViewHolder(roommateNameView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RoommateNameAdapter.ViewHolder holder, int position) {
        String roommate = roommates.get(position);

        // if the name is the current user, don't want to display in list
        //if (roommate.equals(viewmodel.roommateName.getValue())) { return; }

        TextView textView = holder.nameTextView;
        textView.setText(roommate);
    }

    @Override
    public int getItemCount() {
        // exclude current user in count, since we won't display their name
        //return roommates.size() - 1;
        return roommates.size();
    }
}
