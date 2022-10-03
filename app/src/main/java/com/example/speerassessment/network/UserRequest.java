package com.example.speerassessment.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.speerassessment.models.UserModel;
import com.example.speerassessment.singleton.AppController;
import com.example.speerassessment.utils.AppUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserRequest {
    private final OnFinished onFinished;

    public interface OnFinished {
        void onFinished(List<UserModel> userModelList);
    }

    public UserRequest(OnFinished onFinished) {
        this.onFinished = onFinished;

    }

    public List<UserModel> getUsers(String searchUser) {

        List<UserModel> userModelList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUrls.SEARCH_USERS + searchUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject object = new JSONObject(response);
                    int itemCount = object.getInt("total_count");


                    JSONArray users = object.getJSONArray("items");

                    for (int i = 0; i < users.length(); i++) {
                        JSONObject userObj = users.getJSONObject(i);
                        UserModel userModel = new UserModel();
                        userModel.login = userObj.getString("login");
                        userModel.id = userObj.getString("id");
                        userModel.node_id = userObj.getString("node_id");
                        userModel.avatar_url = userObj.getString("avatar_url");
                        userModel.gravatar_id = userObj.getString("gravatar_id");
                        userModel.url = userObj.getString("url");
                        userModel.html_url = userObj.getString("html_url");
                        userModel.followers_url = userObj.getString("followers_url");
                        userModel.following_url = userObj.getString("following_url");
                        userModel.gists_url = userObj.getString("gists_url");
                        userModel.starred_url = userObj.getString("starred_url");
                        userModel.subscriptions_url = userObj.getString("subscriptions_url");
                        userModel.organizations_url = userObj.getString("organizations_url");
                        userModel.repos_url = userObj.getString("repos_url");
                        userModel.events_url = userObj.getString("events_url");
                        userModel.received_events_url = userObj.getString("received_events_url");
                        userModel.type = userObj.getString("type");
                        userModel.site_admin = userObj.getString("site_admin");
                        userModel.score = userObj.getString("score");

                        userModelList.add(userModel);

                    }
                    onFinished.onFinished(userModelList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);

        return userModelList;
    }
}
