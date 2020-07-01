package fr.insee.rmes.metadata.service.fragmentInstance;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import fr.insee.rmes.metadata.model.ColecticaItem;
import fr.insee.rmes.metadata.model.ColecticaItemRefList;
import fr.insee.rmes.metadata.service.MetadataServiceItem;
import fr.insee.rmes.metadata.utils.DocumentBuilderUtils;
import fr.insee.rmes.search.model.DDIItemType;
import fr.insee.rmes.utils.ddi.DDIDocumentBuilder;
import fr.insee.rmes.utils.ddi.Envelope;
import fr.insee.rmes.webservice.rest.RMeSException;

@Service
public class FragmentInstanceServiceImpl implements FragmentInstanceService {

	@Autowired
	private MetadataServiceItem metadataServiceItem;

	@Override
	public String getFragmentInstance(String idTopLevel, DDIItemType itemType) throws Exception {

		// Step 1 : get the topLevelItem
		ColecticaItem item;
		if (itemType == null) {
			item = metadataServiceItem.getItem(idTopLevel);
		} else {
			item = metadataServiceItem.getItemByType(idTopLevel, itemType);
		}

		// Step 2 : create a DDIDocumentBuilder
		DDIDocumentBuilder docBuilder = new DDIDocumentBuilder(true, Envelope.FRAGMENT_INSTANCE);

		// Step 3 : replace attributes for the TopLevelReference
		replaceValueEnvelope(docBuilder, "r:Agency", item.agencyId);
		replaceValueEnvelope(docBuilder, "r:ID", item.identifier);
		replaceValueEnvelope(docBuilder, "r:Version", item.version);
		if (itemType == null) {
			replaceValueEnvelope(docBuilder, "r:TypeOfObject", item.getType().getName());
		} else {
			replaceValueEnvelope(docBuilder, "r:TypeOfObject", itemType.getName());
		}

		// Step 4 : add the root Instance and all of its children
		ColecticaItemRefList refs = metadataServiceItem.getChildrenRef(idTopLevel);

		List<ColecticaItem> items = metadataServiceItem.getItems(refs);
		for (ColecticaItem itemUnit : items) {
			Node itemNode = getNode(itemUnit.item, docBuilder.getDocument());
			docBuilder.appendChild(itemNode);
		}
		return docBuilder.toString();
	}

	private Document getDocument(String fragment) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		if (null == fragment || fragment.isEmpty()) {
			return builder.newDocument();
		}
		InputSource ddiSource = new InputSource(new StringReader(fragment));
		return builder.parse(ddiSource);
	}
	
	@Override
	public String getFragmentInstances(String idTopLevel, DDIItemType[] itemTypes) throws Exception {
		
		DDIDocumentBuilder docBuilder = buildFragmentInstanceEnvelope(idTopLevel, itemTypes);

		// Add the root Instance and all of its children
		ColecticaItemRefList refs = metadataServiceItem.getChildrenRef(idTopLevel);
		List<ColecticaItem> items = metadataServiceItem.getItems(refs);
		for (ColecticaItem itemUnit : items) {
			Node itemNode = DocumentBuilderUtils.getNode(itemUnit.item, docBuilder.getDocument());
			docBuilder.appendChild(itemNode);
		}
		return docBuilder.toString();
	}

	private Node getNode(String fragment, Document doc) throws Exception {
		Element node = getDocument(fragment).getDocumentElement();
		Node newNode = node.cloneNode(true);
		// Transfer ownership of the new node into the destination document
		doc.adoptNode(newNode);
		return newNode;
	}
	
	@Override
	public DDIDocumentBuilder buildFragmentInstanceEnvelope(String idTopLevel, DDIItemType[] itemTypes) throws Exception {
		// Step 1 : get the topLevelItem
		ColecticaItem item = null;
		ColecticaItem itemTemp = null;
		String itemTypeName = "";
		if (itemTypes == null || itemTypes.length == 0) {
			itemTemp = metadataServiceItem.getItem(idTopLevel);
			itemTypeName = itemTemp.getType().getName();
			item = itemTemp;
		} else {
			for (DDIItemType ddiItemType : itemTypes) {
				try {
					if (item == null) {
						item = metadataServiceItem.getItemByType(idTopLevel, ddiItemType);
						itemTypeName = item.getType().getName();
					}
				} catch (RMeSException rmesE) {
					throw rmesE;
				} catch (Exception e) {
					e.getMessage();
				}
			}

		}

		// Step 2 : create a DDIDocumentBuilder
		DDIDocumentBuilder docBuilder = new DDIDocumentBuilder(true, Envelope.FRAGMENT_INSTANCE);

		// Step 3 : replace attributes for the TopLevelReference
		replaceValueEnvelope(docBuilder, "r:Agency", item.agencyId);
		replaceValueEnvelope(docBuilder, "r:ID", item.identifier);
		replaceValueEnvelope(docBuilder, "r:Version", item.version);
		if (itemTypes == null) {
			replaceValueEnvelope(docBuilder, "r:TypeOfObject", item.getType().getName());
		} else {
			replaceValueEnvelope(docBuilder, "r:TypeOfObject", itemTypeName);
		}
		
		return docBuilder;
	}

	/**
	 * Replace a specific value of a node thanks to its tagName
	 * 
	 * @param docBuilder
	 *            : Document to edit
	 * @param targetTagName
	 *            : tagName
	 * @param newValue
	 *            : new value applied to the current node.
	 */
	private void replaceValueEnvelope(DDIDocumentBuilder docBuilder, String targetTagName, String newValue) {
		NodeList nodes = docBuilder.getDocument().getElementsByTagName(targetTagName);
		Node node = nodes.item(0);
		node.setTextContent(newValue);
	}

}
