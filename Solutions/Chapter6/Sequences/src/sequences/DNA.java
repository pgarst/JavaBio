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
package sequences;

/**
 *
 * @author peterg
 */
public class DNA 
    extends Sequence<DNABase> {

    DNA(String line) 
        throws SequenceException {
        
        for (int i = 0; i < line.length(); i++) {
            DNABase base    = DNABase.getBase(line.charAt(i));
            if (base == null)
                throw new SequenceException("Invalid dna base " + line.charAt(i));
            add(base);
        }
    }
    
}
