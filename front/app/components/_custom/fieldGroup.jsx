import React from 'react';
import {FormGroup, FormControl, ControlLabel, HelpBlock} from 'react-bootstrap';

const FieldGroup = ({id, label, help, className = null, ...props}) => {
    return (
        <FormGroup controlId={id} className={className}>
            <ControlLabel>{label}</ControlLabel>
            <FormControl {...props}>{props.children}</FormControl>
            {help && <HelpBlock>{help}</HelpBlock>}
        </FormGroup>
    );
};

export default FieldGroup;