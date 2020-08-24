const config = require('./webpack.base.config.js');

config.entry = ['./src/main/js/preview.jsx'];
config.output = {
    path: '/',
    publicPath: '/assets/',
    filename: 'bundle.js'
};

config.devServer = {
    contentBase: "./src/main/resources/views",
    historyApiFallback: true,
    compress: true,
    stats: 'errors-only',
    open: true
};

module.exports = config;
