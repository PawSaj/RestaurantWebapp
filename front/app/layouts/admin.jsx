import React from 'react';
import {NavLink, Route, Switch} from 'react-router-dom';
import {ADMIN_PATHS} from '../consts/paths';

const AdminLayout = () => {
    return (
        <div>
            <nav className="header">
                <div>Zarządzaj:</div>
                <NavLink to={ADMIN_PATHS.RESERVATIONS} activeClassName="active">rezerwacjami</NavLink>
                <NavLink to={ADMIN_PATHS.TABLES} activeClassName="active">stolikami</NavLink>
                <NavLink to={ADMIN_PATHS.MENU} activeClassName="active">menu</NavLink>
                <NavLink to={ADMIN_PATHS.USERS} activeClassName="active">użytkownikami</NavLink>
                <a>Wyloguj</a>
            </nav>
            <div className="content">
                <Switch>
                    <Route path={ADMIN_PATHS.RESERVATIONS}>{}</Route>
                    <Route path={ADMIN_PATHS.TABLES}>{}</Route>
                    <Route path={ADMIN_PATHS.MENU}>{}</Route>
                    <Route path={ADMIN_PATHS.USERS}>{}</Route>
                </Switch>
            </div>
        </div>
    );
};

export default AdminLayout;