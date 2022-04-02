package com.example.background;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.background.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

//    private TextView mTextView;
//    private Handler mHandler;
    private ProgressBar progressBar;
    private ProgressBar progressBar2;
    private Button button;
    private RecyclerView mRecyclerView;
    private UserListAdapter mAdapter;
    private FloatingActionButton addbtn;
    ArrayList<User> mUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mTextView = findViewById(R.id.textView1);
//        mHandler = new Handler();
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        button = findViewById(R.id.button);
        mRecyclerView = findViewById(R.id.recyclerview);
        addbtn = findViewById(R.id.floatingActionButton);
//        addbtn.setVisibility(View.INVISIBLE);
        mAdapter = new UserListAdapter(this, mUserList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getUsers();
    }

    public void startTask(View view) {
//        mTextView.setText(R.string.loading);
//        progressBar.setVisibility(View.VISIBLE);
//        progressBar2.setVisibility(View.VISIBLE);
//        button.setVisibility(View.INVISIBLE);
//        addbtn.setVisibility(View.VISIBLE);

//        new Thread(()->{
//            try {
//                URL url = new URL("https://gorest.co.in/public/v2/users");
//                HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//                while ((inputLine = in.readLine())!=null){
//                    response.append(inputLine);
//                }
//                in.close();
//                mHandler.post(()->{
//                    button.setVisibility(View.VISIBLE);
//                    mTextView.setText("Done: " + response + " ms");
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }).start();

//GET
//        ApiClient.getAPI().getAllUsers().enqueue(new Callback<ArrayList<User>>() {
//            @Override
//            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
//                ArrayList<User> userList = response.body();
//                mTextView.setText("Number of Users: " + userList.size());
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
//                mTextView.setText("Error:" + t.getMessage());
//            }
//        });

//GETbyId
//        ApiClient.getAPI().getUsersByID(2924).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user = response.body();
//                mTextView.setText(user.name);
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                mTextView.setText("Error:" + t.getMessage());
//            }
//        });

//POST
//        User user = new User();
//        user.name = "NuTuan";
//        user.email = "a222@test.com";
//        user.gender = "male";
//        user.status = "Active";
//        ApiClient.getAPI().addUser(user).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user = response.body();
//                mTextView.setText("User ID: " + user.id);
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                mTextView.setText("Error:" + t.getMessage());
//            }
//        });
    }

    public void getUsers(){
        ApiClient.getAPI().getAllUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                mUserList.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }

    public void addUser(View view) {
        Intent intent = new Intent(this,AddEditUserActivity.class);
        startActivity(intent);
    }
}