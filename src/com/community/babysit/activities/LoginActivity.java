package com.community.babysit.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.community.babysit.R;
import com.community.babysit.util.GlobalConst.GlobalConstants;
import com.community.babysit.util.http.JSonParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity implements OnClickListener {
	private EditText user, pass;
	private Button mSubmit, mRegister;

	 // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSonParser jsonParser = new JSonParser();

    private static final String LOGIN_URL = "http://" + GlobalConstants.DB_IP_ADDRESS + "/webservice/login.php";

    //JSON element ids from repsonse of php script:
    private static final String TAG_STATUS = "status";
    private static final String TAG_MESSAGE = "message";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//setup input fields
		user = (EditText)findViewById(R.id.username);
		pass = (EditText)findViewById(R.id.password);

		//setup buttons
		mSubmit = (Button)findViewById(R.id.login);
		mRegister = (Button)findViewById(R.id.register);

		//register listeners
		mSubmit.setOnClickListener(this);
		mRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
				new AttemptLogin().execute();
			break;
		case R.id.register:
				Intent i = new Intent(this, RegisterActivity.class);
				startActivity(i);
			break;

		default:
			break;
		}
	}

	class AttemptLogin extends AsyncTask<String, String, String> {

		 /**
         * Before starting background thread Show Progress Dialog
         * */
		boolean failure = false;
		String salt = "";
		String encryptedPass;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("type", "ADMIN"));
//                params.add(new BasicNameValuePair("pass_salt", salt));

                Log.d("request!", "starting");
                JSONObject json = jsonParser.makeHttpRequest(
                       LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_STATUS);
                if (success == 1) {
                	Log.d("Login Successful!", json.toString());
//                	Intent i = new Intent(LoginActivity.this, ReadComments.class);
//                	finish();
//    				startActivity(i);
//                	return json.getString(TAG_MESSAGE);
                	return json.toString();
                }else{
                	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
//                	return json.getString(TAG_MESSAGE);
                	return json.toString();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

		}
		/**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
            	Toast.makeText(LoginActivity.this, JSonParser.getMessage(file_url), Toast.LENGTH_LONG).show();
            }
            if(file_url != null && file_url.contains("logged in")) {
            	Intent intent = new Intent(LoginActivity.this, SittersPageActivity.class);
            	startActivity(intent);
            }
        }
	}
}
