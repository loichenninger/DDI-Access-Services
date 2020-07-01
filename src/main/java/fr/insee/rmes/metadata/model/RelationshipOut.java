package fr.insee.rmes.metadata.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RelationshipOut {
	
	@JsonProperty("Identifiers")
	private ColecticaItemRef ref;

	@JsonProperty("ItemType")
	private String itemType;

	public RelationshipOut(ColecticaItemRef ref, String itemType) {
		super();
		this.ref = ref;
		this.itemType = itemType;
	}

	public ColecticaItemRef getRef() {
		return ref;
	}

	public void setRef(ColecticaItemRef ref) {
		this.ref = ref;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Override
	public String toString() {
		return "RelationshipOut [ref=" + ref + ", itemType=" + itemType + "]";
	}

	

}
