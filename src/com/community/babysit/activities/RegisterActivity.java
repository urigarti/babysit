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

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends ActionBarActivity implements
		OnClickListener {

	private EditText user, pass;
	private Button mRegister;

	

	// JSON parser class
	JSonParser jsonParser = new JSonParser();

	// php login script

	// localhost :
	// testing on your device
	// put your local ip instead, on windows, run CMD > ipconfig
	// or in mac's terminal type ifconfig and look for the ip under en0 or en1
	// private static final String LOGIN_URL =
	// "http://xxx.xxx.x.x:1234/webservice/register.php";

	// testing on Emulator:
	private static final String REGISTER_URL = "http://" + GlobalConstants.DB_IP_ADDRESS+ "/webservice/register.php";

	// testing from a real server:
	// private static final String LOGIN_URL =
	// "http://www.yourdomain.com/webservice/register.php";

	// ids
	private static final String TAG_STATUS = "status";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		user = (EditText) findViewById(R.id.username);
		pass = (EditText) findViewById(R.id.password);

		mRegister = (Button) findViewById(R.id.register);
		mRegister.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		new CreateUser().execute();
	}

	class CreateUser extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;
		// Progress Dialog
		private ProgressDialog pDialog;
		private String encryptedPassword = "";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RegisterActivity.this);
			pDialog.setMessage("Creating User...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int status;
			String username = user.getText().toString();
			String password = pass.getText().toString();
//			String salt = "";
//			try {
//				salt = PasswordEncryptionService.generateSalt().toString();
//				encryptedPassword = PasswordEncryptionService.getEncryptedPassword(password, salt.getBytes()).toString();
//			} catch (NoSuchAlgorithmException e2) {
//			} catch (InvalidKeySpecException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
//				params.add(new BasicNameValuePair("pass_salt", salt));
				params.add(new BasicNameValuePair("type", "ADMIN"));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST",
						params);
				if(json == null) {
					return null;
				}

				// full json response
				Log.d("Register attempt", json.toString());

				// json success element
				status = json.getInt(TAG_STATUS);
				if (status == 1) {
					Log.d("User Created!", json.toString());
					finish();
					return json.toString();
				} else {
					Log.d("Registeration Failure!", json.getString(TAG_MESSAGE));
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
			if (file_url != null) {
				Toast.makeText(RegisterActivity.this, JSonParser.getMessage(file_url),
						Toast.LENGTH_LONG).show();
			}
		}
	}
}