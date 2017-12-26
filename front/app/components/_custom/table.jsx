import React from 'react';
import {Table, Grid, ModalTitle, Button} from 'react-bootstrap';

const tableHead = (headsTitles, modify) => {
    let heads = [];
    for (let title of headsTitles) {
        heads.push(<th>{title}</th>);
    }

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
    let row = [];
    for (let key in element) {
        if (element.hasOwnProperty(key)) {
            if (key === 'price') {
                row.push(<td>{element[key]} zł</td>)
            } else {
                row.push(<td>{element[key]}</td>)
            }
        }
    }

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

const tableBody = (body, modify) => {
    let tableBody = [];
    for (let element of body) {
        tableBody.push(insertBodyRow(element, modify));
    }

    return tableBody;
};

const CustomTable = ({category = null, modify = false, headsTitles, body}) => {
    let tableClassName = null;
    if (modify === true) {
        tableClassName = 'modify';
    }

    return (
        <Grid>
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
                    {tableBody(body, modify)}
                    </tbody>
                </Table>
            </div>
        </Grid>
    );
};

export default CustomTable;