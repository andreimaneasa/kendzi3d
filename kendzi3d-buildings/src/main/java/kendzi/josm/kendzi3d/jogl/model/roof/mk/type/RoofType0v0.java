/*
 * This software is provided "AS IS" without a warranty of any kind. You use it
 * on your own risk and responsibility!!! This file is shared under BSD v3
 * license. See readme.txt and BSD3 file for details.
 */

package kendzi.josm.kendzi3d.jogl.model.roof.mk.type;

import kendzi.josm.kendzi3d.jogl.model.roof.mk.RoofTypeOutput;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.measurement.MeasurementKey;

/**
 * Roof type 0.0.
 * 
 * @author Tomasz Kędziora (Kendzi)
 * 
 */
public class RoofType0v0 extends RoofType0 {

	Double roofDimension;
	
    @Override
    protected int getType() {
        return 0;
    }

    @Override
    public RoofTypeOutput buildRectangleRoof(RectangleRoofTypeConf conf) {
    	Double h1;
//    	if (HEIGHT_5 != null){
//         h1 = getHeightMeters(conf.getMeasurements(), MeasurementKey.HEIGHT_1, roofDimension);
//    	}else{
    		 h1 = getHeightMeters(conf.getMeasurements(), MeasurementKey.HEIGHT_5, 2.5d);
//    	}
    	
        int type = getType();

        return build(conf.getBuildingPolygon(), conf.getRecHeight(), conf.getRecWidth(), conf.getRectangleContur(), h1, 0, 0, 0,
                0, 0, type, conf.getRoofTextureData());
    }

//	@Override
//	public void setHeight(Double height) {
//		this.roofDimension=height;
//	
//	}

//	@Override
//	public double getNr() {
//		return 0;
//	}


}
