import React from 'react';
import CustomTable from '../_custom/table';
import MenuData from '../../_consts/_menu';

const Menu = () => {
    let tableProps = MenuData[0];
    return <CustomTable {...tableProps}/>;
};

export default Menu;