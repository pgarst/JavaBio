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
package monkey2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Peter
 */
class DataSource {

    private BufferedReader rd = null;
    private String buffer = null;
    private int pos;
    private boolean nvalid = false;
    private int nextch;
    private char[] alphabet;

    DataSource(String fname, char[] alphabet) {

        this.alphabet = alphabet;
        try {
            rd = new BufferedReader(new FileReader(fname));
        }
        catch (IOException e) {
        }
    }

    private boolean tryBuffer() {

        if (buffer == null)
            return false;

        while (pos < buffer.length()) {
            char c = buffer.charAt(pos++);
            c = Character.toUpperCase(c);

            for (int i = 0; i < alphabet.length; i++) {
                if (c == alphabet[i]) {
                    nvalid = true;
                    nextch = i;
                    return true;
                }
            }
        }

        // If we were more careful we would insert a space at the
        // end of the line, if space is in the alphabet
        return false;
    }

    boolean hasNextChar() {

        if (nvalid)
            return true;

        while (true) {
            if (tryBuffer())
                return true;
            if (rd == null)
                return false;

            try {
                buffer = rd.readLine();
                pos = 0;

                if (buffer == null) {
                    rd.close();
                    rd = null;
                    return false;
                }
            }
            catch (IOException ex) {
                rd = null;
                return false;
            }

            // We have a new buffer - try it on next loop
        }
    }

    // Return index in alphabet, not the char itself
    int nextChar() {

        hasNextChar();
        if (nvalid) {
            nvalid = false;
            return nextch;
        }

        return 0;
    }
}
