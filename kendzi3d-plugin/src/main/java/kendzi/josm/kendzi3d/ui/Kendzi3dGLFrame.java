package kendzi.josm.kendzi3d.ui;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.MaskFormatter;

import kendzi.jogl.camera.CameraMoveListener;
import kendzi.jogl.camera.SimpleMoveAnimator;
import kendzi.jogl.model.render.ModelRender;
import kendzi.jogl.texture.library.TextureLibraryStorageService;
import kendzi.josm.kendzi3d.jogl.RenderJOSM;
import kendzi.josm.kendzi3d.jogl.model.NewBuilding;
import kendzi.josm.kendzi3d.jogl.model.building.model.roof.RoofOrientation;
import kendzi.josm.kendzi3d.jogl.model.shape.Cylinder;
import kendzi.josm.kendzi3d.jogl.model.shape.SphereIco;
import kendzi.josm.kendzi3d.jogl.photos.PhotoParmPanel;
import kendzi.josm.kendzi3d.jogl.selection.Selection;
import kendzi.josm.kendzi3d.jogl.selection.TransferableRoof;
import kendzi.josm.kendzi3d.service.MetadataCacheService;
import kendzi.josm.kendzi3d.service.ModelCacheService;
import kendzi.josm.kendzi3d.ui.fps.FpsChangeEvent;
import kendzi.josm.kendzi3d.ui.fps.FpsListener;
import kendzi.kendzi3d.josm.model.perspective.Perspective;

import org.apache.log4j.Logger;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.command.ChangePropertyCommand;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.Way;
import org.openstreetmap.josm.tools.I18n;

import com.google.inject.Inject;
import com.jogamp.common.GlueGenVersion;
import com.jogamp.opengl.JoglVersion;
import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Main application window. Display 3d view, panel with menu and layers.
 * 
 * @author Tomasz Kędziora (Kendzi)
 */
public class Kendzi3dGLFrame extends Frame implements WindowListener, FpsListener, ActionListener, DragGestureListener , MouseWheelListener
{

	private long relativeTimeSpent; // time spent plus a time fixed by developer 
	private long timeSpent; // time spent from TimeAndFps
	private int notches = 0; // -1 for zoom in and 1 for zoom out

	boolean activ = false; // select contains something or not
	JButton buttonColorWall;
	JButton buttonColorRoof;
	JButton buttonChangeHeight;
	JButton buttonChangeOrientation;

	SpinnerModel model = new SpinnerNumberModel(2.5, 0.1, 20, 0.1);     
	JSpinner spinner = new JSpinner(model);

	JList list ;
	public JPanel renderPanel;

	RoofDialogOrientation roofDialogOrientation;

	private Map<String, ImageIcon> imageMap;

	MethodsForKendzi3dGLFrame method = new MethodsForKendzi3dGLFrame();

	Selection selection = getLastSelection();

	Collection<OsmPrimitive> selVal = getOsmPrimitveFromSelectedBuilding(selection);

	JPanel jpanel = new JPanel();

	/**
	 * Model renderer.
	 */
	@Inject
	private ModelRender modelRenderInject;

	/**
	 * Texture library service.
	 */
	@Inject
	private TextureLibraryStorageService textureLibraryStorageService;

	/**
	 * Metadata cache service.
	 */
	@Inject
	private MetadataCacheService metadataCacheService;

	/**
	 * Model cache service
	 */
	@Inject
	private ModelCacheService modelCacheService;

	private Perspective perspectivePoint2d;

	/** Log. */
	private static final Logger log = Logger.getLogger(Kendzi3dGLFrame.class);


	private static final long serialVersionUID = 1L;

	/**
	 * Panel default width.
	 */
	private static final int DEF_WIDTH = 512;

	/**
	 * Panel default height.
	 */
	private static final int DEF_HEIGHT = 512;

	/**
	 * Canvas.
	 */
	private Canvas canvas;

	/**
	 * Display fps.
	 */
	private JTextField jTFFps;

	/**
	 * Display 3d run time.
	 */
	private JTextField jTFTime;

	private PhotoParmPanel photoParmPanel;

	@Inject
	Kendzi3dGLEventListener canvasListener;

	/**
	 * 3d view animator.
	 */
	private AnimatorBase animator;

	/**
	 * Constructor.
	 */
	public Kendzi3dGLFrame() {
		super("Kendzi 3D");
	}

	/**
	 * Init ui after bean is created.
	 */

	public class MyDropTargetListener extends DropTargetAdapter {

		private DropTarget dropTarget;
		private JPanel panel;

		public MyDropTargetListener() {
			super();
		}

		public MyDropTargetListener(JPanel panel) {
			this.panel = panel;

			dropTarget = new DropTarget(panel, this);
		}

		// === drop ===
		public void drop(DropTargetDropEvent event) {

			try {

				Transferable tr = event.getTransferable();

				String obj = (String) tr.getTransferData(TransferableRoof.roofFlavor);

				selection = getLastSelection();
				if (selection != null) {

					event.isDataFlavorSupported(TransferableRoof.roofFlavor);

					event.acceptDrop(DnDConstants.ACTION_COPY);

					event.dropComplete(true);

					selVal = getOsmPrimitveFromSelectedBuilding(selection);

					String value = null;
					Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "3dr:type", value));

					method.switchRoofType(obj, selVal);// set for selected

				}
				else{

					JOptionPane.showMessageDialog(null, I18n.tr("Please select one building", new Object[0]), "Error", 0);
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.info("Error " + e);
			}
		}
	}

	// =======drag ===
	public void dragGestureRecognized(DragGestureEvent event) {
		Cursor cursor = null;
		Object sel= null;
		int[] selectedIx = list.getSelectedIndices();
		for (int i = 0; i < selectedIx.length; i++) {
			sel = list.getModel().getElementAt(selectedIx[i]);
		}

		if (event.getDragAction() == DnDConstants.ACTION_COPY) {
			cursor = DragSource.DefaultCopyDrop;
		}

		event.startDrag(cursor, new TransferableRoof(sel));
	}

	public void initUI() {

		Container c = this;
		c.setLayout(new BorderLayout());
		renderPanel = makeRenderPanel();
		c.add(renderPanel, BorderLayout.CENTER);
		// a row of text fields
		JPanel ctrls = new JPanel();
		ctrls.setLayout(new BoxLayout(ctrls, BoxLayout.X_AXIS));

		imageMap = createImageMap(method.roofTypes);
		list= new JList(method.roofTypes);
		list.setCellRenderer(new RoofListRenderer(imageMap));

		JScrollPane scroll = new JScrollPane(list);
		scroll.setPreferredSize(new Dimension(240, 200));

		new MyDropTargetListener(renderPanel);

		DragSource ds = new DragSource();
		ds.createDefaultDragGestureRecognizer(list, DnDConstants.ACTION_COPY,this);

		renderPanel.addMouseWheelListener(this); // renderPanel listen MouseWheel for zoom in/out


		buttonColorWall = new JButton("Color Wall");
		buttonColorRoof = new JButton("Color Roof");
		buttonChangeHeight = new JButton("Height");
		buttonChangeOrientation = new JButton("Orientation");

		buttonColorWall.setMnemonic(KeyEvent.VK_V); //push button Color
		// keyboard shortcut ALT + V
		buttonColorRoof.setMnemonic(KeyEvent.VK_R); //push button Color
		// keyboard shortcut ALT + R

		buttonColorWall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				selection = getLastSelection();
				if (selection != null){

					if (selection instanceof SphereIco.MyBuildingSelection){
						selVal = getOsmPrimitveFromSelectedShape(selection);
					}
					else if (selection instanceof NewBuilding.MyBuildingSelection)
					{
						selVal = getOsmPrimitveFromSelectedBuilding(selection);
					}

					// start Plugin
					AddAction add = new AddAction(selVal);
					add.startPluginForColorWall();

				}else{
					JOptionPane.showMessageDialog(null, I18n.tr("Please select one building", new Object[0]), "Error", 0);
				}
			}
		});

		buttonColorRoof.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selection = getLastSelection();
				if (selection != null){

					selVal = getOsmPrimitveFromSelectedBuilding(selection);

					// start Plugin
					AddAction add = new AddAction(selVal);
					add.startPluginForColorRoof();

				}else{
					JOptionPane.showMessageDialog(null, I18n.tr("Please select one building", new Object[0]), "Error", 0);
				}
			}
		});

		buttonChangeHeight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selection = getLastSelection();

				if (selection != null){

					selVal = getOsmPrimitveFromSelectedBuilding(selection);

					String height;

					Double d = (Double) spinner.getValue();

					String input  = String.valueOf(d);
					int value = input.indexOf(".");

					height = input.substring(0, value+2);

					Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:type:height",height));

				}else{
					JOptionPane.showMessageDialog(null, I18n.tr("Please select one building", new Object[0]), "Error", 0);
				}

			}
		});

		buttonChangeOrientation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selection = getLastSelection();

				if (selection != null){

					selVal = getOsmPrimitveFromSelectedBuilding(selection);

					String input = roofDialogOrientation.getNameFromComboBox(); 

					Main.main.undoRedo.add(new ChangePropertyCommand(selVal, "roof:orientation",getNameOrientation(input)));

				}else{
					JOptionPane.showMessageDialog(null, I18n.tr("Please select one building", new Object[0]), "Error", 0);
				}
			}
		});


		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner,"#.#");
		editor.getFormat().setGroupingUsed(false);
		spinner.setEditor(editor);
		spinner.setPreferredSize(new Dimension(50,20));

		jpanel.setPreferredSize(new Dimension(250, 512)); 
		jpanel.add(scroll);

		JPanel panelInterface = new JPanel();
		panelInterface.setPreferredSize(new Dimension(180,512));

		panelInterface.add(buttonColorWall); 
		panelInterface.add(buttonColorRoof);

		panelInterface.add(spinner);
		panelInterface.add(buttonChangeHeight);
		roofDialogOrientation = new RoofDialogOrientation(panelInterface);
		roofDialogOrientation.setupTypeCombo();
		panelInterface.add(buttonChangeOrientation);

		jpanel.add(panelInterface);

		this.jTFFps = new JTextField("Fps: unknown");
		this.jTFFps.setEditable(false);
		ctrls.add(this.jTFFps);

		this.jTFTime = new JTextField("Time Spent: 0 secs");
		this.jTFTime.setEditable(false);
		ctrls.add(this.jTFTime);

		c.add(ctrls, BorderLayout.SOUTH);
		c.add(jpanel, BorderLayout.LINE_START); //add panel to container

		addWindowListener(this);

		if (PhotoParmPanel.showPhotoPanel) {
			initPhotoFrame();
		}

		pack();
	}

	public String getNameOrientation(String input){
		char degrees= (char)186;
		if(input.equals("0"+degrees)){
			return RoofOrientation.along.toString();
		}else if(input.equals("90"+degrees)){
			return RoofOrientation.across.toString();
		}else if(input.equals("180"+degrees)){
			return RoofOrientation.straight.toString();
		}else if(input.equals("270"+degrees)){
			return RoofOrientation.reflex.toString();
		}
		return input;
	}

	//A convenience method for creating a MaskFormatter.
	protected MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	private Map<String, ImageIcon> createImageMap(String[] list) {
		Map<String, ImageIcon> map = new HashMap<>();
		for (String s : list) {
			map.put(s, new ImageIcon(Kendzi3dGLFrame.class.getResource("/images/RoofTypes/"  + s + ".jpg")));
		}
		return map;
	}

	/**
	 * 
	 * @return last selection for buildings
	 */
	public Selection getLastSelection() {
		Selection selection = null;
		try {
			selection = canvasListener.getObjectSelectionListener()
					.getLastSelection();

		} catch (NullPointerException e) {
			log.info("Error message from method: getLastSelection()");
		}
		return selection;
	}

	/**
	 * 
	 * @return all dataSet from the map
	 */
	public DataSet getDataSet() {
		return Main.main.getCurrentDataSet();
	}

	public class copyRenderOSM extends JosmAction{

	    private static final long serialVersionUID = 1L;

	    private RenderJOSM renderJosm;

	    private Kendzi3dGLEventListener kendzi3dGLEventListener;

		/**
		 * Constructor.
		 * @param renderJosm
		 * @param kendzi3dGLEventListener
		 */
		@Inject
		public copyRenderOSM(RenderJOSM renderJosm, Kendzi3dGLEventListener kendzi3dGLEventListener) {
			super(
					tr("Move camera"),
					"1306318146_build__24",
					tr("Move camera"),
					null,
					false
					);
			
			this.renderJosm = renderJosm;
			this.kendzi3dGLEventListener = kendzi3dGLEventListener;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
		}
		
		public void getPerspective2D(){ 		
			this.kendzi3dGLEventListener.getCamera();
		}
	}

	public Collection<OsmPrimitive> getOsmPrimitveFromSelectedBuilding(Selection selection) {

		boolean cond = false;

		DataSet dataset = getDataSet();

		NewBuilding newBuilding;
		Collection<OsmPrimitive> sel = new LinkedList<OsmPrimitive>();

		Way ways = null;
		try {
			for (Way way : dataset.getWays()) {
				ways = way;

				newBuilding = new NewBuilding(ways, perspectivePoint2d,
						modelRenderInject, metadataCacheService,
						textureLibraryStorageService);

				if (selection instanceof NewBuilding.MyBuildingSelection) {
					NewBuilding.MyBuildingSelection wo = (NewBuilding.MyBuildingSelection) selection;
					NewBuilding newB = wo.getNewBuildingInstance();

					if (newBuilding.getWay().getUniqueId() == newB.getWay()
							.getUniqueId()) {
						cond = true;
					}

					if (cond) {
						OsmPrimitive osmPrimitive = (OsmPrimitive) newB
								.getWay();
						sel.add(osmPrimitive);
					}

					cond = false;
				}

				selVal = sel;
			}
		} catch (NullPointerException e) {
			log.info("NullPointerException from getOsmPrimitveFromSelectedBuilding() " + e);
		}
		sel = null;
		return selVal;
	}

	public Collection<OsmPrimitive> getOsmPrimitveFromSelectedShape(Selection selection) {

		boolean cond = false;

		DataSet dataset = getDataSet();

		SphereIco sphere;
		Collection<OsmPrimitive> sel = new LinkedList<OsmPrimitive>();

		Node nodes = null;
		Collection<Node> dataSetNode = dataset.getNodes();
		try {
			for (Node node : dataSetNode) {
				nodes = node;

				if (selection instanceof SphereIco.MyBuildingSelection) {
					SphereIco.MyBuildingSelection wo = (SphereIco.MyBuildingSelection) selection;
					SphereIco newB = wo.getNewBuildingInstance();
					
					sphere = new SphereIco(nodes, newB.getPerspective(),
							modelRenderInject, metadataCacheService, modelCacheService);
					
					if (sphere.getNode().getUniqueId() == newB.getNode()
							.getUniqueId()) {
						cond = true;
					}

					if (cond) {
						OsmPrimitive osmPrimitive = (OsmPrimitive) newB
								.getNode();
						sel.add(osmPrimitive);
					}

					cond = false;
				}

				selVal = sel;
			}
		} catch (NullPointerException e) {
			log.info("NullPointerException from getOsmPrimitveFromSelectedShape " + e);
		}
		sel = null;
		return selVal;
	}

	private void initPhotoFrame() {
		JFrame photoFrame = new JFrame();
		this.photoParmPanel = new PhotoParmPanel();
		photoFrame.getContentPane().add(this.photoParmPanel);
		photoFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		photoFrame.pack();
		photoFrame.setVisible(true);

		this.photoParmPanel.addCameraChangeListener(this.canvasListener);
	}

	/**
	 * Make canvas panel.
	 * 
	 * @return panel with canvas
	 */
	private JPanel makeRenderPanel() {
		JPanel renderPane = new JPanel();
		renderPane.setLayout(new BorderLayout());
		renderPane.setOpaque(false);
		renderPane.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
		// XXX
		renderPane.setSize(640, 480);

		this.canvas = makeCanvas(renderPane);

		renderPane.add(this.canvas);
		renderPane.setVisible(true);

		renderPane.getComponentListeners();
		this.getWindowListeners();

		this.canvas.setFocusable(true);
		this.canvas.requestFocus(); // the canvas now has focus, so receives key

		return renderPane;
	}

	private Canvas makeCanvas(JPanel render) {

		logJoglManifest();

		// create a profile, in this case OpenGL 2 or later
		GLProfile profile = GLProfile.get(GLProfile.GL2);

		// configure context
		GLCapabilities capabilities = new GLCapabilities(profile);

		setUpCapabilities(capabilities);

		// initialize a GLDrawable of your choice
		GLCanvas canvas = new GLCanvas(capabilities);

		canvas.addGLEventListener(this.canvasListener);

		this.canvasListener.closeEvent(new CloseEvent() {
			@Override
			public void closeAction() {
				windowClosing(null);
			}
		});

		// selection/edition listener first!
		this.canvasListener.registerMouseSelectionListener(canvas);
		this.canvasListener.registerMoveListener(canvas);

		this.canvasListener.addFpsChangeListener(this);

		// Center frame
		// render.setLocationRelativeTo(null);

		this.animator = new FPSAnimator(canvas, 50);// Animator(canvas);
		this.animator.start();

		canvas.setFocusable(true);
		canvas.requestFocus();

		return canvas;
	}

	/**
	 * Log Jogl manifest information.
	 */
	private void logJoglManifest() {
		log.info("is set debug for GraphicsConfiguration: "
				+ jogamp.opengl.Debug.debug("GraphicsConfiguration"));

		StringBuilder sb = new StringBuilder();
		sb.append("JoglVersion: \n");
		JoglVersion.getInstance().getFullManifestInfo(sb);

		sb.append("\nGlueGenVersion: \n");
		GlueGenVersion.getInstance().getFullManifestInfo(sb);

		log.info(sb.toString());
	}

	/**
	 * Set up openGL capabilities.
	 * 
	 * @param capabilities
	 *            openGL capabilities
	 */
	private void setUpCapabilities(GLCapabilities capabilities) {
		String zbuffer = System.getProperty("kendzi3d.opengl.zbuffer");
		log.info("user zbuffer: " + zbuffer);

		// setup z-buffer
		if (zbuffer == null) {
			// Default use 16
			capabilities.setDepthBits(16);
		} else if (!"default".equals(zbuffer)) {
			capabilities.setDepthBits(Integer.parseInt(zbuffer));
		}

		// FIXME enabling sample buffers on dual screen ubuntu cause problems...
		// https://jogamp.org/bugzilla/show_bug.cgi?id=995

		// String sampleBuffers =
		// System.getProperty("kendzi3d.opengl.sampleBuffers");
		// log.info("user sampleBuffers: " + sampleBuffers);
		//
		// if (sampleBuffers == null) {
		// capabilities.setSampleBuffers(true);
		// } else if ("true".equals(sampleBuffers)) {
		// capabilities.setSampleBuffers(true);
		// } else if ("false".equals(sampleBuffers)) {
		// capabilities.setSampleBuffers(false);
		// }
		//
		// String sampleBuffersNum =
		// System.getProperty("kendzi3d.opengl.sampleBuffersNum");
		// log.info("user sampleBuffersNum: " + sampleBuffersNum);
		//
		// if (sampleBuffersNum == null) {
		// capabilities.setNumSamples(2);
		// } else if (!"default".equals(sampleBuffersNum)) {
		// capabilities.setNumSamples(Integer.parseInt(sampleBuffersNum));
		// }

		log.info("GLCapabilities: " + capabilities);
	}

	/**
	 * 
	 * @return CameraMoveListener object
	 */
	public CameraMoveListener returnCameraMoveListener(){
		SimpleMoveAnimator kinematicsSimpleAnimator = canvasListener.getSimpleMoveAnimator();
		CameraMoveListener c = new CameraMoveListener(kinematicsSimpleAnimator);
		return c;
	}

	/**
	 * Get time spent plus a time fixed by developer.
	 */
	private long getRelativeTime(){
		return relativeTimeSpent+2;
	}

	/**
	 * Get time spent from TimeAndFps.
	 */
	private long getTimeSpent() {
		return timeSpent;
	}

	/**
	 * Set time spent from TimeAndFps.
	 */
	public void setTimeSpent(long timeSpent) {
		this.timeSpent = timeSpent;
	}

	public void setTimeAndFps(final long time, final int fps) {
		// in some configuration it is called from outside event queue
		// and with out generated EDT violation
		final JTextField textField = this.jTFFps;
		final JTextField timeField = this.jTFTime;

		// GuiHelper.runInEDT(new Runnable() {
		SwingUtilities.invokeLater(new Runnable() {
			// always in by query, with out it it could create dead lock in AWT
			@Override
			public void run() {
				textField.setText("Fps: " + fps);
				timeField.setText("Time Spent: " + time + " secs");

				setTimeSpent(time);

				if (notches < 0){
					//					log.info( "face zoom in " ) ;
					returnCameraMoveListener().moveActionForward(true);
				}
				else{
					returnCameraMoveListener().moveActionForward(false);
				}

				if(getRelativeTime() == time) {
					//					log.info("--am oprit zoom in");
					returnCameraMoveListener().moveActionForward(false);
					notches=0;
				}

				if(notches > 0){
					//					log.info( "face zoom out " ) ;
					returnCameraMoveListener().moveActionBackwards(true);
				}
				else{
					returnCameraMoveListener().moveActionBackwards(false);
				}

				if(getRelativeTime() == time) {
					//					log.info("--am oprit zoom out");
					returnCameraMoveListener().moveActionBackwards(false);
					notches=0;
				}
			}
		});
	}

	// ----------------- window listener methods -------------

	@Override
	public void windowActivated(WindowEvent e) {
		// this.canvas.resumeGame();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// this.canvas.pauseGame();
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// this.canvas.resumeGame();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// this.canvas.pauseGame();
	}

	@Override
	public void windowClosing(WindowEvent e) {

		if (Kendzi3dGLFrame.this.animator.isStarted()) {
			Kendzi3dGLFrame.this.animator.stop();
		}

		this.canvasListener.removeFpsChangeListener(this);

		new Thread(new Runnable() {

			@Override
			public void run() {

				Kendzi3dGLFrame.this.setVisible(false);
				Kendzi3dGLFrame.this.dispose();
			}
		}).start();

	}

	@Override
	public void windowClosed(WindowEvent e) {
		//
	}

	@Override
	public void windowOpened(WindowEvent e) {
		//
	}

	/**
	 * @return the canvasListener
	 */
	public Kendzi3dGLEventListener getCanvasListener() {
		return this.canvasListener;
	}

	/**
	 * @param canvasListener
	 *            the canvasListener to set
	 */
	public void setCanvasListener(Kendzi3dGLEventListener canvasListener) {
		this.canvasListener = canvasListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void dispatchFpsChange(FpsChangeEvent fpsChangeEvent) {
		if (fpsChangeEvent != null) {
			setTimeAndFps(fpsChangeEvent.getTime(), fpsChangeEvent.getFps());
		}
	}

	/**
	 * event from mouseWheel 
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		notches = e.getWheelRotation(); // return -1 for zoom in and 1 for zoom out

		long timeSpent = getTimeSpent();
		relativeTimeSpent = timeSpent;
	}

}