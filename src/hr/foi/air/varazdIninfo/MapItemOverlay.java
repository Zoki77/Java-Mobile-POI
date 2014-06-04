package hr.foi.air.varazdIninfo;

import hr.foi.air.types.Helper;
import hr.foi.air.types.PoiInfo;

import java.util.ArrayList;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * This class attaches overlay over map
 * which has many Itemized overlays
 *
 */
@SuppressWarnings("rawtypes")
public class MapItemOverlay extends ItemizedOverlay {
	private Context context;
	private ArrayList<OverlayItem> myOverlayItems = new ArrayList<OverlayItem>();
	/**
	 * Class constructor
	 * 
	 * @param defaultMarker Default marker of this Overlay
	 * @param context Context in which this class is invoked
	 */
	public MapItemOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return myOverlayItems.get(i);
	}

	@Override
	public int size() {
		return myOverlayItems.size();
	}

	@Override
	protected boolean onTap(int index) {
		Drawable icon = null;
		OverlayItem item = myOverlayItems.get(index);
		int[][] slike = Helper.getSlike();
		
		
		String ime = item.getTitle();
		int id = Integer.parseInt(ime);

		for (final PoiInfo poiInfo:Helper.getPois()){
			if (poiInfo.getId()==id){
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.custom);
				dialog.setTitle(poiInfo.getInfo());
				TextView text1 = (TextView) dialog.findViewById(R.id.text1);
				text1.setText(poiInfo.getOpis());
				TextView text2 = (TextView) dialog.findViewById(R.id.text2);
				text2.setText(poiInfo.getAdresa());
				TextView text3 = (TextView) dialog.findViewById(R.id.text3);
				text3.setText(poiInfo.getTelefon());
				TextView text4 = (TextView) dialog.findViewById(R.id.text4);
				text4.setText(poiInfo.getWeb());				
				ImageView image = (ImageView) dialog.findViewById(R.id.image);				
				icon = context.getResources().getDrawable(slike[poiInfo.getGroupPos()][poiInfo.getChildPos()]);
								
				image.setImageDrawable(icon);
				
				Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);				
				dialogButtonCancel.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				
				Button dialogButtonDirections = (Button) dialog.findViewById(R.id.dialogButtonDirections);				
				dialogButtonDirections.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						String dlatitude = String.valueOf(poiInfo.getPoi().getLatitudeE6()/1e6);
						String dlongitude = String.valueOf(poiInfo.getPoi().getLongitudeE6()/1e6);
						String slatitude = String.valueOf(Helper.getLocation().getLatitude());
						String slongitude = String.valueOf(Helper.getLocation().getLongitude());
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
							    Uri.parse("http://maps.google.com/maps?saddr="+slatitude+","+slongitude+"&daddr="+dlatitude+","+dlongitude));
						intent.setClassName(
				                 "com.google.android.apps.maps",
				                 "com.google.android.maps.MapsActivity");
						context.startActivity(intent);
						dialog.dismiss();
					}
				});
				dialog.show();
				break;
			}
		}		
		return true;
	}
	/**
	 * Adds OverlayItem to a List
	 * of OverlayItems
	 * 
	 * @param item OverlayItem which will be added to a List
	 */
	public void addItem(OverlayItem item) {
		myOverlayItems.add(item);
		populate();
	}
}
