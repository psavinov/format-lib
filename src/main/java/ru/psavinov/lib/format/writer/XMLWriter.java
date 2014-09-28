package ru.psavinov.lib.format.writer;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.psavinov.lib.format.IObject;
import ru.psavinov.lib.format.IWriter;
import ru.psavinov.lib.format.util.PropertyUtil;

/**
 * Writer implementation for XML format
 * 
 * @author Pavel Savinov
 *
 */
public class XMLWriter implements IWriter {
	
	private static final String ROOT = "Document";
	private Document document;
	private OutputStream stream;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public OutputStream getStream() {
		return stream;
	}

	public void setStream(OutputStream stream) {
		this.stream = stream;
	}

	public XMLWriter(OutputStream stream) throws ParserConfigurationException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		this.document = doc;
		this.stream = stream;
	}
	
	/**
	 * @see ru.psavinov.lib.format.IWriter.write
	 */
	@SuppressWarnings("rawtypes")
	public void write(List<IObject> objects, Class clazz) throws Exception {
		Document doc = getDocument();
		Element root = doc.createElement(ROOT);
		doc.appendChild(root);
		for (IObject obj : objects) {
			if (clazz.isInstance(obj)) {
				Element e = doc.createElement(clazz.getSimpleName());
				root.appendChild(e);
				for (Field field : clazz.getDeclaredFields()) {
					String fName = new String(field.getName().charAt(0)+"")
						.toUpperCase().concat(field.getName().substring(1));
					Element p = doc.createElement(fName);
					p.setTextContent(PropertyUtil.convertFrom(
							obj.getProperty(field.getName()),
							field.getType()));
					e.appendChild(p);
				}
				
			}
		}
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(getStream());
		transformer.transform(source, result);
		getStream().close();
	}

}
