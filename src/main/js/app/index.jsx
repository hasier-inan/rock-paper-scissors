import "./index.scss";
import React, {Component} from "react";
import {STOMP_DETAILS, TOPICS} from "./topic-constants"
import WebSocketClient, {requestToTopic} from "./web-socket-client";
import PlayedRoundTable from './played-round-table';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {"userResults": []};
    }

    handleNewRound() {
        requestToTopic(`${STOMP_DETAILS.appPrefix}${TOPICS.playRound}`);
    }

    handleSockJsMessage(results) {
        let {userResults} = this.state;
        userResults.push(results);
        this.setState({"userResults": userResults});
    }

    renderSockJs() {
        let topicNames = [`${STOMP_DETAILS.userPrefix}${STOMP_DETAILS.brokerPrefix}${TOPICS.playRound}`];
        return <WebSocketClient
            topics={topicNames}
            stompDetails={STOMP_DETAILS}
            onMessageReceived={(message, topic) => this.handleSockJsMessage(message, topic)}
        />;
    }

    renderResultsTable() {
        const {userResults} = this.state;
        return (<div className={"controls-container__results-table"}>
            <span className={"results-table__title"}>Rounds Played:</span>
            <PlayedRoundTable userResults={userResults} />
        </div>);
    }

    render() {
        return (<div className={"controls-container"}>

                    <button
                        className={"btn btn-primary btn-block controls-container__new-round"}
                        onClick={() => this.handleNewRound()}>
                        PLAY ROUND
                    </button>
            {this.renderSockJs()}
            {this.renderResultsTable()}
        </div>);
    }
}

export default App;