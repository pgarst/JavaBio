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
package driftswing;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author peterg
 */
public class GraphArea
        extends JPanel {

    private double[][] data = null;
    private Color   linecolor = Color.BLUE;

    void setData(double[][] data) {

        this.data = data;
        repaint();
    }
    
    void setLineColor(Color color) {
        
        linecolor   = color;
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        g.setColor(linecolor);

        if (data == null)
            return;

        // Get transform from data to screen.
        // We're assuming a y range of 0 - 1
        int xlen = data.length;
        double xbeg = 20;
        double xfact = ((double) (getWidth() - 40)) / data[0].length;
        double ybeg = getHeight() - 20;
        double yfact = 40 - getHeight();

        for (int i = 0; i < xlen; i++) {
            int prevx = (int) (xbeg);
            int prevy = (int) (ybeg + yfact * data[i][0]);

            for (int j = 1; j < data[i].length; j++) {
                int x = (int) (xbeg + j * xfact);
                int y = (int) (ybeg + yfact * data[i][j]);

                g.drawLine(prevx, prevy, x, y);

                prevx = x;
                prevy = y;
            }
        }
    }

}
