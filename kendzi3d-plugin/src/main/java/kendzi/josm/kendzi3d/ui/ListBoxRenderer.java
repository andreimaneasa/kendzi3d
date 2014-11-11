package kendzi.josm.kendzi3d.ui;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

@SuppressWarnings("serial")
public class ListBoxRenderer extends DefaultListCellRenderer
{
	JLabel lbl = new JLabel();
	Map<String, ImageIcon> images = new HashMap();
	private Font uhOhFont;

	public ListBoxRenderer()
	{
		this.lbl.setHorizontalAlignment(2);
		this.lbl.setVerticalAlignment(0);

		this.lbl.setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		this.lbl = label;

		String key = (String)value;

		ImageIcon icon = getImage(key);

		this.lbl.setOpaque(true);
		this.lbl.setIcon(icon);

		String text = key + (icon == null ? " NO ICON" : "");

		this.lbl.setText(text);

		return this.lbl;
	}

	private ImageIcon getImage(String key)
	{
		if (this.images.containsKey(key)) {
			return (ImageIcon)this.images.get(key);
		}
		ImageIcon img = loadImage(key);
		this.images.put(key, img);
		return img;
	}

	public ImageIcon loadImage(String key)
	{
		return null;
	}

	private String tr(String key)
	{
		return key;
	}

	protected void setUhOhText(JLabel lbl, String uhOhText, Font normalFont)
	{
		if (this.uhOhFont == null) {
			this.uhOhFont = normalFont.deriveFont(2);
		}
		lbl.setFont(this.uhOhFont);
		lbl.setText(uhOhText);
	}

}
