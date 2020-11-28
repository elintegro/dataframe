package com.elintegro.utils.cleanup



class CodeCleanUpBackUp {

/*    <------------------- DataframeInstance code start here ------------------------->

  public def retrieveAndGetJson(){
		if (!isDefault){
			//retrieving from DB!
			populateInstance()
		}

		jData = new JSONObject((Map) SerializationUtils.clone(df.domainFieldMap))

		Map jsonRet = [:];
		Map jsonMapDf = [:];
		Map jsonAdditionalData = [:];
		Map jsonDefaults = [:];
		Map additionalDataRequestParamMap = [:];

		additionalDataRequestParamMap.putAll(this.requestParams)
		additionalDataRequestParamMap.putAll(this.resultMap)

		if( isGood() || isDefault){
			//!!!!!!!!!!!!!!!!!!!!!!!!! main loop on fields
			df.fields.getList().each{ fieldName ->
				Map fieldProps = df.fields.get(fieldName)
				String myDomainAlias = null
				//load data for widgets
				loadDataForWidgets(fieldProps, additionalDataRequestParamMap)
			}
		}
		addKeyDataForNamedParameters(jsonMapDf) // adding key- fields for vue js
		jsonMapDf.put("keys", getNamedParameters())
		jsonMapDf.put("additionalData", jsonAdditionalData)

		//Replace default add datan with relev values
		jsonDefaults.put("keys", getNamedParameters())
		jsonDefaults.put("additionalData", jsonAdditionalData)

		//These are for refresh parent dataframe, if provided (aka requested):
		def parentDataframe = requestParams?.parentDataframe
		def parentNode = requestParams?.parentNode
		def parentLevel = requestParams?.level
		def parentFieldName = requestParams?.parentFieldName
		def parentNodeLevel = getLevelFromUiIdConstruct(parentNode)

		log.debug("\n *******   Request Params: when retrieved \n" + reqParamPrintout(requestParams) + "\n ***************\n")

		//TODO: 1 All this should be coming from additionalParameters jsonMapDf[additionalParams]
		//df.dataFrameParamsToRefresh = [parentNode:parentNode, parentDataframe:parentDataframe, level:parentLevel, parentFieldName:parentFieldName]
		def html = df.getHtml()
		jsonRet.put("html", html)
		jsonRet.put("parentDataframe", parentDataframe)
		jsonRet.put("parentNode", parentNode)
		jsonRet.put("level", parentLevel)
		jsonRet.put("parentFieldName", parentFieldName)
		jsonRet.put("dataframe",df.dataframeName);
		//TODO: 1
		if (isDefault){
			jsonRet.put("data",jsonDefaults)
		}else {
			//jsonRet.put("data", jsonMapDf)
			jsonRet.put("data", jData)
		}
		jsonRet.put("default",jsonDefaults)
		jsonRet.put("operation","R");

		String domainAlias = df.hql?df.parsedHql?.hqlDomains?.keySet()?.asList()?.get(0):"";

		jsonRet.put("dataFrameParamsToRefresh",
				[   'parentNode':parentNode,
					'parentNodeId':getIdFromUiIdConstruct(parentNode),
					'parentNodeLevel':getLevelFromUiIdConstruct(parentNode),
					'parentDataframe':parentDataframe,
					'level':parentLevel,
					'parentFieldName': Dataframe.buildFullFieldName_(df.dataframeName, domainAlias, parentFieldName)
				])

		return jsonRet
	}

    private def retrieveAndGetJsonNew(){

        if(!df.hql){
            return;
        }

        Query query = createSQLQuery()

        setNamedParametersFromRequestOrSession(query)

        jData = new JSONObject(df.domainFieldMap)
        df.writableDomains.each { domainName, domain ->
            DomainClassInfo domainClassInfo = domain.get(DataframeConstants.PARSED_DOMAIN);
            PersistentEntity queryDomain = domainClassInfo.getValue();
            def domainInstance = domainClassInfo.clazz.findById(namedParmeters.get("id")) //todo hardcoded just for now
            df.fields.getList().each{ fieldName ->
                Map fieldProps = df.fields.get(fieldName)
                String myDomainAlias = null
                if(isFieldExistInDb(fieldProps)){ //todo first make it work for persistents
                    def fldValue = domainInstance."${fieldProps.name}"
                    myDomainAlias = fieldProps.get(DataframeConstants.FIELD_PROP_DOMAIN_ALIAS)
                    Widget widget = fieldProps.get(DataframeConstants.FIELD_PROP_WIDGET_OBJECT)
                    String persistentDomainFieldName = fieldProps.get(DataframeConstants.FIELD_PROP_NAME)
                    List items = getAssociationData(domainClassInfo, fieldProps.name, fieldProps)
                    widget.setPersistedValueToResponse(jData, fldValue, myDomainAlias, persistentDomainFieldName, [items:items])
                }
            }
        }
        Map jsonRet = [:]
        addKeyDataForNamedParameters([:]) // adding key- fields for vue js
        jsonRet.put("data", jData)
        return jsonRet
    }

	private List getAssociationData(DomainClassInfo domainClassInfo, String fieldName, Map field){
		ArrayList resList = []
		if(domainClassInfo.isAssociation(fieldName) || domainClassInfo.isManyToMany(fieldName)){
			String hql = field.hql
			if(!hql) throw new MissingFieldException("this widget requires a hql statement")
			Query query = sessionHibernate.createQuery(hql);
			Transaction tx = sessionHibernate.beginTransaction()
			def results = query.list()
			String valueMember = field.valueMember?:"id"
			String displayMember = field.displayMember?:"name"
			if(results.size()>1){
				results.each{
					Map res = [:]
					res[valueMember] = it[0]
					res[displayMember] = it[1]
					resList.add(res)
				}
			}
		}
		return resList
	}

	private void populateInstanceBackUp(){

		if(!df.hql){
			return;
		}

		Query query = createSQLQuery()

		setNamedParametersFromRequestOrSession(query)

		Transaction tx = sessionHibernate.beginTransaction()
		try{

			this.results = query.list()
			tx.commit();
			if(results && results .size() > 0){
//				this.record = results[0] //TODO: its hard coded assumption we have only one record per Dataframe!
				calculateFieldValuesAsKeyValueMap();// todo merge with below method
				this.record = results.size()==1? results[0]:calculateFieldValuesAsKeyValueMapNew(results)
				log.debug(this.record.toString())
			}else{
				isDefault = true
				//throw new DataframeException(df, "No record found for the Dataframe");
			}

		}catch(Exception e){
			tx.rollback()
			throw new DataframeException(df, "Error: ${e.message}", e )		}
	}

private Query createSQLQuery() {
		Query query;
		String sqlGenerated;

		try {
			sqlGenerated = df.parsedHql.getSqlTranslatedFromHql()
			query = sessionHibernate.createQuery(df.hql);
			if (df.sql) {
				log.debug(" SQL was defined in the bean: ${df.sql}") //TODO if specified, run this thing ...
			}
			log.debug(" hql = $df.hql \n sqlGenerated = $sqlGenerated \n sql = $df.sql \n")
		} catch (Exception e) {
			throw new DataframeException(df, "Hql-Sql creation problem " + e + "\n hql = $df.hql \n sql = $df.sql\n");
		}
		return query
	}


    <------------------- DataframeInstance code ends here ------------------------->
 */

}