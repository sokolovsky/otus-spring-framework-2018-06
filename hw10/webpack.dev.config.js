var path = require('path');

module.exports = {
    entry: './src/ui/index.js',
    devtool: 'inline-source-map',
    output: {
        path: path.resolve(__dirname),
        filename: 'bundle.js',
        libraryTarget: 'umd'
    },

    devServer: {
        contentBase: path.join(__dirname, 'src/ui'),
        compress: true,
        port: 9000,
        host: 'localhost',
        historyApiFallback: true,
        open: true,
        before: (app) => {
            app.get('/text', (req, res) => res.send({
                text: 'Привяу!'
            }));
        }
    },

    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components|build)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['env']
                    }
                }
            }
        ]
    }
}
