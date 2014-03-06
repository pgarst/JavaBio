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
package numbers1;

/**
 * Chapter 3
 * Numbers1 sample program
 * This program uses numbers and variables. It combines variables with
 * basic numeric operations and assignment.
 * 
 * @author peterg
 */
public class Numbers {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
        {
        
        int volume;

        // Determined experimentally
        double slope = 0.0474, intercept = -0.0649;
        double opticalDensity;     // Measured variable
        double concentration;      // Output variable

        // These were measured in the experiment.
        volume = 15;
        opticalDensity = .775;

        /*
         * Calculate the mass from
         * opticalDensity = slope * mass + intercept
         */
        double mass = (opticalDensity - intercept) / slope;
        concentration = mass / volume;

        System.out.println("Concentration = " + concentration);
        }
}
