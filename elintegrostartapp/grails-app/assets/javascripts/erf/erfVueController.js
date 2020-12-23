var excon = new Vue({
    el: '#dfr',
    methods: {
        mapStringify:function(j, valueMember){
            var json="";
            if(j == null || j == "" || j == undefined){
                return json;
            }
            var dq='"';
            var last=Object.keys(j).length;
            var count=0;
            j.forEach(function(data){
                var val = data[valueMember];
                json+="{";
                json += dq + valueMember + dq+":" + dq + val + dq;
                count++;
                json+="}";
                if(count<last){
                    json +=",";
                }
            });
            return json;
        },
        capitalize: function(s) {
            if (typeof s !== 'string') return '';
            return s.charAt(0).toUpperCase() + s.slice(1);
        },
        goToTab: function(containerDataframe, targetDataframe) {
            //key_<dfname>_<domain>_id_id
            excon.saveToStore(containerDataframe, containerDataframe + "_tab_model", targetDataframe + "-tab-id");
        },


        /**
         *
         * @param dataframeComponent
         * @param domain
         * @param field
         */
        getStateDataframeFieldValue: function(dataframeComponent, domain, field){
            var ret = null
            try {
                ret = dataframeComponent.state.persisters[domain][field]['value'];
            } catch (e) {
                log.console("Problem to retrieve variable " + domain + "." + field)
                //TODO: send here error to the backend!
            }finally{
                return ret;
            }

        },
        saveToStateTest: function(containerVariable, key, value=''){
            if((containerVariable == null || containerVariable == undefined || containerVariable == "") && (key == null || key == undefined || key == "")){
                return
            }
            const obj = eval("store.state."+ containerVariable +"");
            if (obj){
                Vue.set(obj, key, value);
            }
        },
/*        saveToState: function(containerVariable, value=''){
            if((containerVariable == null || containerVariable == undefined || containerVariable == "") && (key == null || key == undefined || key == "")){
                return
            }
            if(store.state.hasOwnProperty(containerVariable)){
                const obj = eval("store.state."+ containerVariable +"");
                if(obj.hasOwnProperty(key)){
                    // store.commit(key,value);
                    Vue.set(obj, key, value);
                    /!*var obj1 = eval(""+obj+"."+key)
                    obj1 = value;*!/
                } else {
                    // const Obj1 = eval(store.state +"."+ containerVariable);
                    Vue.set(obj,key, value);
                }
            } else {
                Vue.set(store.state, containerVariable, key);
            }

        }*/
        /**
         *saves data to store
         excon.saveToStore("vueNewEmployeeBasicInformationDataframe",state)
         excon.saveToStore("vueNewEmployeeBasicInformationDataframe","state",response.data.data)
         excon.saveToStore("vueNewEmployeeApplicantDataframe", "vueNewEmployeeApplicantDataframe_tab_model", "vueNewEmployeeUploadResumeDataframe-tab-id");
         * @param containerVariable: main key of store (dataframeName usually)
         * @param key
         * @param value
         */
        saveToStore: function(containerVariable, key, value){
            if((containerVariable == null || containerVariable == undefined || containerVariable == "") && (key == null || key == undefined || key == "")){
                return
            }
            if(store.state.hasOwnProperty(containerVariable)){
                const obj = eval("store.state."+ containerVariable +"");
                // if(obj.hasOwnProperty(key)){
                if(arguments.length === 2){
                    Vue.set(store.state, containerVariable, key);
                    return
                }
                Vue.set(obj, key, value);
                // }
                /*
                                else {
                                    (value === null || value === undefined || value === '')?Vue.set(store.state, containerVariable, key):Vue.set(obj,key, value);
                                }
                */
            } else {
                Vue.set(store.state, containerVariable, key);
            }
        },

        /**
         *
         params['id']= excon.getFromStore('vueNewEmployeeBasicInformationDataframe','domain_keys.application.id');
         * @param containerVariable: dataframeName mainly of store
         * @param key
         * @returns {string|any}
         */
        getFromStore: function(containerVariable, key=''){
            if((containerVariable == null || containerVariable == undefined || containerVariable == "")){
                return ""
            }

            if(store.state.hasOwnProperty(containerVariable)){
                let contaVar = "store.state."+ containerVariable +""
                const obj = eval(contaVar);
                if(key == undefined || key == ''){
                    return obj
                }
                return this.getValuePropertyNested(obj, key);
            }
        },

        /**
         * Looking for the value for a path in the JSON object and returns its value
         * @param obj JSON Object
         * @param key is the path in a JSON object, example: 'persisters.application.linkedIn.value'
         * @returns {*} value of the key entry of the object, null if no path found
         * throws "No object for the path" error if no 'key' path exist in the obj
         */
        getValuePropertyNested: function(obj, key){
            var keyWords = key.split(".");
            if(keyWords && keyWords.length > 0){
                let i;
                let curr = obj;
                for (i = 0; i < keyWords.length; i++) {
                    var keyElem = keyWords[i];
                    if(curr.hasOwnProperty(keyElem)){
                        curr = curr[keyElem];
                    }else{
                        throw "No object for the path " + key + " at element " + keyElem;
                    }
                }
                return curr;
            }
            throw "No object for the path " + key;
        },

                matchKeysFromDataframeTo: function(fromDataframe, toDataframe) {

                    var sourceDataframeVars = this.getFromStore(fromDataframe);
                    var targetDataframeVars = this.getFromStore(toDataframe);

                    for(let varName in targetDataframeVars) {
                        let keyPrefix = "key_" + toDataframe;
                        let indStart = varName.indexOf("key_" + toDataframe);
                        if(indStart >= 0) {//The variable is a key
                            let indEnd = varName.lastIndexOf("_");
                            let varToSearch = "key" + varName.substring(keyPrefix.length, indEnd);
                            //Find keys in sourceDataframe and populate in the target with naming convention whatever in target is key_<targetDataframeName>_<domainName>_<fieldName>_<namingParameter>
                            //Should be keu_<domainName>_<fieldName>, for example In target: key_<TargetDataframe>_application_id_id, in source: key_application_id
                            for(let varFromName in sourceDataframeVars){
                                if(varFromName === varToSearch){ //this is our key variable, grab the value and set for the key variable in the target Dataframe
                                    this.saveToStore(toDataframe, varName, sourceDataframeVars[varFromName]);
                                    break;
                                }
                            }
                        }
                    }

                },

        showAlertMessage: function(response, dataframeName="vueAlertMsgDataframe"){
            const msg = response.message?response.message:response.msg
            if(msg){
                if(response.success) {
                    this._setAlertMessage(msg,'success', dataframeName);
                }else {
                    if (response.alert_type === 'info') {
                        this._setAlertMessage(msg, 'info', dataframeName)
                    } else {
                        this._setAlertMessage(msg, 'error', dataframeName);
                    }
                }
            }
        },

        _setAlertMessage: function(msg, type="success", dataframeName){
            this.saveToStore(dataframeName,'alertProp', {'snackbar':true, 'alert_type':type, 'alert_message':msg})
        },

        //todo deprecate this method and use notify() method instead
        showMessage: function(responseData,dataframeName){
            let response = responseData.data;
            let stateData = store.getters.getState(dataframeName);
            let alertProps = stateData.alertProp;
            alertProps['snackbar'] = true;
            alertProps['alert_type'] = response.alert_type;
            alertProps['alert_message'] = response.msg;
            Vue.set(stateData.alertProp, alertProps);
        },

        notify: function(responseData,dataframeName){
            let response = responseData.data;
            let stateData = store.getters.getState(dataframeName);
            let alertProps = stateData.alertProp;
            alertProps['snackbar'] = true;
            alertProps['alert_type'] = response.alert_type;
            alertProps['alert_message'] = response.msg;
            Vue.set(stateData.alertProp, alertProps);
        },
        closeDataframe: function(dataframeName){
            this.unsetVisibility(dataframeName)
        },

        generateRandom: function(){
            return Math.random() * 100;
        },

        updateState: function(response){
            store.commit("updateData", response)
        },
        /**
         * Call this method for refreshing data of one dataframe from another dataframe.
         * params = this.state;
         * params should contain dataframe name.
         * @param url
         * @param doBeforeRefresh
         * @param doAfterRefresh
         * @param params
         */

        fillInitialData : function (params, url = 'dataframe/ajaxValues', doBeforeRefresh=()=>{}, doAfterRefresh=()=>{}){
            if(!params) return;
            if(!params.dataframe) throw new Error("Dataframe name missing from params.");

            params["url"] =  url;
            params["doBeforeRefresh"] = doBeforeRefresh;
            params["doAfterRefresh"] = doAfterRefresh;
            this.refreshData(params);
        },
        refreshData : function(params){
            store.dispatch("refreshData", params);
        },
        saveData : function(params){
            store.dispatch("saveData", params);
        },
        callApi: function(url, method, params){
            method = method || 'post';
            return axios({
                method:method,
                url: url,
                data: params
            })
        },

        /**
         * Use this for passing Get Query params (not as JSON)
         * @param url
         * @param method
         * @param params
         * @returns {*}
         */
        callApiWithQuery: function(url, method="GET", params={}){
            return axios({
                method:method,
                url: url,
                params: params
            })
        },
        addToResponse: function (questionId, question, answerType, response) {
            var params = new Object();
            params.questionId = questionId;
            params.question = question;
            params.answerType = answerType;
            params.response = response;
            return params;
        },

        createResponseFromList: function (response) {
            var re = [];
            var res = response.split(",");
            for(var ix of res){
                if(ix){
                    re.push(ix);
                }
            }
            return re;
        },

        refreshDataForGrid: function(response, dataframeName, fldName, operation = "U", type="persisters"){

            const newData = response['persisters'][fldName];//hard guess that it will always be persisters
            if(!dataframeName && !fldName && !newData) return;
            let state = store.getters.getState(dataframeName);
            const items = state[type][fldName]['items'];
            const headers = state[type][fldName]['headers']
            let row = {};
            for(const [index, headerValue] of Object.entries(headers)){
                let key = headerValue.keys;
                let value = headerValue.value
                let isInNewData = newData[key]
                if (isInNewData){
                    if ((operation==="I") && (key==="id")){
                        row[value] = isInNewData?isInNewData.value:response['domain_keys'][fldName]["id"] //in case of insert id is not in persister so getting from domain_keys todo://change it
                    }else {
                        row[value] = isInNewData.value
                    }
                }
            }

            state['stateName'] = dataframeName;
            if (operation==="I") {
                state[type][fldName]['items'].push(row);
                store.commit('updateData', state)
            } else {
                const selectedRow = state[fldName +'_selectedrow'];
                const editedIndex = items.indexOf(selectedRow);
                Object.assign(state[type][fldName]['items'][editedIndex], row);
                store.commit('updateData', state)
            }
        },



        grabVariableFromParentdataframe: function(fromDataframe, fromVariable, toDataframe, toVariable) {
            //key_<dfname>_<domain>_id_id

            var sourceDataframeVars = this.getFromStore(fromDataframe);
            this.saveToStore(toDataframe, toVariable, sourceDataframeVars[varFromName]);
        },

        getStateWithKey(key) {
            if (typeof key === 'object') {
                const obj = key;
                const parentData = store.getters.getState(obj.key);
                let result = parentData;
                if (parentData && obj.innerKey) {
                    result = parentData[obj.innerKey];
                }
                return result;
            } else if (typeof key === 'string') {
                return store.getters.getState(key);
            } else {
                throw("Cannot get state for key: " + key);
            }
        },
        setVisibility: function(dataframeName, setVisible = true){
            if(setVisible){
                store.commit('setVisibility', dataframeName);
            } else {
                store.commit('unsetVisibility', dataframeName);
            }
        },
        unsetVisibility: function(dataframeName){
            store.commit('unsetVisibility', dataframeName);
        },
        reset: function(dataframeName){

            let oldData = store.getters.getState(dataframeName);
        },
        setSelectedGridDataToRequestParams: function (selectedDataRecord, dataframeName){
            let namedParams = this.getFromStore(dataframeName, "namedParameters")
            if (!namedParams)
                return
            for(const [key, namedValue] of Object.entries(namedParams)) {
                let namedParamVal = selectedDataRecord['Id'] || selectedDataRecord[key]
                if (namedParamVal){
                    namedValue.value = namedParamVal;
                }
            }
        },
        /**
         *
         usage: excon.setValuesForRequestParams({'targetDataframe': 'vueElintegroUserProfileDataframe',
                                                            'type': 'persisters',
                                                            'domainAlias':'person',
                                                            'sourceDataframe': 'vueNewEmployeeBasicInformationDataframe',
                                                            'fieldName':'application',
                                                            'key': 'id'});
         * @param object
         */
        setValuesForRequestParams: function(object){
            if(!object.targetDataframe) throw "targetDataframe key must have dataframeName in params"
            if(!object.sourceDataframe) throw "sourceDataframe key must have dataframeName in params"
            if(!object.domainAlias) throw "domainAlias is required"
            let type = object.type?object.type:'persisters';// 'persisters' or 'transits'
            object.type = type;
            if(object instanceof Array){
                for(let obj in object){
                    this._setValuesToRequestParams(obj)
                }
            } else if (object instanceof Object){
                this._setValuesToRequestParams(object)
            } else {
                throw "Only Objects allowed"
            }
        },
        /**
         *
         usage: excon.setValuesForNamedParamsForGrid({'targetDataframe': 'vueElintegroUserProfileDataframe',
                                                            'namedParamKey':'person',
                                                            'sourceDataframe': 'vueNewEmployeeBasicInformationDataframe',
                                                            'fieldName':'application',
                                                            'key': 'id'});
         * @param object
         */
        setValuesForNamedParamsFromGrid: function(object){
            if(object instanceof Array){
                for(let obj in object){
                    object[obj]['grid'] = true;
                    this._setValuesForNamedParams(object[obj])
                }
            } else if (object instanceof Object){
                object['grid'] = true;
                this._setValuesForNamedParams(object)
            } else {
                throw "Only Objects allowed"
            }
        },
        /**
         *
         usage: excon.setValuesForNamedParams({'targetDataframe': 'vueElintegroUserProfileDataframe',
                                                            'namedParamKey':'person',
                                                            'sourceDataframe': 'vueNewEmployeeBasicInformationDataframe',
                                                            'fieldName':'application',
                                                            'key': 'id'});
         * @param object
         */
        setValuesForNamedParams: function(object){
            if(object instanceof Array){
                for(let obj in object){
                    this._setValuesForNamedParams(obj)
                }
            } else if (object instanceof Object){
                this._setValuesForNamedParams(object)
            } else {
                throw "Only Objects allowed"
            }
        },
        _setValuesToRequestParams: function(object){
            const sourceDataframe = this.getFromStore(object.sourceDataframe)
            const val = sourceDataframe.fieldName[key];
            let targetDataframe = this.getFromStore(object.targetDataframe)
            targetDataframe[object.type][object.domainAlias][object.fieldName].value = val;
            this.saveToStore(object.targetDataframe, targetDataframe);
        },
        _setValuesForNamedParams: function(object){
            if(!object.namedParamKey) throw "namedParamKey is missing in params"
            if(!object.targetDataframe) throw "targetDataframe key must have dataframeName in params"
            this._getValuesForParams(object)
            let targetDataframe = this.getFromStore(object.targetDataframe)

            if(!targetDataframe) throw new Error(object.targetDataframe + "doesnot exist. might be a type")

            targetDataframe[object.namedParamKey] = this._getValuesForParams(object);
            this.saveToStore(object.targetDataframe, targetDataframe);
        },
        _getValuesForParams: function(object){
            if(!object.sourceDataframe) throw "sourceDataframe key must have dataframeName in params"
            if(!object.fieldName) throw "fieldName is required in params"

            const sourceDataframe = this.getFromStore(object.sourceDataframe)

            if(!sourceDataframe) throw new Error(object.sourceDataframe + "doesnot exist. might be a type")

            let value = '';
            if(object.grid){// for children of grid
                const gridRow = sourceDataframe[object.fieldName]
                if(!gridRow) throw new Error(gridRow + "doesnot exist. might be a type")
                const selectedRow = gridRow.selectedRow;
                value = object.key?selectedRow[object.key]:selectedRow;
            } else {
                value = this._getValueFromDomainKeys(sourceDataframe, object.fieldName, object.key);
            }
            return value
        },
        //Assumption that there will always be id in domain_keys
        _getValueFromDomainKeys: function(sourceDataframe, fieldName, key){
            if(!sourceDataframe.domain_keys) return '';
            let val = sourceDataframe.domain_keys[fieldName];
            if(!val) throw new Error(fieldName + "doesnot exist. might be a type")
            if(key){
               val = val[key];
            }
            return val
        },
        enableDisableButton:function (dataframeName , valueToObserve){
            let state = store.getters.getState(dataframeName);
            let dataToChange;
            if(valueToObserve == null && valueToObserve == undefined){
                dataToChange = true
            }
            else if (valueToObserve && valueToObserve.length == 0){
                dataToChange = true
            }
            else {
                dataToChange = false
            }
            return dataToChange
        },
        redirectPage:function (dataFrame,pageToRedirect,routeId){
            if(routeId == null || routeId === 0){
                dataFrame.$router.push('/'+pageToRedirect+'/0');
            }
            else {
                dataFrame.$router.push('/'+pageToRedirect+'/'+routeId);
            }
        },
        refreshPage: function(){
            window.location.reload();
        },
        getImageDataInfo:function (file){
            let imageData = {};
            if(file){
                imageData["imageName"] = file.name;
                imageData["imageSize"] = file.size;
                imageData["imageType"] = file.type;
            }
            return imageData
        },

        /**
         * removes object from list
         * @param list
         * @param object
         */
        removeFromList: function(list, object){
            list.splice(object, 1);
        }
    }
});