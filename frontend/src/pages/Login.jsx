import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../auth/AuthContext'
import Alert from '../components/Alert'

const DEMO_ACCOUNTS = {
  admin: { email: 'admin@echallenge.com', password: 'admin123' },
  candidate: { email: 'candidat@echallenge.com', password: 'candidat123' },
}

export default function Login() {
  const { login } = useAuth()
  const navigate = useNavigate()
  const [email, setEmail] = useState(DEMO_ACCOUNTS.admin.email)
  const [password, setPassword] = useState(DEMO_ACCOUNTS.admin.password)
  const [error, setError] = useState(null)

  const fillDemo = (key) => {
    setEmail(DEMO_ACCOUNTS[key].email)
    setPassword(DEMO_ACCOUNTS[key].password)
    setError(null)
  }

  const submit = async (e) => {
    e.preventDefault()
    setError(null)
    try {
      const user = await login(email, password)
      navigate(user.role === 'ADMIN' ? '/admin' : '/candidate')
    } catch (err) {
      setError(err.response?.data?.error || 'Échec de la connexion')
    }
  }

  return (
    <div className="card auth-card">
      <h2>Connexion</h2>
      {error && <Alert className="mt" onClose={() => setError(null)}>{error}</Alert>}
      <form className="stack mt" onSubmit={submit}>
        <div>
          <label>Email</label>
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        </div>
        <div>
          <label>Mot de passe</label>
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </div>
        <button className="btn" type="submit">Se connecter</button>
      </form>
      <div className="demo-accounts mt">
        <span className="muted">Comptes de démonstration :</span>
        <button type="button" className="btn btn-sm btn-outline" onClick={() => fillDemo('admin')}>
          Admin
        </button>
        <button type="button" className="btn btn-sm btn-outline" onClick={() => fillDemo('candidate')}>
          Candidat
        </button>
      </div>
      <p className="hint">Pas encore de compte ? <a href="/register">Inscrivez-vous</a></p>
    </div>
  )
}
