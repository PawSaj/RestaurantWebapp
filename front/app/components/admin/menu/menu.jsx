import React from 'react';
import CustomTable from '../../_custom/table';
import MenuData from '../../../_consts/_menu';
import {Tabs, Tab, Grid} from 'react-bootstrap'

const Menu = () => {
    let menu = MenuData.map(element => <CustomTable {...element}/>);

    return (
        <Grid>
            <Tabs defaultActiveKey={1} animation={false} id="noanim-tab-example">
                <Tab eventKey={1} title="Edycja dań">{menu}</Tab>
                <Tab eventKey={2} title="Dodawanie dań">Dodawanie</Tab>
            </Tabs>
        </Grid>);
};

export default Menu;