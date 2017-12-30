import React from 'react';
import Layout from '../containers/custom/layout';
import {ADMIN_NAV} from '../_consts/layouts/navigations';
import {ADMIN_CONTENT} from '../_consts/layouts/contents';

const AdminLayout = () => {
    let layoutProp = {
        navigation: ADMIN_NAV,
        content: ADMIN_CONTENT
    };

    return <Layout {...layoutProp}/>;
};

export default AdminLayout;