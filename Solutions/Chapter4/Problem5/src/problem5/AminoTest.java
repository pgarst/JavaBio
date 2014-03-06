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
package problem5;

/**
 *
 * @author peterg
 */
public class AminoTest {

    /**
     * Solution to section 4.9, problem 5.
     * The main program takes a command and a protein sequence, where on
     * input the sequence is represented by one character amino acid codes.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length != 2) {
            System.out.println("Usage: command sequence");
            System.exit(0);
        }
        
        Protein p   = new Protein(args[1]);
        switch (args[0]) {
            case "mass":
                System.out.println("protein mass " + p.getMass());
                break;
            case "as3":
                System.out.println("protein: " + p.as3());
                break;
            case "as1":
                System.out.println("protein: " + p.as1());
                break;
            case "asdna":
                System.out.println("protein: " + p.asdna());
                break;
            default:
                System.out.println("Command can be mass, as3, as1 or asdna");
        }
    }
    
}
