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
package genereg2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Peter
 */
public class CellState {

    private int time = 0;
    private int nextevent = 0;
    private ArrayList<Gene> genes;
    private ArrayList<SimEvent> events;

    public CellState(String fname) {

        genes = new ArrayList<>();
        events = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fname))) {
            boolean result = initInternal(reader);

            if (!result)
                System.out.println("Error in configuration file format");
        }
        catch (IOException ex) {
            System.out.println("Unable to read configuration file");
        }
    }

    @Override
    public String toString() {

        // String state = "Time " + time + ":  ";
        String state = " " + time + "  ";

        for (Gene gene : genes) {
            String fmt = String.format("   %5.2f",
                    gene.getConcentration());
            state += fmt;
        }

        return state;
    }

    /*
     *
     */
    public boolean advance() {

        // Do the events
        while (events.size() > nextevent) {
            SimEvent ev = events.get(nextevent);
            if (ev.getTime() > time)
                break;

            if (ev.getEvent() == SimEvent.END)
                return false;

            ev.doEvent();
            nextevent++;
        }

        // Advance the genes
        for (Gene gene : genes) {
            gene.advance();
        }

        time++;
        return true;
    }

    private boolean initInternal(BufferedReader reader)
            throws IOException {

        String line;
        while ((line = reader.readLine()) != null) {
            StringTokenizer tok = new StringTokenizer(line);

            if (!tok.hasMoreTokens())
                continue;

            String word = tok.nextToken();
            if (word.equalsIgnoreCase("gene")) {
                if (!addGene(tok))
                    return false;
            } else if (word.equalsIgnoreCase("activate")) {
                if (!activateGene(tok, true))
                    return false;
            } else if (word.equalsIgnoreCase("repress")) {
                if (!activateGene(tok, false))
                    return false;
            } else if (word.equalsIgnoreCase("function")) {
                if (!setFunction(tok))
                    return false;
            } else if (word.equalsIgnoreCase("signal")) {
                if (!signalGene(tok))
                    return false;
            } else if (word.equalsIgnoreCase("concentration")) {
                if (!setConcentration(tok))
                    return false;
            } else if (word.equalsIgnoreCase("end")) {
                int end = Integer.parseInt(tok.nextToken());
                events.add(new SimEvent(SimEvent.END, end));
            }
        }

        return true;
    }

    private boolean addGene(StringTokenizer tok) {

        if (!tok.hasMoreTokens())
            return false;
        String name = tok.nextToken();

        if (!tok.hasMoreTokens())
            return false;
        double production = Double.parseDouble(tok.nextToken());

        if (!tok.hasMoreTokens())
            return false;
        double degrade = Double.parseDouble(tok.nextToken());

        genes.add(new Gene(name, production, degrade));
        return true;
    }

    private boolean activateGene(StringTokenizer tok, boolean b) {

        // Arguyments: sourcegene targgene thresh
        Gene src = findGene(tok.nextToken());
        Gene targ = findGene(tok.nextToken());
        double thresh = Double.parseDouble(tok.nextToken());

        targ.getPromoter().activate(src, thresh);

        return true;
    }

    private boolean signalGene(StringTokenizer tok) {

        // Arguments: gene [on | off] time
        if (!tok.hasMoreTokens())
            return false;
        Gene gene = findGene(tok.nextToken());
        if (gene == null)
            return false;
        if (!tok.hasMoreTokens())
            return false;

        String val = tok.nextToken();
        boolean ison = val.equalsIgnoreCase("on");
        int time = Integer.parseInt(tok.nextToken());

        events.add(new SimEvent(SimEvent.SIGNAL, gene, ison, time));

        return true;
    }

    private boolean setFunction(StringTokenizer tok) {

        // Arguments: gene [and | or]
        if (!tok.hasMoreTokens())
            return false;
        Gene gene = findGene(tok.nextToken());
        if (gene == null)
            return false;
        if (!tok.hasMoreTokens())
            return false;

        String val = tok.nextToken();
        gene.getPromoter().setAnd(val.equalsIgnoreCase("and"));

        return true;
    }

    private boolean setConcentration(StringTokenizer tok) {

        // Arguments: gene level - assume time 0
        Gene gene = findGene(tok.nextToken());
        double level = Double.parseDouble(tok.nextToken());

        events.add(new SimEvent(SimEvent.LEVEL, gene, level, 0));

        return true;
    }

    private Gene findGene(String name) {

        // We'll see a much better way to do this later
        for (Gene gene : genes) {
            if (name.equals(gene.getName()))
                return gene;
        }

        return null;
    }

}
