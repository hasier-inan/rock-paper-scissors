import PropTypes from "prop-types";
import React from "react";
import SockJsClient from 'react-stomp';

let sockJsClient;

export const requestToTopic = (topic) => {
    if (!sockJsClient) {
        /* istanbul ignore next */
        setTimeout(() => {requestToTopic(topic)}, 500);
        return;
    }
    sockJsClient.sendMessage(topic);
}

const WebSocketClient = (props) => {
    const debug = false,
        origin = window.location.origin,
        {topics, onMessageReceived, stompDetails} = props;
    return (
        <SockJsClient
            id="sockJsClient"
            url={`${origin}${stompDetails.wsEndpoint}`}
            topics={topics}
            onMessage={(msg, topic) => onMessageReceived(msg, topic)}
            debug={debug}
            ref={(client) => {
                sockJsClient = client
            }}
        />
    );
}

WebSocketClient.propTypes = {
    "topics": PropTypes.arrayOf(PropTypes.string).isRequired,
    "stompDetails": PropTypes.shape({
        "appPrefix": PropTypes.string,
        "brokerPrefix": PropTypes.string,
        "userPrefix": PropTypes.string,
        "wsEndpoint": PropTypes.string
    }),
    "onMessageReceived": PropTypes.func
};

/* istanbul ignore next */
WebSocketClient.defaultProps = {
    "onMessageReceived": () => {
    },
    "stompDetails": {
        "appPrefix": "",
        "brokerPrefix": "",
        "userPrefix": "",
        "wsEndpoint": ""
    },
};

export default WebSocketClient;