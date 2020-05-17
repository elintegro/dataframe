
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
                if(obj.hasOwnProperty(key)){
                    let obj1 = contaVar + "." + key;
                    let evalobj1 = eval(obj1);
                    return  evalobj1;
                } else {
                    return "";
                }
            }
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

        showAlertMessage: function(success, msg){
            if(success) {
                if(msg){
                    store.commit('alertMessage', {'snackbar':true, 'alert_type':'success', 'alert_message':msg});
                }
            }else {
                if(msg){
                    store.commit('alertMessage', {'snackbar':true, 'alert_type':'error', 'alert_message':msg});
                }
            }
        },

        showAlertMessage: function(response){
            if(response.success) {
                if(response.msg){
                    store.commit('alertMessage', {'snackbar':true, 'alert_type':'success', 'alert_message':response.msg});
                }
            }else {
                if(response.msg){
                    store.commit('alertMessage', {'snackbar':true, 'alert_type':'error', 'alert_message':response.msg})
                }
            }
        },

        closeDataframe: function(dataframeName){
            var dfNameDisplay = dataframeName +"_display";
            // if(this.\$store.state.vueInitDataframe){
            excon.saveToStore("dataframeShowHideMaps", dfNameDisplay, false);
            // Vue.set(this.\$store.state.vueInitDataframe, dfNameDisplay, false);
            // }
        },

        generateRandom: function(){
            return Math.random() * 100;
        },


        callApi: function(_params){
            return axios(_params);
        },
        formatData:function(param){
            var allParams = [];

            var survey = param.survey;
            if(!survey){
                return
            }
            console.log(survey);
            var questionCHoicesPair = this.constructCHoices(survey);
            let choiceMap = questionCHoicesPair.choiceMap;
            for(var data of questionCHoicesPair.keyset){
                console.log(data + ":" + survey[data]);
                var splits = data.split("_");
                var answerType = splits[1];
                var questionId = splits[2];
                var response = survey[data];
                if(answerType === "MultipleChoice" || answerType === "RadioList" || answerType === "DropdownCustom" || answerType === "SingleChoice"){
                    response = choiceMap[questionId];
                } else if("ListInput" === answerType){
                    response = this.createResponseFromList(response);
                }
                let question ="";
                allParams.push(this.addToResponse(questionId, question, answerType, response));
            }

            return allParams;
        },

        addToResponse: function (questionId, question, answerType, response) {
            var allParams = new Object();
            allParams.questionId = questionId;
            allParams.question = question;
            allParams.answerType = answerType;
            allParams.response = response;
            return allParams;
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

        constructCHoices: function(survey){

            var _map = {choiceMap:'', keyset:''};
            let _set = new Set();
            var choiceMap = new Object();
            for(var data in survey) {

                var splits = data.split("_");
                var answerType = splits[1];
                var questionId = splits[2];
                if(answerType === "MultipleChoice" || answerType === "RadioList" || answerType === "DropdownCustom" || answerType === "SingleChoice") {

                    var response = survey[data];
                    if(!response){
                        continue;
                    }
                    if(!response.choice){
                        continue;
                    }
                    if(answerType === "MultipleChoice"){
                        let e = "question_"+answerType +"_"+questionId;
                        _set.add(e);
                    }else {
                        _set.add(data);
                    }
                    if(answerType === "DropdownCustom"){
                        let choiceList = [];
                        for(let resp of response){
                            choiceList.push(resp.choice);
                        }
                        choiceMap[questionId] = choiceList;
                    } else if(answerType === "MultipleChoice"){

                        if(!choiceMap.hasOwnProperty(questionId)){
                            choiceMap[questionId] = [];
                        }
                        let input = '';
                        if(response.hasTextField){
                            let formatedInputVar = 'question_' + answerType + "_"+questionId+"_"+response.choiceId+"_input";
                            input = survey[formatedInputVar]?survey[formatedInputVar]:'';
                        }
                        choiceMap[questionId].push({choiceId:response.choiceId, choice:response.choice, input: input});
                    } else if(answerType === "RadioList"){
                        let input = '';
                        if(response.hasTextField){
                            input = response.input;
                        }
                        choiceMap[questionId] = {choiceId:response.choiceId, choice: response.choice, input: input}
                    }else {

                        choiceMap[questionId] = response.choice;
                    }

                }
            }
            _map.keyset = _set;
            _map.choiceMap = choiceMap;
            return _map;
        },

        updateQuestionGrid(response){
            let stateData = response;
            if(stateData){
                let rowData = {Id:stateData.questionId ,Question:stateData.question, SortOrder: stateData.sortOrder}
                let oldData = excon.getStateWithKey({key:'vueQuestionsGridDataframe', innerKey:'vueQuestionsGridDataframe_question_grid_items'});
                oldData.push(rowData);
                stateData['stateName'] = 'vueQuestionsGridDataframe';
                stateData["vueQuestionsGridDataframe_question_grid_items"] = oldData;
                store.commit("updateState", stateData);
            }
        },
        refreshDataForGrid: function(response, dataframeName, fldName, operation = "U"){

            const newData = response.newData;
            if(!dataframeName && !fldName && !newData) return;
            let state = store.getters.getState(dataframeName);
            const items = state[fldName +'_items'];
            const selectedRow = state[fldName +'_selectedrow'];
            const editedIndex = items.indexOf(selectedRow);
            let row = {};
            for(let key in newData){
                let dataMap = newData[key];
                for(let j in dataMap){
                    if(selectedRow && Object.keys(selectedRow).length !== 0){
                        if (j in selectedRow) {
                            row[j] = dataMap[j];
                        }
                    } else {
                        row[j] = dataMap[j];
                    }

                }
            }
            state['stateName'] = dataframeName;
            if (operation==="I") {
                state[fldName +'_items'].push(row);
                store.commit('updateState', state)
            } else {
                Object.assign(state[fldName +'_items'][editedIndex], row);
                store.commit('updateState', state)
            }
//                          this.gridDataframes[refreshParams.dataframe] = false;
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
        }
    }
    });