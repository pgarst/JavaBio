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
public class Protein {
    
    // One copy of the amino acid set for all proteins
    private static Alphabet alphabet    = null;
    
    private AminoAcid[] sequence;

    Protein(String pr) {
        
        if (alphabet == null)
            alphabet    = new Alphabet();
        
        sequence    = new AminoAcid[pr.length()];
        for (int i = 0; i < pr.length(); i++)
            sequence[i] = alphabet.get1(pr.charAt(i));
    }

    double getMass() {
        
        double  total   = 0;
        for (int i = 0; i < sequence.length; i++)
            total   += sequence[i].getMass();
        
        return total;
    }

    String as3() {
          
        StringBuilder   val = new StringBuilder();
        for (int i = 0; i < sequence.length; i++)
            val.append(sequence[i].get3());
        
        return val.toString();
    }

    String asdna() {
          
        StringBuilder   val = new StringBuilder();
        for (int i = 0; i < sequence.length; i++)
            val.append(sequence[i].getCodon());
        
        return val.toString();
    }

    String as1() {
        
        StringBuilder   val = new StringBuilder();
        for (int i = 0; i < sequence.length; i++)
            val.append(sequence[i].get1());
        
        return val.toString();
    }
    
}
