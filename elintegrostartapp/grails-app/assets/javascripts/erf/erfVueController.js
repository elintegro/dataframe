
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
        saveToStore: function(containerVariable, key, value=''){
            if((containerVariable == null || containerVariable == undefined || containerVariable == "") && (key == null || key == undefined || key == "")){
                return
            }
            if(store.state.hasOwnProperty(containerVariable)){
                const obj = eval("store.state."+ containerVariable +"");
                if(obj.hasOwnProperty(key)){
                    Vue.set(obj, key, value);
                } else {
                    value?Vue.set(obj,key, value):Vue.set(store.state, containerVariable, key);
                }
            } else {
                Vue.set(store.state, containerVariable, key);
            }
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
        saveToState: function(containerVariable, value=''){
            if((containerVariable == null || containerVariable == undefined || containerVariable == "") && (key == null || key == undefined || key == "")){
                return
            }
            if(store.state.hasOwnProperty(containerVariable)){
                const obj = eval("store.state."+ containerVariable +"");
                if(obj.hasOwnProperty(key)){
                    // store.commit(key,value);
                    Vue.set(obj, key, value);
                    /*var obj1 = eval(""+obj+"."+key)
                    obj1 = value;*/
                } else {
                    // const Obj1 = eval(store.state +"."+ containerVariable);
                    Vue.set(obj,key, value);
                }
            } else {
                Vue.set(store.state, containerVariable, key);
            }

        },
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

        /*
                updateStoreState: function(response, stateVar, propKey){

                    var dataframe = response.dataframe;
                    let stateVarDf = stateVar+"."+dataframe;
                    var response = response.data
                    let id = response.keys["id"]?response.keys["id"]:'';
                    let stateVarObj1 = eval(stateVarDf);

                    if(stateVarObj1){
                        Vue.set(eval(' stateVarObj1'), 'key', id);
                    }
                    if(response.hasOwnProperty('additionalData') ) {
                        Object.keys(response.additionalData).forEach(function (key) {
                            var embDfr = response.additionalData[key];
                            if (embDfr.hasOwnProperty('data')){
                                if (embDfr.data.hasOwnProperty('additionalData') && embDfr.data.additionalData.data) {
                                    this.updateStoreState(embDfr, stateVar)
                                } else {
                                    dataframe = embDfr.dataframe;
                                    if(dataframe){

                                        let stateVarDf =stateVar + "." + dataframe;
                                        if(embDfr.data.hasOwnProperty('keys')){
                                            let id = embDfr.data.keys["id"];
                                            let stateVarObj2 = eval(stateVarDf);
                                            if(stateVarObj2){
                                                Vue.set(eval('stateVarObj2'), 'key', id);
                                                let propKey1 = propKey +"." +dataframe + "_data";
                                                Vue.set(eval(propKey1), 'key', id);
                                            }
                                        }

                                    }
                                }
                            }

                        });
                    }
                },

        */
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
            var dfNameDisplay = dataframeName +"_display";
            excon.saveToStore("dataframeShowHideMaps", dfNameDisplay, false);
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
        setVisibility: function(dataframeName, setVisible){
            if(setVisible){
                store.commit('setVisibility', dataframeName);
            } else {
                store.commit('unsetVisibility', dataframeName);
            }
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
         excon.setValuesForRequestParams({'setValueTo': 'vueElintegroUserProfileDataframe',
                                                            'type': 'persisters',
                                                            'domainAlias':'person',
                                                            'getValueFrom': 'vueNewEmployeeBasicInformationDataframe',
                                                            'fieldName':'application',
                                                            'key': 'id'});
         * @param object
         */
        setValuesForRequestParams: function(object){
            if(!object.setValueTo) throw "setValueTo key must have dataframeName in params"
            if(!object.getValueFrom) throw "getValueFrom key must have dataframeName in params"
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
        _setValuesToRequestParams: function(object){
            const getValueFrom = this.getFromStore(object.getValueFrom)
            const val = getValueFrom.fieldName[key];
            let setValueTo = this.getFromStore(object.setValueTo)
            setValueTo[object.type][object.domainAlias][object.fieldName].value = val;
            this.saveToStore(object.setValueTo, setValueTo);
        },
        setValuesForNamedParamsFromGrid: function(object){
            if(object instanceof Array){
                for(let obj in object){
                    obj['grid'] = true;
                    this._setValuesForNamedParams(obj)
                }
            } else if (object instanceof Object){
                object['grid'] = true;
                this._setValuesForNamedParams(object)
            } else {
                throw "Only Objects allowed"
            }
        },
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
        _setValuesForNamedParams: function(object){
            if(!object.namedParamKey) throw "namedParamKey is missing in params"
            if(!object.setValueTo) throw "setValueTo key must have dataframeName in params"
            this._getValuesForParams(object)
            let setValueTo = this.getFromStore(object.setValueTo)
            setValueTo[object.namedParamKey] = this._getValuesForParams(object);
            this.saveToStore(object.setValueTo, setValueTo);
        },
        _getValuesForParams: function(object){
            if(!object.getValueFrom) throw "getValueFrom key must have dataframeName in params"
            if(!object.fieldName) throw "fieldName is required in params"

            const getValueFrom = this.getFromStore(object.getValueFrom)
            let value = '';
            if(object.grid){// for children of grid
                const gridRow = getValueFrom[object.fieldName].selectedRow;
                value = object.key?gridRow[object.key]:gridRow;
            } else {
                value = this._getValueFromDomainKeys(getValueFrom, object.fieldName, object.key);
            }
            return value
        },
        //Assumption that there will always be id in domain_keys
        _getValueFromDomainKeys: function(getValueFrom, fieldName, key){
            if(!getValueFrom.domain_keys) return '';
            let val = getValueFrom.domain_keys[fieldName];
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
            if(routeId == null){
                dataFrame.$router.push('/'+pageToRedirect+'/0');
            }
            else {
                dataFrame.$router.push('/'+pageToRedirect+'/'+routeId);
            }
        },
        refreshPage: function(){
            window.location.reload;
        }
    }

});