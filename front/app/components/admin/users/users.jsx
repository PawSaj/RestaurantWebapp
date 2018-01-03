import React from 'react';
import {Grid} from 'react-bootstrap';
import CustomTable from '../../_custom/table';
import CustomTabs from '../../_custom/tabs';
import USERS from '../../../_consts/mocks/users';

// let tableProps = {
//     headsTitles: ['Id', 'Imię', 'Nazwisko', 'Rola'],
//     modify: true,
//     body: USERS
// };
//
// let tabsProps = {
//     id: "users-tab",
//     tabs: [
//         {
//             title: 'Edycja użytkowników',
//             content: <CustomTable {...Object.assign({}, tableProps, passed)}/>
//         }
//     ],
// };

class Users extends React.Component {

    constructor(props) {
        super(props);
        this.passed = props.passed;
        this.getAllUsers = props.passed.adminFunctions.getAllUsers;
        this.state = {
            users: props.passed.admin.users
        };

        this.prepareUsersTable = this.prepareUsersTable.bind(this);

    }

    prepareUsersTable() {
        if (this.state.users.data !== undefined) {
            let preparedElement = Object.assign({}, {
                order: ['id', 'name', 'surname', 'role'],
                headsTitles: ['Id', 'Imię', 'Nazwisko', 'Rola'],
                modify: true,
                body: this.state.users.data
            });

            return <CustomTable {...Object.assign({}, preparedElement, this.passed)}/>
        }
    }

    componentWillReceiveProps(nextProps) {
        this.setState({users: nextProps.passed.admin.users});
    }

    componentDidMount() {
        if (Object.keys(this.state.users).length === 0) {
            this.getAllUsers();
        }
    }


    render() {
        let tabsProps = {
            id: "users-tab",
            tabs: [
                {
                    title: 'Edycja użytkowników',
                    content: this.prepareUsersTable()
                }
            ],
        };
        return (
            <Grid>
                <CustomTabs {...tabsProps}/>
            </Grid>
        );
    }
}

export default Users;