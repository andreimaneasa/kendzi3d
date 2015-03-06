/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */
package kendzi.josm.kendzi3d.jogl.selection;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.openstreetmap.josm.data.osm.OsmPrimitive;

public abstract class BuildingSelection implements Selection {

	Point3d center;
	Point3d boundMin;
	Point3d boundMax;
	double radius;
	double minHeight;
	Vector3d scale;

	long wayId;

	/**
	 * Constructor for buildings
	 */
	public BuildingSelection(long wayId, Point3d center, double radius) {
		super();
		this.center = center;
		this.radius = radius;
		this.wayId = wayId;
	}

	/**
	 * Constructor for cylinder shape
	 */
	public BuildingSelection(long wayId, Point3d center, Point3d boundMin, Point3d boundMax, double radius, double minHeight, Vector3d scale) {
		super();
		this.center = center;
		this.boundMin = boundMin;
		this.boundMax = boundMax;
		this.radius = radius;
		this.wayId = wayId;
		this.minHeight = minHeight;
		this.scale = scale;
	}

	@Override
	public Vector3d getScale() {
		return scale;
	}

	public void setScale(Vector3d scale) {
		this.scale = scale;
	}

	@Override
	public double getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(double minHeight) {
		this.minHeight = minHeight;
	}

	@Override
	public Point3d getBoundMin() {
		return boundMin;
	}

	public void setBoundMin(Point3d boundMin) {
		this.boundMin = boundMin;
	}
	@Override
	public Point3d getBoundMax() {
		return boundMax;
	}

	public void setBoundMax(Point3d boundMax) {
		this.boundMax = boundMax;
	}

	@Override
	public Point3d getCenter() {
		return this.center;
	}

	@Override
	public double getRadius() {
		return this.radius;
	}

	@Override
	public long getWayId() {
		return this.wayId;
	}

	@Override
	public abstract void select(boolean selected);

	//    /**
	//     * @return the wayId
	//     */
	//    public long getWayId() {
	//        return wayId;
	//    }

	/**
	 * @param wayId the wayId to set
	 */
	public void setWayId(long wayId) {
		this.wayId = wayId;
	}

	/**
	 * @param center the center to set
	 */
	public void setCenter(Point3d center) {
		this.center = center;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

}
