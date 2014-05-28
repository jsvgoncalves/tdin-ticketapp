package org.up.fe.tdin.tdintroubletickets;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UnAssignedTicketsFragment extends android.support.v4.app.Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_home, container, false);

		return rootView;
	}
}
