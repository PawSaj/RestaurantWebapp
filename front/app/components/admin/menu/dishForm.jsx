import React from 'react';
import {Button, Grid} from 'react-bootstrap';
import FieldGroup from '../../_custom/fieldGroup';
import {getIDFromPath} from '../../../lib/helpers/urlHelpers';

const setMealNewState = (state) => {
    let ingredients = state.ingredients.map(ingredient => ({name: ingredient}));
    let mealCategory = {name: state.category};
    return Object.assign({}, state, {ingredients}, {mealCategory}, {ingredientsObj: state.ingredients});
};

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
        return {
            id: null,
            name: '',
            ingredients: [],
            price: '',
            category: 'Pizza'
        };
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
        console.log(props)
        this.new = props.new;
        this.updateMeal = props.passed.adminFunctions.updateMeal;
        this.addMeal = props.passed.adminFunctions.addMeal;
        this.dish = getDish(props.passed.location.pathname, props.passed.shared.menu.data);
        this.state = setDishData(this.dish);

        this.handleChange = this.handleChange.bind(this);
        this.handleMealUpdate = this.handleMealUpdate.bind(this);
        this.handleAddMeal = this.handleAddMeal.bind(this);
    }

    handleChange(event) {
        let {id, value} = event.target, newValue = {};
        if (id === 'ingredients') {
            value = value.split(',').map((ingredient) => ingredient.trim());
        }
        newValue[id] = value;
        this.setState(newValue);
    }


    handleMealUpdate(event) {
        event.preventDefault();
        this.updateMeal(this.state.id, setMealNewState(this.state));
    }

    handleAddMeal(event) {
        event.preventDefault();
        this.addMeal(setMealNewState(this.state));
    }


    render() {
        return (
            <Grid className="edit-form">
                <FieldGroup
                    id="name"
                    type="text"
                    label="Nazwa"
                    placeholder="Wprowadź nazwę"
                    value={this.state.name ? this.state.name : ''}
                    onChange={this.handleChange}
                />
                <FieldGroup
                    id="category"
                    label="Kategoria"
                    componentClass="select"
                    defaultValue={this.state.category ? this.state.category : undefined}
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
                    value={this.state.ingredients ? this.state.ingredients.join(', ') : ''}
                    onChange={this.handleChange}
                />
                <FieldGroup
                    id="price"
                    type="text"
                    label="Cena"
                    placeholder="Wprowadź cenę"
                    value={this.state.price ? this.state.price : ''}
                    onChange={this.handleChange}
                />
                <Button bsClass="btn btn-panel"
                        onClick={(this.new === true) ? this.handleAddMeal : this.handleMealUpdate}>Zapisz</Button>
            </Grid>
        );
    }
}

export default DishForm;