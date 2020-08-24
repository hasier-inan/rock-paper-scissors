const LodashModuleReplacementPlugin = require("lodash-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const autoPrefixer = require('autoprefixer');
const path = require('path');

module.exports = {
    mode: 'development',
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel-loader',
                query: {
                    plugins: ['lodash'],
                    presets: [['@babel/preset-env', { 'targets': { 'node': 6 } }]]
                }
            },
            {
                test: /\.jsx?$/,
                exclude: [/node_modules/, /dist/, /resources/],
                use: {
                    loader: 'babel-loader',
                }
            },
            {
                test: /\.(png|jpg|jpeg|gif|svg|woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?|(ttf|eot)(\?v=[0-9]\.[0-9]\.[0-9])?)$/,
                loader: "file-loader",
                options: {
                    context: path.resolve(__dirname, "./src/main/js"),
                    name: '[path][hash].[ext]'
                }
            },
            {
                test: /\.s?css$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    {
                        loader: "css-loader",
                        options: {
                            publicPath: './src/main/resources/views/',
                            minimize: {
                                safe: true
                            }
                        }
                    },
                    {
                        loader: "postcss-loader",
                        options: {
                            autoPrefixer: {
                                browsers: ["ie>=10,edge>=13,firefox>=44,chrome>=48,safari>=9.1,opera>=35,ios_saf>=9.3,and_chr>=67,and_ff>=60,samsung>=5"]
                            },
                            plugins: () => [
                            autoPrefixer
                        ]
                    }
            },
            {
                loader: "sass-loader",
                options: {}
            }
        ]
    }
]
},
resolve: {
    extensions: ['.js', '.jsx', '.scss', '.css', '.md', '.jpg', '.png', '.gif', '.svg', '.ttf', '.eot', '.woff', '.woff2']
},
stats: {
    colors: true,
        warnings: false
},
externals: {
    'react/addons': true,
        'react/lib/ExecutionEnvironment': true,
        'react/lib/ReactContext': true
},
performance: { hints: false },
plugins: [
    new LodashModuleReplacementPlugin({
        shorthands: true
    }),
    new MiniCssExtractPlugin({
        filename: `[name].css`
    })
],
    devtool: 'inline-source-map'
};
