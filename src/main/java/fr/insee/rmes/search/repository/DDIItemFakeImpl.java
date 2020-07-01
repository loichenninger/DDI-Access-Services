package fr.insee.rmes.search.repository;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Repository;

import fr.insee.rmes.config.DDIItemFakeImplCondition;
import fr.insee.rmes.search.model.ColecticaItemSolr;
import fr.insee.rmes.search.model.DDIItem;
import fr.insee.rmes.search.model.DDIQuery;
import fr.insee.rmes.search.model.DataCollectionContext;
import fr.insee.rmes.search.model.ResponseItem;
import fr.insee.rmes.search.model.ResponseSearchItem;

@Repository
@Conditional(value = DDIItemFakeImplCondition.class)
public class DDIItemFakeImpl implements DDIItemRepository {

	@Override
	public IndexResponse save(String type, ResponseItem item) throws Exception {
		return null;
	}

	@Override
	public List<DDIItem> findByLabel(String label, String... types) throws Exception {
		// TODO implement a fake method
		return null;
	}

	@Override
	public List<DDIItem> findByLabelInSubGroup(String label, String subgroupId, String... types) throws Exception {
		// TODO implement a fake method
		return null;
	}

	@Override
	public List<DDIItem> getSubGroups() throws Exception {
		List<DDIItem> subGroups = new ArrayList<DDIItem>();
		DDIItem ddiItem2 = new DDIItem("SG02", "Enquête auprès des salariés de l'État", null, "SubGroup");
		DDIItem ddiItem3 = new DDIItem("SG03", "Enquête Emploi en continu", null, "SubGroup");
		DDIItem ddiItem4 = new DDIItem("SG04", "Technologies de l'information et de la communication", null,
				"SubGroup");
		DDIItem ddiItem5 = new DDIItem("SG05", "Statistiques sur les ressources et conditions de vie", null,
				"SubGroup");
		DDIItem ddiItem6 = new DDIItem("SG06", "Enquête sectorielle annuelle", null, "SubGroup");
		DDIItem ddiItem7 = new DDIItem("SG07", "Enquête trimestrielle de conjoncture dans l'artisanat du bâtiment",
				null, "SubGroup");
		DDIItem ddiItem8 = new DDIItem("SG08", "Enquête trimestrielle de conjoncture dans la promotion immobilière",
				null, "SubGroup");
		DDIItem ddiItem9 = new DDIItem("SG09",
				"Enquête sur les investissements dans l'industrie pour protéger l'environnement ", null, "SubGroup");
		DDIItem ddiItem10 = new DDIItem("SG10", "Enquête Achats-Production", null, "SubGroup");
		DDIItem ddiItem11 = new DDIItem("SG11", "Enquête mensuelle sur l'activité des grandes surfaces alimentaires",
				null, "SubGroup");
		subGroups.add(ddiItem2);
		subGroups.add(ddiItem3);
		subGroups.add(ddiItem4);
		subGroups.add(ddiItem5);
		subGroups.add(ddiItem6);
		subGroups.add(ddiItem7);
		subGroups.add(ddiItem8);
		subGroups.add(ddiItem9);
		subGroups.add(ddiItem10);
		subGroups.add(ddiItem11);
		return subGroups;
	}

	@Override
	public List<DDIItem> getStudyUnits(String subGroupId) throws Exception {
		List<DDIItem> studyUnits = new ArrayList<DDIItem>();
		System.out.println(subGroupId);
		if (subGroupId.equals("SG02") || subGroupId == null) {
			DDIItem ddiItem = new DDIItem("SU02", "Enquête auprès des salariés de l'État 2019", "SG02", "StudyUnit");
			studyUnits.add(ddiItem);
		}
		if (subGroupId.equals("SG03") || subGroupId == null) {
			DDIItem ddiItem = new DDIItem("SU03", "Enquête Emploi en continu 2017", "SG03", "StudyUnit");
			studyUnits.add(ddiItem);
		}
		if (subGroupId.equals("SG04") || subGroupId == null) {
			DDIItem ddiItem = new DDIItem("SU04",
					"Enquête sur les technologies de l'information et de la communication (auprès des ménages) 2019",
					"SG04", "StudyUnit");
			studyUnits.add(ddiItem);
		}
		if (subGroupId.equals("SG05") || subGroupId == null) {
			DDIItem ddiItem = new DDIItem("SU05", "Statistiques sur les ressources et conditions de vie 2018", "SG05",
					"StudyUnit");
			studyUnits.add(ddiItem);
		}
		if (subGroupId.equals("SG06") || subGroupId == null) {
			DDIItem ddiItem = new DDIItem("SU06", "Enquête sectorielle annuelle 2018", "SG06", "StudyUnit");
			studyUnits.add(ddiItem);
		}
		if (subGroupId.equals("SG07") || subGroupId == null) {
			DDIItem ddiItem = new DDIItem("SU07",
					"Enquête trimestrielle de conjoncture dans l'artisanat du bâtiment 2018", "SG07", "StudyUnit");
			studyUnits.add(ddiItem);
		}
		if (subGroupId.equals("SG08") || subGroupId == null) {
			DDIItem ddiItem = new DDIItem("SU08",
					"Enquête trimestrielle de conjoncture dans la promotion immobilière 2018", "SG08", "StudyUnit");
			studyUnits.add(ddiItem);
		}
		if (subGroupId.equals("SG09") || subGroupId == null) {
			DDIItem ddiItem = new DDIItem("SU09",
					"Enquête sur les investissements dans l'industrie pour protéger l'environnement 2017", "SG09",
					"StudyUnit");
			studyUnits.add(ddiItem);
		}
		if (subGroupId.equals("SG10") || subGroupId == null) {
			DDIItem ddiItem = new DDIItem("SU10", "Enquête Achats-Production 2018", "SG10", "StudyUnit");
			studyUnits.add(ddiItem);
		}
		if (subGroupId.equals("SG11") || subGroupId == null) {
			DDIItem ddiItem = new DDIItem("SU11",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires 2018", "SG11", "StudyUnit");
			studyUnits.add(ddiItem);
		}
		return studyUnits;
	}

	@Override
	public List<DDIItem> getDataCollections(String studyUnitId) throws Exception {
		List<DDIItem> dataCollections = new ArrayList<DDIItem>();
		if (studyUnitId.equals("SU02")) {
			DDIItem ddiItem = new DDIItem("DC0201", "Enquête auprès des salariés de l'État 2019", "SU02",
					"DataCollection");
			dataCollections.add(ddiItem);
		}
		if (studyUnitId.equals("SU03")) {
			DDIItem ddiItem = new DDIItem("DC0301", "Enquête Emploi en continu 2017 - Vague 1", "SU03",
					"DataCollection");
			DDIItem ddiItem2 = new DDIItem("DC0302", "Enquête Emploi en continu 2017 - Vague 2", "SU03",
					"DataCollection");
			DDIItem ddiItem3 = new DDIItem("DC0303", "Enquête Emploi en continu 2017 - Vague 3", "SU03",
					"DataCollection");
			DDIItem ddiItem4 = new DDIItem("DC0304", "Enquête Emploi en continu 2017 - Vague 4", "SU03",
					"DataCollection");
			DDIItem ddiItem5 = new DDIItem("DC0305", "Enquête Emploi en continu 2017 - Vague 5", "SU03",
					"DataCollection");
			DDIItem ddiItem6 = new DDIItem("DC0306", "Enquête Emploi en continu 2017 - Vague 6", "SU03",
					"DataCollection");
			dataCollections.add(ddiItem);
			dataCollections.add(ddiItem2);
			dataCollections.add(ddiItem3);
			dataCollections.add(ddiItem4);
			dataCollections.add(ddiItem5);
			dataCollections.add(ddiItem6);
		}
		if (studyUnitId.equals("SU04")) {
			DDIItem ddiItem = new DDIItem("DC0400",
					"Enquête sur les technologies de l'information et de la communication (auprès des ménages) 2019",
					"SU04", "DataCollection");
			dataCollections.add(ddiItem);
		}
		if (studyUnitId.equals("SU05")) {
			DDIItem ddiItem = new DDIItem("DC0500", "Statistiques sur les ressources et conditions de vie 2018", "SU05",
					"DataCollection");
			dataCollections.add(ddiItem);
		}
		if (studyUnitId.equals("SU06")) {
			DDIItem ddiItem = new DDIItem("DC0600", "Enquête sectorielle annuelle 2018", "SU06", "DataCollection");
			dataCollections.add(ddiItem);
		}
		if (studyUnitId.equals("SU07")) {
			DDIItem ddiItem = new DDIItem("DC07101",
					"Enquête trimestrielle de conjoncture dans l'artisanat du bâtiment 1er trimestre 2018", "SU07",
					"DataCollection");
			DDIItem ddiItem2 = new DDIItem("DC07202",
					"Enquête trimestrielle de conjoncture dans l'artisanat du bâtiment 2ème trimestre 2018", "SU07",
					"DataCollection");
			DDIItem ddiItem3 = new DDIItem("DC07303",
					"Enquête trimestrielle de conjoncture dans l'artisanat du bâtiment 3ème trimestre 2018", "SU07",
					"DataCollection");
			DDIItem ddiItem4 = new DDIItem("DC07404",
					"Enquête trimestrielle de conjoncture dans l'artisanat du bâtiment 4ème trimestre 2018", "SU07",
					"DataCollection");
			dataCollections.add(ddiItem);
			dataCollections.add(ddiItem2);
			dataCollections.add(ddiItem3);
			dataCollections.add(ddiItem4);
		}
		if (studyUnitId.equals("SU08")) {
			DDIItem ddiItem = new DDIItem("DC08101",
					"Enquête trimestrielle de conjoncture dans la promotion immobilière 1er trimestre 2018", "SU08",
					"DataCollection");
			DDIItem ddiItem2 = new DDIItem("DC08202",
					"Enquête trimestrielle de conjoncture dans la promotion immobilière 2ème trimestre 2018", "SU08",
					"DataCollection");
			DDIItem ddiItem3 = new DDIItem("DC08303",
					"Enquête trimestrielle de conjoncture dans la promotion immobilière 3ème trimestre 2018", "SU08",
					"DataCollection");
			DDIItem ddiItem4 = new DDIItem("DC08404",
					"Enquête trimestrielle de conjoncture dans la promotion immobilière 4ème trimestre 2018", "SU08",
					"DataCollection");
			dataCollections.add(ddiItem);
			dataCollections.add(ddiItem2);
			dataCollections.add(ddiItem3);
			dataCollections.add(ddiItem4);
		}
		if (studyUnitId.equals("SU09")) {
			DDIItem ddiItem = new DDIItem("DC0900",
					"Enquête sur les investissements dans l'industrie pour protéger l'environnement 2017", "SU09",
					"DataCollection");
			dataCollections.add(ddiItem);
		}
		if (studyUnitId.equals("SU10")) {
			DDIItem ddiItem = new DDIItem("DC1000", "Enquête Achats-Production 2018", "SU10", "DataCollection");
			dataCollections.add(ddiItem);
		}
		if (studyUnitId.equals("SU11")) {
			DDIItem ddiItem = new DDIItem("DC1101",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires janvier 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem2 = new DDIItem("DC1102",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires février 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem3 = new DDIItem("DC1103",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires mars 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem4 = new DDIItem("DC1104",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires avril 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem5 = new DDIItem("DC1105",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires mai 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem6 = new DDIItem("DC1106",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires juin 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem7 = new DDIItem("DC1107",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires juillet 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem8 = new DDIItem("DC1108",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires août 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem9 = new DDIItem("DC1109",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires septembre 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem10 = new DDIItem("DC1110",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires octobre 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem11 = new DDIItem("DC1111",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires novembre 2018", "SU11",
					"DataCollection");
			DDIItem ddiItem12 = new DDIItem("DC1112",
					"Enquête mensuelle sur l'activité des grandes surfaces alimentaires décembre 2018", "SU11",
					"DataCollection");
			dataCollections.add(ddiItem);
			dataCollections.add(ddiItem2);
			dataCollections.add(ddiItem3);
			dataCollections.add(ddiItem4);
			dataCollections.add(ddiItem5);
			dataCollections.add(ddiItem6);
			dataCollections.add(ddiItem7);
			dataCollections.add(ddiItem8);
			dataCollections.add(ddiItem9);
			dataCollections.add(ddiItem10);
			dataCollections.add(ddiItem11);
			dataCollections.add(ddiItem12);
		}
		return dataCollections;
	}

	@Override
	public DataCollectionContext getDataCollectionContext(String dataCollectionId) throws Exception {
		DataCollectionContext dataCollectionContext = new DataCollectionContext();
		dataCollectionContext.setDataCollectionId(dataCollectionId);
		dataCollectionContext.setSerieId("SG" + dataCollectionId.substring(2, 4));
		dataCollectionContext.setOperationId("SU" + dataCollectionId.substring(2, 4));
		return dataCollectionContext;
	}

	@Override
	public DeleteResponse delete(String type, String id) throws Exception {
		return null;
	}

	@Override
	public List<ResponseSearchItem> getItemsByCriteria(String subgroupId, String operationId, String dataCollectionId,
			DDIQuery criteria) throws Exception {

		List<ResponseSearchItem> response = new ArrayList<ResponseSearchItem>();

		List<String> types = criteria.getTypes();

		for (String type : types) {
			if (type.toLowerCase().equals("instrument")) {
				String filter = criteria.getFilter();
				response = getIntruments(subgroupId, operationId, dataCollectionId, filter);
			}
			if (type.toLowerCase().equals("codelist")) {
				String filter = criteria.getFilter();
				response = getCodeLists(subgroupId, operationId, dataCollectionId, filter);
			}

		}

		return response;
	}

	private List<ResponseSearchItem> getCodeLists(String subgroupId, String operationId, String dataCollectionId,
			String filter) throws Exception {

		List<ResponseSearchItem> codeLists = initCodeLists();
		List<ResponseSearchItem> codeListsFiltered = new ArrayList<ResponseSearchItem>();
		List<ResponseSearchItem> codeListsFilteredFinal = new ArrayList<ResponseSearchItem>();
		if (subgroupId != null) {
			if (operationId != null) {
				if (dataCollectionId != null) {
					for (ResponseSearchItem codeList : codeLists) {
						if (codeList.getDataCollectionId() != null
								&& codeList.getDataCollectionId().equals(dataCollectionId)) {
							codeListsFiltered.add(codeList);
						}
					}
				} else {
					for (ResponseSearchItem codeList : codeLists) {
						if (codeList.getStudyUnitId() != null && codeList.getStudyUnitId().equals(operationId)) {
							codeListsFiltered.add(codeList);
						}
					}
				}
			} else {
				for (ResponseSearchItem codeList : codeLists) {
					if (codeList.getSubgroupId() != null && codeList.getSubgroupId().equals(subgroupId)) {
						codeListsFiltered.add(codeList);
					}
				}
			}
		} else {
			codeListsFiltered.addAll(codeLists);
		}
		codeListsFiltered = addCommonCodeList(codeListsFiltered);

		for (ResponseSearchItem codeList : codeListsFiltered) {
			if (codeList.getTitle().toUpperCase().contains(filter.toUpperCase())) {
				codeListsFilteredFinal.add(codeList);
			}
		}
		codeListsFilteredFinal = updateContextLabel(codeListsFilteredFinal);

		return codeListsFilteredFinal;

	}

	private List<ResponseSearchItem> addCommonCodeList(List<ResponseSearchItem> rsiList) {
		ResponseSearchItem rsi = new ResponseSearchItem("CLI0101", "COMMUN - Liste de codes Oui/Non", null, null);
		rsiList.add(rsi);
		rsi = new ResponseSearchItem("CLI0102", "COMMUN - Liste de codes Femme/Homme", null, null);
		rsiList.add(rsi);
		return rsiList;
	}

	private List<ResponseSearchItem> getIntruments(String subgroupId, String operationId, String dataCollectionId,
			String filter) throws Exception {

		List<ResponseSearchItem> instruments = initIntruments();
		List<ResponseSearchItem> instrumentsFiltered = new ArrayList<ResponseSearchItem>();
		List<ResponseSearchItem> instrumentsFilteredFinal = new ArrayList<ResponseSearchItem>();
		if (subgroupId != null) {
			if (operationId != null) {
				if (dataCollectionId != null) {
					for (ResponseSearchItem instrument : instruments) {
						if (instrument.getDataCollectionId().equals(dataCollectionId)) {
							instrumentsFiltered.add(instrument);
						}
					}
				} else {
					for (ResponseSearchItem instrument : instruments) {
						if (instrument.getStudyUnitId().equals(operationId)) {
							instrumentsFiltered.add(instrument);
						}
					}
				}
			} else {
				for (ResponseSearchItem instrument : instruments) {
					if (instrument.getSubgroupId().equals(subgroupId)) {
						instrumentsFiltered.add(instrument);
					}
				}
			}
		} else {
			instrumentsFiltered.addAll(instruments);
		}
		for (ResponseSearchItem instrument : instrumentsFiltered) {
			if (instrument.getTitle().toUpperCase().contains(filter.toUpperCase())) {
				instrumentsFilteredFinal.add(instrument);
			}
		}

		instrumentsFilteredFinal = updateContextLabel(instrumentsFilteredFinal);

		return instrumentsFilteredFinal;
	}

	private List<ResponseSearchItem> updateContextLabel(List<ResponseSearchItem> rsiList) throws Exception {

		for (ResponseSearchItem rsi : rsiList) {

			String subGroupID = rsi.getSubgroupId();
			String studyUnitID = rsi.getStudyUnitId();

			if (rsi.getSubgroupId() != null) {
				for (DDIItem ddiItem : this.getSubGroups()) {
					if (rsi.getSubgroupId().equals(ddiItem.getId())) {
						rsi.setSubgroupId(ddiItem.getLabel());
					}
				}
			}
			if (rsi.getStudyUnitId() != null) {
				for (DDIItem ddiItem : this.getStudyUnits(subGroupID)) {
					if (rsi.getStudyUnitId().equals(ddiItem.getId())) {
						rsi.setStudyUnitId(ddiItem.getLabel());
					}
				}
			}
			if (rsi.getDataCollectionId() != null) {
				for (DDIItem ddiItem : this.getDataCollections(studyUnitID)) {
					if (rsi.getDataCollectionId().equals(ddiItem.getId())) {
						rsi.setDataCollectionId(ddiItem.getLabel());
					}
				}
			}
		}
		return rsiList;
	}

	private List<ResponseSearchItem> initCodeLists() {

		List<ResponseSearchItem> codeLists = new ArrayList<ResponseSearchItem>();
		ResponseSearchItem rsi = new ResponseSearchItem("CLI0101", "COMMUN - Liste de codes Oui/Non", null, null);
		codeLists.add(rsi);
		rsi = new ResponseSearchItem("CLI0102", "COMMUN - Liste de codes Femme/Homme", null, null);
		codeLists.add(rsi);
		String subgroupId = "SG02";
		String studyUnitId = "SU02";
		rsi = new ResponseSearchItem("CLI0201", "Liste de codes Oui/Non/Peut-être FPE", subgroupId, studyUnitId);
		codeLists.add(rsi);
		rsi = new ResponseSearchItem("CLI0202", "Liste de codes Femme/Homme FPE", subgroupId, studyUnitId);
		codeLists.add(rsi);
		subgroupId = "SG03";
		studyUnitId = "SU03";
		rsi = new ResponseSearchItem("CLI0301", "Liste de codes Type de contrat EEC", subgroupId, studyUnitId);
		codeLists.add(rsi);
		rsi = new ResponseSearchItem("CLI0302", "Liste de codes Oui/Non/Peut-être EEC", subgroupId, studyUnitId);
		codeLists.add(rsi);
		rsi = new ResponseSearchItem("CLI0303", "Liste de codes Secteur activité EEC", subgroupId, studyUnitId);
		codeLists.add(rsi);

		return codeLists;
	}

	private List<ResponseSearchItem> initIntruments() {

		List<ResponseSearchItem> instruments = new ArrayList<ResponseSearchItem>();
		String subgroupId = "SG02";
		String studyUnitId = "SU02";
		String dataCollectionId = "DC0201";
		ResponseSearchItem rsi = new ResponseSearchItem("I0201",
				"Questionnaire de l'enquête auprès des salariés de l'État 2019", subgroupId, studyUnitId,
				dataCollectionId);
		instruments.add(rsi);
		subgroupId = "SG03";
		studyUnitId = "SU03";
		dataCollectionId = "DC0301";
		rsi = new ResponseSearchItem("I0301", "Questionnaire de l'enquête Emploi en continu 2017 - Vague 1", subgroupId,
				studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0302";
		rsi = new ResponseSearchItem("I0302", "Questionnaire de l'enquête Emploi en continu 2017 - Vague 2", subgroupId,
				studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0303";
		rsi = new ResponseSearchItem("I0303", "Questionnaire de l'enquête Emploi en continu 2017 - Vague 3", subgroupId,
				studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0304";
		rsi = new ResponseSearchItem("I0304", "Questionnaire de l'enquête Emploi en continu 2017 - Vague 4", subgroupId,
				studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0305";
		rsi = new ResponseSearchItem("I0305", "Questionnaire de l'enquête Emploi en continu 2017 - Vague 5", subgroupId,
				studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0306";
		rsi = new ResponseSearchItem("I0306", "Questionnaire de l'enquête Emploi en continu 2017 - Vague 6", subgroupId,
				studyUnitId, dataCollectionId);
		instruments.add(rsi);
		subgroupId = "SG04";
		studyUnitId = "SU04";
		dataCollectionId = "DC0401";
		rsi = new ResponseSearchItem("I0401",
				"Questionnaire de l'enquête sur les technologies de l'information et de la communication (auprès des ménages) 2019",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		subgroupId = "SG05";
		studyUnitId = "SU05";
		dataCollectionId = "DC0501";
		rsi = new ResponseSearchItem("I0501",
				"Questionnaire de Statistiques sur les ressources et conditions de vie 2018", subgroupId, studyUnitId,
				dataCollectionId);
		instruments.add(rsi);
		subgroupId = "SG06";
		studyUnitId = "SU06";
		dataCollectionId = "DC0601";
		rsi = new ResponseSearchItem("I0601", "Questionnaire de l'enquête sectorielle annuelle 2018", subgroupId,
				studyUnitId, dataCollectionId);
		instruments.add(rsi);
		subgroupId = "SG07";
		studyUnitId = "SU07";
		dataCollectionId = "DC0701";
		rsi = new ResponseSearchItem("I0701",
				"Questionnaire de l'enquête trimestrielle de conjoncture dans l'artisanat du bâtiment 1er trimestre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0702";
		rsi = new ResponseSearchItem("I0702",
				"Questionnaire de l'enquête trimestrielle de conjoncture dans l'artisanat du bâtiment 2ème trimestre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0703";
		rsi = new ResponseSearchItem("I0703",
				"Questionnaire de l'enquête trimestrielle de conjoncture dans l'artisanat du bâtiment 3ème trimestre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0704";
		rsi = new ResponseSearchItem("I0704",
				"Questionnaire de l'enquête trimestrielle de conjoncture dans l'artisanat du bâtiment 4ème trimestre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		subgroupId = "SG08";
		studyUnitId = "SU08";
		dataCollectionId = "DC0801";
		rsi = new ResponseSearchItem("I0801",
				"Questionnaire de l'enquête trimestrielle de conjoncture dans la promotion immobilière 1er trimestre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0802";
		rsi = new ResponseSearchItem("I0802",
				"Questionnaire de l'enquête trimestrielle de conjoncture dans la promotion immobilière 2ème trimestre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0803";
		rsi = new ResponseSearchItem("I0803",
				"Questionnaire de l'enquête trimestrielle de conjoncture dans la promotion immobilière 3ème trimestre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC0804";
		rsi = new ResponseSearchItem("I0804",
				"Questionnaire de l'enquête trimestrielle de conjoncture dans la promotion immobilière 4ème trimestre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		subgroupId = "SG09";
		studyUnitId = "SU09";
		dataCollectionId = "DC0901";
		rsi = new ResponseSearchItem("I0901",
				"Questionnaire de l'enquête sur les investissements dans l'industrie pour protéger l'environnement 2017",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		subgroupId = "SG10";
		studyUnitId = "SU10";
		dataCollectionId = "DC1001";
		rsi = new ResponseSearchItem("I1001", "Questionnaire de l'enquête Achats-Production 2018", subgroupId,
				studyUnitId, dataCollectionId);
		instruments.add(rsi);
		subgroupId = "SG11";
		studyUnitId = "SU11";
		dataCollectionId = "DC1101";
		rsi = new ResponseSearchItem("I1101",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires janvier 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1102";
		rsi = new ResponseSearchItem("I1102",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires février 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1103";
		rsi = new ResponseSearchItem("I1103",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires mars 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1104";
		rsi = new ResponseSearchItem("I1104",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires avril 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1105";
		rsi = new ResponseSearchItem("I1105",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires mai 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1106";
		rsi = new ResponseSearchItem("I1106",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires juin 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1107";
		rsi = new ResponseSearchItem("I1107",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires juillet 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1108";
		rsi = new ResponseSearchItem("I1108",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires août 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1109";
		rsi = new ResponseSearchItem("I1109",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires septembre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1110";
		rsi = new ResponseSearchItem("I1110",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires octobre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1111";
		rsi = new ResponseSearchItem("I1111",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires novembre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);
		dataCollectionId = "DC1112";
		rsi = new ResponseSearchItem("I1112",
				"Questionnaire de l'enquête mensuelle sur l'activité des grandes surfaces alimentaires décembre 2018",
				subgroupId, studyUnitId, dataCollectionId);
		instruments.add(rsi);

		return instruments;
	}

	@Override
	public void deleteAll() throws Exception {
		// Nothing to do
	}

	@Override
	public DDIItem getItemById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DDIItem> getGroups() throws Exception {
		List<DDIItem> groups = new ArrayList<DDIItem>();
		DDIItem ddiItem1 = new DDIItem("G01", "Famille 1", null, "Group");
		DDIItem ddiItem2 = new DDIItem("G02", "Famille 2", null, "Group");
		groups.add(ddiItem2);
		groups.add(ddiItem1);
		return groups;
	}

	@Override
	public List<ColecticaItemSolr> getItemsByLabel(String label) {
		// TODO Auto-generated method stub
		return null;
	}

}
