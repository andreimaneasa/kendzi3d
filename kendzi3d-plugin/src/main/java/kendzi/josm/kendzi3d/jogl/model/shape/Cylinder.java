package kendzi.josm.kendzi3d.jogl.model.shape;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import kendzi.jogl.DrawUtil;
import kendzi.jogl.camera.Camera;
import kendzi.jogl.model.factory.BoundsFactory;
import kendzi.jogl.model.geometry.Bounds;
import kendzi.jogl.model.geometry.Model;
import kendzi.jogl.model.geometry.material.AmbientDiffuseComponent;
import kendzi.jogl.model.geometry.material.Material;
import kendzi.jogl.model.loader.ModelLoadException;
import kendzi.jogl.model.render.ModelRender;
import kendzi.jogl.texture.dto.TextureData;
import kendzi.jogl.texture.library.OsmBuildingElementsTextureMenager;
import kendzi.jogl.texture.library.TextureFindCriteria;
import kendzi.jogl.texture.library.TextureLibraryStorageService;
import kendzi.josm.kendzi3d.jogl.model.AbstractModel;
import kendzi.josm.kendzi3d.jogl.model.building.builder.BuildingPartOutput;
import kendzi.josm.kendzi3d.jogl.model.building.model.BuildingModel;
import kendzi.josm.kendzi3d.jogl.model.building.model.BuildingPart;
import kendzi.josm.kendzi3d.jogl.model.building.model.NodeBuildingPart;
import kendzi.josm.kendzi3d.jogl.model.building.model.SphereNodeBuildingPart;
import kendzi.josm.kendzi3d.jogl.model.building.model.WallNode;
import kendzi.josm.kendzi3d.jogl.model.building.model.WallPart;
import kendzi.josm.kendzi3d.jogl.model.building.parser.BuildingParser;
import kendzi.josm.kendzi3d.jogl.model.export.ExportItem;
import kendzi.josm.kendzi3d.jogl.model.export.ExportModelConf;
import kendzi.josm.kendzi3d.jogl.model.lod.DLODSuport;
import kendzi.josm.kendzi3d.jogl.model.lod.LOD;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.RoofDebugOut;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.RoofOutput;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.RoofTypeOutput;
import kendzi.josm.kendzi3d.jogl.model.tmp.AbstractPointModel;
import kendzi.josm.kendzi3d.jogl.selection.BuildingSelection;
import kendzi.josm.kendzi3d.jogl.selection.Selection;
import kendzi.josm.kendzi3d.jogl.selection.editor.ArrowEditorJosmImp;
import kendzi.josm.kendzi3d.jogl.selection.editor.Editor;
import kendzi.josm.kendzi3d.service.MetadataCacheService;
import kendzi.josm.kendzi3d.service.ModelCacheService;
import kendzi.josm.kendzi3d.util.ModelUtil;
import kendzi.kendzi3d.josm.model.perspective.Perspective;
import kendzi.math.geometry.line.LineSegment3d;
import kendzi.math.geometry.point.TransformationMatrix3d;

import org.apache.log4j.Logger;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.OsmPrimitiveType;
import org.openstreetmap.josm.data.projection.datum.CentricDatum;

/**
 * Cylinder for nodes.
 * 
 * @author Andrei Maneasa
 */
public class Cylinder extends /*AbstractModel*/AbstractPointModel implements DLODSuport {

	/** Log. */
	private static final Logger log = Logger.getLogger(Cylinder.class);

	/**
	 * Renderer of model.
	 */
	private ModelRender modelRender;

	/**
	 * Model cache service
	 */
	private ModelCacheService modelCacheService;

	/**
	 * Metadata cache service.
	 */
	private MetadataCacheService metadataCacheService;

	private EnumMap<LOD, Model> modelLod;
	private String type;

	Vector3d scale;

	private double minHeight;

	private List<Selection> selection = Collections.<Selection> emptyList();

	private Bounds bounds;

	private boolean selected;

	private List<LineSegment3d> edges;

	private Model model;

	private BuildingModel bm;

	private Model boundModel;

	protected boolean preview;

	private double angle = 0;

	private List<NewCylinderDebug> debug = new ArrayList<NewCylinderDebug>();

	private Node node;

	private double maxY;

	private Bounds boundsTest;

	/**
	 * @param node
	 *            node
	 * @param perspective
	 *            perspective
	 * @param pModelRender
	 *            model render
	 * @param pMetadataCacheService
	 *            metadata cache service
	 * @param pModelCacheService
	 *            model cache service
	 */
	public Cylinder(Node node, Perspective perspective, ModelRender pModelRender, MetadataCacheService pMetadataCacheService,
			ModelCacheService pModelCacheService) {
		super(node, perspective);

		this.modelLod = new EnumMap<LOD, Model>(LOD.class);

		this.scale = new Vector3d(2d, 2d, 2d);

		this.modelRender = pModelRender;
		this.metadataCacheService = pMetadataCacheService;
		this.modelCacheService = pModelCacheService;

		this.node= node;
	}

	/**
	 * Texture library service.
	 */
	private TextureLibraryStorageService textureLibraryStorageService;

	@Override
	public void buildWorldObject() {

		buildModel(LOD.LOD1);

		BuildingModel bm = this.bm;

		if (!this.preview || bm == null) {

			bm = BuildingParser.parseBuildingNode(this.node, this.perspective);

			this.selection = parseSelection(this.node.getId(), bm);

			this.preview = false;
			this.bm = bm;

		}
		this.buildModel = true;


	}

	@Override
	public void buildModel(LOD pLod) {

		this.type = this.node.get("type");

		this.minHeight= getMinHeight(this.node, this.type, this.metadataCacheService); 

		Double maxHeight = getMaxHeight(this.node, this.type, this.metadataCacheService);

		if (maxHeight == null) {
			double height = getHeight(this.node, this.type, this.metadataCacheService);
			maxHeight = /*this.minHeight +*/ height;
		}

		boundModel = null;
		boundModel = findSimpleModel(this.type, pLod, this.metadataCacheService, this.modelCacheService);

		//		setupScale(boundModel, maxHeight, this.minHeight);

		if(maxHeight == 0){

			this.scale.x = 0;
			this.scale.y = 0;
			this.scale.z = 0;
			//angle = 0;

		}else{
			setupHeight(boundModel, maxHeight, minHeight);
			//angle = 180;
		}

		this.modelLod.put(pLod, boundModel);
	}

	class CacheOsmBuildingElementsTextureMenager extends
	OsmBuildingElementsTextureMenager {

		Map<TextureFindCriteria, TextureData> cache = new HashMap<TextureFindCriteria, TextureData>();

		public CacheOsmBuildingElementsTextureMenager(
				TextureLibraryStorageService TextureLibraryStorageService) {
			super(TextureLibraryStorageService);
		}

		@Override
		public TextureData findTexture(TextureFindCriteria pTextureFindCriteria) {
			TextureData textureData = this.cache.get(pTextureFindCriteria);

			if (textureData == null) {
				textureData = super.findTexture(pTextureFindCriteria);
				this.cache.put(pTextureFindCriteria, textureData);
			}
			return textureData;
		}
	}

	public class NewArrowEditorJosmImp extends ArrowEditorJosmImp {

		@Override
		public void preview(double newValue) {
			log.info("preview: " + newValue);

			Cylinder.this.preview = true;
			Cylinder.this.buildModel = false;

			log.info("preview: " + Cylinder.this.preview + " buildModel: " + Cylinder.this.buildModel);

		}
	}


	private List<Selection> parseSelection(long wayId, final BuildingModel bm) {

		Double maxHeight = getMaxHeight(this.node, this.type, this.metadataCacheService);
		//		double maxHeight = getHeight(this.node, this.type, this.metadataCacheService);

		BoundsFactory bf = new BoundsFactory();

		//	List<BuildingPart> parts = bm.getParts();

		List<NodeBuildingPart> part = bm.getNodeParts();

		if (part != null) {
			for (NodeBuildingPart np : part) {

				Point2d p = np.getPoint();
				bf.addPoint(p.x, minHeight, -p.y); //np.getDefaultMinHeight()
				bf.addPoint(p.x, maxHeight, -p.y);//bp.getDefaultMaxHeight()


				//				List<WallPart> wallParts = bp.getWall().getWallParts();
				//				for (WallPart wp : wallParts) {
				//					for (WallNode wn : wp.getNodes()) {

				//						Point2d p = wn.getPoint();
				//
				//						bf.addPoint(p.x, bp.getDefaultMinHeight(), -p.y);
				//						bf.addPoint(p.x, bp.getDefaultMaxHeight(), -p.y);

				// trebe sa lucrez aici la cum se adauga bounds
				// face un bound min si unu max, cu add point verifica daca e vreunu mai mare sau mai mic
				// eu trebe adaug la pointul ala 3D din clasa AbstractPointModel componenta Y (height) min si max
				// 

				//					}
				//				}
			}
		}

		Bounds boundsInitial = boundModel.getBounds();
		Bounds bounds = bf.toBounds();
		this.boundsTest = bounds;

		maxY = bounds.getCenter().y;

		this.bounds = boundsInitial;

		if (this.node != null) {
			final ArrowEditorJosmImp ae = new NewArrowEditorJosmImp();
						Point3d point = new Point3d();
						point.set(point3D.x, this.scale.y, point3D.z);

			ae.setPoint(new Point3d(point3D.x, 0, point3D.z));
//			ae.setPoint(bounds.getMin());
			ae.setVector(new Vector3d(0, 1, 0));
			ae.setLength(bounds.max.y);
			//			Map<String, String> a = node.getKeys();
			//			Collection<String> cylinderHeight = null;
			//			double d = height;
			//			if (a.containsKey("height")){
			//				cylinderHeight = a.values();
			//				String s = cylinderHeight.toString();
			//				int i = s.indexOf(",");
			//				String str = s.substring(1, i);
			//				d = Double.valueOf(str);
			//			}
			//			// plus 2 for visualisation all the arrow
			//			if(minHeight != 0){
			//				ae.setLength(d + minHeight + 2);
			//			}else{
			//				ae.setLength(d + 2);
			//			}
			ae.setFildName("height");
			ae.setPrimitiveId(this.node.getUniqueId());
			ae.setPrimitiveType(OsmPrimitiveType.NODE);

			return Arrays.<Selection> asList(new MyBuildingSelection(wayId,
					point/*bounds.getCenter()*/, bounds.getMin(), bounds.getMax(), bounds.getRadius(),
					minHeight, scale, ae));

		}
		return Collections.emptyList();
	}

	public class MyBuildingSelection extends BuildingSelection {

		private ArrowEditorJosmImp ae;

		public MyBuildingSelection(long wayId, Point3d center, Point3d boundMin, Point3d boundMax, double radius,
				double minHeight,Vector3d scale) {
			super(wayId, center, boundMin, boundMax, radius, minHeight, scale);
		}

		public MyBuildingSelection(long wayId, Point3d center, Point3d boundMin, Point3d boundMax, double radius, 
				double minHeight, Vector3d scale, ArrowEditorJosmImp ae) {
			this(wayId, center, boundMin, boundMax, radius, minHeight, scale);
			this.ae = ae;
		}

		@Override
		public void select(boolean selected) {
			Cylinder.this.selected = selected;
		}

		@Override
		public List<Editor> getEditors() {
			return Arrays.<Editor> asList(ae);
		}

		public Cylinder getNewBuildingInstance() {
			return Cylinder.this;
		}
	}

	private void setupHeight(Model model2, double maxHeight, double minHeight) {

		double height = maxHeight; //- minHeight;

		Bounds bounds = model2.getBounds();

		double modelHeight = bounds.max.y;

		double modelScaleHeight = height / modelHeight;

		//double modelScaleWidht = modelScaleHeight;

		double modelScaleWidhtX = bounds.radius/bounds.max.x;
		double modelScaleWidhtZ = bounds.radius/bounds.max.z;

		this.scale.x = modelScaleWidhtX;
		this.scale.y = modelScaleHeight;
		this.scale.z = modelScaleWidhtZ;

	}

	private void setupScale(Model model2, double maxHeight, double minHeight) {

		double height = maxHeight - minHeight;

		Bounds bounds = model2.getBounds();

		double modelHeight = bounds.max.y;

		double modelScaleHeight = height / modelHeight;

		double modelScaleWidht = modelScaleHeight;

		this.scale.x = modelScaleWidht;
		this.scale.y = modelScaleHeight;
		this.scale.z = modelScaleHeight;

	}


	public static double getMinHeight(OsmPrimitive node, String type, MetadataCacheService metadataCacheService) {
		return ModelUtil.getMinHeight(node, 0d);
	}


	public static Double getMaxHeight(OsmPrimitive node, String type,
			MetadataCacheService metadataCacheService) {
		return ModelUtil.getHeight(node, null);
	}


	/**
	 * Finds simple model for cylinder. Finding type
	 * 
	 * @param type
	 *            cylinder type
	 * @param pLod
	 *            lod level
	 * @param metadataCacheService
	 * @param modelCacheService
	 * 
	 * @return model
	 */
	public static Model findSimpleModel(String type, LOD pLod, MetadataCacheService metadataCacheService, ModelCacheService modelCacheService) {

		String model = null;

		String typeModel = metadataCacheService.getPropertites("cylinder.type.unknown.{1}.model",null, null, "" + pLod);

		if (typeModel != null) {
			model = typeModel;
		}

		if (model != null)
		{
			try {
				Model loadModel = modelCacheService.loadModel(model);
				loadModel.useLight = true;
				setAmbientColor(loadModel);
				return loadModel;

			} catch(ModelLoadException e){
				log.error(e, e);
			}
		}

		return null;
	}

	private static void setAmbientColor(Model pModel) {
		for (int i = 0; i < pModel.getNumberOfMaterials(); i++) {
			Material material = pModel.getMaterial(i);

			material.setAmbientDiffuse(new AmbientDiffuseComponent(material.getAmbientDiffuse().getDiffuseColor(), material
					.getAmbientDiffuse().getDiffuseColor()));
		}
	}

	/**
	 * Finds height for cylinder. Order of finding is: - node type
	 * 
	 * @param node
	 * @param type
	 * @param metadataCacheService
	 * 
	 * @return height
	 */
	public static double getHeight(OsmPrimitive node, String type, MetadataCacheService metadataCacheService) {

		Double height = 1d;

		Double nodeHeight = ModelUtil.getObjHeight(node, null);

		Double typeHeight = metadataCacheService.getPropertitesDouble("cylinder.type.height", null, type);

		if (nodeHeight != null) {
			height = nodeHeight;
		} else if (typeHeight != null) {
			height = typeHeight;
		} 

		return height;
	}


	@Override
	public void draw(GL2 gl, Camera camera, LOD pLod) {

		this.model =  this.modelLod.get(pLod);
		//model.setBounds(this.boundsTest);
		Model model2 = model;
		if (model2 != null) {

			Double maxHeight = getMaxHeight(this.node, this.type, this.metadataCacheService);

			gl.glPushMatrix();
			gl.glTranslated(this.getGlobalX(), minHeight/*(this.boundsTest.getCenter().y)-maxHeight/2*/, -this.getGlobalY());

			gl.glEnable(GLLightingFunc.GL_NORMALIZE);
			//gl.glScaled(1,1,1);
			//gl.glRotated(angle, this.scale.x, this.scale.y, this.scale.z);
			gl.glScaled(this.scale.x, this.scale.y, this.scale.z);
			this.modelRender.render(gl, model2);

			if (this.selected){

				gl.glColor3fv(Color.RED.darker().getRGBComponents(new float[4]), 0);

				gl.glLineWidth(6);

				DrawUtil.drawBox(gl,this.bounds.getMax(), this.bounds.getMin());

			}

			gl.glDisable(GLLightingFunc.GL_NORMALIZE);

			// rotate in the opposite direction to the camera

			gl.glPopMatrix();

		}
	}

	@Override
	public boolean isModelBuild(LOD pLod) {
		if (this.modelLod.get(pLod) != null) {
			return true;
		}
		return false;
	}

	@Override
	public void draw(GL2 pGl, Camera pCamera) {

	}

	@Override
	public List<ExportItem> export(ExportModelConf conf) {
		if (this.modelLod.get(LOD.LOD1) == null) {
			buildModel(LOD.LOD1);
		}

		return Collections.singletonList(new ExportItem(this.modelLod.get(LOD.LOD1), new Point3d(this.getGlobalX(), 0, -this
				.getGlobalY()), new Vector3d(1, 1, 1)));
	}

	@Override
	public Model getModel() {

		return this.modelLod.get(LOD.LOD1);
	}

	@Override
	public List<Selection> getSelection() {
		return this.selection;
	}

}
