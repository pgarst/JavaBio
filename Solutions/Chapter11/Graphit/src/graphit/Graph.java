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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Peter
 */
public class Graph {

    private double minv, maxv;
    private int nlines = 0;
    private ArrayList<Double> xvalues;
    private ArrayList<ArrayList<Double>> lines;

    // Optional configuration information
    private boolean maxvset = false, minvset = false;
    private ArrayList<Integer> columns = null;

    /*
     * Arguments are [config] data
     */
    public Graph(String[] args) {

        int apos = 0;
        if (args.length > 1) {
            config(args[0]);
            apos++;
        }
        String fname = args[apos];

        try (BufferedReader rd = new BufferedReader(new FileReader(fname))) {
            xvalues = new ArrayList<>();
            lines = new ArrayList<>();

            String gline;
            boolean started = false;
            while ((gline = rd.readLine()) != null) {
                NumTokenizer tok = new NumTokenizer(gline);
                if (!tok.hasMoreTokens())
                    continue;

                double xval = tok.nextNum();
                xvalues.add(xval);

                boolean makeyvect = (nlines == 0);
                int cno = 0;
                for (int i = 0; tok.hasMoreTokens(); i++) {
                    double yval = tok.nextNum();

                    if (columns != null) {
                        if (cno >= columns.size())
                            continue;
                        if (i != columns.get(cno))
                            continue;
                        cno++;
                    }

                    if (started) {
                        if (!minvset)
                            minv = Math.min(minv, yval);
                        if (!maxvset)
                            maxv = Math.max(maxv, yval);
                    } else {
                        started = true;
                        if (!minvset)
                            minv = yval;
                        if (!maxvset)
                            maxv = yval;
                    }

                    if (makeyvect) {
                        ArrayList<Double> yv = new ArrayList<>();
                        yv.add(yval);
                        lines.add(yv);
                        nlines++;
                    } else {
                        ArrayList<Double> yv = lines.get(i);
                        yv.add(yval);
                    }
                }
            }
        }
        catch (IOException e) {
            nlines = 0;
        }

    }

    private void config(String cname) {

        try (BufferedReader rd = new BufferedReader(new FileReader(cname))) {
            
            String  line;
            while ((line = rd.readLine()) != null) {
                StringTokenizer tok = new StringTokenizer(line);
                if (!tok.hasMoreTokens())
                    continue;
                
                String  tag = tok.nextToken();
                switch (tag) {
                    case "maxv":
                        maxv    = Double.parseDouble(tok.nextToken());
                        maxvset = true;
                        break;
                    case "minv":
                        minv    = Double.parseDouble(tok.nextToken());
                        minvset = true;
                        break;
                    case "line":
                        if (columns == null)
                            columns = new ArrayList<>();
                        columns.add(Integer.parseInt(tok.nextToken()));
                        break;
                }
            }
        }
        catch (IOException ex) {
        }
    }

    public double getMaxv() {
        return maxv;
    }

    public double getMinv() {
        return minv;
    }

    public int getNlines() {
        return nlines;
    }

    public int getNpoints() {
        return xvalues.size();
    }

    public ArrayList<Double> getXvalues() {
        return xvalues;
    }

    public ArrayList<Double> getLine(int n) {
        return lines.get(n);
    }
    private double mx, bx;
    private double my, by;

    // Map so that all lines are on the screen
    void setTransform(int width, int height) {

        // Map minx to 10, maxx to width - 10
        mx = (width - 20) / (xvalues.get(xvalues.size() - 1) - xvalues.get(0));
        bx = 10 - mx * xvalues.get(0);

        // Flip y axis - map minv to height - 10, maxv to 10
        my = (height - 20) / (minv - maxv);
        by = 10 - my * maxv;
    }

    int[] getXpoints() {

        int np = xvalues.size();
        int[] xp = new int[np];

        for (int i = 0; i < np; i++) {
            xp[i] = (int) (mx * xvalues.get(i) + bx);
        }

        return xp;
    }

    int[] getYpoints(int n) {

        int np = xvalues.size();
        int[] yp = new int[np];

        ArrayList<Double> yv = lines.get(n);
        for (int i = 0; i < np; i++) {
            yp[i] = (int) (my * yv.get(i) + by);
        }

        return yp;
    }
}
