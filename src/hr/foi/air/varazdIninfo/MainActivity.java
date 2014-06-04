package hr.foi.air.varazdIninfo;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
/**
 * Main activity that shows main screen
 *
 */
public class MainActivity extends Activity implements LocationListener {
	
	private LocationManager locationManager;
	private Geocoder geocoder;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String groups[] = getResources().getStringArray(R.array.main_objekti);
        String children[][]= {getResources().getStringArray(R.array.banke__objekti),
        		getResources().getStringArray(R.array.bankomati_objekti),
        		getResources().getStringArray(R.array.benziske_objekti),
        		getResources().getStringArray(R.array.bolnica_objekti),
        		getResources().getStringArray(R.array.ducani_objekti),
        		getResources().getStringArray(R.array.smjestaj_objekti),
        		getResources().getStringArray(R.array.kladionica_objekti),
        		getResources().getStringArray(R.array.restorani_objekti),
        		getResources().getStringArray(R.array.sportski_objekti),
        		getResources().getStringArray(R.array.transport_objekti)};
        
        locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
        
        geocoder = new Geocoder(this);
        
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //<5>
        if (location != null) {
          this.onLocationChanged(location);
        }
       
        ExpandableListAdapter mAdapter;
        ExpandableListView epView = (ExpandableListView) findViewById(R.id.expandableListView1);
        mAdapter = new MyExpandableListAdapter(this, groups,children);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        epView.setIndicatorBounds(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10));
        epView.setAdapter(mAdapter);
    }
    /**
     * Gets the dips from pixels
     * 
     * @param pixels Number of pixels
     * @return Size of dips according to pixels
     */
    public int GetDipsFromPixel(float pixels){ 
     final float scale = getResources().getDisplayMetrics().density;
     return (int) (pixels * scale + 0.5f);
    }
    
    /**
     * Opens map activity
     * 
     * @param target Target View
     */
    public void openMap(View target){
    	Intent i = new Intent(this, MapActivity.class);
    	startActivity(i);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent i = new Intent(MainActivity.this, SettingsActivity.class);
			startActivity(i);
			break;
		}
    	return true;
    }
    
    public void onLocationChanged(Location location) {
		List<Address> addresses;
		try {
			addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
			for (Address address : addresses) {
		        System.out.println(address.getAddressLine(0));
		     }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //<10>
	    
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
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this); //<7>
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this); //<8>
	  }
}
