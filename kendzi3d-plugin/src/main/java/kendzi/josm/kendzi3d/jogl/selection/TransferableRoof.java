package kendzi.josm.kendzi3d.jogl.selection;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TransferableRoof implements Transferable{

	public static DataFlavor roofFlavor =
			new DataFlavor(DataFlavor.class, "A Color Object");

	protected static DataFlavor[] supportedFlavors = {
		roofFlavor,
		DataFlavor.stringFlavor,
	};

	//	String roofType ;
	//	JComboBox comboBox;
	Object a; 

	public TransferableRoof(Object a) { this.a = a; }

	@Override
	public DataFlavor[] getTransferDataFlavors() {return supportedFlavors;}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (flavor.equals(roofFlavor) || 
				flavor.equals(DataFlavor.stringFlavor)) return true;
		return false;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (flavor.equals(roofFlavor))
			return a;
		else if (flavor.equals(DataFlavor.stringFlavor)) 
			return a.toString();
		else 
			throw new UnsupportedFlavorException(flavor);
	}
}
