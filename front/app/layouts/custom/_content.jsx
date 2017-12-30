import React from 'react';
import {Route, Redirect, Switch} from 'react-router-dom';

const Content = (props) => {
    return (
        <div className="content">
            <Switch>
                {props.routes.map((route, index) =>
                    <Route key={index} exact path={route.path}
                           component={route.component}/>)}
                <Redirect to={props.urlToRedirect}/>
            </Switch>
        </div>
    );
};

export default Content;