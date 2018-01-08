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

export const stateAfterCreate = (data, action) => {
    if (data === undefined) {
        return [action.data];
    }

    data.push(action.data);

    return data;
};

export const stateAfterMenuDelete = (data, action) => {

    return data.map((category) => {
        return {
            category: category.category,
            body: category.body.map((element) => {
                if (!element)
                    return null;

                if (element.id === action.id) {
                    return null;
                }
                return element;
            })
        }
    });
};

export const stateAfterMenuAdd = (data, action) => {
    let categoryExists = false;
    let newData = {
        id: action.data.id,
        ingredients: action.data.ingredientsObj,
        price: action.data.price,
        name: action.data.name
    };

    let menu = data.map((category) => {
        if (category.category === action.data.category) {
            categoryExists = true;
            category.body.push(newData);
            return category
        }
        return category;
    });

    if (categoryExists) {
        return menu;
    }

    data.push({
        category: action.data.category,
        body: [newData]
    });

    return data;

};


export const stateAfterMealGetByID = (action) => {
    let newData = {
        id: action.data.id,
        ingredients: action.data.ingredients,
        price: action.data.price,
        name: action.data.name
    };

    return [
        {
            category: action.data.category,
            body: [newData]
        }
    ]
};

export const stateAfterMenuUpdate = (data, action) => {
    let newData = {
        id: action.data.id,
        ingredients: action.data.ingredientsObj,
        price: action.data.price,
        name: action.data.name
    };

    return data.map((category) => {
        if (category.category === action.data.category) {

            return {
                category: action.data.category,
                body: category.body.map((element) => {
                    if (!element)
                        return null;
                    if (element.id === action.data.id) {
                        return newData;
                    }
                    return element;
                })
            }
        }
        return category
    });
};
