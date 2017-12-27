import React from 'react';
import {Modal} from 'react-bootstrap';

const CustomModal = (props) => {

    const onHide = () => {
        console.log('onHide: hi3');
    };

    let {body, title, show} = props;

    return (
        <Modal show={show}>
            <Modal.Header closeButton onHide={onHide}>
                <Modal.Title>{title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {body}
            </Modal.Body>
        </Modal>
    );
};

export default CustomModal;