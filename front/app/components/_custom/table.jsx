import React from 'react';
import {Table, Grid, ModalTitle, Button} from 'react-bootstrap';

const tableHead = (headsTitles, modify) => {
    let heads = headsTitles.map(title =>
        <th>{title}</th>
    );

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

const insertBodyRow = (element, modify) => {
    let row = Object.keys(element).map(key => {
        if (key === 'price') {
            return <td>{element[key]} zł</td>
        } else {
            return <td>{element[key]}</td>
        }
    });

    if (modify === true) {
        return (
            <tr>
                {row}
                <td><Button bsClass="btn btn-panel">Edytuj</Button></td>
                <td><Button bsClass="btn btn-panel">Usuń</Button></td>
            </tr>
        );
    }

    return (
        <tr>
            {row}
        </tr>
    );
};

const CustomTable = ({category = null, modify = false, headsTitles, body}) => {
    let tableClassName = null;
    if (modify === true) {
        tableClassName = 'modify';
    }

    return (
        <div className="table-container">
            {(category !== null) ?
                <ModalTitle className="table-category-title">{category}</ModalTitle>
                : null
            }
            <Table responsive className={tableClassName}>
                <thead>
                {tableHead(headsTitles, modify)}
                </thead>
                <tbody>
                {body.map(element => insertBodyRow(element, modify))}
                </tbody>
            </Table>
        </div>
    );
};

export default CustomTable;