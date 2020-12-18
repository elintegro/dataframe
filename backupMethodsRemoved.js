class backup
{
    updateState(state, response)
    {
        if (!response) {
            return
        }

        if (typeof response === 'object' || response instanceof Map) {
            if (!response.stateName) {
                console.log("Error: statename missing")
            }
            let stateVar = state[response.stateName];
            if (!stateVar) {
                console.log("Error: state variable missing for this dataframe")
            }
            for (let i in response) {
                if (i === 'additionalData') {
                    const additionalData = response[i];
                    Object.keys(additionalData).forEach(function (key) {
                        const dafrKey = additionalData[key];
                        if (dafrKey.hasOwnProperty('data')) {
                            if (dafrKey.data.hasOwnProperty('additionalData') && dafrKey.data.additionalData.data) {
                                // Todo make recursive for handling inner additonial datas for embedded dfrs
                            } else {
                            }
                        } else {
                            if (dafrKey) {
                                const dictionary = dafrKey['dictionary'];
                                stateVar[key + '_items'] = dictionary;
                                const headers = dafrKey['headers'];
                                if (headers) {
                                    stateVar[key + '_headers'] = dafrKey['headers'];
                                } else {
                                    const selectedData = dafrKey['selectedData'];
                                    stateVar[key] = selectedData;
                                }
                            }
                        }

                    });
                } else {
                    Vue.set(stateVar, i, response[i]);
                }
            }
        } else {
            console.log("PupulateState() method only works for object or map as of now");
        }
    }

}