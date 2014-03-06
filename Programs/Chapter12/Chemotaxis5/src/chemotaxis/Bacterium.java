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
import java.util.Random;

/**
 *
 * @author pgarst
 */
public class Bacterium
        extends Thread {

    private PondView pview;
    private Pond pond;
    private Random rand;
    private boolean done = false;
    
    // Parameters controlling chemotaxis
    private double brownian = 0.1;
    private Genotype    params;
    
    // Current state
    private int x, y;           // Position
    private double direction;   // In radians
    private double tprob;       // tumbling probability
    private double basec;       // Food concentration for steady state
    private double hitpts = 100;
    private int    steps = 0;

    public Bacterium
        (
        Pond pond, 
        PondView pview, 
        Random rand, 
        int x, 
        int y,
        Genotype params
        ) {

        this.params = params;
        init(pond, pview, rand, x, y);
    }

    public Bacterium(Pond pond, PondView pview, Random rand) {

        x = pond.randomX();
        y = pond.randomY();

        params  = new Genotype();
        init(pond, pview, rand, x, y);
    }

    private void init(Pond pond, PondView pview, Random rand, int x, int y) {

        this.pview = pview;
        this.pond = pond;
        this.rand = rand;
        this.x = x;
        this.y = y;

        direction = 2 * Math.PI * rand.nextDouble();

        pview.addBug(this);
    }

    @Override
    public void run() {

        while (true) {
            if (done) {
                pview.remBug(this);
                pond.remBug(this);
                break;
            }

            try {
                sleep(20);
            } catch (InterruptedException e) {
            }

            if (!checkFood()) {
                pond.remBug(this);
                pview.remBug(this);
                pview.repaint();
                break; 
            }
            
            nextPosition();
            pview.repaint();
        }
    }
    
    private boolean checkFood () {
        
        // Food is high at 0
        double  food = 1 - ((double ) x) / pview.getWidth();
        
        // Split it up 
        food    = 50 * (food / pond.getPopulation());  
        
        hitpts  += food - params.get(Genes.CONSUMPTION);
        if (hitpts < 0)
            return false;
        
        if ((hitpts > 200) && !done) {
            pond.newBug(x, y, params.mutate(rand));
            hitpts   -= 100;
        }
        
        return true;
    }

    private void nextPosition() {

        // Swimming or tumbling
        if (tumble()) {
            direction = 2 * Math.PI * rand.nextDouble();
            steps = 0;
            return;
        }

        steps++;
        tprob  = getTprob();

        // Direction
        addBrownian();

        // Bounce off the walls
        bounce();

        // Get next position
        double  speed   = params.get(Genes.SPEED);
        x += (int) (speed * Math.cos(direction));
        y += (int) (speed * Math.sin(direction));
    }
   
    // Tumbling probability is higher when food is lower,
    // toward the right hand side.
    // We adapt to the current food level over time; this is a
    // behavioral simulation, not a simulation of the mechanism.
    private double getTprob () {
        
        // Get x position in 0 - 1 range
        double  pos = ((double ) x) / pview.getWidth();
        
        // Food is high at 0
        double  food    = 1 - pos;
        double  diff    = food - basec;
        
        // If diff is 0 we return target value. 
        // If it is > 0 we return something less; < 0, something more
        double  val = params.get(Genes.TPROB) - diff * params.get(Genes.SLOPE);
        val = Math.max(0.01, val);
        val = Math.min(0.5, val);
        
        // Update base food level for next time
        basec   += params.get(Genes.REVERT) * (food - basec);
        
        return val;
    }

    private boolean tumble() {
        
        // return (steps * tprob >= 1.0);
        return rand.nextDouble() <= tprob;
    }

    private void addBrownian() {

        direction += brownian * (rand.nextDouble() - 0.5);
        restrict();
    }

    private void restrict() {

        while (direction < 0) {
            direction += 2 * Math.PI;
        }
        while (direction > 2 * Math.PI) {
            direction -= 2 * Math.PI;
        }
    }

    private void bounce() {

        int w = pview.getWidth();
        int h = pview.getHeight();

        boolean left = (direction > (Math.PI / 2))
                && (direction < (1.5 * Math.PI));
        boolean reflect = ((x < 10) && left) || ((x > (w - 10)) && !left);
        if (reflect) {
            direction = Math.PI - direction;
            restrict();
        }

        if (y < 10) {
            if (direction > Math.PI) {
                direction = 2 * Math.PI - direction;
            }
        } else if (y > (h - 10)) {
            if (direction < Math.PI) {
                direction = 2 * Math.PI - direction;
            }
        }
    }

    public void finished() {

        done = true;
    }
    
    public double getGene (Genes g) {
        
        return params.get(g);
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public int getRadius() {

        return 3;
    }
}
