import "./index.scss";
import PlayedRoundTable from './played-round-table';
import PropTypes from "prop-types";
import React from "react";
import history from "../history";

const UserRounds = (props) => {

    const renderControls = () => {
        const {newRound, resetRound} = props;
        return (<React.Fragment>
            <button onClick={() => history.push("/total-rounds")}
                className={"btn btn-dark btn-block controls-container__total-rounds"}>
                CHECK TOTAL ROUNDS
            </button>
            <button
                className={"btn btn-primary btn-block controls-container__new-round"}
                onClick={() => newRound()}>
                PLAY ROUND
            </button>
            <button
                className={"btn btn-secondary btn-block controls-container__reset"}
                onClick={() => resetRound()}>
                RESET
            </button>
        </React.Fragment>);
    };

    const renderResultsTable = () => {
        const {userResults} = props;
        return (<div className={"controls-container__results-table"}>
            <span className={"results-table__title"}>Rounds Played:</span>
            <PlayedRoundTable userResults={userResults}/>
        </div>);
    };

    return (<div className={"controls-container controls-container--user-rounds"}>
        {renderControls()}
        {renderResultsTable()}
    </div>);
};

export default UserRounds;

UserRounds.propTypes = {
    "userResults": PropTypes.arrayOf(PropTypes.shape({
        "player1Hand": PropTypes.string,
        "player2Hand": PropTypes.string,
        "gameResult": PropTypes.string
    })),
    "newRound": PropTypes.func,
    "resetRound": PropTypes.func
};

/* istanbul ignore next */
UserRounds.defaultProps = {
    "userResults": [],
    "newRound": () => {
    },
    "resetRound": () => {
    }
};