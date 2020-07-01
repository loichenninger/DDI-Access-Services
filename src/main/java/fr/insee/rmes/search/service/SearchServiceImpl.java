package fr.insee.rmes.search.service;

import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.insee.rmes.search.model.ColecticaItemSolr;
import fr.insee.rmes.search.model.DDIItem;
import fr.insee.rmes.search.model.DDIQuery;
import fr.insee.rmes.search.model.DataCollectionContext;
import fr.insee.rmes.search.model.ResponseItem;
import fr.insee.rmes.search.model.ResponseSearchItem;
import fr.insee.rmes.search.repository.DDIItemRepository;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private DDIItemRepository ddiItemRepository;

	@Override
	public IndexResponse save(String type, ResponseItem item) throws Exception {
		return ddiItemRepository.save(type, item);
	}

	@Override
	public List<DDIItem> searchByLabel(String label, String... types) throws Exception {
		return ddiItemRepository.findByLabel(label, types);
	}

	@Override
	public List<DDIItem> searchByLabelInSubgroup(String label, String subgroupId, String... types) throws Exception {
		return ddiItemRepository.findByLabelInSubGroup(label, subgroupId, types);
	}

	@Override
	public DeleteResponse delete(String type, String id) throws Exception {
		return ddiItemRepository.delete(type, id);
	}

	@Override
	public List<DDIItem> getSubGroups() throws Exception {
		return ddiItemRepository.getSubGroups();
	}

	@Override
	public List<DDIItem> getStudyUnits(String subGroupId) throws Exception {
		return ddiItemRepository.getStudyUnits(subGroupId);
	}

	@Override
	public List<DDIItem> getDataCollections(String operationId) throws Exception {
		return ddiItemRepository.getDataCollections(operationId);
	}

	@Override
	public DataCollectionContext getDataCollectionContext(String dataCollectionId) throws Exception {
		return ddiItemRepository.getDataCollectionContext(dataCollectionId);
	}

	@Override
	public List<ResponseSearchItem> searchByLabel(String subgroupId, String operationId, String dataCollectionId,
			DDIQuery criteria) throws Exception {
		return ddiItemRepository.getItemsByCriteria(subgroupId, operationId, dataCollectionId,
				criteria);
	}

	@Override
	public void deleteAll() throws Exception {
		ddiItemRepository.deleteAll();
			
	}

	@Override
	public DDIItem getDDIItemById(String id) throws Exception {
		return ddiItemRepository.getItemById(id);
	}

	@Override
	public List<DDIItem> getGroups() throws Exception {
		return ddiItemRepository.getGroups();
	}

	@Override
	public List<ColecticaItemSolr> getItemsByLabel(String label) {
		return ddiItemRepository.getItemsByLabel(label);
	}

}
