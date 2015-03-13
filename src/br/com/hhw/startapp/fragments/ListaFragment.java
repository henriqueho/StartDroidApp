package br.com.hhw.startapp.fragments;import java.io.ByteArrayOutputStream;import java.util.ArrayList;import java.util.List;import android.annotation.SuppressLint;import android.app.Activity;import android.content.Context;import android.graphics.Bitmap;import android.os.Bundle;import android.support.v4.app.Fragment;import android.support.v4.widget.SwipeRefreshLayout;import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.view.ViewStub;import android.widget.ArrayAdapter;import android.widget.ImageView;import android.widget.ListView;import android.widget.TextView;import br.com.hhw.startapp.R;import br.com.hhw.startapp.handlers.DestaqueModelHandler;import br.com.hhw.startapp.helpers.ServiceRestClientHelper;import br.com.hhw.startapp.helpers.TryAgainHelper;import br.com.hhw.startapp.helpers.TryAgainHelper.OnClickToTryAgain;import br.com.hhw.startapp.models.DestaqueModel;import br.com.hhw.startapp.models.ws.StartAppServices;import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;import com.loopj.android.http.AsyncHttpClient;import com.nostra13.universalimageloader.core.ImageLoader;public class ListaFragment extends Fragment implements OnRefreshListener,		OnClickToTryAgain {	AsyncHttpClient client = new AsyncHttpClient();	private Activity ownerActivity;	private ListView listView;	private ArrayList<DestaqueModel> destaques = new ArrayList<DestaqueModel>();	private SwipeRefreshLayout swipeLayout;	private ViewStub internetFailureViewStub;	@Override	public View onCreateView(LayoutInflater inflater, ViewGroup container,			Bundle savedInstanceState) {		setHasOptionsMenu(true);		View contentView = inflater.inflate(R.layout.fragment_destaque,				container, false);		setSwipeRefresh(contentView);		setStubViewsError(contentView);		listView = (ListView) contentView.findViewById(R.id.pacotes_listview);		loadDestaques();		return contentView;	}	@SuppressWarnings("deprecation")	private void setSwipeRefresh(View contentView) {		swipeLayout = (SwipeRefreshLayout) contentView				.findViewById(R.id.swipe_container);		swipeLayout.setOnRefreshListener((OnRefreshListener) this);		swipeLayout.setColorScheme(R.color.holo_blue_light,				R.color.holo_green_light, R.color.holo_orange_light,				R.color.holo_red_light);	}	private void setStubViewsError(View contentView) {		internetFailureViewStub = (ViewStub) contentView				.findViewById(R.id.pacotes_internet_failure);		internetFailureViewStub				.setLayoutResource(R.layout.error_internet_start_app);		((TryAgainHelper) internetFailureViewStub.inflate())				.setOnClickAndMessage(this, getString(R.string.error_pacotes));	}	private void loadDestaques() {		swipeLayout.setEnabled(false);		swipeLayout.setRefreshing(true);		swipeLayout.setVisibility(View.VISIBLE);		StartAppServices.getDestaques(new DestaqueModelHandler() {			@Override			public void setDestaques(ArrayList<DestaqueModel> destaquesHandler) {				destaques = destaquesHandler;				showDestaques();				swipeLayout.setRefreshing(false);				swipeLayout.setEnabled(true);			}			@Override			public void setErro(Throwable e) {				showError();			}		});	}	private void showError() {		swipeLayout.setRefreshing(false);		swipeLayout.setVisibility(View.GONE);		internetFailureViewStub.setVisibility(View.VISIBLE);	}	private void showDestaques() {		ArrayAdapter<DestaqueModel> adapter = new DestaquesAdapter(				ownerActivity, destaques);		listView.setAdapter(adapter);	}	@Override	public void onAttach(Activity activity) {		super.onAttach(activity);		ownerActivity = activity;	}	@Override	public void onDestroy() {		super.onDestroy();		ServiceRestClientHelper.cancelRequests(ownerActivity);	}	@Override	public void onRefresh() {		loadDestaques();	}	@Override	public void tryAgain() {		loadDestaques();	}	public class DestaquesAdapter extends ArrayAdapter<DestaqueModel> {		private List<DestaqueModel> destaques;		private Context context;		public DestaquesAdapter(Context context, List<DestaqueModel> objects) {			super(context, 0, objects);			this.destaques = objects;			this.context = context;		}		@Override		public int getCount() {			return destaques.size();		}		@SuppressLint("InflateParams")		@Override		public View getView(int position, View convertView, ViewGroup parent) {			View v = convertView;			final CompleteListViewHolder viewHolder;			DestaqueModel currentPacote = destaques.get(position);			if (convertView == null) {				LayoutInflater li = (LayoutInflater) getContext()						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);				v = li.inflate(R.layout.adapter_lista_simples, null);				viewHolder = new CompleteListViewHolder(v);				v.setTag(viewHolder);				v.setOnClickListener(new View.OnClickListener() {					@Override					public void onClick(View view) {						// CompleteListViewHolder viewCompleteListViewHolder =						// (CompleteListViewHolder) view						// .getTag();						// DestaqueModel destaque = new DestaqueModel();						// destaque.byteArrayImage =						// viewCompleteListViewHolder.image;						// destaque.idPacote =						// viewCompleteListViewHolder.idPacote;						// Intent intent = new Intent(context,						// PacoteActivity.class);						// Bundle extra = new Bundle();						// extra.putSerializable(PacoteActivity.BUNDLE_PACOTE,						// pacote);						// intent.putExtra(PacoteActivity.EXTRA_PACOTE, extra);						// context.startActivity(intent);					}				});			} else {				viewHolder = (CompleteListViewHolder) v.getTag();			}			viewHolder.titulo.setText(currentPacote.titulo);			viewHolder.idPacote = currentPacote.idPacote;			ImageLoader.getInstance().loadImage(currentPacote.urlImagem,					new SimpleImageLoadingListener() {						@Override						public void onLoadingComplete(String imageUri,								View view, Bitmap loadedImage) {							viewHolder.imageview.setImageBitmap(loadedImage);							ByteArrayOutputStream stream = new ByteArrayOutputStream();							loadedImage.compress(Bitmap.CompressFormat.PNG,									100, stream);							viewHolder.image = stream.toByteArray();						}					});			return v;		}		class CompleteListViewHolder {			public int idPacote;			public TextView titulo;			public ImageView imageview;			public byte[] image;			public CompleteListViewHolder(View base) {				titulo = (TextView) base						.findViewById(R.id.adapter_pacote_titulo_textview);				imageview = (ImageView) base						.findViewById(R.id.adapter_pacote_imageview);			}		}	}}