package com.example.speerassessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.speerassessment.adapter.UserListAdapter;
import com.example.speerassessment.models.UserDetailModel;
import com.example.speerassessment.models.UserModel;
import com.example.speerassessment.network.FollowerFollowingRequest;
import com.example.speerassessment.network.UserDetailRequest;
import com.example.speerassessment.network.UserRequest;
import com.example.speerassessment.utils.AppUrls;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetail extends AppCompatActivity implements UserDetailRequest.OnFinished, FollowerFollowingRequest.OnFinished, UserListAdapter.OnUserClick {
    ImageView iv_userDetailImage;
    TextView tv_userDetailName;
    TextView tv_userDetailBio;
    TextView tv_userDetailFollowers;
    TextView tv_userDetailFollowings;
    RecyclerView rv_userFollowList;
    Toolbar user_detail_toolbar;
    LinearLayoutManager linearLayoutManager;
    List<UserModel> userModelList = new ArrayList<>();
    UserListAdapter userListAdapter = null;
    String followers = "";
    String following = "";
    String userLogin = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        iv_userDetailImage = findViewById(R.id.iv_userDetailImage);
        tv_userDetailName = findViewById(R.id.tv_userDetailName);
        tv_userDetailBio = findViewById(R.id.tv_userDetailBio);
        tv_userDetailFollowers = findViewById(R.id.tv_userDetailFollowers);
        tv_userDetailFollowings = findViewById(R.id.tv_userDetailFollowings);
        rv_userFollowList = findViewById(R.id.rv_userFollowList);
        user_detail_toolbar = findViewById(R.id.user_detail_toolbar);

        setSupportActionBar(user_detail_toolbar);

        linearLayoutManager = new LinearLayoutManager(this);
        rv_userFollowList.setLayoutManager(linearLayoutManager);
        rv_userFollowList.setHasFixedSize(true);

        getDataFromIntent();

        tv_userDetailFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FollowerFollowingRequest(UserDetail.this).getUsers(AppUrls.USERS + userLogin + AppUrls.FOLLOWERS);

            }
        });

        tv_userDetailFollowings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FollowerFollowingRequest(UserDetail.this).getUsers(AppUrls.USERS + userLogin + AppUrls.FOLLOWING);

            }
        });


    }

    private void getDataFromIntent() {
        if (getIntent().hasExtra("user")) {
            UserModel userModel = getIntent().getParcelableExtra("user");
            new UserDetailRequest(UserDetail.this).getUserDetails(userModel.url);
            userLogin = userModel.login;
            Glide.with(this).load(userModel.avatar_url).into(iv_userDetailImage);


        }
    }

    @Override
    public void onFinished(UserDetailModel userDetailModel) {
        followers = userDetailModel.followers;
        following = userDetailModel.following;


        tv_userDetailName.setText(userDetailModel.name);
        tv_userDetailBio.setText(userDetailModel.bio);

        tv_userDetailFollowers.setText(followers + getString(R.string.followers));
        tv_userDetailFollowings.setText(following + getString(R.string.followings));
    }

    @Override
    public void onFinished(List<UserModel> userModelList) {
        Log.v("sfsfksjdf",""+userModelList.size());
        userListAdapter = new UserListAdapter(userModelList, this, this);
        rv_userFollowList.setAdapter(userListAdapter);
    }

    @Override
    public void onUserClick(UserModel userModel) {
        startActivity(new Intent(this, UserDetail.class)
                .putExtra("user", userModel));
    }
}