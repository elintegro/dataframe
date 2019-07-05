
var drfExtCont = new Vue({
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
            drfExtCont.saveToStore("dataframeShowHideMaps", dfNameDisplay, false);
                // Vue.set(this.\$store.state.vueInitDataframe, dfNameDisplay, false);
            // }
        },

        updateToStore: function(response, dataInProp){
            if(!dataInProp){
                return
            }
            if(dataInProp.refreshGrid != undefined && dataInProp.refreshGrid == true){
                drfExtCont.saveToStore(response.dataframe,"savedResponseData", response)
            }
        }
    }

});