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
package seqtools;

import java.util.Random;

/**
 *
 * @author Peter
 */
public class GenRandom
    extends Random
    {

    int choose(double[] dist)
        {

        double  p   = nextDouble();
        double  tot = 0;
        int     len = dist.length;
        
        for (int i = 0; i < len; i++)
            {
            tot += dist[i];
            if (p <= tot)
                return i;
            }
        
        // Should never get here
        return 0;
        }
    }
