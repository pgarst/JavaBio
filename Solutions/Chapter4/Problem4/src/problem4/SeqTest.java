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
package problem4;

/**
 *
 * @author peterg
 */
public class SeqTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Sequence seq    = new Sequence("gattc");
        
        // RNA or DNA?
        System.out.println("DNA? " + seq.isDNA() + "   RNA? " + seq.isRNA());
        
        // Get both versions
        System.out.println("DNA: " + seq.getDNA() + "   RNA: " + seq.getRNA());
        
        // And get reverse complement
        System.out.println("Reverse complement: " + seq.getRevComplement());
    }
    
}
