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

    handleSessionReset() {
        this.setState({"userResults": []});
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

    renderControls() {
        return (<React.Fragment>
            <button
                className={"btn btn-primary btn-block controls-container__new-round"}
                onClick={() => this.handleNewRound()}>
                PLAY ROUND
            </button>
            <button
                className={"btn btn-secondary btn-block controls-container__reset"}
                onClick={() => this.handleSessionReset()}>
                RESET
            </button>
        </React.Fragment>);
    }

    renderResultsTable() {
        const {userResults} = this.state;
        return (<div className={"controls-container__results-table"}>
            <span className={"results-table__title"}>Rounds Played:</span>
            <PlayedRoundTable userResults={userResults}/>
        </div>);
    }

    render() {
        return (<div className={"controls-container"}>
            {this.renderControls()}
            {this.renderSockJs()}
            {this.renderResultsTable()}
        </div>);
    }
}

export default App;