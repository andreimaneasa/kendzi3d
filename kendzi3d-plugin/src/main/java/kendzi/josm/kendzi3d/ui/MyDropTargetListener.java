package kendzi.josm.kendzi3d.ui;


import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JPanel;

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

	}
}