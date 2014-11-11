package kendzi.josm.kendzi3d.ui;

import java.util.Collection;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.ChangePropertyCommand;
import org.openstreetmap.josm.data.osm.OsmPrimitive;

public class MethodsForKendzi3dGLFrame {

	String[] a = {
			"ROOF_TYPE0_0", 
			"ROOF_TYPE0_1",
			"ROOF_TYPE0_2", 
			"ROOF_TYPE0_3", 
			"ROOF_TYPE0_4",
			"ROOF_TYPE0_5",// not implemented

			"ROOF_TYPE1_0", 
			"ROOF_TYPE1_1", 

			"ROOF_TYPE2_0", 
			"ROOF_TYPE2_1", 
			"ROOF_TYPE2_2", 
			"ROOF_TYPE2_3", 
			"ROOF_TYPE2_4", 
			"ROOF_TYPE2_5", 
			"ROOF_TYPE2_6",
			"ROOF_TYPE2_7",
			"ROOF_TYPE2_8",
			"ROOF_TYPE2_9",

			"ROOF_TYPE3_0",
			"ROOF_TYPE3_1",// not implemented
			"ROOF_TYPE3_2",// not implemented
			"ROOF_TYPE3_3",// not implemented
			"ROOF_TYPE3_4",// not implemented

			"ROOF_TYPE4_0",
			"ROOF_TYPE4_1",// not implemented
			"ROOF_TYPE4_2",

			"ROOF_TYPE5_0",
			"ROOF_TYPE5_1",// not implemented
			"ROOF_TYPE5_2",
			"ROOF_TYPE5_3",// not implemented
			"ROOF_TYPE5_4",// not implemented
			"ROOF_TYPE5_5",// not implemented
			"ROOF_TYPE5_6",
			"ROOF_TYPE5_7",// not implemented
			"ROOF_TYPE5_8",// not implemented

			"ROOF_TYPE6_0",// not implemented
			"ROOF_TYPE6_1",// not implemented
			"ROOF_TYPE6_2",// not implemented
			"ROOF_TYPE6_3",// not implemented
			"ROOF_TYPE6_4",// not implemented

			"ROOF_TYPE8_0",
			"ROOF_TYPE9_0",

	}; 

	String [] roofTypes ={
			"flat", 
			"flat simple terraced",
			"flat double terraced",
			"flat triple terraced",
			"flat terraced",

			"skillion",
			"skillion diagonally",

			"gabled",
			"gabled height moved",
			"side hipped",
			"half hipped",
			"hipped",
			"pyramidal",
			"double skillion",
			"triple skillion",
			"diagonal pass",
			"diagonal pass 2",


			"half saltbox",
			//			"Roof3_1",	
			//			"Roof3_2",
			//			"Roof3_3",
			//			"Roof3_4",

			"gambrel",
			//			"Roof4_1",
			"mansard",
			//			"Roof4_2_1",

			"round",
			"round hipped",

			//			"Roof6_4_4",

			"cone",

			"equal hipped",
			//			"Roof9_1",
			//			"Roof9_2",
	};



	public void switchRoofType(String option, Collection<OsmPrimitive> selVal){

		switch (option) {//check for a match
		case "flat":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","flat"));//0.0
			break;
		case "flat simple terraced":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","flat simple terraced"));//0.1
			break;
		case "flat double terraced":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","flat double terraced"));//0.2
			break;
		case "flat triple terraced":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","flat triple terraced"));//0.3
			break;
		case "flat terraced":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","flat terraced"));//0.4
			break;
			//		case "Roof0_5":
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","0.5"));
			//			break;



			// type 1_
		case "skillion":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","skillion"));//1.0
			break;

		case "skillion diagonally":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","skillion diagonally"));//1.1
			break;



			//type 2_
		case "gabled":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","gabled"));//2.0
			break;

		case "gabled height moved":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","gabled height moved"));//2.1
			break;
		case "side hipped":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","side hipped"));//2.2
			break;
		case "half hipped":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","half hipped"));//2.3
			break;
		case "hipped":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","hipped"));//2.4
			break;
		case "pyramidal":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","pyramidal"));//2.5
			break;
		case "double skillion":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","double skillion"));//2.6
			break;
		case "triple skillion":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","triple skillion"));//2.7
			break;
		case "diagonal pass":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","diagonal pass"));//2.8
			break;
		case "diagonal pass 2":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","diagonal pass 2"));//2.9
			break;



			// type 3_
		case "half saltbox":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","half saltbox"));//3.0
			break;

			//		case "Roof3_1"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","3.1"));
			//			break;
			//
			//		case "Roof3_2"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","3.2"));
			//			break;
			//
			//		case "Roof3_3"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","3.3"));
			//			break;
			//
			//		case "Roof3_4"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","3.4"));
			//			break;



			//type 4_
		case "gambrel":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","gambrel"));//4.0
			break;
			//		case "Roof4_1"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","4.1"));
			//			break;

		case "mansard":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","mansard"));//4.2
			break;
			//		case "Roof4_2_1":
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","4.2.1"));
			//			break;



			//type 5_
		case "round":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","round"));//5.0
			break;

		case "round hipped"://

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","round hipped"));//5.6
			break;

			//		case "Roof5_2":
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","5.2"));
			//			break;
			//		case "Roof5_3"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","5.3"));
			//			break;
			//		case "Roof5_4"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","5.4"));
			//			break;
			//		case "Roof5_5"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","5.5"));
			//			break;
			//
			//		case "Roof5_6":
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","5.6"));
			//			break;



			//			//type 6_
			//		case "Roof6_4_4"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","6.4"));
			//			break;
			//
			//		case "Roof0_0"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","6.1"));
			//			break;
			//		case "Roof0_0"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","6.2"));
			//			break;
			//		case "Roof0_0"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","6.3"));
			//			break;
			//		case "Roof0_0"://
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","6.4"));
			//			break;



			//type 8_
		case "cone":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","cone"));//8.0
			break;



			//type 9_					
		case "equal hipped":

			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","equal hipped"));//9.0
			break;
			//		case "Roof9_1":
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","9.1"));
			//			break;
			//		case "Roof9_2":
			//
			//			Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:shape","9.2"));
			//			break;

		default:
			break;
		}
	}
}
