/*
 * Copyright 2013 Peter Garst.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * GraphArea.java
 *
 * Created on Mar 18, 2010, 6:18:46 PM
 */
package graphit;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Peter
 */
public class GraphArea 
    extends javax.swing.JPanel {

    private Graph graph = null;
    // Cycle through a list of colors for the lines.
    private Color[] clist = {
        Color.red, Color.blue, Color.green
    };

    /**
     * Creates new form GraphArea
     */
    public GraphArea() {
    }

    public void setGraph(Graph graph) {

        System.out.println("New graph " + graph.getNpoints());
        this.graph = graph;
        repaint();
    }

    private void paintGraph(Graphics g, int width, int height) {

        // Find a transformation from values in the graph object to
        // pixels in the panel.
        graph.setTransform(width, height);

        // Now draw each line.
        int np = graph.getNpoints();
        int[] xp = graph.getXpoints();

        for (int i = 0; i < graph.getNlines(); i++) {
            int cindex = i % clist.length;
            g.setColor(clist[cindex]);

            int[] yp = graph.getYpoints(i);
            g.drawPolyline(xp, yp, np);
        }
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        if (graph != null)
            paintGraph(g, getWidth(), getHeight());    
    }
}
