/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */
package kendzi.josm.kendzi3d.jogl.selection;

import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.openstreetmap.josm.data.osm.OsmPrimitive;

import kendzi.josm.kendzi3d.jogl.selection.editor.Editor;

public interface Selection {
	Point3d getCenter();
	double getRadius();
	long getWayId();
	void select(boolean selected);

	List<Editor> getEditors();
	
}
