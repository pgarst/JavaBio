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
package fibonacci;

/**
 *
 * @author peterg
 */
public class Fibonacci {

    /**
     * Solution to section 9.4.2.7, problem 2.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: fibonacci number");
            System.exit(0);
        }

        int n = 0;
        try {
            n = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            System.out.println("Usage: fibonacci number");
            System.exit(0);
        }
        
        System.out.println("Fib(" + n + ") = " + fib(n));
    }
    
    // Not very efficient - can you do better?
    private static int fib (int n) {
        
        if (n < 3)
            return 1;
        return fib(n-1) + fib(n-2);
    }

}
