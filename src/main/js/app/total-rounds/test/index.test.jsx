import {configure, mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import React from "react";
import TotalRounds from "../";
import {assert} from "chai";

configure({"adapter": new Adapter()});

describe("TotalRounds", () => {

    describe("Component rendering", () => {

        it("includes results in stats table", () => {
            const result = {"totalRounds": 669, "totalWins": 666, "totalLoses": 1, "totalDraws": 2},
                userRounds = mount(<TotalRounds totalResults={result} />);
            assert.deepEqual(userRounds.find("FilterableTable").getElement().props.data,
                [result], "Expected results table to be set with new result");
        });

    });
});