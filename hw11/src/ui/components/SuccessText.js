import React  from 'react'
import PropTypes from 'prop-types'

/**
 * @return {string}
 */
export function SuccessText(props) {
  const {text, success} = props
  if (!success) {
    return ""
  }
  let { style } = props
  style = style || {}
  return <span style={{...style}} className="text-success">{text}</span>
}

SuccessText.propTypes = {
  text: PropTypes.string.isRequired,
  success: PropTypes.bool.isRequired,
  style: PropTypes.object
}
