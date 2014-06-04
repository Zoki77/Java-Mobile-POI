package hr.foi.air.varazdIninfo;

import java.util.List;

import hr.foi.air.types.Helper;
import hr.foi.air.varazdIninfo.MapItemOverlay;
import hr.foi.air.interfaces.IPoi;
import hr.foi.air.plugins.poi_json.JsonPoiLoader;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Activity that shows map on the screen
 *
 */
public class MapActivity extends com.google.android.maps.MapActivity implements LocationListener{
	
	private GeoPoint geoPoint;
	private MapView mapView;
	LocationManager locationManager;
	private boolean flag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		flag = true;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //<5>
	    if (location != null) {
	      this.onLocationChanged(location); 
	    }
		IPoi poiLoader = new JsonPoiLoader(this);
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.poi_marker);
		MapItemOverlay poiOverlay = new MapItemOverlay(drawable, this);
		ProgressDialog dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true, true);
		PoiLoaderAsyncTask task = new PoiLoaderAsyncTask();
		task.execute(new Object[]{poiLoader, poiOverlay, mapView,dialog});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		MapView mapView = (MapView) findViewById(R.id.mapView);
		switch (item.getItemId()) {
		case R.id.mapMenuRefresh:
			Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();
			break;
		case R.id.mapSat:
			mapView.setSatellite(true);
			break;
		case R.id.mapStreet:
			mapView.setSatellite(false);
			break;
		}
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	/**
	 * Overlay that will be drawn on map as users 
	 * current location
	 *
	 */
	private class MyOverlay extends com.google.android.maps.Overlay {

        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {
            super.draw(canvas, mapView, shadow);

            if (!shadow) {

                Point point = new Point();
                mapView.getProjection().toPixels(geoPoint, point);

                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.point);

                /** 
                 * Shift it left so the center of the image is aligned with the x-coordinate of the geo point
                 */
                int x = point.x - bmp.getWidth() / 2;

                /** 
                 * Shift it upward so the bottom of the image is aligned with the y-coordinate of the geo point
                 */
                int y = point.y - bmp.getHeight();

                canvas.drawBitmap(bmp, x, y, null);

            }
        }

    }
	
	public void onLocationChanged(Location location) {
        
        geoPoint = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
        //mapView = (MapView) findViewById(R.id.mapView);
        Helper.setLocation(location);
        MapController mc = mapView.getController();
        if(flag){
        	mc.setZoom(18);
            mc.animateTo(geoPoint);
        }
        flag = false;

        List<Overlay> overlays = mapView.getOverlays();
        //overlays.clear();
        overlays.add(new MyOverlay());

        mapView.invalidate();
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}


