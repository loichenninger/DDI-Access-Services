package fr.insee.rmes.metadata.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColecticaSearchSetBody {
	
	@JsonProperty("RootItem")
	private ColecticaItemRef rootItem;

	@JsonProperty("Facet")
	private ColecticaFacet facet;

	public ColecticaSearchSetBody(ColecticaItemRef rootItem, ColecticaFacet facet) {
		super();
		this.rootItem = rootItem;
		this.facet = facet;
	}
	
}
