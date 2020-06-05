package fr.insee.rmes.metadata.service;

import java.util.List;
import java.util.Map;

import fr.insee.rmes.metadata.model.ColecticaItem;
import fr.insee.rmes.metadata.model.ColecticaItemPostRef;
import fr.insee.rmes.metadata.model.ColecticaItemPostRefList;
import fr.insee.rmes.metadata.model.ColecticaItemRefList;
import fr.insee.rmes.search.model.DDIItemType;
import fr.insee.rmes.search.model.ResponseItem;

public interface MetadataServiceItem {

	ColecticaItem getItem(String id) throws Exception;

	ColecticaItem getItemByType(String id, DDIItemType type) throws Exception;

	ColecticaItem getDDIInstance(String id) throws Exception;

	ColecticaItem getSequence(String id) throws Exception;

	ColecticaItem getQuestion(String id) throws Exception;

	ColecticaItemRefList getChildrenRef(String id) throws Exception;

	Map<String, ColecticaItem> getMapItems(ColecticaItemRefList refs) throws Exception;

	List<ColecticaItem> getItems(ColecticaItemRefList refs) throws Exception;

	List<ColecticaItem> getItemsByType(DDIItemType type) throws Exception;
	
	ResponseItem getDDIRoot(String id) throws Exception;

	List<ResponseItem> getDDICodeListSchemeFromResourcePackage(String id) throws Exception;

	List<ResponseItem> getDDICodeListSchemeFromGroupRoot(String id) throws Exception;

	Map<ColecticaItemPostRef, String> postNewItems(ColecticaItemPostRefList refs) throws Exception;

	Map<ColecticaItemPostRef, String> postUpdateItems(ColecticaItemPostRefList refs) throws Exception;

	ColecticaItem getStudyUnit(String idStudyUnit) throws Exception;

}
