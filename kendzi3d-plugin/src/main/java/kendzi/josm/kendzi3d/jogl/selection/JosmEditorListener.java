/*
 * This software is provided "AS IS" without a warranty of any kind.
 * You use it on your own risk and responsibility!!!
 *
 * This file is shared under BSD v3 license.
 * See readme.txt and BSD3 file for details.
 *
 */
package kendzi.josm.kendzi3d.jogl.selection;

import java.text.DecimalFormat;

import kendzi.josm.kendzi3d.jogl.selection.editor.ArrowEditorJosm;
import kendzi.josm.kendzi3d.jogl.selection.event.ArrowEditorChangeEvent;
import kendzi.josm.kendzi3d.jogl.selection.event.EditorChangeEvent;

import org.apache.log4j.Logger;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.ChangeCommand;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.Way;

public class JosmEditorListener implements kendzi.josm.kendzi3d.jogl.selection.ObjectSelectionListener.EditorChangeListener {

    /** Log. */
    private static final Logger log = Logger.getLogger(JosmEditorListener.class);

    DecimalFormat formater = new DecimalFormat( "#0.0" );

    @Override
    public void onEditorChange(EditorChangeEvent event) {

        if (event instanceof ArrowEditorChangeEvent) {
            ArrowEditorChangeEvent aece = (ArrowEditorChangeEvent) event;

            if ( aece.getArrowEditor() instanceof ArrowEditorJosm) {

                ArrowEditorJosm ae = (ArrowEditorJosm) aece.getArrowEditor();
                double newValue = aece.getHeight();

                if (aece.isEnd() ) {
                    if (Main.main.getCurrentDataSet() == null) {
                        throw new RuntimeException("No current dataset!");
                    }
                    OsmPrimitive primitive = Main.main.getCurrentDataSet().getPrimitiveById(ae.getPrimitiveId(), ae.getPrimitiveType());

                    if (primitive instanceof Node) {
                    	Node newNode = new Node((Node) primitive);


                    	newNode.put(ae.getFildName(), this.formater.format(newValue));

                        ae.setValue(newValue);

                        Main.main.undoRedo.add(new ChangeCommand(primitive, newNode));

                    } else if (primitive instanceof Way) {
                        Way newWay = new Way((Way) primitive);


                        newWay.put(ae.getFildName(), this.formater.format(newValue));

                        ae.setValue(newValue);

                        Main.main.undoRedo.add(new ChangeCommand(primitive, newWay));

                    } else {
                        throw new RuntimeException("TODO");
                    }

                } else {
                    ae.preview(newValue);
                }

                ae.setValue(newValue);

            } else {
                throw new RuntimeException("TODO");
            }
        } else {
            throw new RuntimeException("TODO");
        }
    }
}
