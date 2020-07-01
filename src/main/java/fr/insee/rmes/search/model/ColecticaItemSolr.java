package fr.insee.rmes.search.model;

import java.util.List;

public class ColecticaItemSolr {

	private String id = "";
	private String label = "";
	private String type;
	private Integer version;
	
	private List<String> modalities;
	private List<String> subGroups;
	private List<String> dataCollections;
	private List<String> studyUnits;

	public ColecticaItemSolr(String id, String label) {
		super();
		this.id = id;
		this.label = label;
	}

	/**
	 * UUID type of the Colectica Repository, the name of this type is available
	 * through the {@link fr.insee.rmes.search.model.DDIItemType #DDIItemType}
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<String> getModalities() {
		return modalities;
	}

	public void setModalities(List<String> modalities) {
		this.modalities = modalities;
	}

	public List<String> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(List<String> subGroups) {
		this.subGroups = subGroups;
	}

	public List<String> getDataCollections() {
		return dataCollections;
	}

	public void setDataCollections(List<String> dataCollections) {
		this.dataCollections = dataCollections;
	}

	public List<String> getStudyUnits() {
		return studyUnits;
	}

	public void setStudyUnits(List<String> studyUnits) {
		this.studyUnits = studyUnits;
	}

	@Override
	public String toString() {
		return "ColecticaItemSolr [id=" + id + ", label=" + label + ", type=" + type + ", version=" + version
				+ ", modalities=" + modalities + ", subGroups=" + subGroups + ", dataCollections=" + dataCollections
				+ ", studyUnits=" + studyUnits + "]";
	}

	
	
}
