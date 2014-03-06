/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package getids;

import java.io.StringWriter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author pgarst
 */
class ShowInfo {

    private String gene;
    private String nl = System.getProperty("line.separator");

    ShowInfo(String gene) {

        this.gene = gene;
    }

    private void showResult(Response ans) {

        if (ans.getStatus() == 200) {
            System.out.println(ans.readEntity(String.class));
        } else {
            System.out.println("Bad return code " + ans.getStatus());
        }
    }

    private Response doGet(String xml, WebTarget mart) {

        mart = mart.queryParam("query", xml);
        Invocation inv = mart.request().buildGet();
        return inv.invoke();

        // Simplest form of get:
        // mart.request().get(String.class);
    }

    private Response doPost(String xml, WebTarget mart) {

        MultivaluedMap formdata = new MultivaluedHashMap<String, String>();
        formdata.add("query", xml);
        Invocation inv = mart.request().buildPost(Entity.form(formdata));
 
        return inv.invoke();
    }

    private String constructXml() {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element ele = doc.createElement("Query");
            ele.setAttribute("virtualSchemaName", "default");
            ele.setAttribute("formatter", "TSV");
            ele.setAttribute("header", "0");
            ele.setAttribute("uniqueRows", "1");
            ele.setAttribute("count", "");
            ele.setAttribute("datasetConfigVersion", "0.6");

            Element dset = doc.createElement("Dataset");
            dset.setAttribute("name", "hsapiens_gene_ensembl");
            dset.setAttribute("interface", "default");

            Element nd = doc.createElement("Filter");
            nd.setAttribute("name", "hgnc_symbol");
            nd.setAttribute("value", gene);
            dset.appendChild(nd);

            nd = doc.createElement("Attribute");
            nd.setAttribute("name", "ensembl_gene_id");
            dset.appendChild(nd);

            nd = doc.createElement("Attribute");
            nd.setAttribute("name", "ensembl_peptide_id");
            dset.appendChild(nd);

            nd = doc.createElement("Attribute");
            nd.setAttribute("name", "mmusculus_homolog_ensembl_gene");
            dset.appendChild(nd);

            nd = doc.createElement("Attribute");
            nd.setAttribute("name", "mmusculus_homolog_ensembl_peptide");
            dset.appendChild(nd);

            nd = doc.createElement("Attribute");
            nd.setAttribute("name", "mmusculus_homolog_perc_id");
            dset.appendChild(nd);

            ele.appendChild(dset);
            doc.appendChild(ele);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer serializer = tFactory.newTransformer();

            Source s = new DOMSource(doc);
            StringWriter stw = new StringWriter();
            serializer.transform(s, new StreamResult(stw));
            return stw.toString();
        }
        catch (ParserConfigurationException ex) {
            System.out.println("Exception " + ex);
        }
        catch (TransformerConfigurationException ex) {
            System.out.println("Exception " + ex);
        }
        catch (TransformerException ex) {
            System.out.println("Exception " + ex);
        }

        return null;
    }

    private String constructXml1() {

        String test
                = "<?xml version='1.0' encoding='UTF-8'?>"
                + "<!DOCTYPE Query>"
                + "<Query  virtualSchemaName = 'default' formatter = 'TSV' header = '0' uniqueRows = '0' count = '' datasetConfigVersion = '0.6' >"
                + "<Dataset name = 'hsapiens_gene_ensembl' interface = 'default' >"
                + "<Filter name = 'hgnc_symbol' value = 'ZFY'/>"
                + "<Filter name = 'with_homolog_acar' excluded = '0'/>"
                + "<Attribute name = 'ensembl_gene_id' />"
                + "<Attribute name = 'ensembl_transcript_id' />"
                + "</Dataset>"
                + "</Query>";

        return test;
    }

    void results() {

        Client cl = ClientBuilder.newClient();
        String service = "http://www.ensembl.org/biomart/martservice";
        WebTarget mart = cl.target(service);

        // Construct the query
        String xml = constructXml();
        Response ans = doPost(xml, mart);

        showResult(ans);
        cl.close();
    }
}
