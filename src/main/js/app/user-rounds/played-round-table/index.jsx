import "./index.scss";
import FilterableTable from "react-filterable-table";
import PropTypes from "prop-types";
import React from "react";

const PlayedRoundTable = (props) => {
    const columns = [
            {"name": "player1Hand", "displayName": "Player1 selection"},
            {"name": "player2Hand", "displayName": "Player2 selection"},
            {"name": "gameResult", "displayName": "Result"}
        ],
        {userResults} = props;

    return <FilterableTable
        key={"userResults"}
        className="user-round-results-table"
        data={userResults}
        fields={columns}
        topPagerVisible={false}
        bottomPagerVisible={false}
        pageSize={Number.MAX_SAFE_INTEGER}
        pageSizes={null}
        recordCountName={"round"}
        recordCountNamePlural={"rounds"}
        trClassName={(record, index) => index % 2 == 0 ? 'even' : 'odd'}
    />;
};

export default PlayedRoundTable;

PlayedRoundTable.propTypes = {
    "userResults": PropTypes.arrayOf(PropTypes.shape({
        "player1Hand": PropTypes.string,
        "player2Hand": PropTypes.string,
        "gameResult": PropTypes.string
    }))
};

/* istanbul ignore next */
PlayedRoundTable.defaultProps = {
    "userResults": []
};