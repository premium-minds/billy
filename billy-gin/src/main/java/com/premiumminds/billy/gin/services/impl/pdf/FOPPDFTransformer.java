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

import com.premiumminds.billy.gin.services.exceptions.ExportServiceException;
import com.premiumminds.billy.gin.services.export.ParamsTree;
import com.premiumminds.billy.gin.services.export.ParamsTree.Node;
import io.nayuki.qrcodegen.QrCode;
import io.nayuki.qrcodegen.QrCode.Ecc;
import io.nayuki.qrcodegen.QrSegment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

    private Source mapParamsToSource(ParamsTree<String, String> documentParams) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();

        writeXML(doc, doc, documentParams.getRoot());

        return new DOMSource(doc);
    }
    private void writeXML(Document doc, org.w3c.dom.Node parentNode, ParamsTree.Node<String, String> node) {
        Element element = doc.createElement(node.getKey());
        parentNode.appendChild(element);
        if (null != node.getValue()) {
            element.setTextContent(node.getValue());
        }

        for (ParamsTree.Node<String, String> child : node.getChildren()) {
            this.writeXML(doc, element, child);
        }
    }

    protected void transformToStream(InputStream templateStream, ParamsTree<String, String> documentParams,
            OutputStream outStream) throws ExportServiceException {

        // creation of transform source
        StreamSource transformSource = new StreamSource(templateStream);

        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(URI.create("file:/"));
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
        } catch (TransformerException|ParserConfigurationException e) {
            throw new ExportServiceException("Error generating pdf from template and data source", e);
        } catch (IOException e) {
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

    private Path createQR(String data) throws IOException
    {
        final QrCode.Ecc errCorLvl = Ecc.MEDIUM;
        final QrCode qr = QrCode.encodeSegments(
            Arrays.asList(QrSegment.makeBytes(data.getBytes(StandardCharsets.UTF_8))),
            errCorLvl,
            9,
            QrCode.MAX_VERSION,
            -1,
            false);

        final Path file = Files.createTempFile(UUID.randomUUID().toString().replace("-", ""), ".png");

        writePng(toImage(qr, 10, 4), file.toFile());

        return file;
    }

    private static BufferedImage toImage(QrCode qr, int scale, int border) {
        return toImage(qr, scale, border, 0xFFFFFF, 0x000000);
    }
    /**
     * Returns a raster image depicting the specified QR Code, with
     * the specified module scale, border modules, and module colors.
     * <p>For example, scale=10 and border=4 means to pad the QR Code with 4 light border
     * modules on all four sides, and use 10&#xD7;10 pixels to represent each module.
     * @param qr the QR Code to render (not {@code null})
     * @param scale the side length (measured in pixels, must be positive) of each module
     * @param border the number of border modules to add, which must be non-negative
     * @param lightColor the color to use for light modules, in 0xRRGGBB format
     * @param darkColor the color to use for dark modules, in 0xRRGGBB format
     * @return a new image representing the QR Code, with padding and scaling
     * @throws NullPointerException if the QR Code is {@code null}
     * @throws IllegalArgumentException if the scale or border is out of range, or if
     * {scale, border, size} cause the image dimensions to exceed Integer.MAX_VALUE
     */
    private static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0)
            throw new IllegalArgumentException("Value out of range");
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale)
            throw new IllegalArgumentException("Scale or border too large");

        BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }
    private static void writePng(BufferedImage img, File file) throws IOException {
        ImageIO.write(img, "png", file);
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
