package com.example.speerassessment.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.speerassessment.models.UserDetailModel;
import com.example.speerassessment.models.UserModel;
import com.example.speerassessment.singleton.AppController;
import com.example.speerassessment.utils.AppUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserDetailRequest {

    UserDetailModel userDetailModel;

    private OnFinished onFinished;

    public interface OnFinished {
        void onFinished(UserDetailModel userDetailModel);
    }

    public UserDetailRequest(OnFinished onFinished) {
        this.onFinished = onFinished;

    }

    public UserDetailModel getUserDetails(String url) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject object = new JSONObject(response);


                    userDetailModel = new UserDetailModel();
                    userDetailModel.name = object.getString("name");
                    userDetailModel.company = object.getString("company");
                    userDetailModel.blog = object.getString("blog");
                    userDetailModel.location = object.getString("location");
                    userDetailModel.email = object.getString("email");
                    userDetailModel.hireable = object.getString("hireable");
                    userDetailModel.bio = object.getString("bio");
                    userDetailModel.twitter_username = object.getString("twitter_username");
                    userDetailModel.public_repos = object.getString("public_repos");
                    userDetailModel.public_gists = object.getString("public_gists");
                    userDetailModel.followers = object.getString("followers");
                    userDetailModel.following = object.getString("following");

                    onFinished.onFinished(userDetailModel);


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
        return userDetailModel;

    }

}
