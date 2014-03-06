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
 * @author Peter
 */
public class CalciumConductance
        extends Conductance {

    // An instantaneous voltage gated current
    CalciumConductance(NumericParams np) {

        maxg = np.lookup("maxca", 4.4);
        equilibrium = np.lookup("caequilibrium", 120);
        v1 = np.lookup("cav1", -1.2);
        v2 = np.lookup("cav2", 18);
    }

    private static double v1;
    private static double v2;

    protected double permeability(double potential) {

        double transformed = (potential - v1) / v2;
        return 0.5 * (1 + Math.tanh(transformed));
    }

    public String toString() {

        return "Ca++";
    }
}
