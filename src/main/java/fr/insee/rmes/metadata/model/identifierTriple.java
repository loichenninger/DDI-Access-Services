package fr.insee.rmes.metadata.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class identifierTriple {

	/*
	 * [ { "Item1": { "Item1": "string", "Item2": 0, "Item3": "string" },
	 * "Item2": "string" } ]
	 */

	@JsonProperty("Item1")
	private String identifier;
	@JsonProperty("Item2")
	private Integer version;
	@JsonProperty("Item3")
	private String agencyId;

	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
		return "identifierTriple [identifier=" + identifier + ", Version=" + version + ", agency=" + agencyId + "]";
	}

}
