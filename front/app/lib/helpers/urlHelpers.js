const getQueries = (search) => {
    let param = search.substring(1);
    let queriesArray = param.split('&');
    let queryObject = {};

    queriesArray.map((query) => {
        let singleQuery = query.split('=');
        queryObject[singleQuery[0]] = singleQuery[1];
    });

    return queryObject;
};

export const getParam = ({search, name}) => {
    let queries = getQueries(search);

    if (queries.hasOwnProperty(name)) {
        return queries[name];
    }

    return '';
};

export const parseDataToURI = (data) => {
    let encodedData = "";
    for (let key in data) {
        if (data.hasOwnProperty(key)) {
            encodedData += encodeURIComponent(key) + "=" + encodeURIComponent(data[key]) + "&";
        }
    }
    return encodedData;
};

export const getIDFromPath = (path) => {
    let array = path.split('/');
    return parseInt(array[2]);
};