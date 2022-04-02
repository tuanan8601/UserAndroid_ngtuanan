package com.example.background;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.background.model.User;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditUserActivity extends AppCompatActivity {
    TextView txt_title;
    EditText txt_name;
    EditText txt_email;
    Spinner spn_gender;
    Spinner spn_status;
    Button btn_finish;
    AdapterView.OnItemSelectedListener spinnerListener;
    int userId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);
        txt_title = findViewById(R.id.txt_title);
        txt_name = findViewById(R.id.name_text);
        txt_email = findViewById(R.id.email_text);
        spn_gender = findViewById(R.id.gender_spinner);
        spn_status = findViewById(R.id.status_spinner);
        btn_finish = findViewById(R.id.btn_finish);
        spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String spinnerLabel = parent.getItemAtPosition(position).toString();
                displayToast(spinnerLabel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spn_gender.setOnItemSelectedListener(spinnerListener);
        spn_status.setOnItemSelectedListener(spinnerListener);
        if(getIntent().getBooleanExtra("update",false)){
            txt_title.setText(R.string.updatetitle);
            btn_finish.setText(R.string.updatebtn);
            userId = getIntent().getIntExtra("userId",0);
            Log.d("An_Test","id: "+userId);
            ApiClient.getAPI().getUsersByID(userId).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    txt_name.setText(user.name);
                    txt_email.setText(user.email);
                    switch (user.gender) {
                        case "male":
                            spn_gender.setSelection(0);
                            break;
                        case "female":
                            spn_gender.setSelection(1);
                            break;
                        default:
                    }
                    switch (user.status) {
                        case "active":
                            spn_status.setSelection(0);
                            break;
                        case "inactive":
                            spn_status.setSelection(1);
                            break;
                        case "archived":
                            spn_status.setSelection(2);
                            break;
                        default:
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });
        }
        else {
            txt_title.setText(R.string.addtitle);
            btn_finish.setText(R.string.addbtn);
        }
    }

    public void finish(View view) {
        User user = new User();
        user.name = txt_name.getText().toString();
        user.email = txt_email.getText().toString();
        user.gender = spn_gender.getSelectedItem().toString();
        user.status = spn_status.getSelectedItem().toString();
        if(getIntent().getBooleanExtra("update",false)){
            ApiClient.getAPI().updateUserByID(userId,user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User resuser = response.body();
//                    displayToast("User ID updated: " + resuser.id);
                    Intent returnIntent = new Intent(AddEditUserActivity.this,UserDetailActivity.class);
                    returnIntent.putExtra("userId",userId);
                    displayToast("Update success");
                    startActivity(returnIntent);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });
        }
        else{
            ApiClient.getAPI().addUser(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User resuser = response.body();
//                    displayToast("User ID added: " + resuser.id);
                    Intent returnIntent = new Intent(AddEditUserActivity.this,UserDetailActivity.class);
                    returnIntent.putExtra("userId",resuser.id);
                    displayToast("Add success");
                    startActivity(returnIntent);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}