import {configure, mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import App from "../";
import React from "react";
import {assert} from "chai";
import sinon from "sinon";

configure({"adapter": new Adapter()});

describe.only("App", () => {
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

        it("triggers a new request when new round is clicked", () => {
            app.find(".controls-container__new-round").simulate("click");
            assert(sockJsClient.sendMessage.calledOnce, "Expected ws request to be made");
        });

        it("handles response when triggered", () => {
            const message = "aMessage";
            app.find("SockJsClient").instance().props.onMessage(message);
            assert.equal(app.instance().state.message, message, "Expected state to be updated with new message");
        });
    });
});