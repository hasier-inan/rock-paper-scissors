import React, {Component} from "react";
import {STOMP_DETAILS, TOPICS} from "./topic-constants"
import WebSocketClient, {requestToTopic} from "./web-socket-client";

class App extends Component {

    handleNewRound() {
        requestToTopic(`${STOMP_DETAILS.appPrefix}${TOPICS.playRound}`);
    }

    handleSockJsMessage(msg) {
        this.setState({"message": msg});
    }

    renderSockJs() {
        let topicNames = [`${STOMP_DETAILS.userPrefix}${STOMP_DETAILS.brokerPrefix}${TOPICS.playRound}`];
        return <WebSocketClient
            topics={topicNames}
            stompDetails={STOMP_DETAILS}
            onMessageReceived={(message, topic) => this.handleSockJsMessage(message, topic)}
        />;
    }

    render() {
        return (<div className={"controls-container"}>
            <button
                className={"controls-container__new-round"}
                onClick={() => this.handleNewRound()}>
                PLAY ROUND
            </button>
            {this.renderSockJs()}
        </div>);
    }
}

export default App;