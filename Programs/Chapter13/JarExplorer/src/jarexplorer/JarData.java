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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author pgarst
 */
class JarData {
    // Assume a depth first directory structure in the jar file -
    // is this accurate?

    private ArrayList<JarNode> dirs;
    private JarNode root;
    private URLClassLoader loader;

    JarData(File file, JTree jarTree) {

        if (!file.canRead())
            throw new IllegalArgumentException("Unable to read " + file.getName());

        root = new JarNode(file.getName(), "");

        dirs = new ArrayList<>();

        // Get a class loader for the jarTree file
        try {
            URL jurl = new URL("file://" + file.getAbsolutePath());
            URL[] urls = {jurl};
            loader = new URLClassLoader(urls);
        }
        catch (MalformedURLException ex) {
        }

        try {
            // Read the new file
            JarFile jf = new JarFile(file);
            Enumeration<JarEntry> e = jf.entries();

            while (e.hasMoreElements()) {
                showEntry(e.nextElement());
            }
        }
        catch (IOException ex) {
        }

        DefaultTreeModel mod = new DefaultTreeModel(root);
        jarTree.setModel(mod);
    }

    private void showEntry(JarEntry entry) {

        // Get base name
        String nm = baseName(entry.getName());

        // This might attach to the current directory, or a 
        // previous one.
        JarNode nd = findDir(entry.getName());

        JarNode leaf = new JarNode(nm, entry.getName());
        nd.add(leaf);

        if (entry.isDirectory()) {
            dirs.add(leaf);
        } else if (nm.endsWith(".class")) {
            String cname = className(entry.getName());
            showMethods(leaf, cname);
        }
    }

    // We are making some assumptions about the structure of the
    // jar file.
    // fname is like A/B/C/D.class and we want A.B.C.D
    public static String className(String fname) {

        String cname = fname.substring(0, fname.length() - 6);
        cname = cname.replace('/', '.');
        return cname;
    }

    private void showMethods(JarNode hd, String cname) {

        try {
            Class cl = loader.loadClass(cname);
            Method[] methods = cl.getDeclaredMethods();

            for (Method m : methods) {
                // Is it public?
                int mod = m.getModifiers();
                if (!Modifier.isPublic(mod))
                    continue;

                JarNode mn = new JarNode("" + m, "");
                hd.add(mn);
            }
        }
        catch (ClassNotFoundException | NoClassDefFoundError ex) {
            hd.setUserObject(hd.getUserObject() + " ERROR");
        }
    }

    // Find the most recent directory for which the name is a prefix
    // of this name.
    private JarNode findDir(String name) {

        int pos = dirs.size() - 1;

        while (pos >= 0) {
            String dir = dirs.get(pos).getFullname();
            if (name.startsWith(dir))
                break;

            pos--;
        }

        // This is an optimization:
        // remove directories that have been fully used.
        int nsize = pos + 1;
        while (dirs.size() > nsize) {
            dirs.remove(nsize);
        }

        if (pos < 0)
            return root;
        return dirs.get(pos);
    }

    static public String baseName(String name) {

        if (name == null)
            return name;

        int lpos = name.length() - 1;
        int pos = name.indexOf('/');
        if ((pos < 0) || (pos == lpos))
            return name;

        while (true) {
            int np = name.indexOf('/', pos + 1);
            if ((np == lpos) || (np < 0))
                return name.substring(pos + 1);

            pos = np;
        }
    }

    private class DNode {

        String fullname;
        DefaultMutableTreeNode nd;
    }

}
