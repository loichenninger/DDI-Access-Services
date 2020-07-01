package fr.insee.rmes.search.model;

import java.util.HashMap;
import java.util.Map;

public class ColecticaItemSolrChild {

	public String id = "";
	public String agencyId = "";
	private String label = "";
	/**
	 * UUID type of the Colectica Repository, the name of this type is available
	 * through the {@link fr.insee.rmes.search.model.DDIItemType #DDIItemType}
	 */
	private String type;
	public Integer version;

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	@Override
	public String toString() {
		return "ColecticaItemSolrChild [id=" + id + ", agencyId=" + agencyId + ", Label=" + label + ", type=" + type
				+ ", version=" + version + "]";
	}

}
