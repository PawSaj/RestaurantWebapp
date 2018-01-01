import React from 'react';
import CustomTable from '../_custom/table';
import MenuData from '../../_consts/mocks/menu';
import {Grid, Button} from 'react-bootstrap';


class Menu extends React.Component {
    constructor(props) {
        super(props);
        this.getMenu = props.passed.sharedFunctions.getMenu;
        this.state = {
            menu: props.passed.shared.menu.data
        };
    }

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
        return <Grid id="menu-tab"></Grid>
    }

    // return <Grid id="menu-tab">{menu}</Grid>

}

export default Menu;