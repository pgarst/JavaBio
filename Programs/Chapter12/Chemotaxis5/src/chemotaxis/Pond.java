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
package chemotaxis;

import chemoui.PondView;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author pgarst
 */
public class Pond {

    private final ArrayList<Bacterium> bugs = new ArrayList<>();
    private Random rand = new Random(1);
    private PondView pview;
    private boolean running = false;

    /**
     * This is the real program initialization
     */
    public Pond() {

        // Initialize evolution
        Genes.SPEED.setValues(3.0, 7.0, 0.1, 5.0);
        Genes.SLOPE.setValues(0.1, 0.8, 0.02, 0.5);
        Genes.CONSUMPTION.setValues(0.5, 1.5, 0.03, 1.0);
        Genes.REVERT.setValues(0.05, 0.2, 0.01, 0.1);
        Genes.TPROB.setValues(0.01, 0.2, 0.001, 0.05);
    }

    public void populate(PondView pview) {

        this.pview = pview;
        running = true;

        // Start with 10
        for (int i = 0; i < 10; i++) {
            Bacterium bug = new Bacterium(this, pview, rand);
            synchronized (bugs) {
                bugs.add(bug);
            }

            bug.start();
        }
    }

    void newBug(int x, int y, Genotype params) {

        Bacterium bug = new Bacterium(this, pview, rand, x, y, params);

        synchronized (bugs) {
            if (!running) {
                return;
            }

            bugs.add(bug);
        }

        bug.start();
    }

    void remBug(Bacterium bug) {

        synchronized (bugs) {
            bugs.remove(bug);
            pview.remBug(bug);
        }
    }

    public void stop() {

        synchronized (bugs) {
            running = false;
            for (Bacterium bug : bugs) {
                bug.finished();
            }
            bugs.clear();
        }
        pview.clear();
        pview.repaint();
    }

    int getPopulation() {

        synchronized (bugs) {
            return bugs.size();
        }
    }

    int getPosition() {

        int totpos = 0;
        int nbugs;

        synchronized (bugs) {
            nbugs = bugs.size();

            for (Bacterium bug : bugs) {
                totpos += bug.getX();
            }
        }
        if (nbugs == 0)
            return 50;

        int pos = (100 * totpos) / (nbugs * pview.getWidth());
        return pos;
    }

    private String getGeneVals() {

        String res = "  Params ";

        for (Genes g : Genes.values()) {
            double total = 0;

            synchronized (bugs) {
                int nbugs = bugs.size();

                for (Bacterium bug : bugs) {
                    total += bug.getGene(g);
                }
                if (nbugs > 0)
                    total /= nbugs;
            }

            res = res + String.format(" %5.3f", total);
        }

        return res;
    }

    String getReport() {

        return "Position " + getPosition()
                + "  Population " + getPopulation()
                + getGeneVals();
    }

    int randomX() {

        return rand.nextInt(pview.getWidth());
    }

    int randomY() {

        return rand.nextInt(pview.getHeight());
    }

    PondView getView() {

        return pview;
    }
}
