import WebSocketClient, {requestToTopic} from "../";
import {configure, mount} from "enzyme";
import Adapter from "enzyme-adapter-react-16";
import React from "react";
import {assert} from "chai";
import sinon from "sinon";

configure({"adapter": new Adapter()});

describe("WebSocketClient", () => {

    describe("Component rendering", () => {
        it("includes SockJsClient component with pre-defined topics", () => {
            const topics = ["some", "topics"];
            let comp = mount(<WebSocketClient topics={topics}/>);

            assert.deepEqual(comp.find("SockJsClient").instance().props.topics, topics,
                "Expected defined topics to match");
        });

        it("triggers message received callback", () => {
            const message = "aMessage",
                topic = "aTopic",
                spy = sinon.spy();
            let comp = mount(<WebSocketClient
                topics={[topic]}
                onMessageReceived={spy}
            />);
            comp.find("SockJsClient").instance().props.onMessage(message, topic);

            assert(spy.calledOnce, "Expected callback to be triggered");
            assert.deepEqual(spy.args[0], [message, topic], "Expected message and topic to match");
        });
    });

    describe("request to topic", () => {
        it("sends a message to specific topic one client mount", () => {
            const sockJsClient = {sendMessage: sinon.spy()},
                topic = "aTopic";
            let comp = mount(<WebSocketClient topics={[topic]}/>);
            assert.isNotNull(comp.find("SockJsClient").instance().props.sockJsClient,
                "Expected client reference to be set");

            comp.find("SockJsClient").getElement().ref(sockJsClient);
            requestToTopic(topic);

            assert(sockJsClient.sendMessage.calledOnce, "Expected message to be sent");
            assert.equal(sockJsClient.sendMessage.args[0][0], topic, "Expected request to topic to be made");
        });

        it("sends no message and throws no exception if client is undefined", () => {
            const sockJsClient = undefined,
                topic = "aTopic";
            let comp = mount(<WebSocketClient topics={[topic]}/>);
            assert.isNotNull(comp.find("SockJsClient").instance().props.sockJsClient,
                "Expected client reference to be set");

            comp.find("SockJsClient").getElement().ref(sockJsClient);
            requestToTopic(topic);
        });
    });
});