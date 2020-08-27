let history;

/* istanbul ignore next */
if (typeof document !== 'undefined') {
    history = require("history").createBrowserHistory();
} else {
    history = require("history").createMemoryHistory();
}

export default history
