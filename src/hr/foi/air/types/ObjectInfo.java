package hr.foi.air.types;

/**
 * Class that represents information of application objects
 *
 */
public class ObjectInfo {
	
	
	private int id;
	private String name;
	/**
	 * Class constructor
	 * @param id Object id
	 * @param name Object name
	 */
	public ObjectInfo(int id, String name){
		this.id = id;
		this.name = name;
	}
	/**
	 * Getter for object id
	 * 
	 * @return Object id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
