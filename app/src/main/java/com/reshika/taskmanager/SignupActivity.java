package com.reshika.taskmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.reshika.taskmanager.api.UsersApi;
import com.reshika.taskmanager.model.Users;
import com.reshika.taskmanager.serverresponse.SignUpResponse;
import com.reshika.taskmanager.url.Url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText etFname,etLname,etUser,etPass,etCpass;
    Button btnSignup;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ///BINDING
        imageView=findViewById(R.id.imageView);
        etFname=findViewById(R.id.etFname);
        etLname=findViewById(R.id.etLname);
        etUser=findViewById(R.id.etUser);
        etPass=findViewById(R.id.etPass);
        etCpass=findViewById(R.id.etCpass);
        btnSignup=findViewById(R.id.btnSignup);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BrowseImage();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPass.getText().toString().equals((etCpass.getText().toString())))
                {

                    signup();
                }

                else {
                    Toast.makeText(SignupActivity.this, "Password doesnot match", Toast.LENGTH_SHORT).show();
                    etPass.requestFocus();
                    return;
                }
            }
        });

    }

    private void BrowseImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            if (data==null){
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }

            Uri uri=data.getData();
            imageView.setImageURI(uri);
        }
    }

    private void signup(){
        ///declaration
        String fname= etFname.getText().toString();
        String lname= etLname.getText().toString();
        String username= etUser.getText().toString();
        String password = etPass.getText().toString();

        Users users= new Users(fname,lname,username,password);

        UsersApi userapi = Url.getInstance().create(UsersApi.class);

        Call<SignUpResponse>signUpResponseCall= userapi.registerUser(users);

        signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(SignupActivity.this, "Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

                Toast.makeText(SignupActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
