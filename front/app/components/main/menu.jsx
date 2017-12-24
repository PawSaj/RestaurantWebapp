import React from 'react';
import {Table, Grid, ModalTitle, ModalHeader} from 'react-bootstrap';

const Menu = () => {
    return (
        <div className="menu">
            <Grid>
                <div className="menu-category">
                    <ModalTitle className="menu-category-title">Pizza</ModalTitle>
                    <Table responsive>
                        <thead>
                        <tr>
                            <th>Nazwa</th>
                            <th>Składniki</th>
                            <th>Cena</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Pizza Capriciosa</td>
                            <td>ser, szynka, pieczarki</td>
                            <td>14,50zł</td>
                        </tr>
                        </tbody>
                    </Table>

                    <ModalTitle className="menu-category-title">Zupy</ModalTitle>
                    <Table responsive>
                        <thead>
                        <tr>
                            <th>Nazwa</th>
                            <th>Składniki</th>
                            <th>Cena</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Rosół z kury</td>
                            <td>kura, makaron, marchewka, cebula, pietruszka, seler, por, liście laurowe, ziele
                                angielskie
                            </td>
                            <td>12,50zł</td>
                        </tr>
                        </tbody>
                    </Table>
                </div>
            </Grid>
        </div>
    )

};

export default Menu;