import React from 'react';
import CustomPanel from '../_custom/panel';
import Login from './login';
import Registration from './registration';
import Modal from '../_custom/modal';

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showLoginModal: false,
            showRegisterModal: false
        };
        this.panelProperties = {
            header: <div className="description">
                <h2>Witaj na stronie naszej reasturacji</h2>
                <p>Zaloguj się, a jeśli nie masz jeszcze konta to zarejestruj się!</p>
            </div>,
            buttons: [
                {
                    name: 'Logowanie',
                    callback: () => this.loginCallback()
                }, {
                    name: 'Rejestracja',
                    callback: () => this.registerCallback()
                }
            ]
        };

        this.loginCallback = this.loginCallback.bind(this);
        this.registerCallback = this.registerCallback.bind(this);
    }

    loginCallback() {
        this.setState({showLoginModal: true, showRegisterModal: false});
    };

    registerCallback() {
        this.setState({showLoginModal: false, showRegisterModal: true});
    };

    render() {
        return (
            <div id="home">
                <Modal {...{title: 'Logowanie', body: <Login/>, show: this.state.showLoginModal}}/>
                <Modal {...{title: 'Rejestracja', body: <Registration/>, show: this.state.showRegisterModal}}/>
                <CustomPanel {...this.panelProperties}/>
            </div>
        )
    }
}

export default Home;