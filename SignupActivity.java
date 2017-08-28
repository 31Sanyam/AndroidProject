package com.example.sanyam.ftt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sanyam on 02-08-2017.
 */
public class SignupActivity extends Activity
{
    private EditText et_name,et_pass,et_confirm_pass,et_address,et_email,et_mobileno;
    private Button btn_signup;
    private TextView tv_login;
    private DataBaseHelper databaseHelper;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

       // this.getActionBar().setDisplayHomeAsUpEnabled(true);
        databaseHelper = new DataBaseHelper(this);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
       // signupDataBaseAdapter = new SignupDataBaseAdapter(this);
        //signupDataBaseAdapter=signupDataBaseAdapter.open();

        et_name= (EditText) findViewById(R.id.input_name);
        et_address= (EditText) findViewById(R.id.input_address);
        et_mobileno= (EditText) findViewById(R.id.input_mobileno);
        et_email= (EditText) findViewById(R.id.input_email);
        et_pass= (EditText) findViewById(R.id.input_password);
        et_confirm_pass= (EditText) findViewById(R.id.input_reEnterPassword);
        tv_login= (TextView) findViewById(R.id.link_login);
        btn_signup= (Button) findViewById(R.id.btn_signup);



        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                Toast.makeText(getApplicationContext(), "Go to login class", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void signup() {
        Log.i("Sanyam", "SignupCalled");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btn_signup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        String name = et_name.getText().toString();
        String address = et_address.getText().toString();
        String email = et_email.getText().toString();
        String mobile = et_mobileno.getText().toString();
        String password = et_pass.getText().toString();
        String reEnterPassword = et_confirm_pass.getText().toString();

        if(databaseHelper.Signup(email,mobile))
        {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            onSignupFailed();
                            // onSignupFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        }
        else
        {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if(task.isSuccessful()){
                                //display some message here
                                Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_LONG).show();
                            }else{
                                //display some message here
                                Toast.makeText(getApplicationContext(),"Registration Error",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            //databaseHelper.insertEntry(name, address, mobile, reEnterPassword);

            UserInformation userInformation=new UserInformation(name,address,email,mobile);
            databaseReference.child("User Details").setValue(userInformation);

            //Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
            onSignupSuccess();
        }
    }



    public void onSignupSuccess() {
        Log.i("Sanyam","onSignupSuccesscalled");
        btn_signup.setEnabled(true);
        setResult(RESULT_OK, null);
        this.finish();
        Log.i("Sanyam", "onSignupSuccessend");
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "EmailId already exists ", Toast.LENGTH_LONG).show();

        btn_signup.setEnabled(true);
    }

    public boolean validate() {
        Log.i("Sanyam","validatecalled");
        boolean valid = true;

        String name = et_name.getText().toString();
        String address = et_address.getText().toString();
        String email = et_email.getText().toString();
        String mobile = et_mobileno.getText().toString();
        String password = et_pass.getText().toString();
        String reEnterPassword = et_confirm_pass.getText().toString();



        if (name.isEmpty() || name.length() < 3) {
            et_name.setError("at least 3 characters");
            valid = false;
        } else {
            et_name.setError(null);
        }

        if (address.isEmpty()) {
            et_address.setError("Enter Valid Address");
            valid = false;
        } else {
            et_address.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("enter a valid email address");
            valid = false;
        } else {
            et_email.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            et_mobileno.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            et_mobileno.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            et_pass.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            et_confirm_pass.setError("Password Do not match");
            valid = false;
        } else {
            et_confirm_pass.setError(null);
        }
        Log.i("Sanyam","validatend");
        return valid;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id == android.R.id.home)
        {

            //onBackPressed();
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

