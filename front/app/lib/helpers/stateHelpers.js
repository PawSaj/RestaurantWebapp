export const stateAfterDelete = (data, action) => {
    let elementIndex = null;
    data.map((element, index) => {
        if (element.id === action.id) {
            elementIndex = index;
        }
    });
    return data.slice(0, elementIndex).concat(data.slice(elementIndex + 1, data.lenght));
};

export const stateAfterUpdate = (data, action) => {
    return data.map((element) => {
        if (element.id === action.id) {
            return action.data
        }
        return element
    });
};