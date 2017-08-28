package com.example.sanyam.ftt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Sanyam on 02-08-2017.
 */
public class LoginActivity extends AppCompatActivity
{
    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;

    private FirebaseAuth auth;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final int REQUEST_SIGNUP = 0;

    TextView tv_signup,tv_login;
    EditText et_email;
    EditText et_pass;
    TextView tv_reset_password;
    Button btn_login;

    private CheckBox saveLoginCheckBox;
    private SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor ;
    private DataBaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //this.getActionBar().setDisplayHomeAsUpEnabled(true);


        databaseHelper = new DataBaseHelper(this);
        saveLoginCheckBox= (CheckBox) findViewById(R.id.remember_me);
        tv_reset_password= (TextView) findViewById(R.id.reset_password);
        et_email= (EditText) findViewById(R.id.input_email_login);
        et_pass= (EditText) findViewById(R.id.input_password_login);
        btn_login=(Button)findViewById(R.id.btn_login);
        tv_signup=(TextView)findViewById(R.id.link_signup);
        tv_login= (TextView) findViewById(R.id.tv_login);

        sharedpreferences = getSharedPreferences(null, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        Intent intent=getIntent();
        String strTemp=intent.getStringExtra("tempString");
        tv_login.setText(strTemp);

        auth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        tv_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Sanyam","resetCalled");
                resetPassword();
                //Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                //startActivity(intent);
            }
        });
    }

    public void login() {
        Log.i("Sanyam", "Login");
        if (!validate()) {
            onLoginFailed();
            return;
        }

        btn_login.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = et_email.getText().toString();
        String password = et_pass.getText().toString();

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                      //  progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful())
                            {
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                Log.i("Sanyam","called1");
                                                // On complete call either onLoginSuccess or onLoginFailed
                                                onLoginFailed();
                                                // onLoginFailed();
                                                progressDialog.dismiss();
                                                Log.i("Sanyam", "end1");
                                            }
                                        }, 3000);
                            }
                        else {
                            onLoginSuccess();
                        }
                    }
                });
        if (saveLoginCheckBox.isChecked()) {
            savePreferences();
        }
        else {
            editor.clear();
            editor.commit();
        }

    }



   private void resetPassword() {
        Log.i("Sanyam","reserPasswordStarted");
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_reset_password, null);
        dialogBuilder.setView(dialogView);

        final EditText editEmail = (EditText) dialogView.findViewById(R.id.email);
        final Button btnReset = (Button) dialogView.findViewById(R.id.btn_reset_password);
       final Button btnBack = (Button) dialogView.findViewById(R.id.btn_back);
        final ProgressBar progressBar1 = (ProgressBar) dialogView.findViewById(R.id.progressBar);
        //dialogBuilder.setTitle("Send Photos");
        final AlertDialog dialog = dialogBuilder.create();

        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar1.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar1.setVisibility(View.GONE);
                                dialog.dismiss();
                            }
                        });

            }
        });
        dialog.show();
       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Log.i("Sanyam","onActivityResultCalled");
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                Log.i("Sanyam", "onActivityResultEnd");
            }
        }
    }
    public void onLoginSuccess() {
        btn_login.setEnabled(true);

        Intent startStudentMain=new Intent(LoginActivity.this, StudentActivity.class);
        startActivity(startStudentMain);
        Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show();
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btn_login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
    Log.i("Sanyam","validate called");
        String name = et_email.getText().toString();
        String password = et_pass.getText().toString();

        if (name.isEmpty()) {
            et_email.setError("enter a name");
            valid = false;
        } else {
            et_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            et_pass.setError(null);
        }
        Log.i("Sanyam", "validate End");
        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        //savePreferences();

    }
    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }
    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = et_email.getText().toString();
        PasswordValue = et_pass.getText().toString();
        System.out.println("onPause save name: " + UnameValue);
        System.out.println("onPause save password: " + PasswordValue);
        editor.putString(PREF_UNAME, UnameValue);
        editor.putString(PREF_PASSWORD, PasswordValue);
        editor.commit();
    }
    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        PasswordValue = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
        et_email.setText(UnameValue);
        et_pass.setText(PasswordValue);
        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }
}
