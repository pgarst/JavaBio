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

    private int x, y;       // Position
    private boolean swimming;   // Swimming or tumbling
    private double direction;  // In radians
    private int speed = 5;
    private double brownian = 0.1;

    public Bacterium(Pond pond, PondView pview, Random rand, int x, int y) {

        init(pond, pview, rand, x, y);
    }

    public Bacterium(Pond pond, PondView pview, Random rand) {

        x = pond.randomX();
        y = pond.randomY();

        init(pond, pview, rand, x, y);
    }

    private void init(Pond pond, PondView pview, Random rand, int x, int y) {

        this.pview = pview;
        this.pond = pond;
        this.rand = rand;
        this.x = x;
        this.y = y;

        swimming = true;
        direction = 2 * Math.PI * rand.nextDouble();

        pview.addBug(this);
    }

    @Override
    public void run() {

        while (true) {

            try {
                sleep(50);
            }
            catch (InterruptedException e) {
            }

            nextPosition();
            pview.repaint();
        }
    }

    private void nextPosition() {

        // Swimming or tumbling
        if (rand.nextInt(10) == 5) {
            // Tumbling
            direction = 2 * Math.PI * rand.nextDouble();
            return;
        }

        // Direction
        addBrownian();

        // Bounce off the walls
        bounce();

        // Get next position
        x += (int) (speed * Math.cos(direction));
        y += (int) (speed * Math.sin(direction));
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
            if (direction > Math.PI)
                direction = 2 * Math.PI - direction;
        } else if (y > (h - 10)) {
            if (direction < Math.PI)
                direction = 2 * Math.PI - direction;
        }
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
