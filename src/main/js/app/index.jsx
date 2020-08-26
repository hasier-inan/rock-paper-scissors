import "./index.scss";
import React, {Component} from "react";
import {Router, Route} from "react-router-dom";
import {STOMP_DETAILS, TOPICS} from "./topic-constants"
import WebSocketClient, {requestToTopic} from "./web-socket-client";
import TotalRounds from "./total-rounds";
import UserRounds from "./user-rounds";
import history from "./history";

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

    renderUserRounds() {
        const {userResults} = this.state;
        return <UserRounds
            newRound={() => this.handleNewRound()}
            resetRound={() => this.handleSessionReset()}
            userResults={userResults}
        />;
    }

    renderTotalRounds() {
        return <TotalRounds
        />;
    }

    render() {
        const topicNames = [`${STOMP_DETAILS.userPrefix}${STOMP_DETAILS.brokerPrefix}${TOPICS.playRound}`];
        return (
            <React.Fragment>
                <WebSocketClient
                    topics={topicNames}
                    stompDetails={STOMP_DETAILS}
                    onMessageReceived={(message, topic) => this.handleSockJsMessage(message, topic)}
                />
                <Router history={history}>
                    <main>
                        <Route
                            exact
                            path="/"
                            render={() => this.renderUserRounds()}
                        />
                        <Route
                            exact
                            path="/total-rounds"
                            render={() => this.renderTotalRounds()}
                        />
                    </main>
                </Router>
            </React.Fragment>);
    }
}

export default App;