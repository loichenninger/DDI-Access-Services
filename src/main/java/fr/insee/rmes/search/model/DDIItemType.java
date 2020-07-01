package fr.insee.rmes.search.model;

public enum DDIItemType {

	QUESTION("Question","question","A1BB19BD-A24A-4443-8728-A6AD80EB42B8"),
	QUESTIONNAIRE("Instrument","instrument","F196CC07-9C99-4725-AD55-5B34F479CF7D"),
	SEQUENCE("Sequence","sequence","DF457731-A75C-47C3-AEB4-7969D55AA049"),
	DATA_COLLECTION("Data Collection","data-collection","C5084916-9936-47A9-A523-93BE9FD816D8"),
	STUDY_UNIT("StudyUnit","study-unit","30EA0200-7121-4F01-8D21-A931A182B86D"),
	CODE_LIST("CodeList","code-list","8B108EF8-B642-4484-9C49-F88E4BF7CF1D"),
	GROUP("Group","group","4BD6EEF6-99DF-40E6-9B11-5B8F64E5CB23"),
	SUB_GROUP("Sub Group","sub-group","2D57296D-095C-485A-B970-8C63C215C1D0"),
	DDI_INSTANCE("DDIInstance","DDIInstance","F2B9352A-D976-4EAC-8EE1-0C76DA7CFCA4"),
	VARIABLE("Variable","variable","683889C6-F74B-4D5E-92ED-908C0A42BB2D"),
	
	RESSOURCEPACKAGE("RessourcePackage","ressource-package","679A61F5-4246-4C89-B482-924DEC09AF98"),
	ORGANISATION_SCHEME("OrganisationScheme","organisation-scheme","08ED326A-8043-4DA2-ACE9-3F5BD19B6196"),
	CONCEPT_SCHEME("ConceptScheme","concept-scheme","63C9F58D-1EA3-4239-99CF-E4418EC384C5"),
	UNIVERSE_SCHEME("UniverseScheme","universe-scheme","101F901A-2C28-4931-88D6-8F80B36D5650"),
	INTERVIEWERINSTRUCTION_SCHEME("InterviewerInstructionScheme","interviewer-instruction-scheme","5BF598A9-9333-4C84-8C10-46195776800A"),
	CONTROLCONSTRUCT_SCHEME("ControlConstructScheme","control-construct-scheme","ED3801FE-6798-4EA6-808B-73052CC1C633"),
	QUESTION_SCHEME("QuestionScheme","question-scheme","0A63FCF6-FFDD-4214-B38C-147D6689D6A1"),
	CATEGORY_SCHEME("CategoryScheme","category-scheme","1C11DE94-A36D-4D80-95DC-950C6F37F624"),
	CATEGORY("Category","category","7E47C269-BCAB-40F7-A778-AF7BBC4E3D00"),
	VARIABLE_SCHEME("VariableScheme","variable-scheme","50907716-B67A-4DCD-8F9F-8A283CB5FEE0"),
	PROCESSINGEVENT_SCHEME("ProcessingEventScheme","processing-event-scheme","DC15C74B-9B4A-492E-9DDD-4E02ECA9D9D7"),
	PROCESSINGINSTRUCTION_SCHEME("ProcessingInstructionScheme","processing-instruction-scheme","A1A5C54A-3A7B-4DFB-A5FA-46ED8EF465CC"),
	PHYSICALSTRUCTURE_SCHEME("PhysicalStructureScheme","physical-structure-scheme","19273B86-934A-4C2C-9B64-BD2B3BB07ACD"),
	PHYSICAL_DATA_PRODUCT("PhysicalDataProduct","physical-data-product","E27AAC79-BE4A-47D3-96E3-36DA178F3923"),
	RECORDLAYOUT_SCHEME("RecordLayoutScheme","record-layout-scheme","8E4D59DB-E757-4E94-BB19-1AC72761566E"),
	QUALITYSTATEMENT_SCHEME("QualityStatementScheme","quality-statement-scheme","4AA7EA9C-495D-4919-95F7-AC107C877F56"),
	CONCEPTUALVARIABLE_SCHEME("ConceptualVariableScheme","conceptual-variable-scheme","CE0F8AF6-DB9C-4FB3-A31A-E9523FC53668"),
	REPRESENTEDVARIABLE_SCHEME("RepresentedVariableScheme","represented-variable-scheme","14404696-2DB3-45AC-A94D-139521DE6E21"),
	REPRESENTED_VARAIBLE("RepresentedVariable","represented-variable","1044459C-8AE2-474A-AD96-6EC18B04953C"),
	REPRESENTED_VARIABLE_GROUP("RepresentedVariableGroup","represented-variable-group","A8CECEF5-4493-47B8-9C83-82A2F1CFB08E"),
	NCUBE_SCHEME("NCubeScheme","n-cube-scheme","31E8515B-C0CC-4E88-9E00-AE4BB6D4AC25"),
	INSTRUMENT_SCHEME("InstrumentScheme","instrument-scheme","F152EE61-08BA-4FCA-8A3A-DAF8F87F972E"),
	CODELIST_SCHEME("CodeListScheme","code-list-scheme","4193D389-B5AE-4368-B399-CD5A7EE3653C"),
	MANAGEDREPRESENTATION_SCHEME("ManagedRepresentationScheme","managed-representation-scheme","16D4D829-41E1-4677-AA17-81190B6A0E66"),
	LOGICAL_PRODUCT("LogicalProduct","logical-product","965C8D28-7D48-4950-BEA7-04B27E52BB9B");
	
	private String name = "" ; 
	private String type ="";
	private String UUID ="";
	
	DDIItemType(String name, String type, String UUID){
		this.name=name;
		this.type=type;
		this.UUID = UUID;
	}
	
	public static DDIItemType searchByUUID(String uuid) {
		DDIItemType item = null;
		for (DDIItemType ddiItemType : values()) {
			if (ddiItemType.getUUID().equals(uuid)) {
				item = ddiItemType;
			}
		}
		return item;
	}
	
	public String toString(){
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}
	
	
	
	
}
