import React from 'react';
import CustomTable from '../_custom/table';
import MenuData from '../../_consts/mocks/menu';
import {Grid, Button} from 'react-bootstrap';
import CustomModal from "../_custom/modal";

class Menu extends React.Component {
    constructor(props) {
        super(props);
        this.passed = props.passed;
        this.getMenu = props.passed.sharedFunctions.getMenu;
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
                modify: false
            });
            return <CustomTable key={index} {...Object.assign({}, preparedElement, this.passed)}/>
        })
    };


    // let {menu, getMenu} = passed;
    // let menu = MenuData.map((element, index) => {
    //     element.modify = false;
    //     return <CustomTable key={index} {...Object.assign({}, element, passed)}/>
    // });

    componentWillReceiveProps(nextProps) {
        this.setState({menu: nextProps.passed.shared.menu.data});
    }

    componentDidMount() {
        if (this.state.menu === undefined) {
            this.getMenu();
        }
    }

    render() {
        return <Grid id="menu-tab">{this.state.menu && this.prepareMenu(this.state.menu)}</Grid>
    }


}

export default Menu;