/*
 * Copyright (C) 2017 Premium Minds.
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
package com.premiumminds.billy.gin.services.impl.pdf;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Optional;
import java.util.UUID;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FOPPDFTransformer {

    private static final Logger log = LoggerFactory.getLogger(FOPPDFTransformer.class);
    private static final String QR_CODE_PATH = "qrCodePath";
    private static final String QR_CODE = "qrCode";

    private final TransformerFactory transformerFactory;

    public FOPPDFTransformer(TransformerFactory transformerFactory) {
        this.transformerFactory = transformerFactory;
    }

    public FOPPDFTransformer() {
        this(TransformerFactory.newInstance());
    }

    private Source mapParamsToSource(ParamsTree<String, String> documentParams) {
        return new StreamSource(new StringReader(this.generateXML(documentParams)));
    }

    private String generateXML(ParamsTree<String, String> tree) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");

        this.writeXML(strBuilder, tree.getRoot());

        return strBuilder.toString();
    }

    private void writeXML(StringBuilder strBuilder, Node<String, String> node) {
        strBuilder.append("<").append(node.getKey());
        if (null == node.getValue() && !node.hasChildren()) {
            strBuilder.append("/>");
        } else {
            strBuilder.append("> ");
            if (null != node.getValue()) {
                strBuilder.append(StringEscapeUtils.escapeXml(node.getValue()));
            }

            for (Node<String, String> child : node.getChildren()) {
                this.writeXML(strBuilder, child);
            }
            strBuilder.append("</").append(node.getKey()).append(">");
        }
    }

    protected void transformToStream(InputStream templateStream, ParamsTree<String, String> documentParams,
            OutputStream outStream) throws ExportServiceException {

        // creation of transform source
        StreamSource transformSource = new StreamSource(templateStream);

        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance();
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // to store output

        Optional<Node<String, String>> qrCodeString = documentParams
            .getRoot()
            .getChildren()
            .stream()
            .filter(stringStringNode -> stringStringNode.getKey().equals(QR_CODE))
            .findAny();

        Path qr = null;
        try {
            if(qrCodeString.isPresent() && !qrCodeString.get().getValue().isEmpty()){
                qr = createQR(qrCodeString.get().getValue());
                documentParams.getRoot().addChild(QR_CODE_PATH, qr.toString());
            }
            // the XML file from which we take the name
            Source source = this.mapParamsToSource(documentParams);

            Transformer xslfoTransformer = this.getTransformer(transformSource);

            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outStream);

            // Resulting SAX events (the generated FO)
            // must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // everything will happen here..
            xslfoTransformer.transform(source, res);
        } catch (FOPException e) {
            throw new ExportServiceException("Error using FOP to open the template", e);
        } catch (TransformerException e) {
            throw new ExportServiceException("Error generating pdf from template and data source", e);
        } catch (IOException | WriterException e) {
            throw new ExportServiceException("Error generating qrCode", e);
        } finally {
            deleteTempFileIfExists(qr);
        }
    }

    public File toFile(URI fileURI, InputStream templateStream, ParamsTree<String, String> documentParams)
            throws ExportServiceException {
        // if you want to save PDF file use the following code
        File pdffile = new File(fileURI);
        try (OutputStream out = new java.io.BufferedOutputStream(new FileOutputStream(pdffile))) {
            this.transformToStream(templateStream, documentParams, out);
            return pdffile;
        } catch (FileNotFoundException e) {
            throw new ExportServiceException("Could not create pdf file", e);
        } catch (IOException e) {
            throw new ExportServiceException("IO error while saving the pdf file", e);
        }
    }

    private Transformer getTransformer(StreamSource streamSource) throws TransformerConfigurationException {
        return this.transformerFactory.newTransformer(streamSource);
    }

    private Path createQR(String data)
        throws WriterException, IOException
    {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        EnumMap<EncodeHintType, String> hints = new EnumMap<> (EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M.name());
        hints.put(EncodeHintType.MARGIN, String.valueOf(4));
        hints.put(EncodeHintType.QR_VERSION, String.valueOf(9));
        BitMatrix bitMatrix = qrCodeWriter.encode(
            new String(data.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8),
            BarcodeFormat.QR_CODE,
            350, 350,hints);

        final Path file = Files.createTempFile(UUID.randomUUID().toString().replace("-", ""), ".png");
        MatrixToImageWriter.writeToPath(
            bitMatrix,
            "png",
            file);

        return file;
    }

    private void deleteTempFileIfExists(Path path) {
        if(path != null) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                log.error("Could not delete file {}", path, e);
            }
        }
    }

}
