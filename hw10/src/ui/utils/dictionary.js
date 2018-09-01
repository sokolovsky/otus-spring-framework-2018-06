export const Dictionary = function(obj) {
  this.obj = obj
}

Dictionary.prototype.getKeys = function() {
  return Object.keys(this.obj)
}

Dictionary.prototype.getValues = function() {
  const th = this
  return this.getKeys().map(k => th.obj[k])
}

Dictionary.of = (obj) => {
  return new Dictionary(obj)
}
