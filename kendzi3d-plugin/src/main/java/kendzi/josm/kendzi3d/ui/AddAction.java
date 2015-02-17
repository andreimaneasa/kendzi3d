package kendzi.josm.kendzi3d.ui;

import java.awt.event.ActionEvent;
import java.util.Collection;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.osm.OsmPrimitive;

@SuppressWarnings("serial")
public class AddAction extends JosmAction {

	private Collection<OsmPrimitive> selVal;

	public AddAction(Collection<OsmPrimitive> selVal){
		this.selVal=selVal;
	}

	public void setValueForColorBuilding(){
		
			
	}
	
	public void startPluginForColorWall() {
		ColorDialog colorDialog= new ColorDialog();
		colorDialog.performTagAddingForWall(selVal);
		// put color for selected building 
		colorDialog.showDialog();
	}

	public void startPluginForColorRoof() {
		ColorDialog colorDialog =new ColorDialog();
		colorDialog.performTagAddingForRoof(selVal); 
		// put color for selected building 
		colorDialog.showDialog();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
}