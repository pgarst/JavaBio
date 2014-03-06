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

import java.util.Arrays;

/**
 * Solution to section 3.14, problem 5.
 * Find an easy way to sort an array of doubles.
 * @author peterg
 */
public class Problem5 {
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        double[]    test    = {3.141, 2.178, -1, 42};
        
        // Look up the documentation on this class.
        // It also supports searching and other useful functions.
        Arrays.sort(test);
        
        // Here is another useful method in the Arrays class.
        System.out.println(Arrays.toString(test));
    }
}
