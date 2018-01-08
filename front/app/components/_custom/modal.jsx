import React from 'react';
import {Modal} from 'react-bootstrap';

class CustomModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {show: false};

        this.onHide = this.onHide.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        this.setState({show: nextProps.show});
    }

    onHide() {
        this.setState({show: false});
    }

    render() {
        return (
            <Modal show={this.state.show}>
                <Modal.Header closeButton onHide={this.onHide}>
                    <Modal.Title>{this.props.title}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.props.body}
                </Modal.Body>
            </Modal>
        )
    }
}

export default CustomModal;