package com.qust.assistant.ui.fragment.school;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;

import com.google.android.material.textfield.TextInputLayout;
import com.qust.assistant.App;
import com.qust.assistant.R;
import com.qust.assistant.ui.MainActivity;

public class LoginFragment extends BaseSchoolFragment{
	
	private TextInputLayout nameText;
	
	private TextInputLayout passwordText;
	
	public LoginFragment(MainActivity activity){
		super(activity);
	}
	
	@Override
	protected void initLayout(LayoutInflater inflater){
		super.initLayout(inflater);
		
		SharedPreferences data = activity.getSharedPreferences("education", Context.MODE_PRIVATE);
		
		nameText = findViewById(R.id.fragment_login_name);
		passwordText = findViewById(R.id.fragment_login_password);
		
		nameText.getEditText().setText(data.getString("user",""));
		passwordText.getEditText().setText(data.getString("password",""));
		
		findViewById(R.id.fragment_school_query).setOnClickListener(v -> {
			String user = nameText.getEditText().getText().toString();
			String password = passwordText.getEditText().getText().toString();
			new Thread(){
				@Override
				public void run(){
					String errorMsg = loginUtil.login(handler, user, password);
					if(errorMsg == null){
						SharedPreferences.Editor editor = data.edit();
						editor.putString("user", user);
						editor.putString("password", password);
						editor.apply();
						activity.runOnUiThread(() -> {
							dialog.dismiss();
							activity.sendBroadcast(new Intent(App.APP_USER_LOGIN));
							toast("登陆成功！");
							finish();
						});
					}else{
						sendMessage(App.DISMISS_TOAST, errorMsg);
					}
				}
			}.start();
			dialog.show();
		});
	}
	
	@Override
	protected void doQuery(String JSESSIONID){}
	
	@Override
	protected int getLayoutId(){ return R.layout.fragment_login; }
	
	@Override
	public String getName(){
		return "教务系统登陆";
	}
	
}