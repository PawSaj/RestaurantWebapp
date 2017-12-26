import React from 'react';
import Layout from './custom/layout';
import {ADMIN_NAV} from './_consts/navigations';
import {ADMIN_CONTENT} from './_consts/contents';

const AdminLayout = () => {
    let layoutProp = {
        navigation: ADMIN_NAV,
        content: ADMIN_CONTENT
    };

    return (
        <Layout {...layoutProp}/>
    );
};

export default AdminLayout;