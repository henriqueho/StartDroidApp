package br.com.hhw.startapp.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import br.com.hhw.startapp.R;
import br.com.hhw.startapp.activities.helpers.CustomDialogHandler;
import br.com.hhw.startapp.activities.helpers.CustomDialogHelpers;
import br.com.how.HHWSlideMenu;

public class MainActivity extends ActionBarActivity {

	private HHWSlideMenu slideMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_menu);
		
		hackyToResolveBugToShowActionBar();
        
		slideMenu = new HHWSlideMenu(this);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		// Abrindo home no slidemenu
		if (savedInstanceState == null) {
			slideMenu.openItemMenu(slideMenu.STARTPAGE);
		}
	}
	
	@Override
	public void onBackPressed() {
		slideMenu.mDrawerLayout.closeDrawers();
		if (slideMenu.getStartFragment().isVisible()) {
			showDialogToFinish();
		} else {
			super.onBackPressed();
		}
	}

	// @Override
	// public boolean onPrepareOptionsMenu(Menu menu) {
	// @SuppressWarnings("unused")
	// boolean drawerOpen = slideMenu.isDrawerLayoutOpen();
	// return super.onPrepareOptionsMenu(menu);
	// }

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		slideMenu.mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		slideMenu.mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (slideMenu.mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void hackyToResolveBugToShowActionBar() {
		RelativeLayout hacky = (RelativeLayout) findViewById(R.id.drawer_content);
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        hacky.setVisibility(View.GONE);
        hacky.setVisibility(View.VISIBLE);
	}
	
	private void showDialogToFinish() {
		String text = getString(R.string.voce_realmente_deseja_sair_do_aplicativo_);
		CustomDialogHelpers.showGenericDialog(this, text, new CustomDialogHandler() {
			@Override
			public void setOk() {
				finish();
			}	
		});
	}
}
