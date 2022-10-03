package com.example.speerassessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.speerassessment.adapter.UserListAdapter;
import com.example.speerassessment.models.UserModel;
import com.example.speerassessment.network.UserRequest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserRequest.OnFinished, UserListAdapter.OnUserClick {

    private RecyclerView rv_userLists;
    private LinearLayoutManager linearLayoutManager;
    private List<UserModel> userModelList = new ArrayList<>();
    private UserListAdapter userListAdapter = null;
    private Toolbar main_toolbar;
    private Button search_button;


    private EditText et_searchUsers;
    private TextView tv_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_userLists = findViewById(R.id.rv_userLists);
        main_toolbar = findViewById(R.id.main_toolbar);
        et_searchUsers = findViewById(R.id.et_searchUsers);
        tv_results = findViewById(R.id.tv_results);
        tv_results = findViewById(R.id.tv_results);
        search_button = findViewById(R.id.search_button);

        setSupportActionBar(main_toolbar);

        linearLayoutManager = new LinearLayoutManager(this);
        rv_userLists.setLayoutManager(linearLayoutManager);
        rv_userLists.setHasFixedSize(true);



        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = et_searchUsers.getText().toString();

                if (getText.length()>0){
                    new UserRequest(MainActivity.this::onFinished).getUsers(getText);
                }
                else {
                    et_searchUsers.setError(getString(R.string.et_error_msg));
                }
            }
        });




    }

    @Override
    public void onFinished(List<UserModel> userModelList) {
        Log.v("CheckResponses", userModelList.size() + "");

        if (userModelList.size() == 0) {

            tv_results.setText(R.string.no_results);
        } else {
            tv_results.setText(R.string.top_results);
        }
        userListAdapter = new UserListAdapter(userModelList, this, this);
        rv_userLists.setAdapter(userListAdapter);
    }

    @Override
    public void onUserClick(UserModel userModel) {
        startActivity(new Intent(this, UserDetail.class)
                .putExtra("user", userModel));
    }
}