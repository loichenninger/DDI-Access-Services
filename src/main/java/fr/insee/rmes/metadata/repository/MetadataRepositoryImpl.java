package fr.insee.rmes.metadata.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.insee.rmes.metadata.client.MetadataClient;
import fr.insee.rmes.metadata.model.ColecticaItem;
import fr.insee.rmes.metadata.model.ColecticaItemPostRef;
import fr.insee.rmes.metadata.model.ColecticaItemPostRefList;
import fr.insee.rmes.metadata.model.ColecticaItemRefList;
import fr.insee.rmes.metadata.model.ColecticaSearchBody;
import fr.insee.rmes.metadata.model.ColecticaSearchResponse;
import fr.insee.rmes.metadata.model.ColecticaSearchSetBody;
import fr.insee.rmes.metadata.model.Relationship;
import fr.insee.rmes.metadata.model.ObjectColecticaPost;
import fr.insee.rmes.metadata.model.Unit;
import fr.insee.rmes.utils.ddi.ItemFormat;

@Service
public class MetadataRepositoryImpl implements MetadataRepository {

	private final static Logger logger = LogManager.getLogger(MetadataRepositoryImpl.class);

	@Autowired
	MetadataClient metadataClient;

	@Value("${fr.insee.rmes.api.remote.metadata.agency}")
	public String agencyId;

	@Value("${fr.insee.rmes.api.remote.metadata.user}")
	private String versionResponsability;

	@Override
	public ColecticaItem findById(String id) throws Exception {
		return metadataClient.getItem(id);
	}

	@Override
	public ColecticaItemRefList getChildrenRef(String id) throws Exception {
		return metadataClient.getChildrenRef(id);
	}

	@Override
	public List<ColecticaItem> getItems(ColecticaItemRefList refs) throws Exception {
		return metadataClient.getItems(refs);
	}

	@Override
	public List<Unit> getUnits() throws Exception {
		return metadataClient.getUnits();
	}

	@Override
	public Map<ColecticaItemPostRef, String> postNewItems(ColecticaItemPostRefList refs) throws Exception {
		Map<ColecticaItemPostRef, String> results = new HashMap<ColecticaItemPostRef, String>();

		// Valuing new UUID
		for (ColecticaItemPostRef colecticaItemPostRef : refs.getItems()) {
			colecticaItemPostRef.identifier = UUID.randomUUID().toString();

			colecticaItemPostRef.version = "0";
			colecticaItemPostRef.agencyId = agencyId;
			colecticaItemPostRef.setItemType("7E47C269-BCAB-40F7-A778-AF7BBC4E3D00");
			colecticaItemPostRef.setVersionResponsibility(versionResponsability);
			colecticaItemPostRef.setItemFormat(ItemFormat.DDI_32);

		}
		logger.debug(refs.toString());
		String res = metadataClient.postItems(refs);

		for (ColecticaItemPostRef colecticaItemPostRef : refs.getItems()) {
			results.put(colecticaItemPostRef, res);
		}
		return results;
	}

	@Override
	public Map<ColecticaItemPostRef, String> postNewItem(ColecticaItemPostRef ref) throws Exception {
		Map<ColecticaItemPostRef, String> result = new HashMap<ColecticaItemPostRef, String>();
		ref.identifier = UUID.randomUUID().toString();
		metadataClient.postItem(ref);
		result.put(ref, metadataClient.postItem(ref));
		return result;
	}

	@Override
	public Map<ColecticaItemPostRef, String> postUpdateItems(ColecticaItemPostRefList refs) throws Exception {
		Map<ColecticaItemPostRef, String> results = new HashMap<ColecticaItemPostRef, String>();
		int version = 0;
		String result;
		for (ColecticaItemPostRef colecticaItemPostRef : refs.getItems()) {
			version = Integer.valueOf(colecticaItemPostRef.version);
			version++;
			colecticaItemPostRef.version = String.valueOf(version);
			result = metadataClient.postItems(refs);
			results.put(colecticaItemPostRef, result);

		}
		return results;

	}

	@Override
	public Integer getLastestVersionItem(String id) throws Exception {
		return metadataClient.getLastestVersionItem(id);

	}

	@Override
	public Relationship[] getRelationship(ObjectColecticaPost relationshipPost) throws Exception {
		return metadataClient.getRelationship(relationshipPost);
	}

	@Override
	public Relationship[] getRelationshipChildren(ObjectColecticaPost relationshipPost) {
		return metadataClient.getRelationshipChildren(relationshipPost);
	}

	@Override
	public ColecticaSearchResponse searchItems(ColecticaSearchBody body) {
		return metadataClient.searchItems(body);
	}

	@Override
	public Relationship[] searchSets(ColecticaSearchSetBody setBody) {
		return metadataClient.searchSets(setBody);
	}
}
