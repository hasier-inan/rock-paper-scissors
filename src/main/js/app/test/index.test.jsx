import {configure, mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import App from "../";
import React from "react";
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

    describe("Component rendering", () => {

        const message = {"player1Hand": "ROCK", "player2Hand": "ROCK", "gameResult": "DRAW"},
            triggerADummyMessage = () => {
                app.find("SockJsClient").instance().props.onMessage(message);
            };

        it("triggers a new request when new round is clicked", () => {
            app.find(".controls-container__new-round").simulate("click");
            assert(sockJsClient.sendMessage.calledOnce, "Expected ws request to be made");
        });

        it("handles response when triggered", () => {
            triggerADummyMessage();
            assert.deepEqual(app.find("PlayedRoundTable").getElement().props.userResults,
                [message], "Expected results table to be updated with new result");

        });

        it("restarts entries when reset", () => {
            triggerADummyMessage();
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