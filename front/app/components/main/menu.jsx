import React from 'react';
import CustomTable from '../_custom/table';
import MenuData from '../../_consts/_menu';

const Menu = () => {
    let menu = MenuData.map(element => {
        element.modify = false;
        return <CustomTable {...element}/>
    });

    return <div className="menu">{menu}</div>

};

export default Menu;