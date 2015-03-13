package br.com.hhw.startapp.activities;

import android.os.Bundle;
import br.com.hhw.startapp.R;
import br.com.hhw.startapp.activities.helpers.CustomDialogHandler;
import br.com.hhw.startapp.activities.helpers.CustomDialogHelpers;
import br.com.how.hhwslidemenu.HHWMenuActivity;

public abstract class StartAppActivity extends HHWMenuActivity {
	
	protected abstract void onCreateStartApp();

//	protected void createMenu() {
//
//		if (menuItems == null) {
//
//			TesteFragment fragment = new TesteFragment();
//
//			ArrayList<HHWMenuItem> menuItems = new ArrayList<HHWMenuItem>();
//
//			menuItems.add(new HHWMenuItem("Início", new HomeFragment(),
//					getResources().getDrawable(R.drawable.ic_launcher)));
//			menuItems.add(new HHWMenuItem("Meus pontos", fragment
//					.newInstance("Meus pontos")));
//			menuItems.add(new HHWMenuItem("Minhas rotas", fragment
//					.newInstance("Minhas rotas")));
//			menuItems.add(new HHWMenuItem("Saiba mais", fragment
//					.newInstance("Saiba mais")));
//			menuItems
//					.add(new HHWMenuItem("Como usar?", TutorialActivity.class));
//			menuItems.add(new HHWMenuItem("Configurações", fragment
//					.newInstance("Configurações")));
//			menuItems.add(new HHWMenuItem("Sobre", fragment
//					.newInstance("Sobre")));
//
//			slideMenu = new HHWSlideMenu(this, menuItems);
//		} else {
//			slideMenu = new HHWSlideMenu(this, menuItems);
//		}
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onCreateStartApp();
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

//	private void showTutorial() {
//		if (SharedPreferencesHelper.getInstance(this).hasToShowTutorial()) {
//			SharedPreferencesHelper.getInstance(this).setHasToShowTutorial(
//					false);
//			startActivity(new Intent(this, TutorialActivity.class));
//		}
//	}

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
