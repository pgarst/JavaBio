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
 * Solution to section 3.14, problem 3.
 * @author peterg
 */
public class Problem3 {
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Part a: for ( ; test; ) is equivalent to while(test)
        
        // Part d.
        int     nloops      = 1;
        double  previous    = 2.0;
        double  current     = Math.sqrt(previous);
        while (current < previous) {
            nloops++;
            previous    = current;
            current     = Math.sqrt(previous);
        }
        
        System.out.println(nloops + " loops until we flatlined");
        
        // Part e. Use a do - while loop.
        // We need to be careful about the edge conditions to get the
        // same value.
        nloops      = 0;
        current     = 2.0;
        do {
            previous    = current;
            current     = Math.sqrt(previous);
            nloops++;
        } while (current < previous);
        
        System.out.println(nloops + " loops until we flatlined");  
        
    }
}
