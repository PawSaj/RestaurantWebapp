import React from 'react';
import {Grid} from 'react-bootstrap';
import CustomTable from '../../_custom/table';
import CustomTabs from '../../_custom/tabs';

class Users extends React.Component {

    constructor(props) {
        super(props);
        this.passed = props.passed;
        this.getAllUsers = this.passed.adminFunctions.getAllUsers;
        this.deleteUser = this.passed.adminFunctions.deleteUser;
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
                deleteFunction: this.deleteUser,
                body: this.state.users.data
            });

            return <CustomTable {...Object.assign({}, preparedElement, this.passed)}/>
        }
    }

    componentWillReceiveProps(nextProps) {
        this.setState({users: nextProps.passed.admin.users});
    }

    componentDidMount() {
        if (Object.keys(this.state.users).length === 0 || !this.state.users.all) {
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