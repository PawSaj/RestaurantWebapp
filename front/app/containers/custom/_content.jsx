import React from 'react';
import {Route, Redirect, Switch} from 'react-router-dom';

const Content = (props) => {
    let {content} = props;
    return (
        <div className="content">
            <Switch>
                {content.routes.map((route, index) => {
                    if (route.login && props.loggedIn === false) {
                        return null;
                    }
                    return <Route key={index} exact path={route.path}
                                  component={route.component} {...props}/>
                })}
                <Redirect to={content.urlToRedirect}/>
            </Switch>
        </div>
    );
};

export default Content;