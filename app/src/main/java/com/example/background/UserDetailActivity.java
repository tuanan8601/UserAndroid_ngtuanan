package com.example.background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.background.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {
    TextView mTextView;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        mTextView = findViewById(R.id.textView);
        userId = getIntent().getIntExtra("userId",0);
        //GETbyId
        ApiClient.getAPI().getUsersByID(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                mTextView.setText("Name: "+user.name+
                        "\n"+"Email: "+user.email+
                        "\n"+"Gender: "+user.gender+
                        "\n"+"Status: "+user.status);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mTextView.setText("Error:" + t.getMessage());
            }
        });
    }

    public void deleteUser(View view) {
        ApiClient.getAPI().deleteUsersByID(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void updateUser(View view) {
        Intent intent = new Intent(this,AddEditUserActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("update",true);
        startActivity(intent);
    }
}