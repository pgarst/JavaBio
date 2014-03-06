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
package neuron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author peter
 */
public class NumericParams
        extends HashMap<String, Double> {

    /**
     * Construct a parameter hash table from a file. We assume the parameters
     * are floating point numbers.
     *
     * @param fname the file name
     */
    public NumericParams(String fname) {

        addFile(fname);
    }
    
    public void addFile (String fname) {
               
        try {
            BufferedReader rd = new BufferedReader(new FileReader(fname));

            String line;
            while ((line = rd.readLine()) != null) {
                StringTokenizer tok = new StringTokenizer(line);

                if (!tok.hasMoreTokens()) {
                    continue;
                }
                String name = tok.nextToken();

                if (!tok.hasMoreTokens()) {
                    continue;
                }
                String val = tok.nextToken();

                try {
                    Double dval = new Double(val);
                    put(name, dval);
                }
                catch (NumberFormatException e) {
                    // Just skip the line if it is not a valid number.
                }
            }

            rd.close();
        }
        catch (IOException e) {
        }
    }

    public double lookup(String name, double def) {

        Double val = get(name);
        if (val == null) {
            return def;
        }

        return val;
    }
}
