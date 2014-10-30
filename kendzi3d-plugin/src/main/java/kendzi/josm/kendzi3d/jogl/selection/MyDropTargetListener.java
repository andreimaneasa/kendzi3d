package kendzi.josm.kendzi3d.jogl.selection;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import kendzi.josm.kendzi3d.jogl.RenderJOSM;

import org.apache.log4j.Logger;

public class MyDropTargetListener extends DropTargetAdapter {
	
	public void a(){
		log.info("a fost acceptat " );
	}
	private static final Logger log = Logger.getLogger(MyDropTargetListener.class);

	private DropTarget dropTarget;
	private JPanel panel;
	private JButton btn;


	public MyDropTargetListener(JPanel panel) {
		this.panel = panel;

		dropTarget = new DropTarget(panel, this);
		//			dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, this);


		//			dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY, 
		//					this, true, null);
	}


	public void drop(DropTargetDropEvent event) {
//
//		try {
//
//			Transferable tr = event.getTransferable();
//
//			Object obj = (Object) tr.getTransferData(TransferableRoof.roofFlavor);
//
////			if (selection != null){
//			
//			event.isDataFlavorSupported(TransferableRoof.roofFlavor);
//
//			event.acceptDrop(DnDConstants.ACTION_COPY);
//
//			event.dropComplete(true);
//
////			System.out.println("a fost acceptat " + obj);
//			log.info("a fost acceptat " + obj);
//
////			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
////			log.info("Error " + e);
		}



		//			try {
		//				
		//				Transferable tr = event.getTransferable();
		//				Color color = (Color) tr.getTransferData(TransferableColor.colorFlavor);
		//				
		//				if (event.isDataFlavorSupported(TransferableColor.colorFlavor)) {
		//
		//					event.acceptDrop(DnDConstants.ACTION_COPY );
		//					this.panel.setBackground(color);
		////					String selected = (String) comboBox.getSelectedItem();//what you select from comboBox
		//					
		////					this.panel.setToolTipText(selected);
		//					//	              this.btn.setTransferHandler(new TransferHandler(""));
		//					event.dropComplete(true);
		//					log.info("---drop--");
		//					return;
		//				}
		//				event.rejectDrop();
		//			
		//			} catch (Exception e) {
		//				e.printStackTrace();
		//				log.info("__ not drop __");
		//				event.rejectDrop();
		//				
		//			}



	}
