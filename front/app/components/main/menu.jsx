import React from 'react';
import CustomTable from '../_custom/table';
import MenuData from '../../_consts/_menu';
import {Grid} from 'react-bootstrap';

const Menu = () => {
    let menu = MenuData.map(element => {
        element.modify = false;
        return <CustomTable {...element}/>
    });

    return <Grid>{menu}</Grid>

};

export default Menu;