package com.handmeeting.handmeeting.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmeeting.handmeeting.R;

public class TopFragment extends Fragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		System.out.println("top-->onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		return inflater.inflate(R.layout.fragment_top, container, false);
	}
	
	
}
