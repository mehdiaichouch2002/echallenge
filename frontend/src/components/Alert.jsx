export default function Alert({ type = 'error', className = '', onClose, children }) {
  if (!children) return null
  return (
    <div className={`alert alert-${type} ${className}`.trim()} role="alert">
      <span>{children}</span>
      {onClose && (
        <button type="button" className="alert-close" onClick={onClose} aria-label="Fermer le message">
          ×
        </button>
      )}
    </div>
  )
}
