import React from 'react';
import {Route, Redirect, Switch} from 'react-router-dom';

const Content = (props) => {
    let {content} = props, passed = {};

    for (let key in props) {
        if (props.hasOwnProperty(key) && key !== 'navigation' && key !== 'content') {
            passed[key] = props[key];
        }
    }

    return (
        <div className="content">
            <Switch>
                {content.routes.map((route, index) => {
                    if (route.login && props.loggedIn === false) {
                        return null;
                    }
                    let Component = route.component;

                    return <Route key={index} exact path={route.path}
                                  render={() => <Component passed={passed}/>}/>
                })}
                <Redirect to={content.urlToRedirect}/>
            </Switch>
        </div>
    );
};

export default Content;