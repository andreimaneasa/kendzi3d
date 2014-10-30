package kendzi.josm.kendzi3d.jogl.model.building.model.roof;

/**
 * For tag building:roof:orientation=along|across or roof:orientation=along|across.
 *
 * @author Tomasz Kędziora (Kendzi)
 */
public enum RoofOrientation {

	/**
	 *  building:roof:orientation=along or roof:orientation=along
	 */
	along,

	/**
	 *  building:roof:orientation=across or roof:orientation=across
	 */
	across,

//    across means 90 degrees
//	/**
//	 *  for roof Orientation with 90 degrees 
//	 */
//	rigth,

	/**
	 *  for roof Orientation with 180 degrees 
	 */
	straight,
	/**
	 *  for roof Orientation with 270 degrees 
	 */
	reflex;
}
