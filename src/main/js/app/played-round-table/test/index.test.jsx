import {configure, mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import PlayedRoundTable from "../";
import React from "react";
import {assert} from "chai";

configure({"adapter": new Adapter()});

describe("PlayedRoundTable", () => {

    const expectedColumns = [
        {"name": "player1Hand", "displayName": "Player1 selection"},
        {"name": "player2Hand", "displayName": "Player2 selection"},
        {"name": "gameResult", "displayName": "Result"}
    ];

    describe("Component rendering", () => {

        it("includes FilterableTable with static columns and no data by default", () => {
            const roundTable = mount(<PlayedRoundTable/>);
            let renderedTableProps = roundTable.find("FilterableTable").instance().props;
            assert.deepEqual(renderedTableProps.fields,
                expectedColumns, "Expected FilterableTable component to exist and static column values to be set");
            assert.deepEqual(renderedTableProps.data,
                [], "Expected FilterableTable component to exist and data to be empty");
        });

        it("adds data when included as properties", () => {
            const data = [{"player1Hand": "ROCK", "player2Hand": "ROCK", "gameResult": "DRAW"},
                    {"player1Hand": "ROCK", "player2Hand": "SCISSORS", "gameResult": "WIN"}],
                roundTable = mount(<PlayedRoundTable userResults={data}/>);
            assert.deepEqual(roundTable.find("FilterableTable").instance().props.data,
                data, "Expected FilterableTable component to exist and static column values to be set");
            assert.exists(roundTable.find(".filterable-table-container tr.even"),
                "Expected row to include 'even' classname");
            assert.exists(roundTable.find(".filterable-table-container tr.odd"),
                "Expected row to include 'odd' classname");

        });
    });
});