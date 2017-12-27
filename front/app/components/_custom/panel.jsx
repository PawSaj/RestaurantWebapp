import React from 'react';
import {Grid, Row, Col, Button} from 'react-bootstrap';

const createButtonRow = (buttons) => {
    return buttons.map((button, index) =>
        (<Col key={index} xs={6}>
            <Button bsClass="btn btn-panel">
                {button}
            </Button>
        </Col>)
    );
};

const createContent = (buttons) => {
    let buttonsInRow = [], buttonsLength = buttons.length;

    return buttons.map((button, index) => {
        if (buttonsInRow.length === 2) {
            buttonsInRow = [];
        }
        buttonsInRow.push(button);
        if (index === (buttonsLength - 1) || buttonsInRow.length === 2) {
            return <Row key={index}>{createButtonRow(buttonsInRow)}</Row>;
        }
    });
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