package org.up.fe.tdin.tdintroubletickets;

import org.up.fe.tdin.adapter.TabsPagerAdapter;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.Menu;

@SuppressLint("NewApi")
public class HomeActivity  extends FragmentActivity implements ActionBar.TabListener{
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;

	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] tabs = { getString(R.string.tab_item_assigned), getString(R.string.tab_item_unassigned)};

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			setContentView(R.layout.activity_tabs);
			// Initilization
			viewPager = (ViewPager) findViewById(R.id.pager);
			actionBar = getActionBar();
			mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

			viewPager.setAdapter(mAdapter);
			actionBar.setHomeButtonEnabled(false);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        

			// Adding Tabs
			for (String tab_name : tabs) {
				actionBar.addTab(actionBar.newTab().setText(tab_name)
						.setTabListener((TabListener) this));
			}

		}else{ 
			//add compatability with previous versions of android using tabhost
			//instead of adding tabs to actionbar
			setContentView(R.layout.activity_tabs_support);
			mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
			mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

			mTabHost.addTab(mTabHost.newTabSpec(tabs[0]).setIndicator(tabs[0]),
					AssignedTicketsFragment.class, null);
			mTabHost.addTab(mTabHost.newTabSpec(tabs[1]).setIndicator(tabs[1]),
					UnAssignedTicketsFragment.class, null);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
}
