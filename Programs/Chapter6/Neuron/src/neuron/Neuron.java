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

/**
 *
 * @author peterg
 */
public class Neuron {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Set up the neuron
        NumericParams np = new NumericParams(args[0]);

        // Create a single membrane segment
        NeuronSegment seg = new NeuronSegment(np);

        double time = 0;
        double interval = 0.1;
        double endtime = np.lookup("endtime", 50.0);

        // Run the simulation
        // System.out.println(seg.printHeader());
        while (true) {
            // Print first to pick up the initial state
            System.out.println(seg);
            seg.step(interval);

            time += interval;
            if (time >= endtime) {
                break;
            }
        }
    }
}
