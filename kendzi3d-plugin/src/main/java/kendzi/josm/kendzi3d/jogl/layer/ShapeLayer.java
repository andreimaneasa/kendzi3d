package kendzi.josm.kendzi3d.jogl.layer;

import kendzi.jogl.model.render.ModelRender;
import kendzi.jogl.texture.library.TextureLibraryStorageService;
import kendzi.josm.kendzi3d.jogl.model.shape.Cylinder;
import kendzi.josm.kendzi3d.service.MetadataCacheService;
import kendzi.josm.kendzi3d.service.ModelCacheService;
import kendzi.kendzi3d.josm.model.perspective.Perspective;
import kendzi.kendzi3d.world.WorldObject;
import kendzi.kendzi3d.world.quad.layer.Layer;

import org.apache.log4j.Logger;
import org.openstreetmap.josm.actions.search.SearchCompiler;
import org.openstreetmap.josm.actions.search.SearchCompiler.Match;
import org.openstreetmap.josm.actions.search.SearchCompiler.ParseError;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.Relation;
import org.openstreetmap.josm.data.osm.Way;

import com.google.inject.Inject;

public class ShapeLayer implements Layer{
	/** Log. */
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ShapeLayer.class);

	/**
	 * Model renderer.
	 */
	@Inject
	private ModelRender modelRender;

	/**
	 * model cache service.
	 */
	@Inject
	private ModelCacheService modelCacheService;

	/**
	 * Metadata cache service.
	 */
	@Inject
	private MetadataCacheService metadataCacheService;

	/**
	 * Texture library service.
	 */
	@Inject
	private TextureLibraryStorageService textureLibraryStorageService;

	private Match cylinderMatcher;

	{
		try {
			this.cylinderMatcher = SearchCompiler.compile("(shape=cylinder)", false, false);
		} catch (ParseError e) {
			this.cylinderMatcher = new SearchCompiler.Never();
			e.printStackTrace();
		}
	}

	@Override
	public Match getNodeMatcher() {
		return this.cylinderMatcher;
	}

	@Override
	public Match getWayMatcher() {
		return null;
	}

	@Override
	public Match getRelationMatcher() {
		return null;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public WorldObject buildModel(Node pNode, Perspective perspective) {
		return new Cylinder(pNode, perspective, this.modelRender, this.metadataCacheService,
				this.modelCacheService);

	}

	@Override
	public WorldObject buildModel(Way pWay, Perspective perspective) {
		return null;
	}

	@Override
	public WorldObject buildModel(Relation pRelation, Perspective perspective) {
		return null;
	}

	/**
	 * @return the modelRender
	 */
	 public ModelRender getModelRender() {
		 return this.modelRender;
	 }

	 /**
	  * @param modelRender
	  *            the modelRender to set
	  */
	 public void setModelRender(ModelRender modelRender) {
		 this.modelRender = modelRender;
	 }

}
