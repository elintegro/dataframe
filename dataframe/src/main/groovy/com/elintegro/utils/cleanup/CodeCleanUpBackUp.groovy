package com.elintegro.utils.cleanup



class CodeCleanUpBackUp {

    /*
    <--------------------------DataframeVue code start here --------------------------->
    	public String getJsonDataFillScriptbackup(df){
		String dataframeName = df.dataframeName
		StringBuilder paramsSb = new StringBuilder()
		String namedParamKey = mainNamedParamKey?:"id"
		if(route){
			paramsSb.append("params['$namedParamKey'] = this.\$route.params.routeId?this.\$route.params.routeId:1;")
		}else{
			paramsSb.append("""params["$namedParamKey"] = eval(this.namedParamKey);\n""")
		}
		if(!ajaxUrlParams.isEmpty()){
			for(Map.Entry entry: ajaxUrlParams){
				paramsSb.append("params['$entry.key'] = '$entry.value';\n")
			}
		}
		String updateStoreScriptcaller = ""
		if(createStore){
//			updateStoreScriptcaller = """ const stateVar = "${dataframeName}Var.\$store.state";\n excon.updateStoreState(resData, stateVar,${dataframeName}Var);"""
		}
		return """
             ${dataframeName}_fillInitData: function(){
                excon.saveToStore('$dataframeName','doRefresh',false);
                let params = this.state;\n
                const propData = this.${dataframeName}_prop;
                 if(propData){
                    params = propData;
                    if(this.namedParamKey == '' || this.namedParamKey == undefined){
                        this.namedParamKey = "this.${dataframeName}_prop.key?this.${dataframeName}_prop.key:this.\$store.state.${dataframeName}.key";
                    }
                 }
                ${paramsSb.toString()}
                params['dataframe'] = '$dataframeName';
                $doBeforeRefresh
                this.overlay_dataframe = true;
                let self = this;
				axios({
                          method:'post',
                          url:'$df.ajaxUrl',
                          data: params
                      }).then(function (responseData) {
                        let resData = responseData.data;
                        let response = resData?resData.data:'';
                       if(response != null && response != '' && response  != undefined){
//                           response["stateName"] = "$dataframeName";
//                           ${dataframeName}Var.updateState(response);
//                           ${dataframeName}Var.${dataframeName}_populateJSONData(response);
							 excon.saveToStore("${dataframeName}", "persisters", response.persisters);
                        }
                        $doAfterRefresh
                   self.overlay_dataframe = false;
                  ${updateStoreScriptcaller}
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
             },\n
              """
	}

	public String getSaveDataScriptbackup(df, vueSaveVariables, vueFileSaveVariables){
		String dataframeName = df.dataframeName
		StringBuilder embdSaveParms = new StringBuilder("")
		if(embeddedDataframes.size()>0){
			embeddedDataframes.each{
				if(it.trim() != ""){
					embdSaveParms.append("""if(this.\$refs.hasOwnProperty("${it}_ref") && this.\$refs.${it}_ref){for(var a in this.\$refs.${it}_ref.\$data){\n
                                              var dashA = a.split('_').join('-');
                                              params[dashA] = this.\$refs.${it}_ref.\$data[a];\n}}\n""")
				}
			}
		}
		 TODO: remove it after tests
		String addKeyToVueStore
		if(!putFillInitDataMethod){
			addKeyToVueStore = """var nodeArr = response.nodeId; if(nodeArr && Array.isArray(nodeArr) && nodeArr.length){excon.saveToStore("$dataframeName", "key", response.nodeId[0]);}\n"""
		}
		doAfterSaveStringBuilder.append("""
                    var ajaxFileSave = ${dataframeName}Var.params.ajaxFileSave;
                    if(ajaxFileSave){
                       for(let i in ajaxFileSave) {
                         const value = ajaxFileSave[i];
                         $vueFileSaveVariables
  						 self[value.fieldName+'_ajaxFileSave'](responseData, params);
					   }
                    }
                  $addKeyToVueStore
				""")
				TODO: remove it after tests
    return """
${dataframeName}_save: function(){
                  let params = this.state;
                  $vueSaveVariables
${embdSaveParms?.toString()}
${doBeforeSave}
                  params['dataframe'] = '$dataframeName';
                  console.log(params)
                  if (this.\$refs.${dataframeName}_form.validate()) {
                      this.${dataframeName}_save_loading = true;
                      const self = this;
                      axios({
                          method:'post',
                          url:'$df.ajaxSaveUrl',
                          data: params
                      }).then(function (responseData) {
                        self.${dataframeName}_save_loading = false;
                        var response = responseData.data;
                        //TODO: add here assignment of response object to the proper vue structure
                        excon.saveToStore("${dataframeName}", "domain_keys", responseData.data.data.domain_keys);
                        excon.showAlertMessage(response);
			            	if(response.success){
                               ${doAfterSave}
                        	}
                      }).catch(function (error) {
                              self.${dataframeName}_save_loading = false;
                              console.log(error);
                      });
                  }

               },\n"""
}


<--------------------------DataframeVue code end here --------------------------->
     */

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

    /*
    <------------------- GridWidgetVue code start here ------------------------->
 private static String getGridValuesScript(String parentDataframeName, String fldName
                                              ,StringBuilder fieldParams, DataframeVue refDataframe){
        String refDataframeName = refDataframe.dataframeName
        return """
                         ${parentDataframeName}Var.${fldName}_selectedrow = dataRecord;
                   var params = {'dataframe':'$refDataframeName'};
                   $fieldParams
                   axios.get('$refDataframe.ajaxUrl', {
                    params: params
                }).then(function (responseData) {
                        var response = responseData.data.data;
                        console.log(response);
                        excon.setVisibility(${refDataframeName}, true);
                        var refParams = ${parentDataframeName}Var.\$refs.${refDataframeName}_ref.params;
                        var gridRefreshParams = {};
                        gridRefreshParams['isGridRefresh'] = true;
                        gridRefreshParams['fieldName'] = '$fldName';
                        gridRefreshParams['parentDataframe'] = '$parentDataframeName';
                        gridRefreshParams['dataframe'] = '$refDataframeName';
                        refParams['gridRefreshParams'] = gridRefreshParams;

                        ${refDataframe.doAfterRefresh}
                    })
                    .catch(function (error) {
                        console.log(error);
                    });

"""

    }

    <------------------- GridWidgetVue code end here ------------------------->
     */
}