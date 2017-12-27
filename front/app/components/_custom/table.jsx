import React from 'react';
import {Table, ModalTitle, Button} from 'react-bootstrap';
import {NavLink} from 'react-router-dom';

const createHeadRow = (headsTitles) => {
    return headsTitles.map((title, index) => <th key={index}>{title}</th>)
};

const insertHeadRow = (heads, modify) => {
    if (modify === true) {
        return (
            <tr>
                {heads}
                <th/>
                <th/>
            </tr>
        );
    }

    return (
        <tr>
            {heads}
        </tr>
    );
};

const createBodyRow = (element, links) => {
    let {filed, path} = links, value = null;

    return Object.keys(element).map((key) => {
        if (key === 'price') {
            value = `${element[key]} zł`;
        } else {
            value = element[key];
        }

        if (links && key === filed) {
            return <td key={key}><NavLink to={path + '/' + value}>{value}</NavLink></td>;
        }

        return <td key={key}>{value}</td>;
    });

};

const insertBodyRow = (row, modify, index) => {
    if (modify === true) {
        return (
            <tr key={index}>
                {row}
                <td><Button bsClass="btn btn-panel">Edytuj</Button></td>
                <td><Button bsClass="btn btn-panel">Usuń</Button></td>
            </tr>
        );
    }

    return (
        <tr key={index}>
            {row}
        </tr>
    );
};

const CustomTable = ({category = null, modify = false, headsTitles, body, links = {}}) => {
    let tableClassName = null;
    if (modify === true) {
        tableClassName = 'modify';
    }

    return (
        <div className="table-container">
            {category && <ModalTitle className="table-category-title">{category}</ModalTitle>}
            <Table responsive className={tableClassName}>
                <thead>
                {insertHeadRow(createHeadRow(headsTitles), modify)}
                </thead>
                <tbody>
                {body.map((element, index) => insertBodyRow(createBodyRow(element, links), modify, index))}
                </tbody>
            </Table>
        </div>
    );
};

export default CustomTable;