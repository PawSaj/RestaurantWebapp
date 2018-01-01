import React from 'react';
import CustomPanel from '../_custom/panel';
import Login from './login';
import Registration from './registration';
import Modal from '../_custom/modal';

const unloggedComponent = ({passed, showLoginModal, showRegisterModal, panelProperties}) => {
    let {sharedFunctions, shared, mainFunctions, main} = passed;
    return (
        <div>
            <Modal {...{
                title: 'Logowanie',
                body: <Login callback={sharedFunctions.loginUser} pending={shared.user.pending} success={{
                    status: !shared.errors.login,
                    errorText: shared.errors.login
                }}/>,
                show: showLoginModal
            }}/>
            <Modal {...{
                title: 'Rejestracja',
                body: <Registration callback={mainFunctions.registerUser} success={{
                    status: !main.errors.registration,
                    errorText: main.errors.registration
                }}/>,
                show: showRegisterModal
            }}/>
            <CustomPanel {...panelProperties}/>
        </div>
    )
};

const loggedInComponent = (user) => {
    return (
        <div className="greeting-box">
            <h2>Witaj, {user.username + user.surname} !</h2>
            <p>Zachęcamy do przejrzenia naszego menu oraz składania rezerwacji.</p>
        </div>
    )
};


class Home extends React.Component {
    constructor(props) {
        super(props);
        this.passed = props.passed;
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


    componentWillReceiveProps(nextProps) {
        this.passed = nextProps.passed;
        if (!!this.passed.shared.errors.login) {
            this.setState((state) => ({showLoginModal: true, showRegisterModal: state.showRegisterModal}));
        } else if (!!this.passed.main.errors.registration) {
            this.setState((state) => ({showLoginModal: state.showLoginModal, showRegisterModal: true}));
        } else {
            this.setState({showLoginModal: false, showRegisterModal: false});
        }
    }


    render() {
        return (
            <div id="home">
                {(this.passed.loggedIn) ? loggedInComponent(this.passed.shared.user.data) : unloggedComponent({
                    passed: this.passed,
                    showLoginModal: this.state.showLoginModal,
                    showRegisterModal: this.state.showRegisterModal,
                    panelProperties: this.panelProperties
                })}
            </div>
        )
    }
}

export default Home;