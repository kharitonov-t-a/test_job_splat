const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
    entry:  path.join(__dirname, 'src', 'main', 'resources', 'static', 'vue', 'main.ts'),
    module: {
        rules: [
            {
                test: /\.ts$/,
                loader: 'ts-loader',
                options: {
                    appendTsSuffixTo: [/\.vue$/]
                }
            },
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.css$/,
                use: [
                    'vue-style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.less$/,
                use: [
                    'vue-style-loader',
                    'css-loader',
                    'less-loader'
                ]
            }

        ]
    },
    plugins: [
        new VueLoaderPlugin(),
    ],
    output: {
        path: path.join(__dirname, 'src', 'main', 'resources', 'static', 'js'),
        filename: 'main.js',
    },
    resolve: {
        extensions: ['.css', '.less', '.js', ".ts", ".vue"],
        modules: [
            path.join(__dirname, 'src', 'main', 'resources', 'static', 'vue'),
            path.join(__dirname, 'node_modules'),
        ],
    }
};