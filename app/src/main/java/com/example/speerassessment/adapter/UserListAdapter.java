package com.example.speerassessment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.speerassessment.R;
import com.example.speerassessment.models.UserModel;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private List<UserModel> userModelList;
    private Context context;
    private OnUserClick onUserClick;

    public interface OnUserClick {
        void onUserClick(UserModel userModel);

    }


    public UserListAdapter(List<UserModel> userModelList, Context context, OnUserClick onUserClick) {
        this.userModelList = userModelList;
        this.context = context;
        this.onUserClick = onUserClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.MyViewHolder holder, int position) {
        UserModel userModel = userModelList.get(position);

        holder.userName.setText(userModel.login);

        Glide.with(context).load(userModel.avatar_url).into(holder.userImage);

        holder.cv_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClick.onUserClick(userModel);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void notifyDataChange(List<UserModel> movieModelList1) {
        this.userModelList = movieModelList1;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        ImageView userImage;
        CardView cv_users;


        public MyViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            userImage = itemView.findViewById(R.id.userImage);
            cv_users = itemView.findViewById(R.id.cv_users);


        }

    }

}
