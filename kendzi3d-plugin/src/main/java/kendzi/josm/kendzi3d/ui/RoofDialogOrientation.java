package kendzi.josm.kendzi3d.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.openstreetmap.josm.gui.tagging.ac.AutoCompletingTextField;
import org.openstreetmap.josm.tools.I18n;
import org.openstreetmap.josm.tools.ImageProvider;


public class RoofDialogOrientation extends JDialog
{

	protected JComboBox cbType;
	protected JLabel textField;

	@SuppressWarnings("unchecked")
	public RoofDialogOrientation(JPanel panel)
	{
		getContentPane().setLayout(new BorderLayout());

		textField =new JLabel("Choose Orientation:");
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


	//	/*  30: 43 */   private final JPanel contentPanel = new JPanel();
	///*  31:    */   protected JTable personsTable;
	///*  32:    */   protected JTextField txtWikipedia;
	///*  33:    */   protected JTextField txtImage;
	//					protected JList list ;	
	///*  35:    */   protected JComboBox cbReligion;
	///*  36:    */   private JLabel lblTombType;
	///*  37:    */   private JLabel lblReligion;
	///*  38:    */   private JLabel lblTombData;
	///*  39:    */   private JLabel lblWikipediaArticle;
	///*  40:    */   private JLabel lblImage;
	///*  41:    */   private JLabel lblHistoric;
	///*  42:    */   private JComboBox cbHistoric;
	//	/*  43:    */   private JButton btnSearch;
	/*  44:    */   
	//	/*  45:    */   public static void main(String[] args)
	//	/*  46:    */   {
	//		/*  47:    */     try
	//		/*  48:    */     {
	//			/*  49: 63 */       RoofDialog dialog = new RoofDialog();
	//			/*  50: 64 */       dialog.setDefaultCloseOperation(2);
	//			/*  51: 65 */       dialog.setVisible(true);
	//		/*  52:    */     }
	//		/*  53:    */     catch (Exception e)
	//		/*  54:    */     {
	//			/*  55: 67 */       e.printStackTrace();
	//		/*  56:    */     }
	//	/*  57:    */   }
	/*  58:    */   
	/*  59:    */  
	//					JPanel panel;

	//		/*  61: 75 */     setTitle("Tomb editor");
	//		/*  62: 76 */     setBounds(100, 100, 1024, 400);
	/*  63: 77 */     
	//		/*  64: 78 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	//		/*  65: 79 */     getContentPane().add(this.contentPanel, "Center");
	//		/*  66: 80 */     this.contentPanel.setLayout(new BorderLayout(0, 0));
	/*  67:    */     
	//		/*  68: 82 */     panel = new JPanel();
	//		/*  69: 83 */     panel.setBorder(new EmptyBorder(0, 0, 0, 0));
	//		/*  70: 84 */     this.contentPanel.add(panel, "North");
	//		/*  71: 85 */     panel.setLayout(new BorderLayout(0, 0));
	/*  72:    */     
	//		/*  73: 87 */     JPanel panel_1 = new JPanel();
	//		/*  74: 88 */     panel.add(panel, "Center");
	///*  75: 89 */     panel_1.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("100px"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("220px:grow"), ColumnSpec.decode("30dlu"), FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow") }, new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("12dlu") }));

	///*  91:    */ 
	///*  92:106 */     this.lblHistoric = new JLabel("Historic kind");
	///*  93:107 */     this.lblHistoric.setFont(this.lblHistoric.getFont().deriveFont(this.lblHistoric.getFont().getStyle() | 0x1));
	///*  94:108 */     panel_1.add(this.lblHistoric, "1, 1, left, default");
	///*  95:    */     
	///*  96:    */ 
	///*  97:111 */     this.cbHistoric = new JComboBox();
	///*  98:112 */     this.cbHistoric.setModel(new DefaultComboBoxModel(new String[] { "", "tomb", "memorial" }));
	///*  99:113 */     this.cbHistoric.setEditable(true);
	///* 100:114 */     panel_1.add(this.cbHistoric, "3, 1, fill, default");
	///* 101:    */     
	///* 102:    */ 
	///* 103:117 */     this.lblTombData = new JLabel("Tomb data");
	///* 104:118 */     panel_1.add(this.lblTombData, "5, 1");
	///* 105:    */     
	///* 106:    */ 
	///* 107:121 */     this.lblTombType = new JLabel("Tomb type");
	///* 108:122 */     this.lblTombType.setFont(this.lblTombType.getFont().deriveFont(this.lblTombType.getFont().getStyle() | 0x1));
	///* 109:123 */     panel_1.add(this.lblTombType, "1, 3, left, default");
	///* 110:    */     
	/* 111:    */ 

	//		/* 112:126 */     this.list = new JList();
	//		/* 113:127 */     this.list.setEditables(true);
	/* 115:129 */    // panel_1.add(this.cbTombType, "3, 3, fill, default");



	//			ListModel<String> l = new ListModel<String>() {
	//				public String [] b =new String[] { "0_0", "0_1", "0_2", "2_7"};
	//				@Override
	//				public void removeListDataListener(ListDataListener l) {
	//				}
	//				@Override
	//				public int getSize() {
	//					return 0;
	//				}
	//				@Override
	//				public String getElementAt(int index) {
	//					return null;
	//				}
	//				@Override
	//				public void addListDataListener(ListDataListener l) {
	//				}
	//			};
	// 			this.list.;
	/* 116:    */     
	/* 117:    */ 
	///* 118:132 */     this.lblWikipediaArticle = new JLabel("- wikipedia article");
	///* 119:133 */     panel_1.add(this.lblWikipediaArticle, "5, 3, left, default");
	///* 120:    */     
	///* 121:    */ 
	///* 122:136 */     this.txtWikipedia = new JTextField();
	///* 123:137 */     panel_1.add(this.txtWikipedia, "7, 3, fill, default");
	///* 124:138 */     this.txtWikipedia.setColumns(10);
	///* 125:    */     
	///* 126:    */ 
	///* 127:141 */     this.lblReligion = new JLabel("Religion");
	///* 128:142 */     panel_1.add(this.lblReligion, "1, 5, left, default");
	///* 129:    */     
	///* 130:    */ 
	///* 131:145 */     this.cbReligion = new JComboBox();
	///* 132:146 */     this.cbReligion.setEditable(true);
	///* 133:147 */     this.cbReligion.setModel(new DefaultComboBoxModel(new String[] { "", "christian", "jewish", "muslim" , "andrei"}));
	///* 134:148 */     panel_1.add(this.cbReligion, "3, 5, fill, default");
	///* 135:    */     
	///* 136:    */ 
	///* 137:151 */     this.lblImage = new JLabel("- image");
	///* 138:152 */     panel_1.add(this.lblImage, "5, 5, left, default");
	///* 139:    */     
	///* 140:    */ 
	///* 141:155 */     this.txtImage = new JTextField();
	///* 142:156 */     panel_1.add(this.txtImage, "7, 5, fill, default");
	///* 143:157 */     this.txtImage.setColumns(10);
	///* 144:    */     
	/* 145:    */ 
	/* 146:    */ 
	/* 147:    */ 
	//		/* 148:162 */     JPanel panel1 = new JPanel();
	//		/* 149:163 */     panel1.setBorder(new EmptyBorder(0, 0, 0, 0));
	////		/* 150:164 */     this.contentPanel.add(panel1, "Center");
	//		/* 151:165 */     panel1.setLayout(new BorderLayout(0, 0));
	//		/* 152:    */     
	//		/* 153:167 */     JScrollPane scrollPane = new JScrollPane();
	//		/* 154:168 */     scrollPane.setHorizontalScrollBarPolicy(31);
	//		/* 155:169 */     panel1.add(scrollPane);

	/* 156:    */     
	///* 157:171 */     this.personsTable = new JTable();
	///* 158:    */     
	///* 159:    */ 
	///* 160:    */ 
	///* 161:    */ 
	///* 162:    */ 
	///* 163:177 */     int fontHeight = this.personsTable.getFontMetrics(this.personsTable.getFont()).getHeight();
	///* 164:178 */     this.personsTable.setRowHeight(fontHeight + 2);
	///* 165:    */     
	///* 166:180 */     this.personsTable.setModel(new DefaultTableModel(new Object[][] { { "1", "2", "3" }, { "4", "5", "6" } }, new String[] { "New column", "New column", "New column 1" }));
	///* 175:189 */     scrollPane.setViewportView(this.personsTable);
	///* 176:    */     
	/* 177:    */ 
	/* 178:    */ 
	///* 179:193 */     JPanel panel_tableButtons = new JPanel();
	///* 180:194 */     panel1.add(panel_tableButtons, "South");
	///* 181:    */     
	///* 182:196 */     JButton btnAddPerson = new JButton("New");
	///* 183:197 */     btnAddPerson.setToolTipText("New person (Ctrl-n)");
	///* 184:198 */     btnAddPerson.addActionListener(new ActionListener()
	///* 185:    */     {
	///* 186:    */       public void actionPerformed(ActionEvent arg0)
	///* 187:    */       {
	///* 188:200 */         RoofDialog.this.onAddPerson();
	///* 189:    */       }
	///* 190:202 */     });
	///* 191:203 */     panel_tableButtons.add(btnAddPerson);
	///* 192:    */     
	/* 193:    */ 
	///* 194:206 */     JButton btnRemove = new JButton("Remove");
	///* 195:207 */     btnRemove.setToolTipText("Remove person from tomb");
	///* 196:208 */     btnRemove.addActionListener(new ActionListener()
	///* 197:    */     {
	///* 198:    */       public void actionPerformed(ActionEvent e)
	///* 199:    */       {
	///* 200:212 */         RoofDialog.this.onRemovePerson(RoofDialog.this.personsTable.getSelectedRows());
	///* 201:    */       }
	///* 202:214 */     });
	///* 203:215 */     panel_tableButtons.add(btnRemove);
	///* 204:    */     
	///* 205:    */ 
	///* 206:218 */     this.btnSearch = new JButton("Search");
	///* 207:219 */     this.btnSearch.setToolTipText("Search person in OSM database");
	///* 208:220 */     this.btnSearch.addActionListener(new ActionListener()
	///* 209:    */     {
	///* 210:    */       public void actionPerformed(ActionEvent e)
	///* 211:    */       {
	///* 212:222 */         RoofDialog.this.onSearch();
	///* 213:    */       }
	///* 214:224 */     });
	///* 215:225 */     panel_tableButtons.add(this.btnSearch);
	///* 216:    */     
	/* 217:    */ 
	/* 218:    */ 
	/* 219:    */ 
	///* 220:230 */     JPanel buttonPane = new JPanel();
	///* 221:231 */     buttonPane.setLayout(new FlowLayout(2));
	///* 222:232 */     getContentPane().add(buttonPane, "South");
	///* 223:    */     
	///* 224:234 */     JButton okButton = new JButton("OK");
	///* 225:235 */     okButton.addActionListener(new ActionListener()
	///* 226:    */     {
	///* 227:    */       public void actionPerformed(ActionEvent arg0)
	///* 228:    */       {
	///* 229:237 */         RoofDialog.this.onSave();
	///* 230:    */       }
	///* 231:239 */     });
	///* 232:240 */     okButton.setActionCommand("OK");
	///* 233:241 */     buttonPane.add(okButton);
	///* 234:242 */     getRootPane().setDefaultButton(okButton);
	///* 235:    */     
	/* 236:    */ 
	///* 237:245 */     JButton cancelButton = new JButton("Cancel");
	///* 238:246 */     cancelButton.addActionListener(new ActionListener()
	///* 239:    */     {
	///* 240:    */       public void actionPerformed(ActionEvent e)
	///* 241:    */       {
	///* 242:249 */         RoofDialog.this.dispose();
	///* 243:    */       }
	///* 244:251 */     });
	///* 245:252 */     cancelButton.setActionCommand("Cancel");
	///* 246:253 */     buttonPane.add(cancelButton);
	///* 247:    */   }
	/* 248:    */   
	///* 249:    */   protected void onSave() {}
	///* 250:    */   
	///* 251:    */   protected void onAddPerson() {}
	///* 252:    */   
	///* 253:    */   protected void onRemovePerson(int[] rowsId) {}
	///* 254:    */   
	///* 255:    */   protected void onSearch() {}
	///* 256:    */   
	///* 257:    */   protected JLabel getLblTombType()
	///* 258:    */   {
	///* 259:275 */     return this.lblTombType;
	///* 260:    */   }
	///* 261:    */   
	///* 262:    */   public JLabel getLblReligion()
	///* 263:    */   {
	///* 264:279 */     return this.lblReligion;
	///* 265:    */   }
	///* 266:    */   
	///* 267:    */   public JLabel getLblTombData()
	///* 268:    */   {
	///* 269:282 */     return this.lblTombData;
	///* 270:    */   }
	///* 271:    */   
	///* 272:    */   public JLabel getLblWikipediaArticle()
	///* 273:    */   {
	///* 274:285 */     return this.lblWikipediaArticle;
	///* 275:    */   }
	///* 276:    */   
	///* 277:    */   public JLabel getLblImage()
	///* 278:    */   {
	///* 279:288 */     return this.lblImage;
	///* 280:    */   }
	///* 281:    */   
	///* 282:    */   public JLabel getLblHistoric()
	///* 283:    */   {
	///* 284:291 */     return this.lblHistoric;
	///* 285:    */   }
	///* 286:    */   

	/* 291:    */   
	///* 292:    */   public JComboBox getCbHistoric()
	///* 293:    */   {
	///* 294:297 */     return this.cbHistoric;
	///* 295:    */   }
	/* 296:    */ 


	//public class DefaultListModel {
	//	String[] items;
	//	public DefaultListModel(String[] items){
	//		items=this.items;
	//	}
	//}

}

