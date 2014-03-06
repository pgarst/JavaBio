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

    private ArrayList<Bacterium> bugs = new ArrayList<>();
    private Random rand = new Random(1);
    private PondView pview;

    public Pond() {
    }

    public void populate(PondView pview) {

        this.pview = pview;

        // Start with 20
        for (int i = 0; i < 100; i++) {
            Bacterium bug = new Bacterium(this, pview, rand);
            bug.start();
            bugs.add(bug);
        }
    }

    int getPopulation() {

        return bugs.size();
    }

    int getPosition() {
        
        int nbugs   = bugs.size();
        int totpos  = 0;
        
        for (Bacterium bug : bugs)
        {
            totpos  += bug.getX();
        }
        
        int pos = (100 * totpos) / (nbugs * pview.getWidth());
        return pos;
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
