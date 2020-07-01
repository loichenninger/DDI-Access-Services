package fr.insee.rmes.search.service;

import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;

import fr.insee.rmes.search.model.ColecticaItemSolr;
import fr.insee.rmes.search.model.DDIItem;
import fr.insee.rmes.search.model.DDIQuery;
import fr.insee.rmes.search.model.DataCollectionContext;
import fr.insee.rmes.search.model.ResponseItem;
import fr.insee.rmes.search.model.ResponseSearchItem;

public interface SearchService {

	IndexResponse save(String type, ResponseItem item) throws Exception;

	List<DDIItem> searchByLabel(String label, String... types) throws Exception;

	List<ResponseSearchItem> searchByLabel(String subgroupId, String operationId, String dataCollectionId,
			DDIQuery criteria) throws Exception;

	List<DDIItem> searchByLabelInSubgroup(String label, String subgroupId, String... types) throws Exception;

	DeleteResponse delete(String type, String id) throws Exception;

	List<DDIItem> getSubGroups() throws Exception;

	List<DDIItem> getStudyUnits(String seriesId) throws Exception;

	List<DDIItem> getDataCollections(String operationId) throws Exception;

	DDIItem getDDIItemById(String id) throws Exception;

	DataCollectionContext getDataCollectionContext(String id) throws Exception;

	void deleteAll() throws Exception;

	List<DDIItem> getGroups() throws Exception;
	
	List<ColecticaItemSolr> getItemsByLabel(String label);

}
