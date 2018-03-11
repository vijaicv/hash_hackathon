package com.example.vijaicv.youcuredme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    public static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;
    private Button signin;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        queue = Volley.newRequestQueue(login.this);
        intialise();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }

    private void intialise() {
        signin = (Button) findViewById(R.id.sign_in_button);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String mail = account.getEmail();
            Toast.makeText(login.this, "logging in as : " + mail, Toast.LENGTH_LONG).show();
            Request(mail);
        } catch (ApiException e) {
            Log.w("err", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void Request(final String mail) {
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.GET, getString(R.string.serverurl) + "/login?mail="+mail, null, new Response.Listener<JSONObject>() {
            private SharedPreferences sp;

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Boolean stat=response.getBoolean("stat");
                    Log.d("response", "onResponse: "+response);
                    if(stat==true){
                        sp = getSharedPreferences("communities", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("mail",mail);
                        editor.commit();
                        Intent intent = new Intent(login.this, usertype.class);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("responseerr", "onErrorResponse: "+error);
            }
        });

        queue.add(jsonreq);
    }

}