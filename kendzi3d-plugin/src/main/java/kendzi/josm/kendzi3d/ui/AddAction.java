package kendzi.josm.kendzi3d.ui;

import java.awt.event.ActionEvent;
import java.util.Collection;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.osm.OsmPrimitive;

import andrei.josm.plugin.color.ColorDialog;

@SuppressWarnings("serial")
public class AddAction extends JosmAction {
	
	private Collection<OsmPrimitive> selVal;
	
	public AddAction(Collection<OsmPrimitive> selVal){
		this.selVal=selVal;
	}
	
	public void startPluginForColorWall() {
		ColorDialog colorDialog= new ColorDialog();
		colorDialog.performTagAddingForWall(selVal);// setez pt ce//
		// cladire
		// selectata// sa
		// fie pusa//
		// culoarea
		colorDialog.showDialog();
	}

	public void startPluginForColorRoof() {
		ColorDialog colorDialog =new ColorDialog();
		colorDialog.performTagAddingForRoof(selVal); // setez pt ce//	// cladire// selectata// sa	// fie pusa//// culoarea
		colorDialog.showDialog();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
}