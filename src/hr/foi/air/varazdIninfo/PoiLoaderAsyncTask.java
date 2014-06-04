package hr.foi.air.varazdIninfo;

import java.util.List;

import hr.foi.air.interfaces.IPoi;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * This class is used for separating UI task
 * from loading webservices task
 *
 */
public class PoiLoaderAsyncTask extends AsyncTask<Object, Void, MapItemOverlay>{
	private MapView mapView;
	private ProgressDialog dialog;
	@Override
	protected MapItemOverlay doInBackground(Object... params) {
		IPoi poiLoader = (IPoi) params[0];
		MapItemOverlay itemizedOverlay = (MapItemOverlay) params[1];
		mapView = (MapView) params[2];
		dialog = (ProgressDialog) params[3];
		poiLoader.loadOverlay(itemizedOverlay);
		return itemizedOverlay;
	}
	
	@Override
	protected void onPostExecute(MapItemOverlay result) {
		List<Overlay> mapOverlays = mapView.getOverlays();
		mapOverlays.clear();
		mapOverlays.add(result);
		mapView.invalidate();
		dialog.dismiss();
	}
}
