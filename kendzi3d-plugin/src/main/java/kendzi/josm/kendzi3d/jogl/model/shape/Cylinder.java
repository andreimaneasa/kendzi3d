package kendzi.josm.kendzi3d.jogl.model.shape;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import kendzi.jogl.DrawUtil;
import kendzi.jogl.camera.Camera;
import kendzi.jogl.model.factory.BoundsFactory;
import kendzi.jogl.model.factory.MaterialFactory;
import kendzi.jogl.model.factory.MeshFactory;
import kendzi.jogl.model.factory.ModelFactory;
import kendzi.jogl.model.geometry.Bounds;
import kendzi.jogl.model.geometry.Model;
import kendzi.jogl.model.geometry.material.AmbientDiffuseComponent;
import kendzi.jogl.model.geometry.material.Material;
import kendzi.jogl.model.loader.ModelLoadException;
import kendzi.jogl.model.render.ModelRender;
import kendzi.jogl.texture.dto.TextureData;
import kendzi.jogl.texture.library.BuildingElementsTextureManager;
import kendzi.jogl.texture.library.OsmBuildingElementsTextureMenager;
import kendzi.jogl.texture.library.TextureFindCriteria;
import kendzi.jogl.texture.library.TextureLibraryStorageService;
import kendzi.josm.kendzi3d.jogl.model.NewBuildingDebug;
import kendzi.josm.kendzi3d.jogl.model.building.builder.BuildingOutput;
import kendzi.josm.kendzi3d.jogl.model.building.builder.BuildingPartOutput;
import kendzi.josm.kendzi3d.jogl.model.building.builder.roof.registry.RoofTypeBuilderRegistry;
import kendzi.josm.kendzi3d.jogl.model.building.model.BuildingModel;
import kendzi.josm.kendzi3d.jogl.model.building.model.BuildingPart;
import kendzi.josm.kendzi3d.jogl.model.building.model.BuildingUtil;
import kendzi.josm.kendzi3d.jogl.model.building.model.NodeBuildingPart;
import kendzi.josm.kendzi3d.jogl.model.building.model.SphereNodeBuildingPart;
import kendzi.josm.kendzi3d.jogl.model.building.model.WallNode;
import kendzi.josm.kendzi3d.jogl.model.building.model.WallPart;
import kendzi.josm.kendzi3d.jogl.model.building.parser.BuildingParser;
import kendzi.josm.kendzi3d.jogl.model.export.ExportItem;
import kendzi.josm.kendzi3d.jogl.model.export.ExportModelConf;
import kendzi.josm.kendzi3d.jogl.model.lod.DLODSuport;
import kendzi.josm.kendzi3d.jogl.model.lod.LOD;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.DormerTypeBuilder;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.RoofDebugOut;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.RoofOutput;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.RoofTypeOutput;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.dormer.RoofDormerTypeOutput;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.model.DormerRoofModel;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofType5v6;
import kendzi.josm.kendzi3d.jogl.model.roof.mk.type.RoofTypeBuilder;
import kendzi.josm.kendzi3d.jogl.model.tmp.AbstractPointModel;
import kendzi.josm.kendzi3d.jogl.selection.BuildingSelection;
import kendzi.josm.kendzi3d.jogl.selection.Selection;
import kendzi.josm.kendzi3d.jogl.selection.editor.ArrowEditorJosmImp;
import kendzi.josm.kendzi3d.jogl.selection.editor.Editor;
import kendzi.josm.kendzi3d.service.MetadataCacheService;
import kendzi.josm.kendzi3d.service.ModelCacheService;
import kendzi.josm.kendzi3d.util.ModelUtil;
import kendzi.kendzi3d.josm.model.direction.AngleDirection;
import kendzi.kendzi3d.josm.model.perspective.Perspective;
import kendzi.math.geometry.line.LineSegment3d;
import kendzi.math.geometry.point.TransformationMatrix3d;
import kendzi.math.geometry.polygon.PolygonWithHolesList2d;

import org.apache.log4j.Logger;
import org.ejml.ops.SpecializedOps;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.OsmPrimitiveType;

/**
 * Cylinder for nodes.
 * 
 * @author Andrei Maneasa
 */
public class Cylinder extends AbstractPointModel implements DLODSuport {

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

		//this.edges = new ArrayList<LineSegment3d>();

		//		if (bm != null) {

		//BuildingElementsTextureManager tm = new CacheOsmBuildingElementsTextureMenager(this.textureLibraryStorageService);

		//	BuildingOutput buildModel = BuildingBuilder.buildModel(bm, tm);

		//			Model model = buildModel.getModel();
		//			model.useLight = true;
		//			model.useTexture = true;

		//this.model = model;
		this.buildModel = true;

		//			this.debug.clear();
		//
		//		this.edges = new ArrayList<LineSegment3d>();
		//
		//			if (buildModel.getBuildingPartOutput() != null) {
		//				for (BuildingPartOutput bo : buildModel.getBuildingPartOutput()) {
		//					//this.debug.add(new NewBuildingDebug(bo.getRoofDebugOut()));
		//					if (bo.getEdges() != null) {
		//						edges.addAll(bo.getEdges());
		//					}
		//				}
		//			}
		//		}



		BuildingOutput buildModel = buildModel(this.bm);

		Model model = buildModel.getModel();
		
		model.useLight = true;
		model.useTexture = true;

		this.model = model;

	}


	public static BuildingOutput buildModel(BuildingModel buildingModel){

		List<BuildingPartOutput> partsOut = new ArrayList<BuildingPartOutput>();

		ModelFactory mf = ModelFactory.modelBuilder();

		if (buildingModel.getNodeParts() != null) {
			
			for (NodeBuildingPart bp : buildingModel.getNodeParts()) {

				partsOut.add(builNodePart(bp));
			}
		}

		BuildingOutput out = new BuildingOutput();
		out.setModel(mf.toModel());
		out.setBuildingPartOutput(partsOut);
		return out;

	}

	private static BuildingPartOutput builNodePart(NodeBuildingPart bp) {

		BuildingPartOutput partOutput = new BuildingPartOutput();
		if (bp instanceof SphereNodeBuildingPart) {

			RoofOutput roofOutput = build(bp);
			partOutput.setRoofDebugOut(roofOutput.getDebug());
			partOutput.setEdges(roofOutput.getEdges());

			//	SphereNodeBuildingPart sphere = (SphereNodeBuildingPart) bp;
			//			int pIcross = 12;
			//			int icross = pIcross  + 1;
			//
			//			double height = sphere.getHeight();
			//			double radius = sphere.getRadius();
			//			Point2d point = sphere.getPoint();
			//
			//			// create cross section
			//			Point2d [] crossSection = new Point2d[icross];
			//			for (int i = 0; i < icross; i++) {
			//				double a = Math.toRadians(180) / (icross - 1) * i - Math.toRadians(90);
			//
			//				crossSection[i] = new Point2d(Math.cos(a) * radius, Math.sin(a) * radius + height);
			//			}
			//			int pIsection = 12;
			//			RoofType5v6.buildRotaryShape(mesh, point, pIsection, crossSection, true);
		}
		//return new BuildingPartOutput();
		return partOutput;
	}

	//	private static BuildingPartOutput buildPart(NodeBuildingPart bp){
	//		
	//		BuildingPartOutput partOutput = new BuildingPartOutput();
	//		
	//		RoofOutput roofOutput = build(bp);
	//		partOutput.setRoofDebugOut(roofOutput.getDebug());
	//		partOutput.setEdges(roofOutput.getEdges());
	//
	//		return partOutput;
	//	}


		public static RoofOutput build(NodeBuildingPart buildingPart){
	
//			PolygonWithHolesList2d buildingPolygon = BuildingUtil.buildingPartToPolygonWithHoles(buildingPart);
	
//			List<Point2d> polygon = buildingPolygon.getOuter().getPoints();
			Point2d startPoint = buildingPart.getPoint();
					
//					polygon.get(0);
	
			double height =  buildingPart.getHeight();
			
//			DormerRoofModel roof = (DormerRoofModel) buildingPart.getRoof();
//			RoofTypeBuilder roofType = RoofTypeBuilderRegistry.selectBuilder(roof.getRoofType());
	
			double maxHeight = buildingPart.getHeight();
	
//			RoofTypeOutput rto = roofType.buildRoof(startPoint, buildingPolygon, roof, maxHeight, null);
	
//			double minHeight = maxHeight - rto.getHeight();
	
//			List<RoofDormerTypeOutput> roofExtensionsList = DormerTypeBuilder.build(rto.getRoofHooksSpaces(),
//					roof, roof.getMeasurements(), null);
	
//			RoofDebugOut debug = buildDebugInfo(rto, startPoint, height, buildingPart);
	
			RoofOutput out = new RoofOutput();
			out.setHeight(height);
//			out.setDebug(debug);
	
			return out;
		}

	private static RoofDebugOut buildDebugInfo(RoofTypeOutput rto, 
			Point2d startPoint, double height, NodeBuildingPart node) {

		Point3d startPointMark = new Point3d(startPoint.x, height, -startPoint.y);

		List<Point3d> rectangleTransf = new ArrayList<Point3d>();

		List<Point3d> rectangle = rto.getRectangle();
		for (int i = 0; i < rectangle.size(); i++) {
			Point3d p = rectangle.get(i);

			rectangleTransf.add(TransformationMatrix3d.transform(p, rto.getTransformationMatrix()));
		}
		rto.setRectangle(rectangleTransf);
		RoofDebugOut out = new RoofDebugOut();
		out.setBbox(rectangleTransf);
		out.setStartPoint(startPointMark);
		return out;
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


		//		setupScale(model, maxHeight, this.minHeight);

		if(maxHeight == 0){

			this.scale.x = 0;
			this.scale.y = 0;
			this.scale.z = 0;
			angle = 0;

		}else{
			setupHeight(boundModel, maxHeight, minHeight);
			angle = 45;
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

			if (bm != null /*&& bm.getParts() != null*/
					/*&& bm.getParts().size() > 0*/) {


				//				bm.getParts().get
				//	bm.getNodeParts().get(0).getHeight();


				log.info("");

				//List<NodeBuildingPart> bp = bm.getNodeParts();


				//				SphereNodeBuildingPart bnp = (SphereNodeBuildingPart) bm.getNodeParts();
				//				bnp.setHeight(newValue);

				//				List<NodeBuildingPart> bpp = (List<NodeBuildingPart>) new SphereNodeBuildingPart();
				//				bpp.get(0).setCylinderHeight(newValue);

				//				bm = (BuildingModel) bpp;
				//				bp = (List<NodeBuildingPart>) bnp.setHeight(newValue);

				//				bp.get(0).setCylinderHeight(newValue);



				//				bm = (BuildingModel) bp;
				//				bm.setParts(bp);
				//				bm.getParts().get(0).setMaxHeight(newValue);
			}
			Cylinder.this.preview = true;
			Cylinder.this.buildModel = false;

			log.info("preview: " + Cylinder.this.preview + " buildModel: " + Cylinder.this.buildModel);

		}
	}
	private List<Selection> parseSelection(long wayId, final BuildingModel bm) {
		BoundsFactory bf = new BoundsFactory();

		List<BuildingPart> parts = bm.getParts();
		if (parts != null) {
			for (BuildingPart bp : parts) {
				List<WallPart> wallParts = bp.getWall().getWallParts();
				for (WallPart wp : wallParts) {
					for (WallNode wn : wp.getNodes()) {

						Point2d p = wn.getPoint();

						bf.addPoint(p.x, bp.getDefaultMinHeight(), -p.y);
						bf.addPoint(p.x, bp.getDefaultMaxHeight(), -p.y);

					}
				}
			}
		}

		Bounds bounds = boundModel.getBounds();

		this.bounds = bounds;

		if (this.node != null) {
			final ArrowEditorJosmImp ae = new NewArrowEditorJosmImp();

			ae.setPoint(bounds.getMin());
			ae.setVector(new Vector3d(0, 1, 0));
			ae.setLength(bounds.max.y);
			ae.setFildName("height");
			ae.setPrimitiveId(this.node.getUniqueId());
			ae.setPrimitiveType(OsmPrimitiveType.NODE);

			return Arrays.<Selection> asList(new MyBuildingSelection(wayId,
					bounds.getCenter(), bounds.getRadius(), ae) );
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

		double height = maxHeight ;//- minHeight;

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
				//				loadModel.useLight = true;
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
		//	Double unknownHeight = metadataCacheService.getPropertitesDouble("cylinder.type.unknown.{0}.height", null, (String) null);

		// XXX add StringUtil
		if (nodeHeight != null) {
			height = nodeHeight;
		} else if (typeHeight != null) {
			height = typeHeight;
		} 
		//		else {
		//			height = unknownHeight;
		//		}

		//		if (height == null) {
		//			height = 1d;
		//		}

		return height;
	}


	@Override
	public void draw(GL2 gl, Camera camera, LOD pLod) {

		this.model =  this.modelLod.get(pLod);
		Model model2 = model;
		if (model2 != null) {

			gl.glPushMatrix();
			gl.glTranslated(this.getGlobalX(), this.minHeight, -this.getGlobalY());

			gl.glEnable(GLLightingFunc.GL_NORMALIZE);
			gl.glScaled(1,1,1);
			//	gl.glRotated(angle, this.scale.x, this.scale.y, this.scale.z);

			gl.glScaled(this.scale.x, this.scale.y, this.scale.z);
			this.modelRender.render(gl, model2);

			if (this.selected){

				gl.glColor3fv(Color.RED.darker().getRGBComponents(new float[4]), 0);

				gl.glLineWidth(6);

				DrawUtil.drawBox(gl, this.bounds.getMax(), this.bounds.getMin());

			}

			gl.glDisable(GLLightingFunc.GL_NORMALIZE);

			// rotate in the opposite direction to the camera

			gl.glPopMatrix();

			//			if (this.modelRender.isDebugging()/* && this.debug != null*/) {
			//				for (NewCylinderDebug d : this.debug) {
			//					d.drawDebugRoof(gl);
			//				}
			//			}

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
		//draw(pGl, pCamera, LOD.LOD1);

		//		pGl.glPushMatrix();
		//
		//		pGl.glTranslated(this.getGlobalX(), 0, -this.getGlobalY());
		//
		//		pGl.glColor3f((float) 188 / 255, (float) 169 / 255, (float) 169 / 255);
		//
		//		Model m = (Model) this.modelLod.values();
		//
		//		this.modelRender.render(pGl, m);
		//
		//		if (this.selected && this.bounds != null) {
		//
		//			pGl.glColor3fv(Color.RED.darker().getRGBComponents(new float[4]), 0);
		//
		//			pGl.glLineWidth(6);
		//
		//			DrawUtil.drawBox(pGl, this.bounds.getMax(), this.bounds.getMin());
		//		}

		//		if (edges != null) {
		//		pGl.glTranslated(0, 0.1, 0);
		//
		//		pGl.glLineWidth(6);
		//
		//		for (LineSegment3d line : edges) {
		//			pGl.glColor3fv(Color.RED.darker()
		//					.getRGBComponents(new float[4]), 0);
		//
		//			pGl.glBegin(GL.GL_LINES);
		//
		//			pGl.glVertex3d(line.getBegin().x, line.getBegin().y,
		//					line.getBegin().z);
		//			pGl.glVertex3d(line.getEnd().x, line.getEnd().y,
		//					line.getEnd().z);
		//
		//			pGl.glEnd();
		//		}
		//		}

		//		pGl.glPopMatrix();

		// if (this.modelRender.isDebugging() && this.debug != null) {
		// for (NewBuildingDebug d : this.debug) {
		// d.drawDebugRoof(pGl);
		// }
		// }

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
