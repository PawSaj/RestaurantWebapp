import React from 'react';
import {NavLink, Route, Switch} from 'react-router-dom';
import {USER_PATHS} from '../consts/paths';

const UserLayout = () => {
    return (
        <div>
            <nav className="header">
                <NavLink to={USER_PATHS.HOME} activeClassName="active">Strona głowna</NavLink>
                <NavLink to={USER_PATHS.MENU} activeClassName="active">Menu</NavLink>
                <NavLink to={USER_PATHS.RESERVATIONS} activeClassName="active">Rezerwacje</NavLink>
                <NavLink to={USER_PATHS.PROFILE} activeClassName="active">Profil</NavLink>
            </nav>
            <div className="content">
                <Switch>
                    <Route path={USER_PATHS.HOME}>{}</Route>
                    <Route path={USER_PATHS.MENU}>{}</Route>
                    <Route path={USER_PATHS.RESERVATIONS}>{}</Route>
                    <Route path={USER_PATHS.PROFILE}>{}</Route>
                </Switch>
            </div>
        </div>
    );
};

export default UserLayout;