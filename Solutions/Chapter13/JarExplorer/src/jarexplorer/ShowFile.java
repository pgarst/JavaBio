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
package jarexplorer;

import java.awt.Frame;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 * We need a tree selection listener to get the clicks on the files in the jar.
 * It would also be possible to make JarExplorer implement TreeSelectionListener
 * and do it internally there.
 *
 * @author peterg
 */
public class ShowFile
        implements TreeSelectionListener {

    private JTree tree;
    private Frame frame;
    private JarFile jarfile;

    public ShowFile(JTree tree, Frame frame) {

        this.tree = tree;
        this.frame = frame;
    }

    @Override
    public void valueChanged(TreeSelectionEvent tse) {

        JarNode node = (JarNode) tree.getLastSelectedPathComponent();
        if (node == null)
            return;
        if (jarfile == null)
            return;

        // Do we have a plugin for it?
        // Just hardwire a plugin location for now.
        String name = (String) node.getUserObject();
        PluginIterator plugins = new PluginIterator("../Plugins/dist");
        for (JEPlugin jp : plugins) {
            if (name.endsWith(jp.getExtension())) {
                // Get contents of jar entry
                InputStream stream;
                try {
                    stream = jarfile.getInputStream(node.getEntry());
                }
                catch (IOException ex) {
                    continue;
                }
                PluginDisplay pd = new PluginDisplay(frame);
                jp.usePlugin(stream, pd.getPanel());
                pd.setVisible(true);
                return;
            }
        }

    }

    public void setJar(JarFile jf) {

        jarfile = jf;
    }

}
