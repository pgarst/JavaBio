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
package random;

import java.util.Random;

/**
 *
 * @author peterg
 */
public class GenRandom 
    extends Random {
    
    /**
     * Choose a random element of a discrete distribution.
     * @param discrete - must be non-negative, add to 1
     * @return A random index in the distribution
     */
    public int choose (double[] discrete) {
        
        // Check the input
        double  total   = 0;
        for (double d : discrete) {
            if (d < 0)
                throw new RandomException("Negative distribution element");
            total   += d;
        }
        if (Math.abs(total - 1.0) > 0.001)
            throw new RandomException("Distribution must add to 1.");
        
        double  r   = nextDouble();
        total       = 0;
        for (int i = 0; i < discrete.length - 1; i++) {
            total   += discrete[i];
            if (r <= total)
                return i;
        }
        
        return discrete.length - 1;     
    }
    
}
