import React from 'react';
import CustomTable from '../../_custom/table';
import CustomTabs from '../../_custom/tabs';
import {Grid} from 'react-bootstrap';
import NewTable from './newTableForm';

class Tables extends React.Component {
    constructor(props) {
        super(props);
        this.passed = props.passed;
        this.tables = props.passed.admin.tables;
        this.getTables = props.passed.adminFunctions.getTables;
        this.deleteTable = props.passed.adminFunctions.deleteTable;
        this.state = {
            tables: props.passed.admin.tables.data,
            all: props.passed.admin.tables.all
        };

        this.prepareTablesTable = this.prepareTablesTable.bind(this);
    }

    prepareTablesTable() {
        if (this.state.tables !== undefined) {
            let preparedElement = Object.assign({}, {
                order: ['id', 'seats', 'tableNumber', 'floor'],
                headsTitles: ['Id', 'Miejsca', 'Numer', 'Piętro'],
                modify: true,
                body: this.state.tables,
                deleteFunction: this.deleteTable
            });

            return <CustomTable {...Object.assign({}, preparedElement, this.passed)}/>
        }
    }

    componentWillReceiveProps(nextProps) {
        this.setState({tables: nextProps.passed.admin.tables.data, all: nextProps.passed.admin.tables.all});
    }


    componentDidMount() {
        if (!this.state.tables || !this.state.all) {
            this.getTables();
        }
    }

    render() {
        let tabsProps = {
            id: "tables-tab",
            tabs: [
                {
                    title: 'Edycja stolików',
                    content: this.state.tables && this.prepareTablesTable()
                }, {
                    title: 'Dodawanie stolików',
                    content: <NewTable addingEl={true}/>
                }
            ],
        };
        return (
            <Grid>
                <CustomTabs {...tabsProps}/>
            </Grid>
        )

    }
}
export default Tables;