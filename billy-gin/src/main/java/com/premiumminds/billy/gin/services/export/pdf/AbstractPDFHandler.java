/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy GIN.
 * 
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.export.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URI;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;

import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.BillyTemplateBundle;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;

public abstract class AbstractPDFHandler {

	private Source mapParamsToSource(ParamsTree<String, String> documentParams) {
		StreamSource source = new StreamSource(
												new StringReader(
																	AbstractPDFHandler.generateXML(documentParams)));

		return source;
	}

	public static String generateXML(ParamsTree<String, String> tree) {
		String rval = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		return rval + AbstractPDFHandler.toXML(tree.getRoot());
	}

	private static String toXML(Node<String, String> node) {
		String rval = "<" + node.getKey();
		if (null == node.getValue() && !node.hasChildren()) {
			rval += "/>";
		} else {
			rval += ">";
			rval += " " + (null != node.getValue() ? node.getValue() : "");
			for (Node<String, String> child : node.getChildren()) {
				rval += AbstractPDFHandler.toXML(child);
			}
			rval += "</" + node.getKey() + ">";
		}
		return rval;
	}

	protected ByteArrayOutputStream getStream(InputStream templateStream,
			ParamsTree<String, String> documentParams,
			BillyTemplateBundle bundle) throws ExportServiceException {

		return (ByteArrayOutputStream) this.getStream(
				templateStream, documentParams, null, bundle);
	}

	protected OutputStream getStream(InputStream templateStream,
			ParamsTree<String, String> documentParams,
			OutputStream targetStream, BillyTemplateBundle bundle)
		throws ExportServiceException {

		// the XML file from which we take the name
		Source source = this.mapParamsToSource(documentParams);
		// creation of transform source
		StreamSource transformSource = new StreamSource(templateStream);

		// create an instance of fop factory
		FopFactory fopFactory = FopFactory.newInstance();
		// a user agent is needed for transformation
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		// to store output
		OutputStream outStream = targetStream;

		Transformer xslfoTransformer;

		xslfoTransformer = this.getTransformer(transformSource);
		try {
			// Construct fop with desired output format
			Fop fop;
			fop = fopFactory.newFop(
					MimeConstants.MIME_PDF, foUserAgent, outStream);
			// Resulting SAX events (the generated FO)
			// must be piped through to FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			// everything will happen here..
			xslfoTransformer.transform(source, res);
			// if you want to get the PDF bytes, use the following code
			return outStream;
		} catch (FOPException e) {
			throw new ExportServiceException(
												"Error using FOP to open the template",
												e);
		} catch (TransformerException e) {
			throw new ExportServiceException(
												"Error generating pdf from template and data source",
												e);
		}
	}

	public File toFile(URI fileURI, InputStream templateStream,
			ParamsTree<String, String> documentParams,
			BillyTemplateBundle bundle) throws ExportServiceException {
		// if you want to save PDF file use the following code
		File pdffile = new File(fileURI);
		OutputStream out;
		try {
			out = new FileOutputStream(pdffile);
			out = new java.io.BufferedOutputStream(out);
			FileOutputStream str = new FileOutputStream(pdffile);
			this.getStream(templateStream, documentParams, out, bundle);
			str.close();
			out.close();
			return pdffile;
		} catch (FileNotFoundException e) {
			throw new ExportServiceException("Could not create pdf file", e);
		} catch (IOException e) {
			throw new ExportServiceException(
												"IO error while saving the pdf file",
												e);
		}
	}

	private Transformer getTransformer(StreamSource streamSource) {
		// setup the xslt transformer
		net.sf.saxon.TransformerFactoryImpl impl = new net.sf.saxon.TransformerFactoryImpl();

		try {
			return impl.newTransformer(streamSource);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

}
