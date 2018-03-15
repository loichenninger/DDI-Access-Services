package fr.insee.rmes.metadata.service.questionnaire;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import fr.insee.rmes.metadata.model.ColecticaItem;
import fr.insee.rmes.metadata.model.ColecticaItemRef;
import fr.insee.rmes.metadata.model.ColecticaItemRefList;
import fr.insee.rmes.metadata.model.ItemWithParent;
import fr.insee.rmes.metadata.model.ObjectColecticaPost;
import fr.insee.rmes.metadata.model.Relationship;
import fr.insee.rmes.metadata.model.TargetItem;
import fr.insee.rmes.metadata.repository.GroupRepository;
import fr.insee.rmes.metadata.repository.MetadataRepository;
import fr.insee.rmes.metadata.service.MetadataService;
import fr.insee.rmes.metadata.service.MetadataServiceItem;
import fr.insee.rmes.metadata.utils.XpathProcessor;
import fr.insee.rmes.search.model.DDIItemType;
import fr.insee.rmes.search.service.SearchService;
import fr.insee.rmes.utils.ddi.DDIDocumentBuilder;
import fr.insee.rmes.utils.ddi.UtilXML;
import fr.insee.rmes.webservice.rest.RMeSException;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

	private final static Logger logger = LogManager.getLogger(QuestionnaireServiceImpl.class);

	@Autowired
	MetadataRepository metadataRepository;

	@Autowired
	MetadataServiceItem metadataServiceItem;

	@Autowired
	MetadataService metadataService;

	@Autowired
	SearchService searchService;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	XpathProcessor xpathProcessor;

	private String idDDIInstrument;

	private ColecticaItem instrument;

	private ColecticaItem DDIInstance;

	private ColecticaItem subGroupItem;

	private ColecticaItem groupItem;

	private ColecticaItem studyUnitItem;

	private ColecticaItem dataCollection;

	private ColecticaItem instrumentScheme;

	private Node groupNode;

	private Node subGroupNode;

	private Node studyUnitNode;

	private Node DCNode;

	private Node instrumentSchemeNode;

	private Node instrumentNode;

	private Node urnNode;

	private Node agencyNode;

	private Node idNode;

	private Node versionNode;

	private Node userIDNode;

	private Node citationNode;

	@Override
	public String getQuestionnaire(String idDDIInstrument) throws Exception {
		this.idDDIInstrument = idDDIInstrument;

		// Step 1 : Get the DDIInstance, the DDIInstrument and Check type (an
		// Exception throws if not)

		ColecticaItem DDIInstrument = metadataServiceItem.getItemByType(idDDIInstrument, DDIItemType.QUESTIONNAIRE);
		this.instrument = DDIInstrument;
		ObjectColecticaPost objectColecticaPost = new ObjectColecticaPost();
		List<String> itemTypes = new ArrayList<String>();
		itemTypes.add(DDIItemType.INSTRUMENT_SCHEME.getUUID());
		objectColecticaPost.setItemTypes(itemTypes);
		TargetItem targetItem = new TargetItem();
		targetItem.setAgencyId(DDIInstrument.agencyId);
		targetItem.setIdentifier(DDIInstrument.identifier);
		targetItem.setVersion(Integer.valueOf(DDIInstrument.version));
		objectColecticaPost.setTargetItem(targetItem);
		objectColecticaPost.setUseDistinctResultItem(true);
		objectColecticaPost.setUseDistinctTargetItem(true);
		Relationship[] relationshipsInstrument = metadataService.getRelationship(objectColecticaPost);
		String DDIidentifier = relationshipsInstrument[0].getIdentifierTriple().getIdentifier();
		instrumentScheme = metadataServiceItem.getItem(DDIidentifier);
		dataCollection = searchInstrumentParent(itemTypes, DDIItemType.DATA_COLLECTION, objectColecticaPost,
				instrumentScheme);
		studyUnitItem = searchInstrumentParent(itemTypes, DDIItemType.STUDY_UNIT, objectColecticaPost, dataCollection);
		subGroupItem = searchInstrumentParent(itemTypes, DDIItemType.SUB_GROUP, objectColecticaPost, studyUnitItem);
		groupItem = searchInstrumentParent(itemTypes, DDIItemType.GROUP, objectColecticaPost, subGroupItem);
		DDIInstance = searchInstrumentParent(itemTypes, DDIItemType.DDI_INSTANCE, objectColecticaPost, groupItem);
		return buildQuestionnaire();

	}

	private ColecticaItem searchInstrumentParent(List<String> itemTypes, DDIItemType ddiItemType,
			ObjectColecticaPost objectColecticaPost, ColecticaItem itemChild) throws Exception {
		itemTypes.clear();
		itemTypes.add(ddiItemType.getUUID());
		objectColecticaPost.setItemTypes(itemTypes);
		TargetItem targetItem = new TargetItem();
		targetItem.setAgencyId(itemChild.agencyId);
		targetItem.setIdentifier(itemChild.identifier);
		targetItem.setVersion(Integer.valueOf(itemChild.version));
		objectColecticaPost.setTargetItem(targetItem);
		objectColecticaPost.setUseDistinctResultItem(true);
		objectColecticaPost.setUseDistinctTargetItem(true);
		Relationship[] relationshipsInstrument = metadataService.getRelationship(objectColecticaPost);
		String DDIidentifier = relationshipsInstrument[0].getIdentifierTriple().getIdentifier();
		ColecticaItem item = metadataServiceItem.getItem(DDIidentifier);
		return item;
	}

	/**
	 * This method build the DDI Questiuonnaire
	 * 
	 * @return DDIQuestionnaire.toString()
	 * @throws Exception
	 */
	private String buildQuestionnaire() throws Exception {
		// Step 1 : get all the children of the instrument (include the
		// instrument by default)
		ColecticaItemRefList listChildrenWithoutInstrument = metadataServiceItem
				.getChildrenRef(instrument.getIdentifier());
		ColecticaItemRef instrumentTemp = null;
		// Step 2 : Among all of the itemsReferences, the instrument will be get
		// and removed from this list
		for (ColecticaItemRef childInstrument : listChildrenWithoutInstrument.identifiers) {
			if (childInstrument.identifier.equals(idDDIInstrument)) {
				instrumentTemp = childInstrument;
			}
		}
		if (instrumentTemp != null) {
			listChildrenWithoutInstrument.identifiers.remove(instrumentTemp);
		}
		// Step 3 : Build the group, from the
		// studyUnit to the group
		DDIDocumentBuilder docBuilder = new DDIDocumentBuilder();
		convertAsNodesWithXPath(docBuilder);
		convertAsDDINodesInformation(docBuilder);
		appendChildsByParent(docBuilder);
		// Step 4 : return the filled out enveloppe
		// as result
		processItemsRessourcePackage(docBuilder, listChildrenWithoutInstrument);
		return docBuilder.toString();

	}

	private void appendChildsByParent(DDIDocumentBuilder docBuilder) {
		docBuilder.appendChild(urnNode);
		docBuilder.appendChild(agencyNode);
		docBuilder.appendChild(idNode);
		docBuilder.appendChild(versionNode);
		docBuilder.appendChild(userIDNode);
		docBuilder.appendChild(citationNode);
		removeReferences(groupNode);
		docBuilder.appendChild(groupNode);
		removeReferences(subGroupNode);
		docBuilder.appendChildByParent("Group", subGroupNode);
		removeReferences(studyUnitNode);
		docBuilder.appendChildByParent("SubGroup", studyUnitNode);
		// Step 1 : Insert the content of the
		// DataCollection got to the enveloppe as
		// a child of the StudyUnit.
		removeReferences(DCNode);
		docBuilder.appendChildByParent("StudyUnit", DCNode);
		removeReferences(instrumentSchemeNode);
		docBuilder.appendChildByParent("DataCollection", instrumentSchemeNode);
		docBuilder.appendChildByParent("InstrumentScheme", instrumentNode);

	}

	private void convertAsDDINodesInformation(DDIDocumentBuilder docBuilder) throws Exception {
		// Step
		// 1 :
		// Get
		// DDI
		// Instance
		// informations
		// on
		// root : r:URN, r:Agency, r:ID, r:Version,
		// r:UserID, r:Citation
		String urnString = xpathProcessor.queryString(DDIInstance.getItem(), "/Fragment[1]/DDIInstance[1]/URN[1]");
		this.urnNode = getNode(urnString.trim(), docBuilder.getDocument());
		String agencyString = xpathProcessor.queryString(DDIInstance.getItem(),
				"/Fragment[1]/DDIInstance[1]/Agency[1]");
		this.agencyNode = getNode(agencyString, docBuilder.getDocument());
		String idString = xpathProcessor.queryString(DDIInstance.getItem(), "/Fragment[1]/DDIInstance[1]/ID[1]");
		this.idNode = getNode(idString, docBuilder.getDocument());
		String versionString = xpathProcessor.queryString(DDIInstance.getItem(),
				"/Fragment[1]/DDIInstance[1]/Version[1]");
		this.versionNode = getNode(versionString, docBuilder.getDocument());
		String userIDString = xpathProcessor.queryString(DDIInstance.getItem(),
				"/Fragment[1]/DDIInstance[1]/UserID[1]");
		this.userIDNode = getNode(userIDString, docBuilder.getDocument());
		String citationString = xpathProcessor.queryString(DDIInstance.getItem(),
				"/Fragment[1]/DDIInstance[1]/Citation[1]");
		this.citationNode = getNode(citationString, docBuilder.getDocument());

	}

	private void convertAsNodesWithXPath(DDIDocumentBuilder docBuilder) throws Exception {
		this.subGroupNode = getNode(
				UtilXML.nodeToString(xpathProcessor.queryList(subGroupItem.getItem(), "/Fragment[1]/*").item(0)),
				docBuilder.getDocument());

		subGroupNode = getNode(UtilXML.nodeToString(subGroupNode), docBuilder.getDocument());
		this.groupNode = getNode(
				UtilXML.nodeToString(xpathProcessor.queryList(groupItem.getItem(), "/Fragment[1]/*").item(0)),
				docBuilder.getDocument());

		this.studyUnitNode = getNode(
				UtilXML.nodeToString(xpathProcessor.queryList(studyUnitItem.getItem(), "/Fragment[1]/*").item(0)),
				docBuilder.getDocument());

		this.DCNode = getNode(
				UtilXML.nodeToString(xpathProcessor.queryList(dataCollection.getItem(), "/Fragment[1]/*").item(0)),
				docBuilder.getDocument());

		this.instrumentSchemeNode = getNode(
				UtilXML.nodeToString(xpathProcessor.queryList(instrumentScheme.item, "/Fragment[1]/*").item(0)),
				docBuilder.getDocument());

		this.instrumentNode = getNode(
				UtilXML.nodeToString(xpathProcessor.queryList(instrument.item, "/Fragment[1]/*").item(0)),
				docBuilder.getDocument());

	}

	private void processItemsRessourcePackage(DDIDocumentBuilder docBuilder,
			ColecticaItemRefList listItemsChildrenInstrument) throws Exception {

		List<ItemWithParent> parentsWithCildren = new ArrayList<ItemWithParent>();
		List<ColecticaItem> items = metadataServiceItem.getItems(listItemsChildrenInstrument);
		// Step 1 : Insert the other references of
		// the studyUnit to the
		// enveloppe as children of
		// the first RessourcePackage
		processingSchemes(items, docBuilder, parentsWithCildren);

		// Step 2 : get the Ressource Packages (parents of the schemes) with
		// each scheme in the right package.
		List<ItemWithParent> rpItemsNodeString = getRessourcePackagesWithSchemes(docBuilder, parentsWithCildren);

		// Step 3 : Insert the Ressource Packages in the Root Document.
		for (ItemWithParent rpItemNodeString : rpItemsNodeString) {
			removeReferences(rpItemNodeString.getRessourcePackageNode());
			docBuilder.appendChild(rpItemNodeString.getRessourcePackageNode());
		}

	}

	private List<ItemWithParent> getRessourcePackagesWithSchemes(DDIDocumentBuilder docBuilder,
			List<ItemWithParent> parentsWithCildren) throws Exception {
		List<String> identifiersRP = new ArrayList<String>();
		List<ItemWithParent> rpItemsNodeString = new ArrayList<ItemWithParent>();
		for (ItemWithParent itemParentWithChildren : parentsWithCildren) {
			ObjectColecticaPost objectColecticaPost = new ObjectColecticaPost();
			List<String> itemTypes = new ArrayList<String>();
			itemTypes.add(DDIItemType.RESSOURCEPACKAGE.getUUID());
			objectColecticaPost.setItemTypes(itemTypes);
			TargetItem targetItem = new TargetItem();
			targetItem.setAgencyId(itemParentWithChildren.getParent().agencyId);
			targetItem.setIdentifier(itemParentWithChildren.getParent().identifier);
			targetItem.setVersion(Integer.valueOf(itemParentWithChildren.getParent().version));
			objectColecticaPost.setTargetItem(targetItem);
			objectColecticaPost.setUseDistinctResultItem(true);
			objectColecticaPost.setUseDistinctTargetItem(true);
			Relationship[] relationshipsRP = metadataService.getRelationship(objectColecticaPost);
			String identifierRP = relationshipsRP[0].getIdentifierTriple().getIdentifier();

			if (identifiersRP.contains(identifierRP)) {
				for (ItemWithParent rpItemNodeString : rpItemsNodeString) {
					if (rpItemNodeString.getItem().getIdentifier().equals(identifierRP)) {
						removeReferences(itemParentWithChildren.getParentNode());
						rpItemNodeString.getRessourcePackageNode().appendChild(itemParentWithChildren.getParentNode());
					}
				}
			} else {
				ColecticaItem rpItem = metadataServiceItem.getItem(identifierRP);
				ItemWithParent rpItemNodeString = new ItemWithParent();
				rpItemNodeString.setItem(rpItem);
				rpItemNodeString.setRessourcePackageNode(getNode(
						UtilXML.nodeToString(xpathProcessor.queryList(rpItem.getItem(), "/Fragment[1]/*").item(0)),
						docBuilder.getDocument()));
				removeReferences(itemParentWithChildren.getParentNode());
				rpItemNodeString.getRessourcePackageNode().appendChild(itemParentWithChildren.getParentNode());
				rpItemNodeString.setRessourcePackage(rpItem);
				rpItemsNodeString.add(rpItemNodeString);
				identifiersRP.add(identifierRP);
			}
		}
		return rpItemsNodeString;
	}

	private void processingSchemes(List<ColecticaItem> items, DDIDocumentBuilder docBuilder,
			List<ItemWithParent> parentsWithCildren) throws Exception {
		List<String> identifierParentsWithCildren = new ArrayList<String>();
		List<Node> itemSchemeNodes = new ArrayList<Node>();
		List<ColecticaItem> itemSchemes = new ArrayList<ColecticaItem>();
		for (ColecticaItem item : items) {
			ObjectColecticaPost objectColecticaPost = new ObjectColecticaPost();
			Node node = getNode(
					UtilXML.nodeToString(xpathProcessor.queryList(item.getItem(), "/Fragment[1]/*[1]").item(0)),
					docBuilder.getDocument());
			removeReferences(node);
			List<String> itemTypes = new ArrayList<String>();
			for (DDIItemType type : DDIItemType.values()) {
				if (type.name().endsWith("SCHEME") && type.name().contains(node.getNodeName().toUpperCase() + "_")) {
					itemTypes.add(type.getUUID());
				}
			}

			TargetItem targetItem = new TargetItem();
			targetItem.setAgencyId(item.agencyId);
			targetItem.setVersion(Integer.valueOf(item.version));
			targetItem.setIdentifier(item.identifier);
			objectColecticaPost.setItemTypes(itemTypes);

			objectColecticaPost.setTargetItem(targetItem);
			objectColecticaPost.setUseDistinctResultItem(true);
			objectColecticaPost.setUseDistinctTargetItem(true);
			if (itemTypes.size() > 0) {
				Relationship[] relationshipsSchemes = metadataService.getRelationship(objectColecticaPost);

				ItemWithParent itemWithParent = new ItemWithParent();
				itemWithParent.setItem(item);
				itemWithParent.setItemNode(getNode(
						UtilXML.nodeToString(xpathProcessor.queryList(item.getItem(), "/Fragment[1]/*").item(0)),
						docBuilder.getDocument()));
				itemWithParent.setParent(
						metadataServiceItem.getItem(relationshipsSchemes[0].getIdentifierTriple().getIdentifier()));
				itemWithParent.setParentNode(getNode(
						UtilXML.nodeToString(xpathProcessor
								.queryList(itemWithParent.getParent().getItem(), "/Fragment[1]/*").item(0)),
						docBuilder.getDocument()));
				// First adding of a parentNode
				if (!identifierParentsWithCildren.contains(itemWithParent.getParent().getIdentifier())) {
					removeReferences(itemWithParent.getParentNode());
					parentsWithCildren.add(itemWithParent);
					identifierParentsWithCildren.add(itemWithParent.getParent().getIdentifier());
					itemSchemes.add(itemWithParent.getParent());
					itemSchemeNodes.add(itemWithParent.getParentNode());
				} else {
					// Update of the parent node with a new child Node
					for (ItemWithParent itemParentWithChildren : parentsWithCildren) {
						if (itemParentWithChildren.getParent().getIdentifier()
								.equals(itemWithParent.getParent().getIdentifier())) {
							removeReferences(itemWithParent.getItemNode());
							itemParentWithChildren.getParentNode().appendChild(itemWithParent.getItemNode());
						}
					}
				}
			}

		}
	}

	/**
	 * Remove unused references of the DDIDocument builder
	 * 
	 * @param node
	 *            (root node for searching references)
	 */
	private void removeReferences(Node node) {
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node nodeRef = children.item(i);
			if (nodeRef.getNodeType() == Node.ELEMENT_NODE) {
				if (nodeRef.getNodeName().contains("Reference")) {
					Node parentNode = nodeRef.getParentNode();
					parentNode.removeChild(nodeRef);
				}
			}
		}
	}

	private Node getNode(String fragment, Document doc) throws Exception {
		Element node = getDocument(fragment).getDocumentElement();
		Node newNode = node.cloneNode(true);
		// Transfer ownership of the new node into the destination document
		doc.adoptNode(newNode);
		return newNode;
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

}
