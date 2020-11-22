package ca.oneroof.oneroof.ui;

import android.content.Context;
import android.util.Log;
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

    private ArrayList<String> roommates;
    private String user_name;

    public RoommateNameAdapter(ArrayList<String> roommates, String user_name) {
        // TODO: error handling if this is null???
        this.roommates = roommates;
        this.user_name = user_name;
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
        // if this is the user's name, don't display it
        if (roommate.equals(user_name)) { return; }

        TextView textView = holder.nameTextView;
        textView.setText(roommate);
    }

    @Override
    public int getItemCount() {
        // exclude current user in count, since we won't display their name
        return roommates.size() - 1;
    }
}
