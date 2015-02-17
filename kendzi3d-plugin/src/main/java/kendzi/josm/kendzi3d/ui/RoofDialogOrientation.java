package kendzi.josm.kendzi3d.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openstreetmap.josm.gui.tagging.ac.AutoCompletingTextField;
import org.openstreetmap.josm.tools.I18n;


public class RoofDialogOrientation extends JDialog
{

	protected JComboBox cbType;
	protected JLabel textField;

	@SuppressWarnings("unchecked")
	public RoofDialogOrientation(JPanel panel)
	{
		getContentPane().setLayout(new BorderLayout());

		textField = new JLabel("Orientation:");
		this.cbType = new JComboBox();
		this.cbType.setEditable(true);
		char degrees= (char)186;
		this.cbType.setPreferredSize(new Dimension(50,20));
		this.cbType.setModel(new DefaultComboBoxModel(new String[] {"0"+degrees,"90"+degrees,"180"+degrees,"270"+degrees}));

		panel.add(textField);
		panel.add(this.cbType, "3, 3, fill, default");
	}

	public String getNameFromComboBox(){
		String name;
		name =  cbType.getSelectedItem().toString();

		return name;
	}


	public void setupTypeCombo()
	{
		IconListRenderer ilr = new IconListRenderer()
		{
			private static final long serialVersionUID = 1L;

			public ImageIcon loadImage(String key)
			{
				try
				{
					return new ImageIcon( getClass().getResource("/images//RoofTypes//Rotate_"+ key + ".png"));

					//					return ImageProvider.get("C://Users//anma8806//Desktop//OSM//Kendzi3D_Roof_Type//Rotate_" + key + ".png");
				}
				catch (Exception e) {}
				return null;
			}

			public String tr(String str)
			{
				return I18n.tr(str, new Object[0]);
			}
		};
		getCbType().setEditable(true);
		getCbType().setRenderer(ilr);
		AutoCompletingTextField tf = new AutoCompletingTextField();

		getCbType().setEditor(tf);
	}

	public JComboBox getCbType()
	{
		return this.cbType;
	}

}

