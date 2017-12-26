import React from 'react';
import Navigation from './_navigation';
import Content from './_content';

const Layout = ({navigation, content}) => {
    return (
        <div>
            <Navigation {...navigation}/>
            <Content {...content}/>
        </div>
    );
};

export default Layout;