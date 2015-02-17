package kendzi.josm.kendzi3d.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.ChangePropertyCommand;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.gui.ExtendedDialog;
import org.openstreetmap.josm.tools.I18n;

public class ColorDialog
extends ExtendedDialog
implements ActionListener
{
	public String globalValue;
	String value;
	Collection<OsmPrimitive> globalSelectWall;
	Collection<OsmPrimitive> globalSelectRoof;
	public String global;
	public String codHexa;
	String numar;

	public String getCodHexa()
	{
		return this.codHexa;
	}

	public void setCodHexa(String codHexa)
	{
		this.codHexa = codHexa;
	}

	private static final String[] BUTTON_TEXTS = { I18n.tr("Cancel", new Object[0]) };
	private static final String[] BUTTON_ICONS = { "cancel.png" };
	String hexaCode;
	JFormattedTextField myOutput = new JFormattedTextField(createFormatter("*HHHHHH"));

	public void setInputWidth()
	{
		this.myOutput.setColumns(10);
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
		setInputWidth();
		this.myOutput.setText(hexaCode);
		jp.add(this.myOutput);
		repaint();
	}

	public String getTextField(){
		String str; 
		str=myOutput.getText();
		return str;
	}

	JButton button2 = new JButton("Add Attribute");
	JButton button1 = new JButton("Choose Color");

	public ColorDialog()
	{
		super(Main.parent, I18n.tr("Color Picker", new Object[0]), BUTTON_TEXTS);
		setCancelButton(new Integer[] { Integer.valueOf(2) });

		setButtonIcons(BUTTON_ICONS);

		final JPanel jp = new JPanel();

		this.button1.setSize(50, 50);

		this.button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Color color = JColorChooser.showDialog(jp, "Choose a color", Color.red);
				if (color != null) {
					ColorDialog.this.hexaCode = ColorDialog.toRGBCode(color);
				}
				ColorDialog colorDialog = new ColorDialog();

				colorDialog.setCodHexa(ColorDialog.this.hexaCode);

				inserare(jp, ColorDialog.this.hexaCode);// show value after click OK from colorChooser

				global = colorDialog.getCodHexa();
			}
		});

		jp.add(this.button1);

		JLabel l = new JLabel("Hex must contain #");// add text to panel
		jp.add(l);

		inserare(jp, this.global);// show input after Choose color

		this.button2.setSize(50, 50);

		this.button2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ColorDialog.this.value = ColorDialog.this.insertMaxSevenCharaters(e);
				if (ColorDialog.this.checkHex())
				{
					Collection<OsmPrimitive> returnValWall = ColorDialog.this.performTagAddingForWall(ColorDialog.this.globalSelectWall);
					Collection<OsmPrimitive> returnValRoof = ColorDialog.this.performTagAddingForRoof(ColorDialog.this.globalSelectRoof);
					if (returnValWall != null)
					{
						String key = "colour";// set attribute

						Main.main.undoRedo.add(new ChangePropertyCommand(ColorDialog.this.globalSelectWall, key, ColorDialog.this.value));

						ColorDialog.this.dispose();//after click on button close panel
					}
					if (returnValRoof != null)
					{
						String key = "roof:colour";

						Main.main.undoRedo.add(new ChangePropertyCommand(ColorDialog.this.globalSelectRoof, key, ColorDialog.this.value));

						ColorDialog.this.dispose();//after click on button close panel
					}
				}
			}
		});
		jp.add(this.button2);

		setContent(jp);
	}

	/*
	 * return what I select from 3D
	 * for walls 
	 */
	public Collection<OsmPrimitive> performTagAddingForWall(Collection<OsmPrimitive> sel)
	{
		this.globalSelectWall = sel;

		return sel;
	}

	/*
	 * return what I select from 3D
	 * for roof 
	 */
	public Collection<OsmPrimitive> performTagAddingForRoof(Collection<OsmPrimitive> sel)
	{
		this.globalSelectRoof = sel;

		return sel;
	}


	public static String toRGBCode(Color c)
	{
		String hex=null;
		if(c!= null){
			String red = Integer.toHexString(c.getRed() & 0xFFFFFF);
			String green = Integer.toHexString(c.getGreen() & 0xFFFFFF);
			String blue = Integer.toHexString(c.getBlue() & 0xFFFFFF);
			if (red.length() < 2) {
				red = "0" + red;
			}
			if (green.length() < 2) {
				green = "0" + green;
			}
			if (blue.length() < 2) {
				blue = "0" + blue;
			}
			String hexa = red + green + blue;

			hex = "#" + hexa;
		}
		return hex;

	}

	public boolean checkHex()
	{
		boolean diez = this.value.startsWith("#");
		boolean isHex = this.value.matches("^[#0-9A-Fa-f]+$");

		boolean t = false;
		if ((diez) && (isHex)) {
			t = true;
		}
		return t;
	}
	
	public boolean checkGlobalHex()
	{
		boolean diez = this.global.startsWith("#");
		boolean isHex = this.global.matches("^[#0-9A-Fa-f]+$");

		boolean t = false;
		if ((diez) && (isHex)) {
			t = true;
		}
		return t;
	}

	/*
	 * method cut the text if is longer than 7 characters, allows first 7 characters
	 */
	public String insertMaxSevenCharaters(ActionEvent evt)
	{
		if (this.myOutput.getText().length() >= 7) {
			this.myOutput.setText(this.myOutput.getText().substring(0, 7));
		}
		return this.myOutput.getText().substring(0, 7);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {}





}
