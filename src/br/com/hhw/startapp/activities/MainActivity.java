package br.com.hhw.startapp.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
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
import br.com.hhw.startapp.activities.helpers.SharedPreferencesHelper;
import br.com.hhw.startapp.fragments.HomeFragment;
import br.com.hhw.startapp.fragments.TesteFragment;
import br.com.how.DefaultFragment;
import br.com.how.HHWMenuItem;
import br.com.how.HHWSlideMenu;

public class MainActivity extends ActionBarActivity {

	private HHWSlideMenu slideMenu;

	private void createMenu() {
		
		TesteFragment fragment = new TesteFragment();
		
		
		ArrayList<HHWMenuItem> menuItems = new ArrayList<HHWMenuItem>();

		menuItems.add(new HHWMenuItem("Início", new HomeFragment(), getResources().getDrawable(R.drawable.abc_btn_check_material)));
		menuItems.add(new HHWMenuItem("Meus pontos", fragment.newInstance("Meus pontos")));
		menuItems.add(new HHWMenuItem("Minhas rotas", fragment.newInstance("Minhas rotas")));
		menuItems.add(new HHWMenuItem("Saiba mais", fragment.newInstance("Saiba mais")));
		menuItems.add(new HHWMenuItem("Como usar?", TutorialActivity.class));
		menuItems.add(new HHWMenuItem("Configurações", fragment.newInstance("Configurações")));
		menuItems.add(new HHWMenuItem("Sobre", fragment.newInstance("Sobre")));
		
		slideMenu = new HHWSlideMenu(this, menuItems);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_menu);

		hackyToResolveBugToShowActionBar();

		createMenu();

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		showTutorial();

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

	private void showTutorial() {
		if (SharedPreferencesHelper.getInstance(this).hasToShowTutorial()) {
			SharedPreferencesHelper.getInstance(this).setHasToShowTutorial(
					false);
			startActivity(new Intent(this, TutorialActivity.class));
		}
	}

	private void showDialogToFinish() {
		String text = getString(R.string.voce_realmente_deseja_sair_do_aplicativo_);
		CustomDialogHelpers.showGenericDialog(this, text,
				new CustomDialogHandler() {
					@Override
					public void setOk() {
						finish();
					}
				});
	}
}
