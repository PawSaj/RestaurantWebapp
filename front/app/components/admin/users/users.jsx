import React from 'react';
import {Grid} from 'react-bootstrap';
import CustomTable from '../../_custom/table';
import CustomTabs from '../../_custom/tabs';
import USERS from '../../../_consts/mocks/users';
import {getParam} from '../../../lib/helpers/urlHelpers';


const Users = (props) => {
    let {location} = props;
    let userID = getParam({search: location.search, name: 'dama'});

    return null;
};

export default Users;