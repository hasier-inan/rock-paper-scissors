import {configure, mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import App from "../";
import React from "react";
import {TOPICS} from "../topic-constants"
import {assert} from "chai";
import history from "../history";
import sinon from "sinon";

configure({"adapter": new Adapter()});

describe("App", () => {
    let app;
    const sockJsClient = {sendMessage: sinon.spy()},
        createDummyClient = () => {
            app.find("SockJsClient").getElement().ref(sockJsClient);
        };

    beforeEach(() => {
        app = mount(<App/>);
        createDummyClient();
    });

    afterEach(() => {
        history.push('/');
        app.unmount();
    });

    describe("Component rendering", () => {

        const message = {"player1Hand": "ROCK", "player2Hand": "ROCK", "gameResult": "DRAW"},
            triggerADummyMessage = (topic) => {
                app.find("SockJsClient").instance().props.onMessage(message, topic);
            };

        it("triggers a new request when new round is clicked", () => {
            app.find(".controls-container__new-round").simulate("click");
            assert(sockJsClient.sendMessage.calledOnce, "Expected ws request to be made");
        });

        it("handles response when triggered", () => {
            triggerADummyMessage(TOPICS.playRound);
            assert.deepEqual(app.find("PlayedRoundTable").getElement().props.userResults,
                [message], "Expected results table to be updated with new result");

        });

        it("includes all round results in TotalRounds component", () => {
            const totals = {"totalRounds": 666, "totalWins": 0, "totalLoses": 1, "totalDraws": 665};
            app.find(".controls-container__total-rounds").simulate("click");
            app.find("SockJsClient").instance().props.onMessage(totals, TOPICS.totalRounds);
            setImmediate(() => {
                app.update();
                assert.deepEqual(app.find("TotalRounds").getElement().props.totalResults,
                    totals, "Expected results table to be updated with ws data");
            });
        });

        it("handles total results response when subscription received", () => {
            triggerADummyMessage(TOPICS.totalRounds);
            assert.deepEqual(app.instance().state.totalResults,
                message, "Expected total results message to be saved in the state");

        });

        it("ignores unknown topic messages", () => {
            triggerADummyMessage("unknown");
            assert.isTrue(app.instance().state.totalResults === undefined,
                "Expected total results not to be changed");
            assert.isTrue(app.instance().state.userResults.length === 0,
                "Expected user results not to be changed");
        });

        it("restarts entries when reset", () => {
            triggerADummyMessage(TOPICS.playRound);
            app.find(".controls-container__reset").simulate("click");
            assert.deepEqual(app.find("PlayedRoundTable").getElement().props.userResults,
                [], "Expected results table to be updated with no data");
        });

        it("links to total rounds page", () => {
            const app = mount(<App/>);
            history.push('/total-rounds');
            assert.exists(app.find(".controls-container--total-rounds"),
                "Expected Total Rounds component to be rendered");
        });

        it("links to user rounds page", () => {
            const app = mount(<App/>);
            history.push('/');
            assert.exists(app.find(".controls-container--user-rounds"),
                "Expected User Rounds component to be rendered");
        });
    });
});