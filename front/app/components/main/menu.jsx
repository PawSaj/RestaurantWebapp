import React from 'react';
import CustomTable from '../_custom/table';
import MenuData from '../../_consts/mocks/menu';
import {Grid, Button} from 'react-bootstrap';
let dataFetched = false;


const Menu = ({passed}) => {
    let {menu, getMenu} = passed;
    // let menu = MenuData.map((element, index) => {
    //     element.modify = false;
    //     return <CustomTable key={index} {...Object.assign({}, element, passed)}/>
    // });
    if (dataFetched === false) {
        dataFetched = true;
        getMenu();
    }


    return <Grid id="menu-tab">
        <Button>Click me</Button>
    </Grid>

    // return <Grid id="menu-tab">{menu}</Grid>

};

export default Menu;