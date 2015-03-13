package br.com.hhw.startapp.helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * 
 * 
 * 
 */
public class TryAgainHelper extends RelativeLayout implements OnClickListener {

	private Context context;
	
	private int idButton;

	private OnClickToTryAgain onClickToTryAgain;

	public interface OnClickToTryAgain {
		void tryAgain();
	}

	/*
	 * -------------- Default constructors
	 */
	public TryAgainHelper(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public TryAgainHelper(Context context,int idButton) {
		super(context);
		this.context = context;
		this.idButton = idButton;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		init(context, idButton);
	}

	private void init(Context context, int idButton) {
		Button tryRecoverButton = (Button) this
				.findViewById(idButton);

		tryRecoverButton.setOnClickListener(this);
	}

	public void setOnClickToTryAgain(OnClickToTryAgain onClickToTryAgain) {
		this.onClickToTryAgain = onClickToTryAgain;
	}

	@Override
	public void onClick(View v) {
		if (onClickToTryAgain != null) {
			onClickToTryAgain.tryAgain();
		}
	}
}