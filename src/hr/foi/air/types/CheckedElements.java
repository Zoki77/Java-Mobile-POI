package hr.foi.air.types;

/**
 * Class that is used for tracking which
 * checkbox is checked in application
 *
 */
public class CheckedElements {
	private int groupPos;
	private int childPos;
	private String name;
	
	/**
	 * Class constructor
	 * 
	 * @param groupPos Group Position of the checked element
	 * @param childPos Child Poistion of the checked element
	 * @param name Name of the View of checked element
	 */
	public CheckedElements(int groupPos,int childPos,String name){
		this.groupPos = groupPos;
		this.childPos = childPos;
		this.name = name;
	}
	/**
	 * Getter for Group position
	 * 
	 * @return Group position
	 */
	public int getGroupPos() {
		return groupPos;
	}
	/**
	 * Setter for Group position
	 * 
	 * @param groupPos Group position
	 */
	public void setGroupPos(int groupPos) {
		this.groupPos = groupPos;
	}
	/**
	 * Getter for Child position
	 * 
	 * @return Child position
	 */
	public int getChildPos() {
		return childPos;
	}
	/**
	 * Setter for Child position
	 * 
	 * @param childPos Child position
	 */
	public void setChildPos(int childPos) {
		this.childPos = childPos;
	}
	/**
	 * Getter for Child View name
	 * 
	 * @return Child View name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Setter for Child View name
	 * 
	 * @param Child View name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
