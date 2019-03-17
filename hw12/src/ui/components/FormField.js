import React  from 'react'
import PropTypes from 'prop-types'

export function FormField(props) {
    const {forId, label, children} = props
    return <div className="form-group">
      <label htmlFor={forId}>{label}</label>
      {children}
    </div>
}

FormField.propTypes = {
  label: PropTypes.string.isRequired,
  forId: PropTypes.string.isRequired
}
