import React from 'react';
import CustomTable from '../../_custom/table';
import CustomTabs from '../../_custom/tabs';
import {Grid} from 'react-bootstrap';
import NewDish from './dishForm';

class Menu extends React.Component {
    constructor(props) {
        super(props);
        this.passed = props.passed;
        this.getMenu = props.passed.sharedFunctions.getMenu;
        this.deleteMeal = props.passed.adminFunctions.deleteMeal;
        this.state = {
            menu: props.passed.shared.menu.data
        };

        this.prepareMenu = this.prepareMenu.bind(this);
    }

    prepareMenu(menu) {
        return menu.map((element, index) => {
            let preparedElement = Object.assign({}, element, {
                order: ['id', 'name', 'ingredients', 'price'],
                headsTitles: ['Id', 'Nazwa', 'Składniki', 'Cena'],
                modify: true,
                deleteFunction: this.deleteMeal
            });
            return <CustomTable key={index} {...Object.assign({}, preparedElement, this.passed)}/>
        })
    };


    componentWillReceiveProps(nextProps) {
        this.setState({menu: nextProps.passed.shared.menu.data});
    }

    componentDidMount() {
        if (this.state.menu === undefined) {
            this.getMenu();
        }
    }

    render() {
        let tabsProps = {
            id: "menu-tab",
            tabs: [
                {
                    title: 'Edycja dań',
                    content: this.state.menu && this.prepareMenu(this.state.menu)
                }, {
                    title: 'Dodawanie dań',
                    content: <NewDish passed={this.passed} new={true}/>
                }
            ],
        };

        return (
            <Grid id="menu-tab">
                <CustomTabs {...tabsProps}/>
            </Grid>);
    }
}

export default Menu;