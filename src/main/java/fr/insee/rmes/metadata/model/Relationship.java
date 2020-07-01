package fr.insee.rmes.metadata.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Relationship {

	/*
	 * [ { "Item1": { "Item1": "string", "Item2": 0, "Item3": "string" },
	 * "Item2": "string" } ]
	 */
	
	@JsonProperty("Item1")
	private identifierTriple identifierTriple;

	@JsonProperty("Item2")
	private String typeItem;

	public identifierTriple getIdentifierTriple() {
		return identifierTriple;
	}

	public void setIdentifierTriple(identifierTriple identifierTriple) {
		this.identifierTriple = identifierTriple;
	}

	@Override
	public String toString() {
		return "Relationship [identifierTriple=" + identifierTriple + ", typeItem=" + typeItem + "]";
	}

	public String getTypeItem() {
		return typeItem;
	}

	public void setTypeItem(String typeItem) {
		this.typeItem = typeItem;
	}

	public String toJson() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}

}
