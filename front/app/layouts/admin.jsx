import React from 'react';
import {NavLink, Route, Switch, Redirect} from 'react-router-dom';
import {Navbar, Nav, NavItem} from 'react-bootstrap'
import {ADMIN_PATHS, MAIN_PATHS} from '../consts/paths';

const AdminLayout = () => {
    return (
        <div>
            <Navbar inverse collapseOnSelect>
                <Navbar.Header>
                    <Navbar.Brand>
                        <NavLink exact to={ADMIN_PATHS.HOME} activeClassName="active">SajRoz Restaurant</NavLink>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        <li role="presentation">
                            <NavLink exact to={ADMIN_PATHS.HOME} activeClassName="active">Panel</NavLink>
                        </li>
                        <li role="presentation">
                            <NavLink exact to={ADMIN_PATHS.RESERVATIONS} activeClassName="active">Rezerwacje</NavLink>
                        </li>
                        <li role="presentation">
                            <NavLink exact to={ADMIN_PATHS.TABLES} activeClassName="active">Stoliki</NavLink>
                        </li>
                        <li role="presentation">
                            <NavLink exact to={ADMIN_PATHS.MENU} activeClassName="active">Menu</NavLink>
                        </li>
                        <li role="presentation">
                            <NavLink exact to={ADMIN_PATHS.USERS} activeClassName="active">Użytkownicy</NavLink>
                        </li>
                    </Nav>
                    <Nav pullRight>
                        <NavItem eventKey={1}>Wyloguj</NavItem>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>

            <div className="content">
                <Switch>
                    <Route exact path={ADMIN_PATHS.HOME}>{}</Route>
                    <Route exact path={ADMIN_PATHS.RESERVATIONS}>{}</Route>
                    <Route exact path={ADMIN_PATHS.TABLES}>{}</Route>
                    <Route exact path={ADMIN_PATHS.MENU}>{}</Route>
                    <Route exact path={ADMIN_PATHS.USERS}>{}</Route>
                    <Redirect to={ADMIN_PATHS.HOME}/>
                </Switch>
            </div>
        </div>
    );
};

export default AdminLayout;