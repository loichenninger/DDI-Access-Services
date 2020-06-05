package fr.insee.rmes.metadata.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColecticaSearchResponse {
	
	@JsonProperty("Results")
	private List<ColecticaSearchResult> results;
	@JsonProperty("TotalResults")
	private Integer totalResults;
	@JsonProperty("ReturnedResults")
	private Integer returnedResults;
	@JsonProperty("DatabaseTime")
	private String databaseTime;
	@JsonProperty("RepositoryTime")
	private String repositoryTime;
	public List<ColecticaSearchResult> getResults() {
		return results;
	}
	public void setResults(List<ColecticaSearchResult> results) {
		this.results = results;
	}
	public Integer getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}
	public Integer getReturnedResults() {
		return returnedResults;
	}
	public void setReturnedResults(Integer returnedResults) {
		this.returnedResults = returnedResults;
	}
	public String getDatabaseTime() {
		return databaseTime;
	}
	public void setDatabaseTime(String databaseTime) {
		this.databaseTime = databaseTime;
	}
	public String getRepositoryTime() {
		return repositoryTime;
	}
	public void setRepositoryTime(String repositoryTime) {
		this.repositoryTime = repositoryTime;
	}
	@Override
	public String toString() {
		return "ColecticaSearchResponse [results=" + results + ", totalResults=" + totalResults + ", returnedResults="
				+ returnedResults + ", databaseTime=" + databaseTime + ", repositoryTime=" + repositoryTime + "]";
	}
	
	

}
