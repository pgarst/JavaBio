/*
 * Copyright 2014 Peter Garst.
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
package problems;

/**
 * Solution to section 3.14, problem 1.
 * Print 20 Fibonacci numbers, and their ratio.
 * 
 * With a bunch of problems, each with a main method, how do you
 * run one in particular?
 * Right click the Problemn.java file in the project area to the left, and
 * pick run. Or, you can use the dropdown list above to edit the configuration,
 * and pick a different main class for the project, then just use the run
 * button on the toolbar.
 * @author peterg
 */
public class Problem1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int previous    = 1;
        int current     = 1;
        
        // The first one, where we don't have a ratio, is a special case
        System.out.println("1.  " + previous);
        
        for (int i = 2; i <= 20; i++) {
            double  ratio   = ((double ) current) / previous;
            
            // You'll notice this is ugly output.
            // See the format method in String for a way to make it look better.
            System.out.println(i + ".  " + current + "   " + ratio);
            
            // Now prepare the next
            int next    = previous + current;
            previous    = current;
            current     = next;
        }
    }
    
}
