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
 * Solution to section 3.14, problem 4. Explore exceptional values for doubles.
 *
 * @author peterg
 */
public class Problem4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Try invalid values.
        double test = Math.sqrt(-1);
        System.out.println("Try sqrt of -1:  " + test);
        if (Double.isNaN(test))
            System.out.println("    This is not a number");

        test = test + 5;
        System.out.println("Now add 5 to it:  " + test);

        // Generate overflow
        test = 1000;
        for (int i = 1; i < 10; i++) {
            System.out.println(i + ".  " + test);
            if (Double.isInfinite(test))
                System.out.println("    This is infinite");
            test = test * test;
        }
    }
}
