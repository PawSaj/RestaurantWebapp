import React from 'react';
import {Tabs, Tab} from 'react-bootstrap';

const CustomTabs = ({tabs, id, aniamtion = false, defaultActive = 0}) => {
    return (
        <Tabs defaultActiveKey={defaultActive} animation={aniamtion} id={id}>
            {tabs.map((tab, index) =>
                <Tab eventKey={index} title={tab.title}>{tab.content}</Tab>
            )}
        </Tabs>
    );
};

export default CustomTabs;