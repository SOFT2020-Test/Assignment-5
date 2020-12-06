const winston = require('winston');
const { format } = winston;
const { combine, timestamp, prettyPrint, label, json } = format;
const logger = winston.createLogger({
  format: combine(
    timestamp({
      format: 'YYYY-MM-DD HH:mm:ss'
    }),
    json(),
    prettyPrint(),
  ),
  transports: [
    new winston.transports.Console(),
    new winston.transports.File({ filename: './log/catlog.log', level: 'info' })
  ],
});

module.exports = logger;