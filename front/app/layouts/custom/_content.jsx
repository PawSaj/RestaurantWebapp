import React from 'react';
import {Route, Redirect, Switch} from 'react-router-dom';

const createRoute = (props) => {
    let routes = [];
    for (let key in props) {
        if (props.hasOwnProperty(key)) {
            let route = props[key];
            routes.push(<Route exact path={route.path} component={route.component}/>)
        }
    }
    return routes;
};

const Content = (props) => {
    return (
        <div className="content">
            <Switch>
                {createRoute(props.routes)}
                <Redirect to={props.urlToRedirect}/>
            </Switch>
        </div>
    );
};

export default Content;