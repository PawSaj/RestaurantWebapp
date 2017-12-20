import React from 'react';
import {NavLink, Route, Switch} from 'react-router-dom';
import {MANAGER_PATHS} from '../consts/paths';

const ManagerLayout = () => {
    return (
        <div>
            <nav className="header">
                <NavLink to={MANAGER_PATHS.REPORTS} activeClassName="active">Raporty</NavLink>
                <NavLink to={MANAGER_PATHS.PROMOTIONS} activeClassName="active">Promocje</NavLink>
            </nav>
            <div className="content">
                <Switch>
                    <Route path={MANAGER_PATHS.REPORTS}>{}</Route>
                    <Route path={MANAGER_PATHS.PROMOTIONS}>{}</Route>
                </Switch>
            </div>
        </div>
    );
};

export default ManagerLayout;