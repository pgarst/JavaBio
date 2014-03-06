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
package jarexplorer;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Given a directory name, look for jar files which implement the JEPlugin
 * interface.
 * This is inefficient: we are doing this each time we have a new file
 * to look at. Can you find a more efficient way to do it?
 *
 * @author peterg
 */
public class PluginIterator
        implements Iterable<JEPlugin> {

    private Enumeration<JarEntry> jfiles;
    private int dirpos;

    private Class current;

    private ArrayList<JEPlugin> plugins;

    public PluginIterator(String dirname) {

        // Get a list of the plugins right off the bat.
        File dir = new File(dirname);
        File[] dirfiles = dir.listFiles();

        plugins = new ArrayList<>();

        for (File fl : dirfiles) {
            if (!fl.getName().endsWith(".jar"))
                continue;

            ClassLoader loader = null;
            try {
                URL jurl = new URL("file://" + fl.getAbsolutePath());
                URL[] urls = {jurl};
                loader = new URLClassLoader(urls);
            }
            catch (MalformedURLException ex) {
            }
            if (loader == null)
                continue;

            // We want to know if this jar file includes a class file
            // which implements JEPlugin.         
            try {
                JarFile jf = new JarFile(fl);
                Enumeration<JarEntry> e = jf.entries();

                // Check each entry in the jar file to see if it is a
                // class file that implements JEPlugin.
                while (e.hasMoreElements()) {
                    JarEntry je = e.nextElement();
                    if (!je.getName().endsWith(".class"))
                        continue;

                    // We have one class in the file.
                    // Loop over the interfaces it implements.
                    try {
                        Class cl = loader.loadClass(JarData.className(je.getName()));
                        Class[] interfaces  = cl.getInterfaces();
                        for (Class ci : interfaces) {
                            if (ci.getName().equals("jarexplorer.JEPlugin")) {
                                JEPlugin found   = (JEPlugin ) cl.newInstance();
                                plugins.add(found);
                                break;
                            }
                        }
                    }
                    catch (ClassNotFoundException | IllegalAccessException | InstantiationException ce) {
                        // No class, etc
                    }
                }
            }
            catch (IOException ex) {
            }                    
        }

    }

    @Override
    public Iterator<JEPlugin> iterator() {

        return plugins.iterator();
    }

}
