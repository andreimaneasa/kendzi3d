package kendzi.josm.kendzi3d.jogl.model.building.model.roof;

import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofTypeBuilder;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeAliasEnum;

public interface RoofModel {
    double getRoofHeight();
    RoofTypeAliasEnum roofType();
    void setroofType(RoofTypeBuilder rtf);
}
