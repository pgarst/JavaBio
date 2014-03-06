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
 * @author peter
 */
class Stimulation
        implements Current {

    private double start;
    private double end;
    private double level;

    public Stimulation(NumericParams np) {

        start = np.lookup("start", 0);
        end = np.lookup("end", 30);
        level = np.lookup("level", 100);
    }

    public double current(double potential, double time) {

        // Sign on stimulation is the opposite
        if ((time >= start) && (time <= end))
            return -level;

        return 0;
    }
}
