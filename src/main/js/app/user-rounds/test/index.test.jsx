import {configure, mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import React from "react";
import UserRounds from "../";
import {assert} from "chai";
import sinon from "sinon";

configure({"adapter": new Adapter()});

describe("UserRounds", () => {

    describe("Component rendering", () => {

        it("starts a new round", () => {
            const startRound = sinon.spy(),
                userRounds = mount(<UserRounds newRound={startRound} />);
            userRounds.find(".controls-container__new-round").simulate("click");
            assert(startRound.calledOnce, "Expected new round request to be triggered");
        });

        it("handles response when triggered", () => {
            const result = {"player1Hand": "ROCK", "player2Hand": "ROCK", "gameResult": "DRAW"},
                userRounds = mount(<UserRounds userResults={[result]} />);
            assert.deepEqual(userRounds.find("PlayedRoundTable").getElement().props.userResults,
                [result], "Expected results table to be updated with new result");
        });

        it("restarts entries when reset", () => {
            const resetRound = sinon.spy(),
                userRounds = mount(<UserRounds resetRound={resetRound} />);
            userRounds.find(".controls-container__reset").simulate("click");
            assert(resetRound.calledOnce, "Expected reset request to be triggered");
        });

    });
});