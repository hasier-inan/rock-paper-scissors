import "./index.scss";
import FilterableTable from "react-filterable-table";
import PropTypes from "prop-types";
import React from "react";
import history from "../history";

const TotalRounds = (props) => {
    const renderControls = () => {
        return (
            <button onClick={() => history.push("/")}
                className={"btn btn-dark btn-block controls-container__user-rounds"}>
                HOME
            </button>);
    };

    const renderResultsTable = () => {
        const columns = [
                {"name": "totalRounds", "displayName": "Total Rounds Played"},
                {"name": "totalWins", "displayName": "Total Wins P1"},
                {"name": "totalLoses", "displayName": "Total Wins P2"},
                {"name": "totalDraws", "displayName": "Total Draws"}
            ],
            {totalResults} = props;

        return (<div className={"controls-container__results-table"}>
            <span className={"results-table__title"}>Stats:</span>
            <FilterableTable
                key={"totalResults"}
                className="total-rounds-results-table"
                data={[totalResults]}
                fields={columns}
                topPagerVisible={false}
                bottomPagerVisible={false}
                pageSize={1}
                pageSizes={null}
            />
        </div>);
    };

    return (<div className={"controls-container controls-container--total-rounds"}>
        {renderControls()}
        {renderResultsTable()}
    </div>);
};

export default TotalRounds;

TotalRounds.propTypes = {
    "totalResults": PropTypes.shape({
        "totalRounds": PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
        "totalWins": PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
        "totalLoses": PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
        "totalDraws": PropTypes.oneOfType([PropTypes.string, PropTypes.number])
    })
};

/* istanbul ignore next */
TotalRounds.defaultProps = {
    "totalResults": {"totalRounds": "-", "totalWins": "-", "totalLoses": "-", "totalDraws": "-"},
};