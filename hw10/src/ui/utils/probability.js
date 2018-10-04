
export const Probability = function() {
  this.cases = []
  this.commonWeigth = 0
}

Probability.prototype.addCase = function(result, weight) {
  this.cases.push({result, weight})
  this.commonWeigth = this.cases.map((i) => i.weight).reduce((p, i) => i + p)
}

Probability.prototype.getResult = function() {
  const point = Math.floor(Math.random() * Math.floor(this.commonWeigth))
  let iWeigth = 0
  for (let i = 0; i < this.cases.length; i++) {
    const threshold = iWeigth + this.cases[i].weight

    if (point > threshold) {
      iWeigth = threshold
      continue
    }

    return this.cases[i].result
  }
  return {}
}
