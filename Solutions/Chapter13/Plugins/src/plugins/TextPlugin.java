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
package plugins;

import jarexplorer.JEPlugin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This is a sample plugin for the jar explorer.
 *
 * @author peterg
 */
public class TextPlugin
        implements JEPlugin {

    @Override
    public String getExtension() {

        return ".txt";
    }

    @Override
    public void usePlugin(InputStream stream, JPanel panel) {

        // Get the contents into a string.
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            char[] buffer = new char[1000];
            while (true) {
                int nread = in.read(buffer);
                if (nread < 0)
                    break;

                sb.append(buffer, 0, nread);
            }
        }
        catch (IOException e) {
            return;
        }

        JTextArea text = new JTextArea(sb.toString());
        panel.add(text);
    }

}
