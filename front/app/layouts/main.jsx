import React from 'react';
import Layout from './custom/layout';
import {MAIN_NAV} from '../_consts/layouts/navigations';
import {MAIN_CONTENT} from '../_consts/layouts/contents';

const MainLayout = () => {
    let layoutProp = {
        navigation: MAIN_NAV,
        content: MAIN_CONTENT
    };

    return <Layout {...layoutProp}/>;
};

export default MainLayout;