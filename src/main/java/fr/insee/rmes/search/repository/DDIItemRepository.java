package fr.insee.rmes.search.repository;

import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;

import fr.insee.rmes.search.model.ColecticaItemSolr;
import fr.insee.rmes.search.model.DDIItem;
import fr.insee.rmes.search.model.DDIQuery;
import fr.insee.rmes.search.model.DataCollectionContext;
import fr.insee.rmes.search.model.ResponseItem;
import fr.insee.rmes.search.model.ResponseSearchItem;

public interface DDIItemRepository {

	IndexResponse save(String type, ResponseItem item) throws Exception;

	List<DDIItem> findByLabel(String label, String... types) throws Exception;

	List<DDIItem> findByLabelInSubGroup(String label, String subgroupId, String... types) throws Exception;

	DeleteResponse delete(String type, String id) throws Exception;

	List<DDIItem> getSubGroups() throws Exception;

	List<DDIItem> getStudyUnits(String seriesId) throws Exception;

	List<DDIItem> getDataCollections(String operationId) throws Exception;

	DataCollectionContext getDataCollectionContext(String dataCollectionId) throws Exception;

	List<ResponseSearchItem> getItemsByCriteria(String subgroupId, String operationId, String dataCollectionId,
			DDIQuery criteria) throws Exception;

	void deleteAll() throws Exception;

	DDIItem getItemById(String id) throws Exception;

	List<DDIItem> getGroups() throws Exception;
	
	List<ColecticaItemSolr> getItemsByLabel(String label);

}
