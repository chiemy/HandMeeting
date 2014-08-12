package com.handmeeting.handmeeting.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmeeting.handmeeting.R;

public class SalonFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("salon-->onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.salon_fragment, container, false);
		return view;
	}

	@Override
	public void onDestroy() {
		System.err.println("SalonFragment:onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		System.err.println("SalonFragment:onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		System.err.println("SalonFragment:onDetach");
		super.onDetach();
	}

	@Override
	public void onPause() {
		System.err.println("SalonFragment:onPause");
		super.onPause();
	}

	@Override
	public void onStop() {
		System.err.println(this + "SalonFragment:onStop");
		super.onStop();
	}

}
