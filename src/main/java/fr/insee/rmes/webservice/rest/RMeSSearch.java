package fr.insee.rmes.webservice.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.insee.rmes.search.model.ColecticaItemSolr;
import fr.insee.rmes.search.model.DDIItem;
import fr.insee.rmes.search.model.DDIQuery;
import fr.insee.rmes.search.model.DataCollectionContext;
import fr.insee.rmes.search.model.ResponseSearchItem;
import fr.insee.rmes.search.service.SearchService;
import fr.insee.rmes.search.source.ColecticaSourceImporter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@Path("/search")
@Api(value = "DDI Search")
public class RMeSSearch {

	final static Logger logger = LogManager.getLogger(RMeSSearch.class);

	@Autowired
	SearchService searchService;

	@Autowired
	ColecticaSourceImporter colecticaSourceImporter;

	@POST
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = "Search Item", notes = "Search the application index for item across types`")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Unexpected error") })
	public List<ResponseSearchItem> search(
			@ApiParam(value = "Search only items referring to sub-group id", required = false) @QueryParam("subgroupId") String subgroupId,
			@ApiParam(value = "Search only items referring to study-unit id", required = false) @QueryParam("studyUnitId") String studyUnitId,
			@ApiParam(value = "Search only items referring to data-collection id", required = false) @QueryParam("dataCollectionId") String dataCollectionId,
			DDIQuery criteria) throws Exception {
		try {
			return searchService.searchByLabel(subgroupId, studyUnitId, dataCollectionId, criteria);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@DELETE
	@Path("questionnaire/{id}")
	@Consumes(APPLICATION_JSON)
	@Produces(APPLICATION_JSON)
	@ApiOperation(value = "Delete Questionnaire from Index", notes = "Index a new `Questionnaire`")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
			@ApiResponse(code = 404, message = "Not found"), @ApiResponse(code = 500, message = "Unexpected error") })
	public Response deleteQuestionnaire(@PathParam(value = "id") String id) throws Exception {
		try {
			DeleteResponse response = searchService.delete("questionnaire", id);
			return Response.status(NO_CONTENT).entity(response).build();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GET
	@Path("import")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Import indexes from Colectica",
			notes = "This require a living instance of colectica as well as a up and running elasticsearch cluster",
			response = String.class)
	public Response source() throws Exception {
		try {
			colecticaSourceImporter.source();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return Response.ok().build();
	}

	@GET
	@Path("familles")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all groups (familles)", notes = "Retrieve all groups", response = String.class)
	public List<DDIItem> getGroups() throws Exception {
		try {
			return searchService.getGroups();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GET
	@Path("series")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all sub-group (series)", notes = "Retrieve all sub-groups", response = String.class)
	public List<DDIItem> getSubGroups() throws Exception {
		try {
			return searchService.getSubGroups();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GET
	@Path("series/{id}/operations")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all study-units (operations) for a given sub-group (series)",
			notes = "Retrieve all operations with a parent id matching the series id given as a path parameter",
			response = String.class)
	public List<DDIItem> getStudyUnits(@PathParam(value = "id") String id) throws Exception {
		try {
			return searchService.getStudyUnits(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GET
	@Path("/operations")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all study-units (operations) ", notes = "Retrieve all operations ",
			response = String.class)
	public List<DDIItem> getStudyUnits() throws Exception {
		try {
			return searchService.getStudyUnits(null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GET
	@Path("context/data-collection/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get data collection context (Sub-group id, StudyUnit id) for a given data collection", notes = "Retrieve the context (Sub-group id, StudyUnit id) for a id given as a path parameter", response = String.class)
	public DataCollectionContext getDataCollectionContext(@PathParam(value = "id") String id) throws Exception {
		try {
			return searchService.getDataCollectionContext(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GET
	@Path("operations/{id}/data-collection")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all data collections for a given operation", notes = "Retrieve all data collections with a parent id matching the operation id given as a path parameter")
	public List<DDIItem> getDataCollections(@PathParam(value = "id") String id) throws Exception {
		try {
			return searchService.getDataCollections(id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@GET
	@Path("items/{label}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all items for a given label", notes = "Retrieve all items with a matching label")
	public List<ColecticaItemSolr> getItemsFromLabel(@PathParam(value = "label") String label) throws Exception {
		try {
			return searchService.getItemsByLabel(label);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

}