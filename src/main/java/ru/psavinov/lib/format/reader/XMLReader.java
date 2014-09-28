package ru.psavinov.lib.format.reader;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.psavinov.lib.format.IObject;
import ru.psavinov.lib.format.IReader;
import ru.psavinov.lib.format.exception.InvalidPropertyException;
import ru.psavinov.lib.format.exception.UnsupportedEntityException;
import ru.psavinov.lib.format.exception.UnsupportedTypeException;
import ru.psavinov.lib.format.util.PropertyUtil;

/**
 * Reader implementation for XML format
 * 
 * @author Pavel Savinov
 *
 */
public class XMLReader implements IReader {

	private Document document;

	public XMLReader(InputStream stream) throws SAXException, IOException,
			ParserConfigurationException {
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = f.newDocumentBuilder();
		Document doc = builder.parse(stream);
		stream.close();
		this.document = doc;
	}

	/**
	 * @see ru.psavinov.lib.format.IReader.read
	 */
	@SuppressWarnings("rawtypes")
	public List<IObject> read(Class clazz) throws DOMException,
			InstantiationException, IllegalAccessException,
			InvalidPropertyException, UnsupportedEntityException,
			ParseException, UnsupportedTypeException {
		List<IObject> list = new ArrayList<IObject>();
		Node node = getDocument().getFirstChild();
		list.addAll(processNodes(node.getChildNodes(), clazz));
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List<IObject> processNodes(NodeList childNodes, Class clazz)
			throws DOMException, InstantiationException,
			IllegalAccessException, InvalidPropertyException,
			UnsupportedEntityException, ParseException,
			UnsupportedTypeException {
		List<IObject> list = new ArrayList<IObject>();
		for (int k = 0; k < childNodes.getLength(); k++) {
			Node node = childNodes.item(k);
			if (node.getNodeType() == Node.ELEMENT_NODE
					&& node.getNodeName().equalsIgnoreCase(
							clazz.getSimpleName())) {
				list.add(processNode(node, clazz));
			} else if (node.getChildNodes().getLength() > 0) {
				list.addAll(processNodes(node.getChildNodes(), clazz));
			}
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	private IObject processNode(Node node, Class clazz)
			throws InstantiationException, IllegalAccessException,
			DOMException, InvalidPropertyException, UnsupportedEntityException,
			ParseException, UnsupportedTypeException {
		IObject object = (IObject) clazz.newInstance();
		for (int k = 0; k < node.getChildNodes().getLength(); k++) {
			Node child = node.getChildNodes().item(k);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				object.setProperty(
						PropertyUtil.getFieldName(child.getNodeName()),
						PropertyUtil.convertTo(child.getNodeName(),
								child.getTextContent(), clazz));
			}
		}
		return object;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
