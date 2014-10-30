package kendzi.josm.kendzi3d.ui;

import java.awt.Component;
/*  4:   */ import java.awt.Dimension;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.Map;
/*  7:   */ import javax.swing.DefaultListCellRenderer;
/*  8:   */ import javax.swing.ImageIcon;
/*  9:   */ import javax.swing.JLabel;
/* 10:   */ import javax.swing.JList;
/* 11:   */ 


/* 12:   */ @SuppressWarnings("serial")
public class IconListRenderer extends DefaultListCellRenderer
/* 14:   */ {
	/* 15:16 */   private Map<Object, ImageIcon> icons = new HashMap();
	/* 16:   */   
	/* 17:   */   public IconListRenderer() {}
	/* 18:   */   
	/* 19:   */   public IconListRenderer(Map<Object, ImageIcon> icons)
	/* 20:   */   {
		/* 21:23 */     this.icons = icons;
	/* 22:   */   }
	/* 23:   */   
	/* 24:   */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	/* 25:   */   {
		/* 26:33 */     JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		/* 27:   */     
		/* 28:   */ 
		/* 29:   */ 
		/* 30:37 */     label.setPreferredSize(null);
		/* 31:   */     
		/* 32:   */ 
		/* 33:   */ 
		/* 34:41 */     ImageIcon icon = getImage((String)value);
		/* 35:   */     
		/* 36:   */ 
		/* 37:   */ 
		/* 38:45 */     label.setIcon(icon);
		/* 39:46 */     label.setText(tr((String)value));
		/* 40:49 */     if (index == -1) {
			/* 41:50 */       label.setPreferredSize(new Dimension(label.getPreferredSize().width, 10));
		/* 42:   */     }
		/* 43:54 */     return label;
	/* 44:   */   }
	/* 45:   */   
	/* 46:   */   private ImageIcon getImage(String key)
	/* 47:   */   {
		/* 48:58 */     if (this.icons.containsKey(key)) {
			/* 49:59 */       return (ImageIcon)this.icons.get(key);
		/* 50:   */     }
		/* 51:62 */     ImageIcon img = loadImage(key);
		/* 52:63 */     this.icons.put(key, img);
		/* 53:64 */     return img;
	/* 54:   */   }
	/* 55:   */   
	/* 56:   */   public ImageIcon loadImage(String key)
	/* 57:   */   {
		/* 58:70 */     return null;
	/* 59:   */   }
	/* 60:   */   
	/* 61:   */   public String tr(String key)
	/* 62:   */   {
		/* 63:74 */     return key;
	/* 64:   */   }
/* 65:   */ }