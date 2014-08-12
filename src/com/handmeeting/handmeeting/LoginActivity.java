package com.handmeeting.handmeeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class LoginActivity extends Activity implements OnClickListener
{
	private Button tryBt;
	private LinearLayout loginViaTelLayout;
	private LinearLayout txLogin;
	private LinearLayout xlLogin;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginViaTelLayout = (LinearLayout) findViewById(R.id.teleLogin);
		txLogin = (LinearLayout) findViewById(R.id.txWeibo);
		xlLogin = (LinearLayout) findViewById(R.id.xlWeibo);
		tryBt = (Button) findViewById(R.id.tryUse);
		
		loginViaTelLayout.setOnClickListener(this);
		tryBt.setOnClickListener(this);
		txLogin.setOnClickListener(this);
		xlLogin.setOnClickListener(this);
	}


	@Override
	public void onClick(View v)
	{
		switch(v.getId()){
		case R.id.tryUse:
			Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent1);
			break;
		case R.id.teleLogin:
			Intent intent2 = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent2);
			break;

		}
	}

}
