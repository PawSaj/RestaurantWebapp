import React from 'react';
import {Table, Grid} from 'react-bootstrap';

const Menu = () => {
    return (
        <div>
            <Grid>
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
            </Grid>
        </div>
    )

};

export default Menu;