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
package graphit;

import java.util.StringTokenizer;

/**
 * Get a tokenized list of integers, ignoring other fields.
 *
 * @author peterg
 */
public class NumTokenizer {

    private StringTokenizer tok;
    private double cur;
    private boolean haveCur;

    public NumTokenizer(String str) {

        tok = new StringTokenizer(str);
        haveCur = false;
    }

    public boolean hasMoreTokens() {

        if (haveCur)
            return true;

        while (true) {
            if (!tok.hasMoreTokens())
                return false;

            String str = tok.nextToken();
            try {
                cur = Double.parseDouble(str);
                haveCur = true;
                return true;
            }
            catch (NumberFormatException e) {
                // Ignore token and get the next.
            }
        }
    }

    public double nextNum () {

        if (!haveCur) {
            if (!hasMoreTokens())
                return 0;
        }
       
        haveCur = false;
        return cur;
    }

}
