package fr.insee.rmes.metadata.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColecticaSearchBody {
	
	@JsonProperty("Cultures")
	private List<String> cultures = new ArrayList<>();
	@JsonProperty("ItemTypes")
	private List<String> itemTypes = new ArrayList<>();
	@JsonProperty("LanguageSortOrder")
	private List<String> languageSortOrder = new ArrayList<>();
	@JsonProperty("MaxResults")
	private Integer maxResults = 0;
	@JsonProperty("RankResults")
	private Boolean rankResults = true;
	@JsonProperty("ResultOffset")
	private Integer resultOffset = 0;
	@JsonProperty("ResultOrdering")
	private String resultOrdering = "None";
	@JsonProperty("SearchDepricatedItems")
	private Boolean searchDepricatedItems = false;
	@JsonProperty("SearchLatestVersion")
	private Boolean searchLatestVersion = true;
	@JsonProperty("SearchSets")
	List<ColecticaItemRef> searchSets = new ArrayList<>();
	@JsonProperty("SearchTerms")
	List<String> searchTerms = new ArrayList<>();
	public List<String> getCultures() {
		return cultures;
	}
	public void setCultures(List<String> cultures) {
		this.cultures = cultures;
	}
	public List<String> getItemTypes() {
		return itemTypes;
	}
	public void setItemTypes(List<String> itemTypes) {
		this.itemTypes = itemTypes;
	}
	public List<String> getLanguageSortOrder() {
		return languageSortOrder;
	}
	public void setLanguageSortOrder(List<String> languageSortOrder) {
		this.languageSortOrder = languageSortOrder;
	}
	public Integer getMaxResults() {
		return maxResults;
	}
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	public Boolean getRankResults() {
		return rankResults;
	}
	public void setRankResults(Boolean rankResults) {
		this.rankResults = rankResults;
	}
	public Integer getResultOffset() {
		return resultOffset;
	}
	public void setResultOffset(Integer resultOffset) {
		this.resultOffset = resultOffset;
	}
	public String getResultOrdering() {
		return resultOrdering;
	}
	public void setResultOrdering(String resultOrdering) {
		this.resultOrdering = resultOrdering;
	}
	public Boolean getSearchDepricatedItems() {
		return searchDepricatedItems;
	}
	public void setSearchDepricatedItems(Boolean searchDepricatedItems) {
		this.searchDepricatedItems = searchDepricatedItems;
	}
	public Boolean getSearchLatestVersion() {
		return searchLatestVersion;
	}
	public void setSearchLatestVersion(Boolean searchLatestVersion) {
		this.searchLatestVersion = searchLatestVersion;
	}
	public List<ColecticaItemRef> getSearchSets() {
		return searchSets;
	}
	public void setSearchSets(List<ColecticaItemRef> searchSets) {
		this.searchSets = searchSets;
	}
	public List<String> getSearchTerms() {
		return searchTerms;
	}
	public void setSearchTerms(List<String> searchTerms) {
		this.searchTerms = searchTerms;
	}
	@Override
	public String toString() {
		return "ColecticaSearchBody [cultures=" + cultures + ", itemTypes=" + itemTypes + ", languageSortOrder="
				+ languageSortOrder + ", maxResults=" + maxResults + ", rankResults=" + rankResults + ", resultOffset="
				+ resultOffset + ", resultOrdering=" + resultOrdering + ", searchDepricatedItems="
				+ searchDepricatedItems + ", searchLatestVersion=" + searchLatestVersion + ", searchSets=" + searchSets
				+ ", searchTerms=" + searchTerms + "]";
	}

}
