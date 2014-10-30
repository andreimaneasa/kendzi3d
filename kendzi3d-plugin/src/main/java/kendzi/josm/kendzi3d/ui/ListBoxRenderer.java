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

		/*  52: 76 */     String name = tr(key);
		/*  53:    */     
		/*  54: 78 */     this.lbl.setOpaque(true);
		/*  55: 79 */     this.lbl.setIcon(icon);
		/*  56:    */     
		/*  57: 81 */     String text = key + (icon == null ? " NO ICON" : "");
		/*  58:    */     
		/*  59: 83 */     this.lbl.setText(text);

		/*  70: 94 */     return this.lbl;
	/*  71:    */   }
	/*  72:    */   
	/*  73:    */   private ImageIcon getImage(String key)
	/*  74:    */   {
		/*  75: 98 */     if (this.images.containsKey(key)) {
			/*  76: 99 */       return (ImageIcon)this.images.get(key);
		/*  77:    */     }
		/*  78:102 */     ImageIcon img = loadImage(key);
		/*  79:103 */     this.images.put(key, img);
		/*  80:104 */     return img;
	/*  81:    */   }
	/*  82:    */   
	/*  83:    */   public ImageIcon loadImage(String key)
	/*  84:    */   {
		/*  85:110 */     return null;
	/*  86:    */   }
	/*  87:    */   
	/*  88:    */   private String tr(String key)
	/*  89:    */   {
		/*  90:114 */     return key;
	/*  91:    */   }
	/*  92:    */   
	/*  93:    */   protected void setUhOhText(JLabel lbl, String uhOhText, Font normalFont)
	/*  94:    */   {
		/*  95:119 */     if (this.uhOhFont == null) {
			/*  96:120 */       this.uhOhFont = normalFont.deriveFont(2);
		/*  97:    */     }
		/*  98:122 */     lbl.setFont(this.uhOhFont);
		/*  99:123 */     lbl.setText(uhOhText);
	/* 100:    */   }

}
