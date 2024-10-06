package com.tabish.movieapp0139.home.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.tabish.movieapp0139.db.DBHelper;
import com.tabish.movieapp0139.home.MovieListActivity;
import com.tabish.movieapp0139.R;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnlogin;
    ImageButton back;
    ImageView github, facebook, insta;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        btnlogin = findViewById(R.id.btnsignin1);
        back = findViewById(R.id.back_login);
        github = findViewById(R.id.github);
        facebook = findViewById(R.id.facebook);
        insta = findViewById(R.id.insta);
        DB = new DBHelper(this);



        //////Status bar setting//////
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //////Status bar setting//////




        btnlogin.setOnClickListener(view -> {

            String user = username.getText().toString();
            String pass = password.getText().toString();

           if(username.getText().toString().equals("")||password.getText().toString().equals(""))
                Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            else{
                Boolean checkuserpass = DB.checkusernamepassword(username.getText().toString(), password.getText().toString());
                if(checkuserpass){
                    Intent intent  = new Intent(getApplicationContext(), MovieListActivity.class);
                    intent.putExtra("username_key",username.getText().toString());
                    startActivity(intent);
                    this.finish();
                    Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


        back.setOnClickListener(v -> {
            onBackPressed();
        });

        github.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://github.com/Tabish-B");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        facebook.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://m.facebook.com/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        insta.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://m.instagram.com/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

    }



    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}