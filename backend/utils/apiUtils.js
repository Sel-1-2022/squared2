const formatError = message => {
  return {error: message}
}


module.exports = {
  formatError,
  invalidQuery: formatError("invalid Query")
}