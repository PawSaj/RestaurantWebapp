import React from 'react';
import {Grid, Row, Col, Button} from 'react-bootstrap';

const createButtonRow = (buttons) => {
    let rowContent = [];
    for (let button of buttons) {
        rowContent.push(
            <Col xs={6}>
                <Button bsClass="btn btn-panel">
                    {button}
                </Button>
            </Col>);
    }

    return (
        <Row>
            {rowContent}
        </Row>
    );
};

const createContent = (buttons) => {
    let content = [], buttonsInRow = [], buttonsLength = buttons.length;

    for (let key in buttons) {
        if (buttons.hasOwnProperty(key)) {
            buttonsInRow.push(buttons[key]);
        }

        if (key === (buttonsLength - 1) || buttonsInRow.length === 2) {
            content.push(createButtonRow(buttonsInRow));
            /*clean buttons*/
            buttonsInRow = [];
        }
    }

    return content;
};

const CustomPanel = (props) => {
    let {header, buttons} = props;
    return (
        <Grid className="panel-grid">
            <Row>
                <Col xs={12}>
                    {header}
                </Col>
            </Row>
            {createContent(buttons)}
        </Grid>
    )
};

export default CustomPanel;