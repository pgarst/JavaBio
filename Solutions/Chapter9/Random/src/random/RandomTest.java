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

/**
 *
 * @author peterg
 */
public class RandomTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Just exercise the class a bit.
        double[]    discrete    = {0.25, 0.5, 0.25};
        GenRandom   r           = new GenRandom();
        
        for (int i = 0; i < 10; i++)
            System.out.println("I choose " + r.choose(discrete));
        
        // Test the exception
        discrete[0] = 1.7;
        System.out.println("I choose " + r.choose(discrete));
    }
    
}
