import React from 'react';
import CustomTable from '../_custom/table';
import MenuData from '../../_consts/_menu';

const Menu = () => {
    let menu = MenuData.map(element => <CustomTable {...element}/>);

    return <div id="menu">{menu}</div>;
};

export default Menu;