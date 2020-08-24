import App from "./app";
import React from "react";
import ReactDOM from "react-dom";

require("./index.scss");

ReactDOM.render(React.createElement(() => <App />), document.getElementById("container"));
