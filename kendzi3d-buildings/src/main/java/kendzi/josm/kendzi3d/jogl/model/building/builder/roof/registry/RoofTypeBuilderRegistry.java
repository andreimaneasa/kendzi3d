package kendzi.josm.kendzi3d.jogl.model.building.builder.roof.registry;

import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType2v1;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType2v2;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType2v3;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType2v4;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType2v5;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType2v6;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType2v7;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType2v8;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType2v9;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType3v0;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType4v0;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType4v2;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType5v0;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType5v6;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType8v0;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType9v0;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofTypeBuilder;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofTypeDome;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofTypeTented;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeAliasEnum;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeFlat;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeFlatDoubleTerraced;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeFlatSimpleTerraced;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeFlatTerraced;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeFlatTripleTerraced;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeGabled;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeHalfRound;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeOnion;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypePitched;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeSkillion;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeSkillionDiagonally;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeSquareHipped;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.alias.RoofTypeSquarePyramidal;

/**
 * Registered roof type builders.
 * 
 * @author Tomasz Kędziora (Kendzi)
 */
public class RoofTypeBuilderRegistry {

	private RoofTypeBuilderRegistry() {
		//
	}

	/**
	 * Chose builder depending on roof type.
	 * 
	 * @param roofTypeEnum
	 *            roof type
	 * @return roof builder
	 */
	public static RoofTypeBuilder selectBuilder(RoofTypeAliasEnum roofTypeEnum) {
		switch (roofTypeEnum) {

		// type 0_
		case FLAT:
			return new RoofTypeFlat();
		case FLAT_SIMPLE_TERRACED:
			return new RoofTypeFlatSimpleTerraced();
		case FLAT_DOUBLE_TERRACED:
			return new RoofTypeFlatDoubleTerraced();
		case FLAT_TRIPLE_TERRACED:
			return new RoofTypeFlatTripleTerraced();
		case FLAT_TERRACED:
			return new RoofTypeFlatTerraced();

			//type 1_
		case SKILLION:
			return new RoofTypeSkillion();
		case SKILLION_DIAGONALLY:
			return new RoofTypeSkillionDiagonally();

			//type 2_
		case GABLED:
			return new RoofTypeGabled();
		case GABLED_HEIGHT_MOVED:
			return new RoofType2v1();
		case SIDE_HIPPED:
			return new RoofType2v2();
		case HALF_HIPPED:
			return new RoofType2v3();
		case HIPPED:
			return new RoofType2v4();
		case PYRAMIDAL:
			return new RoofType2v5();
		case DOUBLE_SKILLION:
			return new RoofType2v6();
		case TRIPLE_SKILLION:
			return new RoofType2v7();
		case DIAGONAL_PASS:
			return new RoofType2v8();
		case DIAGONAL_PASS_2:
			return new RoofType2v9();

			// type 3_
		case HALF_SALTBOX:
			return new RoofType3v0();

			// type 4_
		case GAMBREL:
			return new RoofType4v0();
		case MANSARD:
			return new RoofType4v2();

			// type 5_
		case ROUND:
			return new RoofType5v0();
		case ROUND_HIPPED:
			return new RoofType5v6();

			//type 8_
		case CONE:
			return new RoofType8v0();

			//type 9_
		case EQUAL_HIPPED:
			return new RoofType9v0();

		case PITCHED:
			return new RoofTypePitched();

			//		case HIPPEDDDD:
			//			return new kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofTypeHipped();
			//			case PYRAMIDAL:
			//				return new RoofTypePyramidal();			
		case SQUARE_HIPPED:
			return new RoofTypeSquareHipped();
		case COMPLEX_HIPPED:
			return new RoofType9v0();
		case SQUARE_PYRAMIDAL:
			return new RoofTypeSquarePyramidal();
		case TENTED:
			return new RoofTypeTented();
		case DOME:
			return new RoofTypeDome();
		case ONION:
			return new RoofTypeOnion();
		case HALF_ROUND:
			return new RoofTypeHalfRound();
		case SALTBOX:
			return new RoofType3v0();


		default:
			return new RoofTypeFlat();
		}
	}
}
