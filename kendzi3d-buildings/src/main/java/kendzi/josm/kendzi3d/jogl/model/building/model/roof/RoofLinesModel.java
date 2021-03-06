package kendzi.josm.kendzi3d.jogl.model.building.model.roof;

import java.util.List;
import java.util.Map;

import javax.vecmath.Point2d;

import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofTypeBuilder;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeAliasEnum;
import kendzi.math.geometry.line.LineSegment2d;

public class RoofLinesModel implements RoofModel {

	private Map<Point2d, Double> heights;

	private List<LineSegment2d> innerSegments;

	private double roofHeight;

	public RoofLinesModel(Map<Point2d, Double> heights, List<LineSegment2d> innerSegments, double roofHeight) {
		super();
		this.heights = heights;
		this.innerSegments = innerSegments;
		this.roofHeight = roofHeight;
	}

	/**
	 * @return the heights
	 */
	public Map<Point2d, Double> getHeights() {
		return this.heights;
	}

	/**
	 * @param heights the heights to set
	 */
	public void setHeights(Map<Point2d, Double> heights) {
		this.heights = heights;
	}

	/**
	 * @return the innerSegments
	 */
	public List<LineSegment2d> getInnerSegments() {
		return this.innerSegments;
	}

	/**
	 * @param innerSegments the innerSegments to set
	 */
	public void setInnerSegments(List<LineSegment2d> innerSegments) {
		this.innerSegments = innerSegments;
	}

	/**
	 * @return the roofHeight
	 */
	@Override
	public double getRoofHeight() {
		return roofHeight;
	}

	/**
	 * @param roofHeight the roofHeight to set
	 */
	public void setRoofHeight(double roofHeight) {
		this.roofHeight = roofHeight;
	}

	@Override
	public RoofTypeAliasEnum roofType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setroofType(RoofTypeBuilder roofType) {
		// TODO Auto-generated method stub

	}
}
