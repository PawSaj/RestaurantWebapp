import React from 'react';
import Layout from './custom/layout';
import {MAIN_NAV} from './_consts/navigations';
import {MAIN_CONTENT} from './_consts/contents';

const MainLayout = () => {
    let layoutProp = {
        navigation: MAIN_NAV,
        content: MAIN_CONTENT
    };

    return <Layout {...layoutProp}/>;
};

export default MainLayout;