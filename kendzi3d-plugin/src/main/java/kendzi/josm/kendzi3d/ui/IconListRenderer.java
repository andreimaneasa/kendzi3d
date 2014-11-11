package kendzi.josm.kendzi3d.ui;

import java.awt.Component;
/*  4:   */ import java.awt.Dimension;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.Map;
/*  7:   */ import javax.swing.DefaultListCellRenderer;
/*  8:   */ import javax.swing.ImageIcon;
/*  9:   */ import javax.swing.JLabel;
/* 10:   */ import javax.swing.JList;


@SuppressWarnings("serial")
public class IconListRenderer extends DefaultListCellRenderer
{
	private Map<Object, ImageIcon> icons = new HashMap();

	public IconListRenderer() {}

	public IconListRenderer(Map<Object, ImageIcon> icons)
	{
		this.icons = icons;
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		label.setPreferredSize(null);

		ImageIcon icon = getImage((String)value);

		label.setIcon(icon);
		label.setText(tr((String)value));
		if (index == -1) {
			label.setPreferredSize(new Dimension(label.getPreferredSize().width, 10));
		}
		return label;
	}

	private ImageIcon getImage(String key)
	{
		if (this.icons.containsKey(key)) {
			return (ImageIcon)this.icons.get(key);
		}
		ImageIcon img = loadImage(key);
		this.icons.put(key, img);
		return img;
	}

	public ImageIcon loadImage(String key)
	{
		return null;
	}

	public String tr(String key)
	{
		return key;
	}
}