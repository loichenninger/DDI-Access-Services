package fr.insee.rmes.metadata.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColecticaFacet {
	
	@JsonProperty("ItemTypes")
	private List<String> itemTypes = new ArrayList<String>();

	@JsonProperty("ReverseTraversal")
	private Boolean reverseTraversal;

	public ColecticaFacet(List<String> itemTypes, Boolean reverseTraversal) {
		super();
		this.itemTypes = itemTypes;
		this.reverseTraversal = reverseTraversal;
	}

	public ColecticaFacet() {
		super();
	}

	public List<String> getItemTypes() {
		return itemTypes;
	}

	public void setItemTypes(List<String> itemTypes) {
		this.itemTypes = itemTypes;
	}

	public Boolean getReverseTraversal() {
		return reverseTraversal;
	}

	public void setReverseTraversal(Boolean reverseTraversal) {
		this.reverseTraversal = reverseTraversal;
	}

	@Override
	public String toString() {
		return "ColecticaFacet [itemTypes=" + itemTypes + ", reverseTraversal=" + reverseTraversal + "]";
	}	

}
