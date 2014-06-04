package hr.foi.air.types;

import com.google.android.maps.GeoPoint;

public class PoiInfo implements Comparable<PoiInfo>{
	
	private int duration;
	private int distance;
	private int groupPos;
	private int childPos;
	private ObjectInfo objectInfo;
	private int id;
	private GeoPoint poi;
	private String info;
	private int objektId;
	private String telefon;
	private String adresa;
	private String grad;
	private String web;
	private String opis;

	public PoiInfo(double latitude, double longitude, String info, int objektId, String telefon, String adresa, String grad, String web, String opis,int id) {
		this.setId(id);
		this.setInfo(info);
		this.setPoi(latitude, longitude);
		this.objektId = objektId;
		this.telefon = telefon;
		this.adresa = adresa;
		this.grad = grad;
		this.web = web;
		this.opis = opis;
	}

	public GeoPoint getPoi() {
		return poi;
	}

	public void setPoi(double latitude, double longitude) {
		this.poi = new GeoPoint((int) (latitude * 1e6), (int) (longitude * 1e6));
	}

	public String getInfo() {
		return info;
	}
	

	public void setInfo(String info) {
		this.info = info;
	}

	public int getObjektId() {
		return objektId;
	}

	public void setObjektId(int objektId) {
		this.objektId = objektId;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ObjectInfo getObjectInfo() {
		return objectInfo;
	}

	public void setObjectInfo(ObjectInfo objectInfo) {
		this.objectInfo = objectInfo;
	}
	
	public int getGroupPos() {
		return groupPos;
	}

	public void setGroupPos(int groupPos) {
		this.groupPos = groupPos;
	}

	public int getChildPos() {
		return childPos;
	}

	public void setChildPos(int childPos) {
		this.childPos = childPos;
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int compareTo(PoiInfo another) {
		return Integer.valueOf(distance).compareTo(Integer.valueOf(another.distance));
	}

}