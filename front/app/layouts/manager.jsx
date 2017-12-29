import React from 'react';
import Layout from './custom/layout';
import {MANAGER_NAV} from '../_consts/layouts/navigations';
import {MANAGER_CONTENT} from '../_consts/layouts/contents';
const ManagerLayout = () => {
    let layoutProp = {
        navigation: MANAGER_NAV,
        content: MANAGER_CONTENT
    };

    return <Layout {...layoutProp}/>;
};

export default ManagerLayout;