import React from 'react';
import {NavLink, Route, Redirect, Switch} from 'react-router-dom';
import {MAIN_PATHS} from '../consts/paths';
import {Navbar, Nav, NavItem} from 'react-bootstrap';

/* components */
import Home from '../components/home';
import Menu from '../components/menu';

const MainLayout = () => {

    return (
        <div>
            <Navbar inverse collapseOnSelect>
                <Navbar.Header>
                    <Navbar.Brand>
                        <NavLink exact to={MAIN_PATHS.HOME} activeClassName="active">SajRoz Restaurant</NavLink>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        <li role="presentation">
                            <NavLink exact to={MAIN_PATHS.HOME} activeClassName="active">Strona główna</NavLink>
                        </li>
                        <li role="presentation">
                            <NavLink exact to={MAIN_PATHS.MENU} activeClassName="active">Menu</NavLink>
                        </li>
                        <li role="presentation">
                            <NavLink exact to={MAIN_PATHS.RESERVATIONS} activeClassName="active">Rezerwacje</NavLink>
                        </li>
                    </Nav>
                    <Nav pullRight>
                        <li role="presentation">
                            <NavLink exact to={MAIN_PATHS.PROFILE} activeClassName="active">Profil</NavLink>
                        </li>
                        <NavItem eventKey={1}>Wyloguj</NavItem>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>

            <div className="content">
                <Switch>
                    <Route exact path={MAIN_PATHS.HOME} component={Home}/>
                    <Route exact path={MAIN_PATHS.MENU} component={Menu}/>
                    <Route exact path={MAIN_PATHS.RESERVATIONS}>{}</Route>
                    <Route exact path={MAIN_PATHS.PROFILE}>{}</Route>
                    <Redirect to={MAIN_PATHS.HOME}/>
                </Switch>
            </div>
        </div>
    );
};

export default MainLayout;