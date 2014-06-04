package hr.foi.air.types;

import java.util.List;

import android.location.Location;
/**
 * Helper class which is used to help
 * reference needed objects in other classes
 *
 */
public class Helper {
	private static Location location;
	private static int[][] slike;
	private static List<CheckedElements> lista;
	private static List<PoiInfo> pois;
	/**
	 * Function that references Bitmap pictures
	 * for drawing on the map
	 * 
	 * @return Array of references on pictures in resources
	 */
	public static int[][] getSlike() {
		return slike;
	}
	/**
	 * Sets the references on pictures from resources
	 * 
	 * @param slikeArg Array of references on pictures in resources
	 */
	public static void setSlike(int[][] slikeArg) {
		slike = slikeArg;
	}
	/**
	 * References List of Checked elements
	 * 
	 * @return List of checked elements
	 */
	public static List<CheckedElements> getLista() {
		return lista;
	}
	/**
	 * Sets the reference on List of checked elements
	 * 
	 * @param listaArg List of checked elements
	 */
	public static void setLista(List<CheckedElements> listaArg) {
		lista = listaArg;
	}
	/**
	 * References on current user location
	 * 
	 * @return Current user Location object
	 */
	public static Location getLocation() {
		return location;
	}
	/**
	 * Sets the reference on current user location
	 * 
	 * @param location Current user Location object
	 */
	public static void setLocation(Location location) {
		Helper.location = location;
	}
	/**
	 * References List of all points of interests
	 * put on map overlay
	 * 
	 * @return List of points of interests
	 */
	public static List<PoiInfo> getPois() {
		return pois;
	}
	/**
	 * Sets the reference on List of points of interests
	 * put on map overlay
	 * 
	 * @param pois List of points of interests
	 */
	public static void setPois(List<PoiInfo> pois) {
		Helper.pois = pois;
	}
	
}
