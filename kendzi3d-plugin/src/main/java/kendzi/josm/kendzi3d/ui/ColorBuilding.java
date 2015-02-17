package kendzi.josm.kendzi3d.ui;

import java.awt.Color;
import java.awt.Frame;
import java.text.ParseException;
import java.util.Collection;

import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.ChangePropertyCommand;
import org.openstreetmap.josm.data.osm.OsmPrimitive;

@SuppressWarnings("serial")
public class ColorBuilding extends Frame  {

	JPanel jpanel;
	Collection<OsmPrimitive> selVal;
	JFormattedTextField myOutput = new JFormattedTextField(createFormatter("*HHHHHH"));
	String value;
	String global;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void addColorToBuildings(){
		String hexaCode;

		System.out.println("-"+ myOutput.getText() +"-");
		setValue(myOutput.getText());
		
		if (!checkHex(value)){
			 
			Color color = JColorChooser.showDialog(getJpanel(), "Choose a color", Color.red);

			hexaCode = ColorDialog.toRGBCode(color);

			inserare(getJpanel(),hexaCode);
			// sa mai verific daca scoate hex, suplimentar
			global= hexaCode;

		}else{
//			if(checkHex()){
				global=value;
//			}else{
//				System.out.println("nu ai bagat HEXA");
//			}
		}
		inserare(getJpanel(), global);
		
		if (global!= null ){

//					global=null;
			
		}

	
	}
	
	public void addAtribute()
	{
		String key = "colour";// set attribute
		Main.main.undoRedo.add(new ChangePropertyCommand(getSelVal(), key, global));
		global=null;
		inserare(getJpanel(),"");
		
	}
	
	//A convenience method for creating a MaskFormatter.
	protected MaskFormatter createFormatter(String s)
	{
		MaskFormatter formatter = null;
		try
		{
			formatter = new MaskFormatter(s);
		}
		catch (ParseException exc)
		{
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	public void inserare(JPanel jp, String hexaCode)
	{
		myOutput.setColumns(10);
		myOutput.setText(hexaCode);
		jp.add(myOutput);
		repaint();
	}

	public boolean checkHex(String value)
	{
		boolean diez = value.startsWith("#");
		boolean isHex = value.matches("^[#0-9A-Fa-f]+$");

		boolean t = false;
		if ((diez) && (isHex)) {
			t = true;
		}
		return t;
	}
	
	public JPanel getJpanel() {
		return jpanel;
	}

	public void setJpanel(JPanel jpanel) {
		this.jpanel = jpanel;
	}

	public Collection<OsmPrimitive> getSelVal() {
		return selVal;
	}

	public void setSelVal(Collection<OsmPrimitive> selVal) {
		this.selVal = selVal;
	}
	
}
