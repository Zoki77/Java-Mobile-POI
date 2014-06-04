package hr.foi.air.plugins.poi_json;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.OverlayItem;

import hr.foi.air.varazdIninfo.MainActivity;
import hr.foi.air.varazdIninfo.MapItemOverlay;
import hr.foi.air.interfaces.IPoi;
import hr.foi.air.types.CheckedElements;
import hr.foi.air.types.Helper;
import hr.foi.air.types.ObjectInfo;
import hr.foi.air.types.PoiInfo;

public class JsonPoiLoader implements IPoi{
	
	private Context c;
	private List<PoiInfo> pois;
	private List<ObjectInfo> objects;
	
	public JsonPoiLoader(Context c){
		this.c = c;
		
	}

	public String getPois() {
		String result = "";

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(
				"http://airvipoiobjects.web44.net/air/index.php?action=list&entity=user&REST=json");

		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		return result;

	}
	
	public String getObjects() {
		String result = "";

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(
				"http://airvipoiobjects.web44.net/air/index.php?action=list&entity=objekt&REST=json");

		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		return result;

	}
	
	public String getDistances(){
		String result = "";
		String url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins="
						+Helper.getLocation().getLatitude()+","+Helper.getLocation().getLongitude()+"&destinations=";
		for (PoiInfo poiInfo : pois) {
			url += poiInfo.getPoi().getLatitudeE6()/1e6+","+poiInfo.getPoi().getLongitudeE6()/1e6+URLEncoder.encode("|"); 
		}
		url = url.substring(0, url.length()-3);
		url += "&mode=walking&sensor=false";
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);
			Log.i("url", url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();

		return result;
	}
	
	public void loadOverlay(MapItemOverlay poiOverlay) {
		int[][] slike = Helper.getSlike();
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(c);
		String viewType = sharedPref.getString("viewType", "ne radi!");
		String str2;
		try {
			createList();
			if (viewType.equals("1")) {
				List<PoiInfo> listOfClosest = createListOfClosest();
				for (PoiInfo poiInfo : listOfClosest) {
					str2 = String.valueOf(poiInfo.getId());
					OverlayItem item = new OverlayItem(poiInfo.getPoi(),
							str2,null);
					Drawable icon = c.getResources()
							.getDrawable(
									slike[poiInfo.getGroupPos()][poiInfo
											.getChildPos()]);
					icon.setBounds(0, 0, 40, 40);
					item.setMarker(icon);
					poiOverlay.addItem(item);
				}
			}
			else{
				for (PoiInfo poiInfo : pois) {
					str2 = String.valueOf(poiInfo.getId());
					OverlayItem item = new OverlayItem(poiInfo.getPoi(),
							str2,null);
					Drawable icon = c.getResources()
							.getDrawable(
									slike[poiInfo.getGroupPos()][poiInfo
											.getChildPos()]);
					icon.setBounds(0, 0, 40, 40);
					item.setMarker(icon);
					poiOverlay.addItem(item);
				}
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		catch(NullPointerException e){
			Activity activity = (Activity)c;
			activity.runOnUiThread(new Runnable() {
				
				public void run() {
					Toast.makeText(c, "Niste izabrali niti jedan objekt ili vam GPS nije ukljuèen!"
							, Toast.LENGTH_SHORT).show();
					Intent i = new Intent(c, MainActivity.class);
					c.startActivity(i);
				}
			});
			
		}

	}

	private void createList() throws JSONException,NullPointerException {
		pois = new ArrayList<PoiInfo>();
		createObjectList();
		String res = this.getPois();
		int len = new JSONArray(res).length();
		String name = null;

		for (int i = 0; i < len; i++) {
			JSONObject json = new JSONArray(res).getJSONObject(i);
			PoiInfo poi = new PoiInfo(json.getDouble("latitude"),
					json.getDouble("longitude"), json.getString("name"), json.getInt("objekt_id"),
					json.getString("telefon"),json.getString("adresa"),json.getString("grad"),
					json.getString("web"),json.getString("opis"),json.getInt("id"));
			for (CheckedElements elem : Helper.getLista()) {
				for (ObjectInfo obj : objects) {
					if(obj.getId() == poi.getObjektId()){
						name = obj.getName();
						poi.setObjectInfo(obj);
					}
				}
				if(elem.getName().equals(name)){
					poi.setGroupPos(elem.getGroupPos());
					poi.setChildPos(elem.getChildPos());
					pois.add(poi);
				}
			}
			
		}
		Helper.setPois(pois);
	}
	
	private void createObjectList() throws JSONException {
		objects = new ArrayList<ObjectInfo>();

		String res = this.getObjects();
		int len = new JSONArray(res).length();

		for (int i = 0; i < len; i++) {
			JSONObject json = new JSONArray(res).getJSONObject(i);
			ObjectInfo object = new ObjectInfo(json.getInt("id"),json.getString("name"));
			objects.add(object);
		}
		
	}
	
	private void assignDistancesToPois(){
		String jsonStr = this.getDistances();
        try {
            JSONObject rootObject = new JSONObject(jsonStr); // Parse the JSON to a JSONObject
            JSONArray rows = rootObject.getJSONArray("rows"); // Get all JSONArray rows

            for(int i=0; i < rows.length(); i++) { // Loop over each each row
                JSONObject row = rows.getJSONObject(i); // Get row object
                JSONArray elements = row.getJSONArray("elements"); // Get all elements for each row as an array

                for(int j=0; j < elements.length(); j++) { // Iterate each element in the elements array
                    JSONObject element =  elements.getJSONObject(j); // Get the element object
                    JSONObject duration = element.getJSONObject("duration"); // Get duration sub object
                    JSONObject distance = element.getJSONObject("distance"); // Get distance sub object

                    pois.get(j).setDistance(distance.getInt("value"));
                    pois.get(j).setDuration(duration.getInt("value"));
                    
                }
            }
        } catch (JSONException e) {
            // JSON Parsing error
            e.printStackTrace();
        }
	}

	private List<PoiInfo> createListOfClosest(){
		assignDistancesToPois();
		List<PoiInfo> listOfClosest = new ArrayList<PoiInfo>();
		Map<ObjectInfo, List<PoiInfo>> map = new HashMap<ObjectInfo, List<PoiInfo>>();
		for (PoiInfo poiInfo : pois) {
			map.put(poiInfo.getObjectInfo(), new ArrayList<PoiInfo>());
		}
		for (PoiInfo poiInfo : pois) {
			map.get(poiInfo.getObjectInfo()).add(poiInfo);
		}
		for (List<PoiInfo> poilist : map.values()) {
			listOfClosest.add(Collections.min(poilist));
		}
		return listOfClosest;
	}
	
}