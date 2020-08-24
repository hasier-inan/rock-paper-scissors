const config = require('./webpack.base.config.js');
const path = require('path');

config.entry = {
    bundle: './src/main/js/index.jsx'
};
config.devtool = 'sourcemaps';
config.output = {
    path: path.resolve(__dirname, 'src/main/resources/views/dist'),
    filename: '[name].js'
};

module.exports = config;
