import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../auth/AuthContext'
import Alert from '../components/Alert'

const initial = {
  email: '', password: '', firstName: '', lastName: '', school: '', field: '', gsm: '',
}

export default function Register() {
  const { register } = useAuth()
  const navigate = useNavigate()
  const [form, setForm] = useState(initial)
  const [error, setError] = useState(null)

  const set = (key) => (e) => setForm({ ...form, [key]: e.target.value })

  const submit = async (e) => {
    e.preventDefault()
    setError(null)
    try {
      await register(form)
      navigate('/candidate')
    } catch (err) {
      setError(err.response?.data?.error || "Échec de l'inscription")
    }
  }

  return (
    <div className="card auth-card" style={{ maxWidth: 520 }}>
      <h2>Créer un compte candidat</h2>
      {error && <Alert className="mt" onClose={() => setError(null)}>{error}</Alert>}
      <form className="stack mt" onSubmit={submit}>
        <div className="form-row">
          <div>
            <label>Prénom</label>
            <input value={form.firstName} onChange={set('firstName')} required />
          </div>
          <div>
            <label>Nom</label>
            <input value={form.lastName} onChange={set('lastName')} required />
          </div>
        </div>
        <div className="form-row">
          <div>
            <label>École</label>
            <input value={form.school} onChange={set('school')} required />
          </div>
          <div>
            <label>Filière</label>
            <input value={form.field} onChange={set('field')} />
          </div>
        </div>
        <div>
          <label>Téléphone (GSM)</label>
          <input value={form.gsm} onChange={set('gsm')} required />
        </div>
        <div>
          <label>Email</label>
          <input type="email" value={form.email} onChange={set('email')} required />
        </div>
        <div>
          <label>Mot de passe (min. 6 caractères)</label>
          <input type="password" value={form.password} onChange={set('password')} required minLength={6} />
        </div>
        <button className="btn" type="submit">S'inscrire</button>
      </form>
    </div>
  )
}
