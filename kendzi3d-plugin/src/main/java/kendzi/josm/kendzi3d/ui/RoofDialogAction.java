package kendzi.josm.kendzi3d.ui;
import java.awt.Component;
/*   4:    */ import java.awt.Font;
/*   5:    */ import java.awt.event.ActionEvent;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.concurrent.ExecutorService;
/*  11:    */ import javax.swing.AbstractAction;
/*  12:    */ import javax.swing.AbstractCellEditor;
/*  13:    */ import javax.swing.ActionMap;
/*  14:    */ import javax.swing.ImageIcon;
/*  15:    */ import javax.swing.InputMap;
/*  16:    */ import javax.swing.JComboBox;
/*  17:    */ import javax.swing.JLabel;
/*  18:    */ import javax.swing.JOptionPane;
import javax.swing.JPanel;
/*  19:    */ import javax.swing.JRootPane;
/*  20:    */ import javax.swing.JTable;
/*  21:    */ import javax.swing.JTextField;
/*  22:    */ import javax.swing.KeyStroke;
/*  23:    */ import javax.swing.table.TableCellEditor;
/*  24:    */ import javax.swing.table.TableColumn;
/*  25:    */ import javax.swing.table.TableColumnModel;
///*  26:    */ import kendzi.josm.plugin.tomb.dto.PersonModel;
///*  27:    */ import kendzi.josm.plugin.tomb.util.StringUtil;
/*  28:    */ import org.openstreetmap.josm.Main;
/*  29:    */ import org.openstreetmap.josm.actions.DownloadPrimitiveAction;
/*  30:    */ import org.openstreetmap.josm.command.AddCommand;
/*  31:    */ import org.openstreetmap.josm.command.ChangeCommand;
/*  32:    */ import org.openstreetmap.josm.data.UndoRedoHandler;
/*  33:    */ import org.openstreetmap.josm.data.osm.DataSet;
/*  34:    */ import org.openstreetmap.josm.data.osm.Node;
/*  35:    */ import org.openstreetmap.josm.data.osm.OsmPrimitive;
/*  36:    */ import org.openstreetmap.josm.data.osm.OsmPrimitiveType;
/*  37:    */ import org.openstreetmap.josm.data.osm.PrimitiveId;
/*  38:    */ import org.openstreetmap.josm.data.osm.Relation;
/*  39:    */ import org.openstreetmap.josm.data.osm.RelationMember;
/*  40:    */ import org.openstreetmap.josm.data.osm.SimplePrimitiveId;
/*  41:    */ import org.openstreetmap.josm.data.osm.Way;
/*  42:    */ import org.openstreetmap.josm.gui.layer.OsmDataLayer;
/*  43:    */ import org.openstreetmap.josm.gui.tagging.ac.AutoCompletingTextField;
/*  44:    */ import org.openstreetmap.josm.tools.I18n;
/*  45:    */ import org.openstreetmap.josm.tools.ImageProvider;
/*  46:    */ 
@SuppressWarnings("serial")
public class RoofDialogAction 
//extends RoofDialog 
{




	//	/*  50:    */   private static final String ROLE_MEMORIAL = "memorial";
	//	/*  51:    */   private static final String ROLE_TOMB = "tomb";
	//	/*  52:    */   private static final String KEY_FROM_FAMILY = "family_name";
	//	/*  53:    */   private static final String KEY_LIVED_IN = "lived_in";
	//	/*  54:    */   private static final String KEY_DESCRIPTION = "description";
	//	/*  55:    */   private static final String KEY_DIED = "died";
	//	/*  56:    */   private static final String KEY_BORN = "born";
	//	/*  57:    */   private static final String KEY_NAME = "name";
	//	/*  58:    */   private static final String KEY_PERSON = "person";
	//	/*  59:    */   private static final String KEY_TYPE = "type";
	//	/*  60:    */   private static final String KEY_IMAGE = "image";
	//	/*  61:    */   private static final String KEY_WIKIPEDIA = "wikipedia";
	//	/*  62:    */   private static final String KEY_RELIGION = "religion";
	//	/*  63:    */   private static final String KEY_HISTORIC = "historic";
	//	/*  64:    */   private static final String KEY_TOMB = "tomb";
	//	/*  65:    */   private static final String VALUE_TOMB = "tomb";
	//	/*  66:    */   private static final String VALUE_MEMORIAL = "memorial";
	//	/*  67:    */   private List<PersonModel> persons;
	//	/*  68:    */   private Set<Relation> personsRemoved;
	//	/*  69:    */   private OsmPrimitive tombPrimitive;
	//	/*  70:    */   private TombDialogPersonTableModel personTableModel;
	/*  71:    */   
	//	/*  72:    */   public RoofDialogAction()
	//	/*  73:    */   {
	////	/*  74: 91 */     bindHotKey();
	//	/*  75:    */     
	////	/*  76: 93 */     localize();
	//	/*  77:    */     
	////	/*  78: 95 */     loadIcon();
	//	/*  79:    */     
	////	/*  80: 97 */     cellRenderer();
	//	/*  81:    */     
	//	/*  82: 99 */     setupTombTypeCombo();
	//	/*  83:    */     
	////	/*  84:101 */     setupHistoricCombo();
	//	/*  85:    */   }
	/*  86:    */   


//	public RoofDialogAction(JPanel panel) {
//		super(panel);
//		setupTombTypeCombo();
//	}




	/* 117:    */   
	//	/* 118:    */   private void setupHistoricCombo()
	//	/* 119:    */   {
	//	/* 120:142 */     IconListRenderer ilr = new IconListRenderer()
	//	/* 121:    */     {
	//	/* 122:    */       private static final long serialVersionUID = 1L;
	//	/* 123:    */       
	//	/* 124:    */       public ImageIcon loadImage(String key)
	//	/* 125:    */       {
	//	/* 126:    */         try
	//	/* 127:    */         {
	//	/* 128:151 */           return ImageProvider.get("historic_" + key + ".png");
	//	/* 129:    */         }
	//	/* 130:    */         catch (Exception e) {}
	//	/* 131:155 */         return null;
	//	/* 132:    */       }
	//	/* 133:    */       
	//	/* 134:    */       public String tr(String str)
	//	/* 135:    */       {
	//	/* 136:161 */         return I18n.tr(str, new Object[0]);
	//	/* 137:    */       }
	//	/* 138:164 */     };
	//	/* 139:165 */     getCbHistoric().setEditable(true);
	//	/* 140:    */     
	//	/* 141:167 */     getCbHistoric().setRenderer(ilr);
	//	/* 142:168 */     AutoCompletingTextField tf = new AutoCompletingTextField();
	//	/* 143:    */     
	//	/* 144:170 */     getCbTombType().setEditor(tf);
	//	/* 145:    */   }
	/* 146:    */   
	//	/* 147:    */   private void cellRenderer() {}
	/* 148:    */   




	//	/* 149:    */   public class BigFontCellEditor extends AbstractCellEditor implements TableCellEditor
	//	/* 152:    */   {
	//	/* 153:181 */     JTextField component = new JTextField();
	//	/* 154:    */     
	//	/* 155:    */     public BigFontCellEditor() {}
	//	/* 156:    */     
	//	/* 157:    */     public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex)
	//	/* 158:    */     {
	//	/* 159:183 */       this.component.setText((String)value);
	//	/* 160:184 */       this.component.setFont(new Font("Tahoma", 0, 14));
	//	/* 161:185 */       return this.component;
	//	/* 162:    */     }
	//	/* 163:    */     
	//	/* 164:    */     public Object getCellEditorValue()
	//	/* 165:    */     {
	//	/* 166:189 */       return this.component.getText();
	//	/* 167:    */     }
	//	/* 168:    */   }
	//	/* 169:    */   
	//	/* 170:    */   private void loadIcon()
	//	/* 171:    */   {
	//	/* 172:197 */     ImageIcon imageIcon = ImageProvider.get("tomb_icon.png");
	//	/* 173:199 */     if (imageIcon != null) {
	//	/* 174:200 */       setIconImage(imageIcon.getImage());
	//	/* 175:    */     }
	//	/* 176:    */   }
	//	/* 177:    */   
	//	/* 178:    */   public void bindHotKey()
	//	/* 179:    */   {
	//	/* 180:208 */     KeyStroke key1 = KeyStroke.getKeyStroke(78, 128);
	//	/* 181:    */     
	//	/* 182:    */ 
	//	/* 183:    */ 
	//	/* 184:212 */     getRootPane().getInputMap(2).put(key1, "new_person");
	//	/* 185:213 */     getRootPane().getActionMap().put("new_person", new AbstractAction()
	//	/* 186:    */     {
	//	/* 187:    */       public void actionPerformed(ActionEvent e)
	//	/* 188:    */       {
	//	/* 189:217 */         TombDialogAction.this.onAddPerson();
	//	/* 190:    */       }
	//	/* 191:    */     });
	//	/* 192:    */   }
	/* 193:    */   












	//	/* 194:    */   public void Load(OsmPrimitive tombPrimitive)
	//	/* 195:    */   {
	//	/* 196:223 */     this.tombPrimitive = tombPrimitive;
	//	/* 197:224 */     fillAttributes(tombPrimitive);
	//	/* 198:    */     
	//	/* 199:226 */     this.persons = loadPersons(tombPrimitive);
	//	/* 200:227 */     this.personsRemoved = new HashSet();
	//	/* 201:    */     
	//	/* 202:229 */     fillPersons(this.persons, this.personsRemoved);
	//	/* 203:    */   }
	/* 204:    */   











	//	/* 205:    */   private void fillPersons(List<PersonModel> persons, Set<Relation> personsRemoved)
	//	/* 206:    */   {
	//	/* 207:234 */     this.personTableModel = new TombDialogPersonTableModel(persons)
	//	/* 208:    */     {
	//	/* 209:    */       public String tr(String str)
	//	/* 210:    */       {
	//	/* 211:237 */         return I18n.tr(str, new Object[0]);
	//	/* 212:    */       }
	//	/* 213:240 */     };
	//	/* 214:241 */     this.personsTable.setModel(this.personTableModel);
	//	/* 215:    */     
	//	/* 216:243 */     this.personsTable.getColumnModel().getColumn(1).setPreferredWidth(30);
	//	/* 217:244 */     this.personsTable.getColumnModel().getColumn(2).setPreferredWidth(30);
	//	/* 218:    */   }
	//	/* 219:    */   
	//	/* 220:    */   private List<PersonModel> loadPersons(OsmPrimitive tombPrimitive)
	//	/* 221:    */   {
	//	/* 222:249 */     List<PersonModel> ret = new ArrayList();
	//	/* 223:    */     
	//	/* 224:251 */     List<OsmPrimitive> referrers = tombPrimitive.getReferrers();
	//	/* 225:253 */     for (OsmPrimitive osmPrimitive : referrers) {
	//	/* 226:254 */       if (((osmPrimitive instanceof Relation)) && ("person".equals(osmPrimitive.get("type"))))
	//	/* 227:    */       {
	//	/* 228:257 */         PersonModel pm = convert((Relation)osmPrimitive);
	//	/* 229:258 */         ret.add(pm);
	//	/* 230:    */       }
	//	/* 231:    */     }
	//	/* 232:262 */     return ret;
	//	/* 233:    */   }
	//	/* 234:    */   
	//	/* 235:    */   private PersonModel convert(Relation osmPrimitive)
	//	/* 236:    */   {
	//	/* 237:266 */     PersonModel pm = new PersonModel();
	//	/* 238:267 */     pm.setName(osmPrimitive.get("name"));
	//	/* 239:268 */     pm.setBorn(osmPrimitive.get("born"));
	//	/* 240:269 */     pm.setDied(osmPrimitive.get("died"));
	//	/* 241:270 */     pm.setWikipedia(osmPrimitive.get("wikipedia"));
	//	/* 242:271 */     pm.setDescription(osmPrimitive.get("description"));
	//	/* 243:272 */     pm.setLivedIn(osmPrimitive.get("lived_in"));
	//	/* 244:273 */     pm.setFromFamily(osmPrimitive.get("family_name"));
	//	/* 245:    */     
	//	/* 246:275 */     pm.setRelation(osmPrimitive);
	//	/* 247:276 */     return pm;
	//	/* 248:    */   }
	//	/* 249:    */   
	//	/* 250:    */   private void fillAttributes(OsmPrimitive tombPrimitive)
	//	/* 251:    */   {
	//	/* 252:281 */     String historic = defaultValue(tombPrimitive.get("historic"), "tomb");
	//	/* 253:    */     
	//	/* 254:283 */     getCbHistoric().setSelectedItem(historic);
	//	/* 255:284 */     this.cbTombType.setSelectedItem(tombPrimitive.get("tomb"));
	//	/* 256:285 */     this.cbReligion.setSelectedItem(tombPrimitive.get("religion"));
	//	/* 257:    */     
	//	/* 258:287 */     this.txtWikipedia.setText(tombPrimitive.get("wikipedia"));
	//	/* 259:288 */     this.txtImage.setText(tombPrimitive.get("image"));
	//	/* 260:    */   }
	//	/* 261:    */   
	//	/* 262:    */   protected void onSave()
	//	/* 263:    */   {
	//	/* 264:294 */     save();
	//	/* 265:    */     
	//	/* 266:    */ 
	//	/* 267:297 */     dispose();
	//	/* 268:    */   }
	//	/* 269:    */   
	//	/* 270:    */   protected void onAddPerson()
	//	/* 271:    */   {
	//	/* 272:303 */     stopEdit();
	//	/* 273:    */     
	//	/* 274:305 */     int rowId = this.personTableModel.addPersonModel(new PersonModel());
	//	/* 275:    */     
	//	/* 276:    */ 
	//	/* 277:308 */     this.personsTable.changeSelection(rowId, 0, false, false);
	//	/* 278:309 */     this.personsTable.requestFocus();
	//	/* 279:    */   }
	//	/* 280:    */   
	//	/* 281:    */   protected void onRemovePerson(int[] rowsId)
	//	/* 282:    */   {
	//	/* 283:318 */     PersonModel pm = this.personTableModel.removePersonModel(rowsId);
	//	/* 284:320 */     if ((pm != null) && (pm.getRelation() != null)) {
	//	/* 285:321 */       this.personsRemoved.add(pm.getRelation());
	//	/* 286:    */     }
	//	/* 287:    */   }
	//	/* 288:    */   
	//	/* 289:    */   private void save()
	//	/* 290:    */   {
	//	/* 291:327 */     stopEdit();
	//	/* 292:    */     
	//	/* 293:329 */     savePersons(this.tombPrimitive, this.persons, this.personsRemoved);
	//	/* 294:    */     
	//	/* 295:331 */     saveTombPrimitive(this.tombPrimitive);
	//	/* 296:    */   }
	//	/* 297:    */   
	//	/* 298:    */   private void stopEdit()
	//	/* 299:    */   {
	//	/* 300:335 */     if (this.personsTable.isEditing()) {
	//	/* 301:336 */       this.personsTable.getCellEditor().stopCellEditing();
	//	/* 302:    */     }
	//	/* 303:    */   }
	//	/* 304:    */   
	//	/* 305:    */   private void saveTombPrimitive(OsmPrimitive tombPrimitive)
	//	/* 306:    */   {
	//	/* 307:342 */     OsmPrimitive newPrimitive = null;
	//	/* 308:343 */     if ((tombPrimitive instanceof Node)) {
	//	/* 309:344 */       newPrimitive = new Node((Node)tombPrimitive);
	//	/* 310:345 */     } else if ((tombPrimitive instanceof Way)) {
	//	/* 311:346 */       newPrimitive = new Way((Way)tombPrimitive);
	//	/* 312:    */     }
	//	/* 313:349 */     injectTombPrimitive(newPrimitive);
	//	/* 314:    */     
	//	/* 315:    */ 
	//	/* 316:    */ 
	//	/* 317:353 */     Main.main.undoRedo.add(new ChangeCommand(tombPrimitive, newPrimitive));
	//	/* 318:    */   }
	//	/* 319:    */   
	//	/* 320:    */   private void injectTombPrimitive(OsmPrimitive n)
	//	/* 321:    */   {
	//	/* 322:360 */     n.put("historic", defaultValue((String)getCbHistoric().getSelectedItem(), "tomb"));
	//	/* 323:    */     
	//	/* 324:362 */     n.put("tomb", nullOnBlank((String)this.cbTombType.getSelectedItem()));
	//	/* 325:363 */     n.put("religion", nullOnBlank((String)this.cbReligion.getSelectedItem()));
	//	/* 326:    */     
	//	/* 327:365 */     n.put("wikipedia", nullOnBlank(this.txtWikipedia.getText()));
	//	/* 328:366 */     n.put("image", nullOnBlank(this.txtImage.getText()));
	//	/* 329:    */   }
	//	/* 330:    */   
	//	/* 331:    */   private String defaultValue(String str, String defaultValue)
	//	/* 332:    */   {
	//	/* 333:370 */     if (StringUtil.isBlankOrNull(str)) {
	//	/* 334:371 */       return defaultValue;
	//	/* 335:    */     }
	//	/* 336:373 */     return str;
	//	/* 337:    */   }
	//	/* 338:    */   
	//	/* 339:    */   private String nullOnBlank(String str)
	//	/* 340:    */   {
	//	/* 341:377 */     if (str == null) {
	//	/* 342:378 */       return null;
	//	/* 343:    */     }
	//	/* 344:381 */     if ("".equals(str.trim())) {
	//	/* 345:382 */       return null;
	//	/* 346:    */     }
	//	/* 347:385 */     return str;
	//	/* 348:    */   }
	//	/* 349:    */   
	//	/* 350:    */   private void savePersons(OsmPrimitive tombPrimitive, List<PersonModel> persons2, Set<Relation> personsRemoved)
	//	/* 351:    */   {
	//	/* 352:391 */     for (PersonModel pm : persons2) {
	//	/* 353:392 */       if (pm.getRelation() != null) {
	//	/* 354:393 */         updateRelation(pm.getRelation(), tombPrimitive, pm);
	//	/* 355:    */       } else {
	//	/* 356:395 */         saveRelation(tombPrimitive, pm);
	//	/* 357:    */       }
	//	/* 358:    */     }
	//	/* 359:399 */     for (Relation relation : personsRemoved) {
	//	/* 360:400 */       removeRelation(tombPrimitive, relation);
	//	/* 361:    */     }
	//	/* 362:    */   }
	//	/* 363:    */   
	//	/* 364:    */   private void removeRelation(OsmPrimitive node, Relation relation)
	//	/* 365:    */   {
	//	/* 366:407 */     Relation newRelation = new Relation(relation);
	//	/* 367:    */     
	//	/* 368:409 */     boolean changed = false;
	//	/* 369:411 */     for (int i = newRelation.getMembersCount() - 1; i >= 0; i--)
	//	/* 370:    */     {
	//	/* 371:413 */       RelationMember m = newRelation.getMember(i);
	//	/* 372:415 */       if ((m.getType().equals(node.getType())) && (m.getUniqueId() == node.getUniqueId()) && (("tomb".equals(m.getRole())) || ("memorial".equals(m.getRole()))))
	//	/* 373:    */       {
	//	/* 374:419 */         newRelation.removeMember(i);
	//	/* 375:420 */         changed = true;
	//	/* 376:    */       }
	//	/* 377:    */     }
	//	/* 378:428 */     if (changed) {
	//	/* 379:429 */       Main.main.undoRedo.add(new ChangeCommand(relation, newRelation));
	//	/* 380:    */     }
	//	/* 381:    */   }
	//	/* 382:    */   
	//	/* 383:    */   private String getRoleForTombPrimitive(OsmPrimitive tombPrimitive)
	//	/* 384:    */   {
	//	/* 385:446 */     String historicValue = tombPrimitive.get("historic");
	//	/* 386:447 */     if ("memorial".equals(historicValue)) {
	//	/* 387:448 */       return "memorial";
	//	/* 388:    */     }
	//	/* 389:450 */     return "tomb";
	//	/* 390:    */   }
	//	/* 391:    */   
	//	/* 392:    */   private boolean isMemberRepresentsThisPrimitive(RelationMember member, OsmPrimitive tombPrimitive)
	//	/* 393:    */   {
	//	/* 394:461 */     return (("tomb".equals(member.getRole())) || ("memorial".equals(member.getRole()))) && (member.getMember().equals(tombPrimitive));
	//	/* 395:    */   }
	//	/* 396:    */   
	//	/* 397:    */   private void saveRelation(OsmPrimitive tombPrimitive, PersonModel pm)
	//	/* 398:    */   {
	//	/* 399:468 */     Relation newRelation = new Relation();
	//	/* 400:469 */     newRelation.addMember(new RelationMember(getRoleForTombPrimitive(tombPrimitive), tombPrimitive));
	//	/* 401:    */     
	//	/* 402:471 */     injectRelation(pm, newRelation);
	//	/* 403:    */     
	//	/* 404:473 */     newRelation.put("type", "person");
	//	/* 405:    */     
	//	/* 406:475 */     Main.main.undoRedo.add(new AddCommand(newRelation));
	//	/* 407:    */   }
	//	/* 408:    */   
	//	/* 409:    */   private void updateRelation(Relation relation, OsmPrimitive tombPrimitive, PersonModel pm)
	//	/* 410:    */   {
	//	/* 411:480 */     Relation newRelation = new Relation(relation);
	//	/* 412:    */     
	//	/* 413:    */ 
	//	/* 414:483 */     boolean relationHaveThisTomb = false;
	//	/* 415:484 */     for (int i = 0; i < relation.getMembersCount(); i++)
	//	/* 416:    */     {
	//	/* 417:485 */       RelationMember member = relation.getMember(i);
	//	/* 418:486 */       if (isMemberRepresentsThisPrimitive(member, tombPrimitive)) {
	//	/* 419:488 */         relationHaveThisTomb = true;
	//	/* 420:    */       }
	//	/* 421:    */     }
	//	/* 422:493 */     if (!relationHaveThisTomb) {
	//	/* 423:495 */       newRelation.addMember(new RelationMember(getRoleForTombPrimitive(tombPrimitive), tombPrimitive));
	//	/* 424:    */     }
	//	/* 425:498 */     injectRelation(pm, newRelation);
	//	/* 426:    */     
	//	/* 427:500 */     newRelation.put("type", "person");
	//	/* 428:    */     
	//	/* 429:502 */     Main.main.undoRedo.add(new ChangeCommand(relation, newRelation));
	//	/* 430:    */   }
	//	/* 431:    */   
	//	/* 432:    */   public void injectRelation(PersonModel pm, Relation newRelation)
	//	/* 433:    */   {
	//	/* 434:512 */     newRelation.put("name", nullOnBlank(pm.getName()));
	//	/* 435:513 */     newRelation.put("born", nullOnBlank(pm.getBorn()));
	//	/* 436:514 */     newRelation.put("died", nullOnBlank(pm.getDied()));
	//	/* 437:515 */     newRelation.put("wikipedia", nullOnBlank(pm.getWikipedia()));
	//	/* 438:516 */     newRelation.put("description", nullOnBlank(pm.getDescription()));
	//	/* 439:    */     
	//	/* 440:518 */     newRelation.put("lived_in", nullOnBlank(pm.getLivedIn()));
	//	/* 441:519 */     newRelation.put("family_name", nullOnBlank(pm.getFromFamily()));
	//	/* 442:    */   }
	//	/* 443:    */   
	//	/* 444:    */   public void localize()
	//	/* 445:    */   {
	//	/* 446:    */     try
	//	/* 447:    */     {
	//	/* 448:525 */       getLblHistoric().setText(I18n.tr(getLblHistoric().getText(), new Object[0]));
	//	/* 449:526 */       getLblTombType().setText(I18n.tr(getLblTombType().getText(), new Object[0]));
	//	/* 450:    */       
	//	/* 451:528 */       getLblReligion().setText(I18n.tr(getLblReligion().getText(), new Object[0]));
	//	/* 452:529 */       getLblTombData().setText(I18n.tr(getLblTombData().getText(), new Object[0]));
	//	/* 453:    */       
	//	/* 454:    */ 
	//	/* 455:532 */       getLblWikipediaArticle().setText("- " + I18n.tr("wikipedia article", new Object[0]));
	//	/* 456:533 */       getLblImage().setText("- " + I18n.tr("image", new Object[0]));
	//	/* 457:    */     }
	//	/* 458:    */     catch (Exception e)
	//	/* 459:    */     {
	//	/* 460:536 */       e.printStackTrace();
	//	/* 461:    */     }
	//	/* 462:    */   }
	//	/* 463:    */   
	//	/* 464:    */   protected void onSearch()
	//	/* 465:    */   {
	//	/* 466:    */     try
	//	/* 467:    */     {
	//	/* 468:543 */       PersonSearchDialogAction dialog = new PersonSearchDialogAction();
	//	/* 469:544 */       dialog.setDefaultCloseOperation(2);
	//	/* 470:545 */       dialog.setModal(true);
	//	/* 471:546 */       dialog.setVisible(true);
	//	/* 472:    */       
	//	/* 473:548 */       Long relationId = dialog.getSelectedRelationId();
	//	/* 474:549 */       if (relationId == null) {
	//	/* 475:550 */         return;
	//	/* 476:    */       }
	//	/* 477:553 */       SimplePrimitiveId primitiveId = new SimplePrimitiveId(relationId.longValue(), OsmPrimitiveType.RELATION);
	//	/* 478:    */       
	//	/* 479:555 */       List<PrimitiveId> ids = new ArrayList();
	//	/* 480:556 */       ids.add(primitiveId);
	//	/* 481:    */       
	//	/* 482:558 */       DownloadPrimitiveAction.processItems(false, ids, false, true);
	//	/* 483:    */       
	//	/* 484:560 */       submitAddTableRowAfterDownload(primitiveId);
	//	/* 485:    */     }
	//	/* 486:    */     catch (Exception e)
	//	/* 487:    */     {
	//	/* 488:565 */       e.printStackTrace();
	//	/* 489:    */       
	//	/* 490:567 */       JOptionPane.showMessageDialog(null, I18n.tr("Error" + e, new Object[0]), "Error", 0);
	//	/* 491:    */     }
	//	/* 492:    */   }
	//	/* 493:    */   
	//	/* 494:    */   private void submitAddTableRowAfterDownload(final SimplePrimitiveId primitiveId)
	//	/* 495:    */   {
	//	/* 496:581 */     Runnable showErrorsAndWarnings = new Runnable()
	//	/* 497:    */     {
	//	/* 498:    */       public void run()
	//	/* 499:    */       {
	//	/* 500:585 */         OsmDataLayer layer = Main.main.getEditLayer();
	//	/* 501:586 */         Relation relation = (Relation)layer.data.getPrimitiveById(primitiveId);
	//	/* 502:    */         
	//	/* 503:588 */         PersonModel pm = TombDialogAction.this.convert(relation);
	//	/* 504:589 */         TombDialogAction.this.personTableModel.addPersonModel(pm);
	//	/* 505:    */       }
	//	/* 506:646 */     };
	//	/* 507:647 */     Main.worker.submit(showErrorsAndWarnings);
	//	/* 508:    */   }
	//	/* 509:    */ }
}
