package com.example.projekuas.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekuas.R;
import com.example.projekuas.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private Context context;
    private List<User> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public UserAdapter(Context context, List<User> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
//        holder.synopsis.setText(list.get(position).getSynopsis());
        holder.genre.setText(list.get(position).getGenre());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, synopsis, genre;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            genre = itemView.findViewById(R.id.genre);
            synopsis = itemView.findViewById(R.id.synopsis);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}

