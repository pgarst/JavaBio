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
public class Alphabet {

    private AminoAcid[] alphabet;

    public Alphabet() {

        alphabet = new AminoAcid[20];

        alphabet[0] = new AminoAcid("Alanine", "Ala", 'A', 71.0788, "GCU");
        alphabet[1] = new AminoAcid("Cysteine", "Cys", 'C', 103.1388, "UGU");
        alphabet[2] = new AminoAcid("Aspartic acid", "Asp", 'D', 115.0886, "GAU");
        alphabet[3] = new AminoAcid("Glutamic acid", "Glu", 'E', 129.1155, "GAA");
        alphabet[4] = new AminoAcid("Phenylalanine", "Phe", 'F', 147.1766, "UUU");
        alphabet[5] = new AminoAcid("Glycine", "Gly", 'G', 57.0519, "GGU");
        alphabet[6] = new AminoAcid("Histidine", "His", 'H', 137.1411, "CAU");
        alphabet[7] = new AminoAcid("Isoleucine", "Ile", 'I', 113.1594, "AUU");
        alphabet[8] = new AminoAcid("Lysine", "Lys", 'K', 128.1741, "AAA");
        alphabet[9] = new AminoAcid("Leucine", "Leu", 'L', 113.1594, "CUU");
        alphabet[10] = new AminoAcid("Methionine", "Met", 'M', 131.1926, "AUG");
        alphabet[11] = new AminoAcid("Asparagine", "Asn", 'N', 114.1038, "AAU");
        alphabet[12] = new AminoAcid("Proline", "Pro", 'P', 97.1167, "CCU");
        alphabet[13] = new AminoAcid("Glutamine", "Gln", 'Q', 128.1307, "CAA");
        alphabet[14] = new AminoAcid("Arginine", "Arg", 'R', 156.1875, "CGU");
        alphabet[15] = new AminoAcid("Serine", "Ser", 'S', 87.0782, "AGU");
        alphabet[16] = new AminoAcid("Threonine", "Thr", 'T', 101.1051, "ACU");
        alphabet[17] = new AminoAcid("Valine", "Val", 'V', 99.1326, "GUU");
        alphabet[18] = new AminoAcid("Tryptophan", "Trp", 'W', 186.2132, "UGG");
        alphabet[19] = new AminoAcid("Tyrosine", "Tyr", 'Y', 163.1760, "UAU");
    }

    AminoAcid get1(char code) {

        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i].get1() == code)
                return alphabet[i];
        }

        return null;
    }

}
