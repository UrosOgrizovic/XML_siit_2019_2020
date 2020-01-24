package com.paperpublish.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;

/**
 * 
 * Primer demonstrira koriscenje Apache FOP programskog API-a za 
 * renderovanje PDF-a primenom XSL-FO transformacije na XML dokumentu.
 *
 */
public class XSLFOTransformer {
	
	private FopFactory fopFactory;
	
	private TransformerFactory transformerFactory;
	
	public static final String RESOURCES_PATH = "src/main/resources"; 
	
	public static final String INPUT_FILE = RESOURCES_PATH + "/data/science_paper.xml";
	
	public static final String SCIENCE_PAPER_XSL_PDF_FILE = RESOURCES_PATH + "/data/xsl/science_paper_pdf.xsl";
	
	public static final String SCIENCE_PAPER_XSL_HTML_FILE = RESOURCES_PATH + "/data/xsl/science_paper_html.xsl";
	
	public static final String OUTPUT_FILE_BASE = RESOURCES_PATH + "/science_paper";
	
	
	public XSLFOTransformer() {
		try {
			// Initialize FOP factory object
			fopFactory = FopFactory.newInstance(new File("src/main/java/fop.xconf"));
			
			// Setup the XSLT transformer factory
			transformerFactory = new TransformerFactoryImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ByteArrayOutputStream generatePDF(String documentId) throws Exception {
		// Point to the XSL-FO file
		File xslPdfFile = new File(SCIENCE_PAPER_XSL_PDF_FILE);

		// Create transformation source
		StreamSource transformSourcePdf = new StreamSource(xslPdfFile);
		
		// Initialize the transformation subject
		StreamSource source = new StreamSource(new File(INPUT_FILE));

		// Initialize user agent needed for the transformation
		FOUserAgent userAgent = fopFactory.newFOUserAgent();
		
		// Create the output stream to store the results
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		// Initialize the XSL-FO transformer object
		Transformer xslFoTransformerPdf = transformerFactory.newTransformer(transformSourcePdf);
		
		// Construct FOP instance with desired output format
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);

		// Resulting SAX events 
		Result res = new SAXResult(fop.getDefaultHandler());
		
		xslFoTransformerPdf.setParameter("document_id", documentId);
		
		// Start XSLT transformation and FOP processing
		xslFoTransformerPdf.transform(source, res);

		return outStream;
	}
	
	public ByteArrayOutputStream generateHTML(String documentId) throws Exception {
		// Point to the XSL-FO file
		File xslHtmlFile = new File(SCIENCE_PAPER_XSL_HTML_FILE);

		// Create transformation source
		StreamSource transformSourceHtml = new StreamSource(xslHtmlFile);
		
		// Initialize the transformation subject
		StreamSource source = new StreamSource(new File(INPUT_FILE));

		// Initialize the XSL-FO transformer object
		Transformer xslFoTransformerHtml = transformerFactory.newTransformer(transformSourceHtml);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		xslFoTransformerHtml.setParameter("document_id", documentId);
		
		// Start XSLT transformation and FOP processing
		xslFoTransformerHtml.transform(source, new StreamResult(baos));
		return baos;
	}

	public static void main(String[] args) {
		try {
			new XSLFOTransformer().generatePDF("1");
			new XSLFOTransformer().generateHTML("2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

