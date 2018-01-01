import React from 'react';
import {Grid, PageHeader, Button, Row, Col} from 'react-bootstrap';
import FieldGroup from '../_custom/fieldGroup';
import Dropzone from 'react-dropzone';
import {serializeFrom} from '../../lib/helpers/formHelpers'

const mock = {
    id: 3,
    email: "teste@wp.pl",
    username: "Test",
    surname: "Testowy",
    password: "adfasdas",
    image: "",
    phone: ""
};


class Profile extends React.Component {
    constructor(props) {
        super(props);
        this.user = props.passed.shared.user.data;
        this.avatar = null;
        this.changeUserData = props.passed.mainFunctions.changeUserData;
        this.state = {
            id: this.user.id,
            name: (this.user.name) ? this.user.name : '',
            surname: (this.user.surname) ? this.user.surname : '',
            email: (this.user.email) ? this.user.email : '',
            phone: (this.user.phone) ? this.user.phone : '',
            password: (this.user.password) ? this.user.password : ''
        };

        this.onDrop = this.onDrop.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleOnClick = this.handleOnClick.bind(this);
    }

    onDrop(files) {
        this.avatar = files[0];
    }

    handleChange(event) {
        let {id, value} = event.target, newValue = {};
        newValue[id] = value;
        this.setState(newValue);
    }

    handleOnClick(event) {
        event.preventDefault();
        let serializedObj = serializeFrom({formId: 'profile-form'});
        this.changeUserData(this.state.id, serializedObj);
    }

    componentWillReceiveProps(nextProps) {
        this.user = nextProps.passed.shared.user.data;
        this.setState({
            id: this.user.id,
            name: (this.user.name) ? this.user.name : '',
            surname: (this.user.surname) ? this.user.surname : '',
            email: (this.user.email) ? this.user.email : '',
            phone: (this.user.phone) ? this.user.phone : '',
            password: (this.user.password) ? this.user.password : ''
        });
    }


    render() {
        return (
            <div className="profile">
                <Grid>
                    <PageHeader>
                        Dane osobowe
                    </PageHeader>
                    <form id="profile-form">
                        <Row>
                            <Col xs={6} md={4}>
                                <Dropzone
                                    className="dropzone"
                                    accept="image/jpg"
                                    multiple={false}
                                    onDrop={this.onDrop}
                                >
                                    <Button>Zmień zdjęcie</Button>
                                </Dropzone>
                            </Col>
                            <Col xs={6} md={8}>
                                <FieldGroup
                                    id="name"
                                    type="text"
                                    label="Imię"
                                    placeholder="Wprowadź swoje imię"
                                    value={this.state.name}
                                    onChange={this.handleChange}
                                />
                                <FieldGroup
                                    id="surname"
                                    type="text"
                                    label="Nazwisko"
                                    placeholder="Wprowadź swoje nazwisko"
                                    value={this.state.surname}
                                    onChange={this.handleChange}
                                />
                                <FieldGroup
                                    id="phone"
                                    type="text"
                                    label="Numer telefonu"
                                    placeholder="Wprowadź swój numer telefonu"
                                    value={this.state.phone}
                                    onChange={this.handleChange}
                                />
                            </Col>
                        </Row>

                        <Row>
                            <Col xs={12}>
                                <FieldGroup
                                    id="email"
                                    type="email"
                                    label="Adres email"
                                    className="email-input"
                                    disabled={true}
                                    value={this.state.email}
                                />
                            </Col>
                        </Row>

                        <Row>
                            <Col xs={12} md={6}>
                                <FieldGroup
                                    id="password"
                                    type="password"
                                    label="Hasło"
                                    value={this.state.password}
                                    onChange={this.handleChange}
                                />
                            </Col>
                            <Col xs={12} md={6}>
                                <FieldGroup
                                    id="passwordConfirm"
                                    type="password"
                                    label="Potwierdzenie hasła"
                                />
                            </Col>
                        </Row>
                        <div className="form-button">
                            <Button type="submit" onClick={this.handleOnClick}>
                                Zmień dane
                            </Button>
                        </div>
                    </form>
                </Grid>
            </div>
        )
    }
}

export default Profile;