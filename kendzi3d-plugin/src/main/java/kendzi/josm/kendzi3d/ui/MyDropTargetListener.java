package kendzi.josm.kendzi3d.ui;


import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JPanel;



//public class MyDropTargetListener {
//
//}
public class MyDropTargetListener extends DropTargetAdapter {

	private DropTarget dropTarget;
	private JPanel panel;

	public MyDropTargetListener() {
		super();
	}

	public MyDropTargetListener(JPanel panel) {
		this.panel = panel;

		dropTarget = new DropTarget(panel, this);
	}

	// === drop ===
	public void drop(DropTargetDropEvent event) {
//
//		try {
//
//			Transferable tr = event.getTransferable();
//
//			String obj = (String) tr.getTransferData(TransferableRoof.roofFlavor);
//
//			if (selection != null) {
//
//				event.isDataFlavorSupported(TransferableRoof.roofFlavor);
//
//				event.acceptDrop(DnDConstants.ACTION_COPY);
//
//				event.dropComplete(true);
//
//				selection = getLastSelection();
//				selVal = getOsmPrimitveFromSelectedBuilding(selection);
//
//				method.switchRoofType(obj, selVal);// set for selected
//
//				list.setEnabled(false);
//
//				log.info("a fost acceptat " + obj);
//
//			}
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.info("Error " + e);
//		}
	}
}