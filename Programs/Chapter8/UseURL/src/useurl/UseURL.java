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
package useurl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author peterg
 */
public class UseURL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            URL goog = new URL("http://www.google.com");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(goog.openStream()));

            while (true) {
                String ln = reader.readLine();
                if (ln == null)
                    break;
                System.out.println(ln);
            }
            reader.close();
        }
        catch (Exception ex) {
            System.out.println("URL access: " + ex);
        }
    }

}
