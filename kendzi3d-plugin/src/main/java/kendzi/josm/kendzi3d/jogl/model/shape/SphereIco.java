package kendzi.josm.kendzi3d.jogl.model.shape;

import java.awt.Color;
import java.util.Arrays;
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
import kendzi.josm.kendzi3d.jogl.model.building.model.BuildingModel;
import kendzi.josm.kendzi3d.jogl.model.building.model.NodeBuildingPart;
import kendzi.josm.kendzi3d.jogl.model.building.parser.BuildingParser;
import kendzi.josm.kendzi3d.jogl.model.export.ExportItem;
import kendzi.josm.kendzi3d.jogl.model.export.ExportModelConf;
import kendzi.josm.kendzi3d.jogl.model.lod.DLODSuport;
import kendzi.josm.kendzi3d.jogl.model.lod.LOD;
import kendzi.josm.kendzi3d.jogl.model.tmp.AbstractPointModel;
import kendzi.josm.kendzi3d.jogl.selection.BuildingSelection;
import kendzi.josm.kendzi3d.jogl.selection.Selection;
import kendzi.josm.kendzi3d.jogl.selection.editor.ArrowEditorJosmImp;
import kendzi.josm.kendzi3d.jogl.selection.editor.Editor;
import kendzi.josm.kendzi3d.service.MetadataCacheService;
import kendzi.josm.kendzi3d.service.ModelCacheService;
import kendzi.josm.kendzi3d.util.ColorUtil;
import kendzi.josm.kendzi3d.util.ModelUtil;
import kendzi.kendzi3d.josm.model.attribute.OsmAttributeKeys;
import kendzi.kendzi3d.josm.model.perspective.Perspective;
import kendzi.util.StringUtil;

import org.apache.log4j.Logger;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.OsmPrimitiveType;

/**
 * Ico Sphere for nodes.
 * 
 * @author Andrei Maneasa
 */
public class SphereIco extends AbstractPointModel implements DLODSuport {
	/** Log. */
	private static final Logger log = Logger.getLogger(SphereIco.class);

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

	private Model model;

	private BuildingModel bm;

	private Model boundModel;

	protected boolean preview;

	private Node node;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	private double scaleHeight;

	private Perspective perspective;

	public Perspective getPerspective() {
		return perspective;
	}

	public void setPerspective(Perspective perspective) {
		this.perspective = perspective;
	}

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
	public SphereIco(Node node, Perspective perspective, ModelRender pModelRender, 
			MetadataCacheService pMetadataCacheService, ModelCacheService pModelCacheService) {
		super(node, perspective);

		this.modelLod = new EnumMap<LOD, Model>(LOD.class);

		this.scale = new Vector3d(2d, 2d, 2d);

		this.modelRender = pModelRender;
		this.metadataCacheService = pMetadataCacheService;
		this.modelCacheService = pModelCacheService;

		this.node = node;
		this.perspective = perspective;
	}

	@Override
	public void buildWorldObject() {

		buildModel(LOD.LOD2);

		BuildingModel bm = this.bm;

		if (!this.preview || bm == null) {

			bm = BuildingParser.parseBuildingNode(this.node, this.perspective);

			this.selection = parseSelection(this.node.getId(), bm, this.boundModel);

			this.preview = false;
			this.bm = bm;
		}
		this.buildModel = true;
	}

	@Override
	public void buildModel(LOD pLod) {

		this.type = this.node.get("type");
		this.minHeight= getMinHeight(this.node, this.type, this.metadataCacheService); 

		boundModel = null;
		boundModel = findSimpleModel(this.node, this.type, pLod, this.metadataCacheService, this.modelCacheService);

		Vector3d scaleVec = new Vector3d();

		if (scale()){
			this.scaleHeight = scaleValue();
			scaleVec = setupScale(boundModel, this.scaleHeight);
		} 

		if (height() && scale()){

			double height = sphereHeight();

			Bounds bounds = boundModel.getBounds();

			double modelHeight = bounds.max.y;

			double modelScaleHeight = height / modelHeight;

			double modelScaleWidhtX = bounds.radius/bounds.max.x;
			double modelScaleWidhtZ = bounds.radius/bounds.max.z;

			if(scaleVec.x != 0 && scaleVec.y != 0 && scaleVec.z != 0){
				this.scale.x = scaleVec.x + modelScaleWidhtX;
				this.scale.y = /*scaleVec.y +*/ modelScaleHeight;
				this.scale.z = scaleVec.z + modelScaleWidhtZ;
			}else{
				this.scale.x = modelScaleWidhtX;
				this.scale.y = modelScaleHeight;
				this.scale.z = modelScaleWidhtZ;
			}

		}else if(height()){
			setupHeight(boundModel, sphereHeight());
		}

		this.modelLod.put(pLod, boundModel);
	}

	/**
	 * 
	 * @return if sphere contains scale
	 */
	private boolean scale(){
		Map<String, String> str = this.node.getKeys();
		if (str.containsKey("scale")||str.containsKey("Scale")||str.containsKey("SCALE")){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return scale value 
	 */
	private double scaleValue(){
		double value = 0;
		String val = null;
		String scaleStr = null;
		Map<String, String> str = this.node.getKeys();
		if (str.containsKey("scale")||str.containsKey("Scale")||str.containsKey("SCALE")){
			String val1 = str.get("scale");
			String val2 = str.get("Scale");
			String val3 = str.get("SCALE");
			if(val1 != null){
				val = val1;
			}else if (val2 != null){
				val = val2;
			}else {
				val = val3; 
			}
			scaleStr = val.trim().toLowerCase();
			scaleStr = scaleStr.replaceAll(",", ".");
		}

		if (value == 0){
			value = getScale(this.node, this.type, this.metadataCacheService);
		}

		try{
			if(val==null){
				return new Double(value);
			}
			else{
				return new Double(scaleStr);
			}
		} catch (Exception e) {
			log.info("Unsupportet height: " + scaleStr);
		}

		return value;
	}

	/**
	 * 
	 * @return if sphere contains height
	 */
	private boolean height(){
		Map<String, String> str = this.node.getKeys();
		if (str.containsKey("height")){
			return true;
		}
		return false;
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

			SphereIco.this.preview = true;
			SphereIco.this.buildModel = false;

			log.info("preview: " + SphereIco.this.preview + " buildModel: " + SphereIco.this.buildModel);

		}
	}

	/**
	 * test parameter "height" from JOSM 
	 * if it's null get default height from
	 *  kendzi-plugin metadata.proprieties
	 * @return height of Sphere
	 */
	private double sphereHeight(){

		Double maxHeight = getMaxHeight(this.node, this.type, this.metadataCacheService);

		if (maxHeight == null) {
			maxHeight = getHeight(this.node, this.type, this.metadataCacheService);
		}
		return maxHeight;
	}

	private List<Selection> parseSelection(long wayId, final BuildingModel bm, Model model) {
		BoundsFactory bf = new BoundsFactory();
		Bounds bnd = model.getBounds();
		List<NodeBuildingPart> part = bm.getNodeParts();

		if (part != null) {
			for (NodeBuildingPart np : part) {

				Point2d p = np.getPoint();
				if (this.minHeight != 0){
					bf.addPoint(p.x, this.minHeight, -p.y); 
					bf.addPoint(p.x, scaleValue(), -p.y); 
					bf.addPoint(bnd.min.x, bnd.min.y, -bnd.min.z);
				}else{
					bf.addPoint(p.x, this.minHeight, -p.y); 
					bf.addPoint(p.x, scaleValue(), -p.y);
				}
			}
		}

		Bounds boundsInitial = boundModel.getBounds();
		Bounds bounds = bf.toBounds();
		this.bounds = boundsInitial;

		if (this.node != null) {
			final ArrowEditorJosmImp ae = new NewArrowEditorJosmImp();
			Point3d point = new Point3d();
			point.set(point3D.x , this.scale.y + minHeight, point3D.z);
			ae.setPoint(new Point3d(point3D.x, minHeight + 2, point3D.z));
			ae.setVector(new Vector3d(0, 1, 0));
			// plus a constant for visualisation all the arrow
			ae.setLength(scaleValue() + 2);
			ae.setFildName("scale");
			ae.setPrimitiveId(this.node.getUniqueId());
			ae.setPrimitiveType(OsmPrimitiveType.NODE);

			return Arrays.<Selection> asList(new MyBuildingSelection(wayId,
					point, bounds.getRadius(), ae));

		}
		return Collections.emptyList();
	}

	public class MyBuildingSelection extends BuildingSelection {

		private ArrowEditorJosmImp ae;

		public MyBuildingSelection(long wayId, Point3d center, double radius) {
			super(wayId, center, radius);
		}

		public MyBuildingSelection(long wayId, Point3d center, double radius, ArrowEditorJosmImp ae) {
			this(wayId, center, radius);
			this.ae = ae;
		}

		@Override
		public void select(boolean selected) {
			SphereIco.this.selected = selected;
		}

		@Override
		public List<Editor> getEditors() {
			return Arrays.<Editor> asList(ae);
		}

		public SphereIco getNewBuildingInstance() {
			return SphereIco.this;
		}
	}

	private void setupHeight(Model model2, double maxHeight) {

		double height = maxHeight;

		Bounds bounds = model2.getBounds();

		double modelHeight = bounds.max.y;

		double modelScaleHeight = height / modelHeight;

		double modelScaleWidhtX = bounds.radius/bounds.max.x;
		double modelScaleWidhtZ = bounds.radius/bounds.max.z;

		this.scale.x = modelScaleWidhtX;
		this.scale.y = modelScaleHeight;
		this.scale.z = modelScaleWidhtZ;

	}

	private Vector3d setupScale(Model model2, double maxHeight) {
		Vector3d vec;
		double height = maxHeight;

		Bounds bounds = model2.getBounds();

		double modelHeight = bounds.max.y;

		double modelScaleHeight = height / modelHeight;

		double modelScaleWidht = modelScaleHeight;

		this.scale.x = modelScaleWidht;
		this.scale.y = modelScaleHeight;
		this.scale.z = modelScaleWidht;
		vec = this.scale;
		return vec;
	}


	public static double getMinHeight(OsmPrimitive node, String type, MetadataCacheService metadataCacheService) {
		return ModelUtil.getMinHeight(node, 0d);
	}


	public static Double getMaxHeight(OsmPrimitive node, String type,
			MetadataCacheService metadataCacheService) {
		return ModelUtil.getHeight(node, null);
	}

	/**
	 * Finds simple model for sphere. Finding type
	 * 
	 * @param type
	 *            sphere type
	 * @param pLod
	 *            lod level
	 * @param metadataCacheService
	 * @param modelCacheService
	 * 
	 * @return model
	 */
	public static Model findSimpleModel(Node node, String type, LOD pLod, MetadataCacheService metadataCacheService, 
			ModelCacheService modelCacheService) {

		String model = null;

		String typeModel = metadataCacheService.getPropertites("sphere.type.unknown.{1}.model",null, null, "" + pLod);

		if (typeModel != null) {
			model = typeModel;
		}

		if (model != null)
		{
			try {
				Model loadModel = modelCacheService.loadModel(model);
				loadModel.useLight = true;
				setAmbientColor(loadModel, node);
				return loadModel;

			} catch(ModelLoadException e){
				log.error(e, e);
			}
		}

		return null;
	}

	/**
	 * Gets object color.
	 * 
	 * @param primitive Osm 
	 * @return color 
	 */
	public static Color parseSphereColor(OsmPrimitive primitive) {

		String typeColor = OsmAttributeKeys.COLOR.primitiveValue(primitive);
		if (StringUtil.isBlankOrNull(typeColor)) {
			typeColor = OsmAttributeKeys.COLOUR.primitiveValue(primitive);
		}

		if (!StringUtil.isBlankOrNull(typeColor)) {
			return ColorUtil.parseColor(typeColor);
		}
		return null;
	}

	/**
	 * set color for sphere
	 * @param pModel
	 * @param node
	 */
	private static void setAmbientColor(Model pModel, Node node) {
		for (int i = 0; i < pModel.getNumberOfMaterials(); i++) {
			Material material = pModel.getMaterial(i);

			Color color = parseSphereColor((OsmPrimitive) node);
			if (color != null){
				// get color from Node attibute
				// 40% color intensity 
				material.setAmbientDiffuse(new AmbientDiffuseComponent(color, new Color(0.4f, 0.4f, 0.4f, 1.0f)));
			}else{
				// get color from mtl file 
				material.setAmbientDiffuse(new AmbientDiffuseComponent(material.getAmbientDiffuse().getDiffuseColor(), 
						material.getAmbientDiffuse().getDiffuseColor()));
			}
		}
	}

	/**
	 * Finds height for sphere. Order of finding is: - node type
	 * 
	 * @param node
	 * @param type
	 * @param metadataCacheService
	 * 
	 * @return height
	 */
	/*
	 * but for sphere, now isn't set a default value in metadata.proprieties
	 */
	public static double getHeight(OsmPrimitive node, String type, MetadataCacheService metadataCacheService) {

		Double height = 1d;

		Double nodeHeight = ModelUtil.getObjHeight(node, null);

		Double typeHeight = metadataCacheService.getPropertitesDouble("sphere.type.height", null, type);

		if (nodeHeight != null) {
			height = nodeHeight;
		} else if (typeHeight != null) {
			height = typeHeight;
		} 

		return height;
	}

	/**
	 * Finds scale for sphere. Order of finding is: - node type
	 * 
	 * @param node
	 * @param type
	 * @param metadataCacheService
	 * 
	 * @return scale
	 */
	public static double getScale(OsmPrimitive node, String type, MetadataCacheService metadataCacheService) {

		Double scale = 1d;

		Double nodeScale = ModelUtil.getObjHeight(node, null);

		Double typeHeight = metadataCacheService.getPropertitesDouble("sphere.type.scale", null, type);

		if (nodeScale != null) {
			scale = nodeScale;
		} else if (typeHeight != null) {
			scale = typeHeight;
		} 

		return scale;
	}

	@Override
	public void draw(GL2 gl, Camera camera, LOD pLod) {

		this.model =  this.modelLod.get(pLod);
		Model model2 = model;
		if (model2 != null) {

			gl.glPushMatrix();
			gl.glTranslated(this.getGlobalX(), minHeight, -this.getGlobalY());
			gl.glEnable(GLLightingFunc.GL_NORMALIZE);

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
