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
 * Solution to section 3.14, problem 2.
 * @author peterg
 */
public class Problem2 {
    
    /*
     * We have not yet discussed methods.
     * You can also solve this problem by putting the code here into
     * the main method.
     */
    private static double getRoundoffD (int nparts) {
        
        double  part    = 1.0 / nparts;
        double  total   = 0;
        
        for (int i = 0; i < nparts; i++)
            total   += part;
        
        return (total - 1.0);      
    }
    
    private static double getRoundoffF (int nparts) {
          
        // The division by itself produces a double, and we
        // must cast it to a float.
        float  part    = (float ) (1.0 / nparts);
        float  total   = 0;
        
        for (int i = 0; i < nparts; i++)
            total   += part;
        
        return (total - 1.0);      
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        for (int n = 10; n <= 100; n += 10) {
            double  roundd  = getRoundoffD(n);
            double  roundf  = getRoundoffF(n);
            double  ratio   = roundf / roundd;
            
            System.out.print("Roundoff error for " + n + " parts: ");
            System.out.println("float error " + roundf + "   double error " + roundd + "   ratio " + ratio);
        }
        
        // Now consider order of addition
        double  totd    = 1.0;
        float   totf    = (float) 1.0;

        for (int i = 2; i < 100000000; i++) {
            totd    += (1.0 / i);
            totf    += (float ) (1.0 / i);
        }

        System.out.println("Answers, big to little: " + totd + "    " + totf);

        totd    = 1.0;
        totf    = (float) 1.0;

        for (int i = 100000000; i > 1; i--) {
            totd    += (1.0 / i);
            totf    += (float ) (1.0 / i);
        }

        System.out.println("Answers, little to big: " + totd + "    " + totf);
    }
}
