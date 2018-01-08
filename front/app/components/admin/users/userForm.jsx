import React from "react";
import {Button, Grid, Row, Col} from "react-bootstrap";
import FieldGroup from "../../_custom/fieldGroup";
import {getIDFromPath} from '../../../lib/helpers/urlHelpers';

const getUser = (path, users) => {
    if (users === undefined) {
        return null;
    }
    let userId = getIDFromPath(path);
    for (let user of users) {
        if (user.id === userId) {
            return user;
        }
    }
    return null;
};

const setUserData = (user) => {
    if (user === null) {
        return {};
    }

    return {
        id: (user.id) ? user.id : null,
        name: (user.name) ? user.name : '',
        surname: (user.surname) ? user.surname : '',
        email: (user.email) ? user.email : '',
        phone: (user.phone) ? user.phone : '',
        password: (user.password) ? user.password : '',
        image: null,
        role: (user.role) ? user.role : 'USER_ROLE'
    }
};

class User extends React.Component {
    constructor(props) {
        super(props);
        this.user = getUser(props.passed.location.pathname, props.passed.admin.users.data);
        this.getUserById = props.passed.adminFunctions.getUserById;
        this.updateUserAdmin = props.passed.adminFunctions.updateUserAdmin;
        this.state = setUserData(this.user);

        // this.onDrop = this.onDrop.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleOnClick = this.handleOnClick.bind(this);
        if (this.user === null) {
            let userId = getIDFromPath(props.passed.location.pathname);
            this.getUserById(userId);
        }
    }

    handleChange(event) {
        let {id, value} = event.target, newValue = {};
        newValue[id] = value;
        this.setState(newValue);
    }

    handleOnClick(event) {
        event.preventDefault();
        this.updateUserAdmin(this.state.id, this.state);
    }

    componentWillReceiveProps(nextProps) {
        this.user = getUser(nextProps.passed.location.pathname, nextProps.passed.admin.users.data);
        this.state = setUserData(this.user);
    }

    render() {
        if (this.user !== null) {
            return (

                <Grid className="edit-form">
                    <Row>
                        <Col xs={6} md={4}>
                            <FieldGroup
                                id="name"
                                label="Imię"
                                type="text"
                                placeholder="Wprowadź imię"
                                value={this.state.name}
                                onChange={this.handleChange}
                            />
                        </Col>
                        <Col xs={6} md={4}>
                            <FieldGroup
                                id="surname"
                                label="Nazwisko"
                                type="text"
                                placeholder="Wprowadź nazwisko"
                                value={this.state.surname}
                                onChange={this.handleChange}
                            />
                        </Col>
                        <Col xs={12} md={4}>
                            <FieldGroup
                                id="phone"
                                label="Numer telefonu"
                                type="text"
                                placeholder="Wprowadź numer telefonu"
                                value={this.state.phone}
                                onChange={this.handleChange}
                            />
                        </Col>
                    </Row>
                    <Row>
                        <Col xs={6} md={8}>
                            <FieldGroup
                                id="email"
                                label="Adres email"
                                type="email"
                                disabled={true}
                                value={this.state.email}
                                onChange={this.handleChange}
                            />
                        </Col>
                        <Col xs={6} md={4}>
                            <FieldGroup
                                id="role"
                                label="Rola"
                                componentClass="select"
                                onChange={this.handleChange}
                                defaultValue={this.user.role}
                            >
                                <option value="ROLE_USER">użytkownik
                                </option>
                                <option value="ROLE_ADMIN">administrator
                                </option>
                                <option value="ROLE_MANAGER">kierownik
                                </option>
                            </FieldGroup>
                        </Col>
                    </Row>
                    <Row>
                        <Col xs={12}>
                            <Button bsClass="btn btn-panel" onClick={this.handleOnClick}>Zapisz</Button>
                        </Col>
                    </Row>
                </Grid>
            )
        }

        return null;
    }

}

export default User;