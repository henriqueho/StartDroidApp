package br.com.hhw.startapp.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import br.com.hhw.startapp.R;
import br.com.hhw.startapp.activities.helpers.CustomDialogHandler;
import br.com.hhw.startapp.activities.helpers.CustomDialogHelpers;
import br.com.hhw.startapp.activities.helpers.SharedPreferencesHelper;
import br.com.hhw.startapp.fragments.HomeFragment;
import br.com.hhw.startapp.fragments.TesteFragment;
import br.com.how.hhwslidemenu.HHWMenuItem;
import br.com.how.hhwslidemenu.HHWSlideMenu;

public class MainStartAppActivity extends MenuStartAppActivity {
	
	protected void createMenu() {

		if (menuItems == null) {

			TesteFragment fragment = new TesteFragment();

			ArrayList<HHWMenuItem> menuItems = new ArrayList<HHWMenuItem>();

			menuItems.add(new HHWMenuItem("Início", new HomeFragment(),
					getResources().getDrawable(R.drawable.ic_launcher)));
			menuItems.add(new HHWMenuItem("Tela 2", fragment
					.newInstance("Tela - 2")));
			menuItems.add(new HHWMenuItem("Tela 3", fragment
					.newInstance("Tela - 3")));
			menuItems.add(new HHWMenuItem("Tela 4", fragment
					.newInstance("Tela - 4")));
			menuItems
					.add(new HHWMenuItem("Tutorial", TutorialStartAppActivity.class));

			slideMenu = new HHWSlideMenu(this, menuItems);
		} else {
			slideMenu = new HHWSlideMenu(this, menuItems);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showTutorial();
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

	private void showTutorial() {
		if (SharedPreferencesHelper.getInstance(this).hasToShowTutorial()) {
			SharedPreferencesHelper.getInstance(this).setHasToShowTutorial(
					false);
			startActivity(new Intent(this, TutorialStartAppActivity.class));
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

	@Override
	protected void onCreateStartApp() {
		// TODO Auto-generated method stub
		
	}
}
