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
package numbers3;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;

/**
 * Chapter 1
 * Numbers3 sample program
 * This program adds use of an external library.
 *
 * @author Peter
 */
public class Numbers
    {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
        {

        double[] opticalDensity =
            {
            .775, .933, .624, .474, .842
            };
        int[] volume =
            {
            15, 20, 12, 18, 17
            };

        // These represent a known relation of mass to
        // optical density
        double slope = 0.0474, intercept = -0.0649;
        double[] mass = new double[5];

        /*
         * Calculate the mass from
         * opticalDensity = slope * mass + intercept
         */
        SummaryStatistics stats = new SummaryStatistics();
        for (int i = 0; i < volume.length; i++)
            {
            mass[i] = (opticalDensity[i] - intercept) / slope;
            double concentration = mass[i] / volume[i];

            System.out.println("Concentration = " + concentration);
            stats.addValue(concentration);
            }

        System.out.println("Mean " + stats.getMean()
            + "    deviation " + stats.getStandardDeviation());
        }
    }
