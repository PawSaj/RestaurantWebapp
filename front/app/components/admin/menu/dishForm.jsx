import React from 'react';
import {Button, Grid} from 'react-bootstrap';
import FieldGroup from '../../_custom/fieldGroup';
import {getIDFromPath} from '../../../lib/helpers/urlHelpers';

const getDish = (path, menu) => {
    if (menu === undefined) {
        return null;
    }
    let dishID = getIDFromPath(path);
    for (let category of menu) {
        for (let dish of category.body) {
            if (dish.id === dishID) {
                return Object.assign({}, dish, {category: category.category});
            }
        }
    }
    return null;
};

const setDishData = (dish) => {
    if (dish === null) {
        return {};
    }

    return {
        id: (dish.id) ? dish.id : null,
        name: (dish.name) ? dish.name : '',
        ingredients: (dish.ingredients) ? dish.ingredients : [],
        price: (dish.price) ? dish.price : '',
        category: (dish.category) ? dish.category : 'Pizza'
    }
};

class DishForm extends React.Component {
    constructor(props) {
        super(props);
        this.dish = (props.addingNew) ? null : getDish(props.passed.location.pathname, props.passed.shared.menu.data);
        this.state = setDishData(this.dish);

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        let {id, value} = event.target, newValue = {};
        if (id === 'ingredients') {
            value = value.split(',').map((ingredient) => ingredient.trim());
        }
        newValue[id] = value;
        this.setState(newValue);
    }

    render() {
        return (
            <Grid className="edit-form">
                <FieldGroup
                    id="name"
                    type="text"
                    label="Nazwa"
                    placeholder="Wprowadź nazwę"
                    value={this.dish ? this.state.name : ''}
                    onChange={this.handleChange}
                />
                <FieldGroup
                    id="category"
                    label="Kategoria"
                    componentClass="select"
                    defaultValue={this.dish ? this.state.category : undefined}
                    onChange={this.handleChange}
                >
                    <option value="pizza">Pizza</option>
                    <option value="soup">Zupa</option>
                </FieldGroup>
                <FieldGroup
                    id="ingredients"
                    label="Składniki"
                    componentClass="textarea"
                    placeholder="Uzupełnij składniki"
                    value={this.dish ? this.state.ingredients.join(', ') : ''}
                    onChange={this.handleChange}
                />
                <FieldGroup
                    id="price"
                    type="text"
                    label="Cena"
                    placeholder="Wprowadź cenę"
                    value={this.dish ? this.state.price : ''}
                    onChange={this.handleChange}
                />
                <Button bsClass="btn btn-panel">Zapisz</Button>
            </Grid>
        );
    }
}

export default DishForm;