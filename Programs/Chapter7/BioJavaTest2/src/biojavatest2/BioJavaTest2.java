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
package biojavatest2;

import org.biojava.bio.structure.Structure;
import org.biojava.bio.structure.gui.BiojavaJmol;
import org.biojava.bio.structure.io.PDBFileReader;

/**
 *
 * @author peterg
 */
public class BioJavaTest2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            PDBFileReader pdbr = new PDBFileReader();
            pdbr.setPath(".");

            String pdbCode = "1IYJ";

            Structure struc = pdbr.getStructureById(pdbCode);
            BiojavaJmol jmolPanel = new BiojavaJmol();
            jmolPanel.setStructure(struc);

            // send some RASMOL style commands to Jmol
            jmolPanel.evalString("select *; color chain;");
            jmolPanel.evalString("select *; wireframe off; backbone 0.4;");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
