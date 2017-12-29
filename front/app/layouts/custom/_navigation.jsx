import React from 'react';
import {Navbar, Nav, NavItem} from 'react-bootstrap';
import {NavLink} from 'react-router-dom';
import {BRAND} from '../../_consts/layouts/navigations';

const links = (links) => {
    return links.map((nav, index) => {

        if (nav.link !== null) {
            return (
                <li key={index} role="presentation">
                    <NavLink exact={nav.exact} to={nav.link} activeClassName="active">{nav.desc}</NavLink>
                </li>);
        } else {
            return (<NavItem key={index} eventKey={index}>{nav.desc}</NavItem>);
        }
    });

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