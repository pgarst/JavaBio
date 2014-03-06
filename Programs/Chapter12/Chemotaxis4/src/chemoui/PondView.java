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
package chemoui;

import chemotaxis.Bacterium;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author pgarst
 */
public class PondView
        extends JComponent {

    private ArrayList<Bacterium> items = new ArrayList<>();
    private int tot = 0;

    @Override
    synchronized public void paint(Graphics g) {

        super.paint(g);

        // Draw background first
        drawGradient(g);
        for (Bacterium p : items) {
            g.setColor(Color.BLACK);
            circle(g, p, 3);
        }
    }

    private void drawGradient(Graphics g) {

        int w = getWidth();
        if (w <= 0)
            return;

        int h = getHeight();
        for (int i = 0; i < w; i++) {
            double fact = ((double) i / w);
            int r = (int) (255 - fact * 204);
            int gr = (int) (fact * 255);
            int b = gr;
            g.setColor(new Color(r, gr, b));
            g.drawLine(i, 0, i, h);
        }
    }

    private void circle(Graphics g, Bacterium p, int radius) {

        // Have center, want upper right
        int sz = 2 * radius;
        int xc = p.getX() - radius;
        int yc = p.getY() - radius;
        g.fillOval(xc, yc, sz, sz);
    }

    public synchronized void addBug(Bacterium p) {

        items.add(p);
        tot++;
    }

    public synchronized void remBug(Bacterium p) {

        items.remove(p);
        tot--;
    }

    public synchronized void clear() {

        items.clear();
    }

}
