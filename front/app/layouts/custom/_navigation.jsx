import React from 'react';
import {Navbar, Nav, NavItem} from 'react-bootstrap';
import {NavLink} from 'react-router-dom';
import {BRAND} from '../_consts/navigations';

const links = (links) => {
    let navLinksElements = [];
    for (let nav of links) {
        let element = null;
        if (nav.link !== null) {
            element =
                <li role="presentation">
                    <NavLink exact={nav.exact} to={nav.link} activeClassName="active">{nav.desc}</NavLink>
                </li>;
        } else {
            element = <NavItem eventKey={nav.key}>{nav.desc}</NavItem>;
        }
        navLinksElements.push(element);
    }

    return navLinksElements;
};

const Navigation = (props) => {
    let {navs, right} = props;

    return (
        <Navbar inverse collapseOnSelect>
            <Navbar.Header>
                <Navbar.Brand>
                    <NavLink exact to={BRAND.link} activeClassName="active">{BRAND.desc}</NavLink>
                </Navbar.Brand>
                <Navbar.Toggle />
            </Navbar.Header>
            <Navbar.Collapse>
                <Nav>{links(navs)}</Nav>
                <Nav pullRight>{links(right)}</Nav>
            </Navbar.Collapse>
        </Navbar>
    );
};

export default Navigation;