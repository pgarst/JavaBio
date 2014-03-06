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
package neuron;

import java.util.ArrayList;
import java.util.Formatter;

/**
 *
 * @author Peter
 */
public class NeuronSegment {

    private double potential;  // Units are mv
    private double capacitance;
    private double time = 0;
    private ArrayList<Current> currents;
    private NumericParams np;
    private Formatter f = null;

    public NeuronSegment(NumericParams np) {

        // Initial values
        this.np = np;
        potential = -65;
        capacitance = np.lookup("capacitance", 30);

        currents = new ArrayList<Current>();

        currents.add(new Stimulation(np));
        currents.add(new CalciumConductance(np));
        currents.add(new PotassiumConductance(np));
        currents.add(new LeakConductance(np));
    }

    public void step(double interval) {

        double current = 0;
        f = new Formatter();

        for (Current c : currents) {
            double cstep = c.current(potential, time);
            current -= cstep;

            // Print the current
            f.format(" %7.2f", cstep);
        }

        // The derivative of the potential is proportional to the current,
        // depending on the capacitance.
        double deltav = current / capacitance;
        potential += deltav;
        time += interval;
    }

    public double getPotential() {

        return potential;
    }

    public String printHeader() {

        String res = "time V";
        for (Current c : currents) {
            res = res + " " + c;
        }

        return res;
    }

    @Override
    public String toString() {

        String val = String.format("%4.1f %8.2f  ", time, potential);

        // Dummy output on first line
        if (f == null)
            val += "    0.00    0.00    0.00    0.00";
        else
            val += f;
        return val;
    }
}
