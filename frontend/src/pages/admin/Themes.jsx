import { useEffect, useState } from 'react'
import client from '../../api/client'
import Alert from '../../components/Alert'

const empty = { name: '', description: '' }

export default function Themes() {
  const [themes, setThemes] = useState([])
  const [form, setForm] = useState(empty)
  const [editing, setEditing] = useState(null)
  const [error, setError] = useState(null)

  const load = () => client.get('/admin/themes').then(({ data }) => setThemes(data))

  useEffect(() => { load() }, [])

  const submit = async (e) => {
    e.preventDefault()
    setError(null)
    try {
      if (editing) {
        await client.put(`/admin/themes/${editing}`, form)
      } else {
        await client.post('/admin/themes', form)
      }
      setForm(empty)
      setEditing(null)
      load()
    } catch (err) {
      setError(err.response?.data?.error || 'Erreur lors de la sauvegarde')
    }
  }

  const edit = (theme) => {
    setEditing(theme.id)
    setForm({ name: theme.name, description: theme.description || '' })
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  const remove = async (id) => {
    if (!window.confirm('Supprimer ce thème ?')) return
    try {
      await client.delete(`/admin/themes/${id}`)
      load()
    } catch (err) {
      setError(err.response?.data?.error || 'Suppression impossible (thème utilisé par des questions ?)')
    }
  }

  return (
    <>
      <div className="page-head"><h2>Gestion des thèmes</h2></div>
      {error && <Alert onClose={() => setError(null)}>{error}</Alert>}
      <div className={editing ? 'card card-editing' : 'card'}>
        <h3>{editing ? `Modifier le thème « ${form.name} »` : 'Nouveau thème'}</h3>
        <form className="stack" onSubmit={submit}>
          <div className="form-row">
            <div>
              <label>Nom</label>
              <input value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required />
            </div>
            <div>
              <label>Description</label>
              <input value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} />
            </div>
          </div>
          <div>
            <button className="btn" type="submit">{editing ? 'Mettre à jour' : 'Créer'}</button>
            {editing && (
              <button className="btn btn-outline" type="button" style={{ marginLeft: '0.5rem' }}
                onClick={() => { setEditing(null); setForm(empty) }}>
                Annuler
              </button>
            )}
          </div>
        </form>
      </div>
      <div className="card">
        <table>
          <thead>
            <tr><th>ID</th><th>Nom</th><th>Description</th><th>Actions</th></tr>
          </thead>
          <tbody>
            {themes.map((t) => (
              <tr key={t.id}>
                <td>{t.id}</td>
                <td>{t.name}</td>
                <td>{t.description}</td>
                <td>
                  <button className="btn btn-sm btn-outline" onClick={() => edit(t)}>Modifier</button>{' '}
                  <button className="btn btn-sm btn-danger" onClick={() => remove(t.id)}>Supprimer</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  )
}
