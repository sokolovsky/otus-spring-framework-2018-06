var path = require('path')
var webpack = require('webpack');

module.exports = {
  entry: './src/ui/index.js',
  devtool: 'inline-source-map',
  output: {
    path: path.resolve(__dirname),
    filename: 'bundle.js',
    libraryTarget: 'umd',
  },

  devServer: {
    contentBase: path.join(__dirname, 'src/ui'),
    compress: true,
    port: 8080,
    host: 'localhost',
    historyApiFallback: true,
    open: true
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: JSON.stringify('mock'),
      },
      '__API__': JSON.stringify('http://localhost:3000/rest')
    })
  ],
  module: {
    loaders: [
      {
        test: /\.js$/,
        exclude: /(node_modules|bower_components|build)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['env'],
          },
        },
      },
    ],
  },
}
