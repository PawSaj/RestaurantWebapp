import React from 'react';
import {NavLink, Route, Redirect, Switch} from 'react-router-dom';
import {MAIN_PATHS} from '../consts/paths';

const MainLayout = () => {
    return (
        <div>
            <nav className="header">
                <NavLink exact to={MAIN_PATHS.HOME} activeClassName="active">Strona głowna</NavLink>
                <NavLink exact to={MAIN_PATHS.MENU} activeClassName="active">Menu</NavLink>
                <NavLink exact to={MAIN_PATHS.RESERVATIONS} activeClassName="active">Rezerwacje</NavLink>
                <NavLink exact to={MAIN_PATHS.PROFILE} activeClassName="active">Profil</NavLink>
            </nav>
            <div className="content">
                <Switch>
                    <Route exact path={MAIN_PATHS.HOME}>{}</Route>
                    <Route exact path={MAIN_PATHS.MENU}>{}</Route>
                    <Route exact path={MAIN_PATHS.RESERVATIONS}>{}</Route>
                    <Route exact path={MAIN_PATHS.PROFILE}>{}</Route>
                    <Redirect to={MAIN_PATHS.HOME}/>
                </Switch>
            </div>
        </div>
    );
};

export default MainLayout;