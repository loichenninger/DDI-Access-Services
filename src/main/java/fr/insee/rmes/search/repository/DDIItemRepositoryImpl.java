package fr.insee.rmes.search.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.DisMaxParams;
import org.apache.solr.common.params.HighlightParams;
import org.apache.solr.common.params.MapSolrParams;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Repository;

import fr.insee.rmes.config.DDIItemRepositoryImplCondition;
import fr.insee.rmes.search.model.ColecticaItemSolr;
import fr.insee.rmes.search.model.DDIItem;
import fr.insee.rmes.search.model.DDIItemType;
import fr.insee.rmes.search.model.DDIQuery;
import fr.insee.rmes.search.model.DataCollectionContext;
import fr.insee.rmes.search.model.ResponseItem;
import fr.insee.rmes.search.model.ResponseSearchItem;

@Repository
@Conditional(value = DDIItemRepositoryImplCondition.class)
public class DDIItemRepositoryImpl implements DDIItemRepository {

	@Value("${fr.insee.rmes.solr.host}")
	private String solrHost;

	private static final String TYPE_SEARCH = "type:%s";

	@Override
	public IndexResponse save(String type, ResponseItem item) throws Exception {
//		ObjectMapper mapper = new ObjectMapper();
//		byte[] data = mapper.writeValueAsBytes(item);
//		IndexRequest request = new IndexRequest(index, type, item.getId()).source(data, XContentType.JSON);
//		return client.index(request);
		return null;
	}

	@Override
	public List<DDIItem> findByLabel(String label, String... types) throws Exception {
//		SearchSourceBuilder srcBuilder = new SearchSourceBuilder()
//				.query(QueryBuilders.fuzzyQuery("label", label).maxExpansions(1).prefixLength(3));
//		SearchRequest request = new SearchRequest().indices(index).types(types).source(srcBuilder);
//		return mapResponse(client.search(request));
		return Collections.emptyList();
	}

	@Override
	public List<DDIItem> findByLabelInSubGroup(String label, String subgroupId, String... types) throws Exception {
//		SearchSourceBuilder srcBuilder = new SearchSourceBuilder()
//				.query(QueryBuilders.boolQuery()
//						.filter(QueryBuilders.fuzzyQuery("label", label).maxExpansions(1)
//								.prefixLength(label.length() - 2))
//						.filter(QueryBuilders.termQuery("subGroupId.keyword", subgroupId)));
//		SearchRequest request = new SearchRequest().indices(index).types(types).source(srcBuilder);
//		return mapResponse(client.search(request));
		return Collections.emptyList();
	}
	
	public QueryResponse querySolr(String queryString) {
		SolrClient solrClient = new HttpSolrClient.Builder(String.format("https://%s/solr", solrHost)).build();
		final Map<String, String> queryParamMap = new HashMap<>();
		queryParamMap.put("q", queryString);
		MapSolrParams queryParams = new MapSolrParams(queryParamMap);

		QueryResponse response = null;
		try {
			response = solrClient.query("testcore", queryParams);
		} catch (SolrServerException | IOException e1) {
			e1.printStackTrace();
		}
		return response;
	}

	@Override
	public List<DDIItem> getSubGroups() throws Exception {
		String queryString = String.format(TYPE_SEARCH, DDIItemType.SUB_GROUP.getUUID().toLowerCase());

		QueryResponse response = querySolr(queryString);
		SolrDocumentList results = response.getResults();

		return mapResponse(results);
	}

	@Override
	public List<DDIItem> getStudyUnits(String subGroupId) throws Exception {
		String queryString = "";
		if (subGroupId == null) {
			queryString = String.format(TYPE_SEARCH, DDIItemType.STUDY_UNIT.getUUID().toLowerCase());
		} else {
			queryString = String.format("type:%s AND subGroup.id:%s", DDIItemType.STUDY_UNIT.getUUID().toLowerCase(),
					subGroupId);
		}

		QueryResponse response = querySolr(queryString);
		SolrDocumentList results = response.getResults();

		return mapResponse(results);
	}

	@Override
	public List<DDIItem> getDataCollections(String studyUnitId) throws Exception {
		String queryString = String.format("type:%s AND studyUnit.id:%s",
				DDIItemType.DATA_COLLECTION.getUUID().toLowerCase(),
				studyUnitId);

		QueryResponse response = querySolr(queryString);
		SolrDocumentList results = response.getResults();

		return mapResponse(results);
	}

	@Override
	public DeleteResponse delete(String type, String id) throws Exception {
//		DeleteRequest request = new DeleteRequest(index, type, id);
//		return client.delete(request);
		return null;
	}

	private List<DDIItem> mapResponse(SolrDocumentList results) {
		return results.stream().map(result ->{
			String parentType;
			String itemTypeName = DDIItemType.searchByUUID(getString(result, "type").toUpperCase()).getName();
			switch(itemTypeName) {
				case "Group":
					parentType="";
					break;
				case "SubGroup":
					parentType="group.id";
					break;
				case "StudyUnit":
					parentType="subGroup.id";
					break;
				case "DataCollection":
					parentType="studyUnit.id";
					break;
				default:
					parentType="";
			}			
			DDIItem item = new DDIItem(getString(result, "id"), getString(result, String.format("labels.fr-FR"))
					, getString(result, parentType), itemTypeName);
			item.setGroupId(getString(result, "group.id"));
			item.setSubGroupId(getString(result, "subGroup.id"));
			item.setStudyUnitId(getString(result, "studyUnit.id"));
			item.setDataCollectionId(getString(result, "dataCollection.id"));
			return item;
		}).collect(Collectors.toList());
	}

	@Override
	public DataCollectionContext getDataCollectionContext(String dataCollectionId) throws Exception {
		String queryString = String.format("id:%s", dataCollectionId);

		QueryResponse response = querySolr(queryString);
		SolrDocumentList results = response.getResults();
		DataCollectionContext context = new DataCollectionContext();
		
		context.setDataCollectionId(getString(results.get(0),"id"));
		context.setSerieId(getString(results.get(0), "subGroup.id"));
		context.setOperationId(getString(results.get(0), "studyUnit.id"));
		
		return context;
	}

	@Override
	public List<ResponseSearchItem> getItemsByCriteria(String subgroupId, String operationId, String dataCollectionId,
			DDIQuery criteria) throws Exception {

		SolrClient solrClient = new HttpSolrClient.Builder(String.format("https://%s/solr", solrHost)).build();

		SolrQuery query = new SolrQuery();
		query.set(CommonParams.Q, "labels.fr-FR:" + criteria.getFilter());
		List<String> typeFilterQueries = new ArrayList<>();
		for (String type : criteria.getTypes()) {
			typeFilterQueries.add(String.format(TYPE_SEARCH, type));
		}
		query.addFilterQuery(String.join(" OR ", typeFilterQueries));
		List<String> parentFilterQueries = new ArrayList<>();
		if (subgroupId != null) {
			parentFilterQueries.add(String.format("subGroup.id:%s", subgroupId));
		}
		if (operationId != null) {
			parentFilterQueries.add(String.format("studyUnit.id:%s", operationId));
		}
		if (dataCollectionId != null) {
			parentFilterQueries.add(String.format("dataCollection.id:%s", dataCollectionId));
		}
		if (!parentFilterQueries.isEmpty()) {
			query.addFilterQuery(String.join(" OR ", parentFilterQueries));
		}

		QueryResponse response = null;
		try {
			response = solrClient.query("colectica", query);
		} catch (SolrServerException | IOException e1) {
			e1.printStackTrace();
		}
		SolrDocumentList results = response.getResults();

		List<ResponseSearchItem> itemsResult = new ArrayList<>();
		for (SolrDocument result : results) {
			String type = getString(result, "type");
			String id = getString(result,"id");
			String labelResult = getString(result, "labels.fr-FR");
			List<Long> versionsLong = (List<Long>) result.getFieldValue("version");
			List<String> versions = new ArrayList<>(versionsLong.size());
			String resultSubGroupId = getString(result, "subGroup.id");
			String resultOperationId = getString(result, "studyUnit.id");
			String resultDataCollectionId = getString(result, "dataCollection.id");
			for (Long myInt : versionsLong) {
				versions.add(String.valueOf(myInt));
			}
			if (id != null && labelResult != null && type != null) {
				ResponseSearchItem item = new ResponseSearchItem();
				item.setId(id);
				item.setName(labelResult);
				item.setTitle(labelResult);
				item.setVersion(versions.get(0));
				item.setType(DDIItemType.searchByUUID(type.toUpperCase()).getName());
				item.setSubgroupId(resultSubGroupId);
				item.setStudyUnitId(resultOperationId);
				item.setDataCollectionId(resultDataCollectionId);
				item.setSubgroupLabel(getLabelById(resultSubGroupId));
				item.setStudyUnitLabel(getLabelById(resultOperationId));
				item.setDataCollectionLabel(getLabelById(resultDataCollectionId));
				itemsResult.add(item);
			}
		}
		return itemsResult;
	}
	
	private String getLabelById(String itemId) throws Exception{
		String queryString = "";
		queryString = String.format("id:%s", itemId);
		QueryResponse response = querySolr(queryString);
		SolrDocumentList results = response.getResults();
		return getString(results.get(0), "labels.fr-FR");
	}

	@Override
	public void deleteAll() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public DDIItem getItemById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DDIItem> getGroups() throws Exception {
		String queryString = String.format(TYPE_SEARCH, DDIItemType.GROUP.getUUID().toLowerCase());

		QueryResponse response = querySolr(queryString);
		SolrDocumentList results = response.getResults();

		return mapResponse(results);
	}

	@Override
	public List<ColecticaItemSolr> getItemsByLabel(String label) {
		SolrClient solrClient = new HttpSolrClient.Builder(String.format("https://%s/solr", solrHost)).build();
		SolrQuery query = new SolrQuery();
		
		//Request
		query.set("defType", "edismax");
		query.set(CommonParams.Q, label);
		query.set(DisMaxParams.QF, "label description");
		query.set(DisMaxParams.BQ, "reusable:true");
		query.set(DisMaxParams.PF, "label description");
		query.set(CommonParams.SORT, "score desc");
		
		//Highlighting
		query.set(HighlightParams.HIGHLIGHT,true);
		query.set(HighlightParams.METHOD, "unified");
		query.set(HighlightParams.FIELDS, "label");
		query.set(HighlightParams.BS_TYPE, "WHOLE");
		
		QueryResponse response = null;
		try {
			response = solrClient.query("testcore", query);
		} catch (SolrServerException | IOException e1) {
			e1.printStackTrace();
		}
		
		List<ColecticaItemSolr> itemsResult = new ArrayList<>();
		SolrDocumentList results = response.getResults();
		for (SolrDocument result : results) {
			String type = getString(result, "type");
			String id = (String) result.getFieldValue("id");
			String labelResult = "";
			if (response.getHighlighting().get(id).get("label").size()>0) {
				labelResult = response.getHighlighting().get(id).get("label").get(0);
			} else {
				labelResult = getString(result, "label");
			}
			List<Long> versions = (List<Long>) result.getFieldValue("version");
			List<String> modalities = getStrings(result,"modalities");
			List<String> subGroups = getStrings(result,"subGroup");
			List<String> subGroupLabels = getStrings(result,"subGroupLabel");
			List<String> studyUnits = getStrings(result,"studyUnit");
			List<String> dataCollections = getStrings(result,"dataCollection");
			if (id != null && labelResult != null && type != null) {
				ColecticaItemSolr item = new ColecticaItemSolr(id, labelResult);
				item.setVersion(versions.get(0).intValue());
				item.setType(DDIItemType.searchByUUID(type.toUpperCase()).getName());
				item.setDescription(getString(result,"description")); 
				item.setModalities(modalities);
				item.setSubGroups(subGroups);
				item.setSubGroupLabels(subGroupLabels);
				item.setStudyUnits(studyUnits);
				item.setDataCollections(dataCollections);
				itemsResult.add(item);
			}
		}
		return itemsResult;
	}

	/**
	 * Safely gets a String for the given field of a solrDocument.
	 *
	 * @param solrDocument the document to get the field from
	 * @param field        the field to get
	 * @return the String value of the field
	 */
	public String getString(SolrDocument solrDocument, String field) {
		String returnVal = null;
		final Object object = solrDocument.getFieldValue(field);
		if (object != null) {
			if (object instanceof String) {
				returnVal = (String) object;
			} else if (object instanceof ArrayList) {
				Collection<Object> objects = solrDocument.getFieldValues(field);
				if (!objects.isEmpty()) {
					returnVal = (String) objects.iterator().next();
				}
			} else {
				returnVal = object.toString();
			}
		}
		return returnVal;
	}
	
	public List<String> getStrings(SolrDocument solrDocument, String field) {
		List<String> vals = new ArrayList<>();
		Collection<Object> fieldValues = solrDocument.getFieldValues(field);
		if (fieldValues != null) {
			for (Object val : fieldValues) {
				vals.add((String) val);
			}
		}
		return vals;
	}
}

