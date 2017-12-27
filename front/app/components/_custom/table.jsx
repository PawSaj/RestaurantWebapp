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
    let {field, path} = links ? links : {}, value = null;

    return Object.keys(element).map((key) => {
        if (key === 'price') {
            value = `${element[key]} zł`;
        } else {
            value = element[key];
        }

        if (links && key === field) {
            return <td key={key}><NavLink to={path + '/' + value}>{value}</NavLink></td>;
        }

        return <td key={key}>{value}</td>;
    });

};

const insertBodyRow = ({element, modify, index, links, url}) => {
    let row = createBodyRow(element, links);
    if (modify === true) {
        return (
            <tr key={index}>
                {row}
                <td>
                    <Button bsClass="btn btn-panel">
                        <NavLink exact to={url + '/' + element['id']}>Edytuj</NavLink>
                    </Button>
                </td>
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

const CustomTable = (props) => {
    let tableClassName = null;
    let {headsTitles, category, modify, body, match} = props;
    let url = match.path;

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
                {body.map((element, index) => insertBodyRow({element, index, url, ...props}))}
                </tbody>
            </Table>
        </div>
    );
};

export default CustomTable;