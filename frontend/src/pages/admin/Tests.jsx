import { useEffect, useState } from 'react'
import client from '../../api/client'
import Alert from '../../components/Alert'

const emptyForm = { name: '', description: '', totalDurationMinutes: 30, questionDurationSeconds: 60, active: true }

export default function Tests() {
  const [tests, setTests] = useState([])
  const [themes, setThemes] = useState([])
  const [form, setForm] = useState(emptyForm)
  const [counts, setCounts] = useState({})
  const [editing, setEditing] = useState(null)
  const [error, setError] = useState(null)

  const load = () => client.get('/admin/tests').then(({ data }) => setTests(data))

  useEffect(() => {
    client.get('/admin/themes').then(({ data }) => setThemes(data))
    load()
  }, [])

  const totalQuestions = Object.values(counts).reduce((sum, n) => sum + (Number(n) || 0), 0)

  const submit = async (e) => {
    e.preventDefault()
    setError(null)
    const themeQuestionCounts = {}
    Object.entries(counts).forEach(([themeId, n]) => {
      if (Number(n) > 0) themeQuestionCounts[themeId] = Number(n)
    })
    const payload = { ...form, totalQuestions, themeQuestionCounts }
    try {
      if (editing) {
        await client.put(`/admin/tests/${editing}`, payload)
      } else {
        await client.post('/admin/tests', payload)
      }
      reset()
      load()
    } catch (err) {
      setError(err.response?.data?.error || 'Erreur lors de la sauvegarde')
    }
  }

  const reset = () => { setEditing(null); setForm(emptyForm); setCounts({}) }

  const edit = (t) => {
    setEditing(t.id)
    setForm({
      name: t.name, description: t.description || '',
      totalDurationMinutes: t.totalDurationMinutes,
      questionDurationSeconds: t.questionDurationSeconds,
      active: t.active,
    })
    setCounts(t.themeQuestionCounts || {})
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  const remove = async (id) => {
    if (!window.confirm('Supprimer ce test ?')) return
    try {
      await client.delete(`/admin/tests/${id}`)
      load()
    } catch (err) {
      setError(err.response?.data?.error || 'Suppression impossible (sessions existantes ?)')
    }
  }

  return (
    <>
      <div className="page-head"><h2>Gestion des tests</h2></div>
      {error && <Alert onClose={() => setError(null)}>{error}</Alert>}

      <div className={editing ? 'card card-editing' : 'card'}>
        <h3>{editing ? `Modifier le test « ${form.name} »` : 'Nouveau test'}</h3>
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
          <div className="form-row">
            <div>
              <label>Durée totale (minutes)</label>
              <input type="number" min={1} value={form.totalDurationMinutes}
                onChange={(e) => setForm({ ...form, totalDurationMinutes: Number(e.target.value) })} required />
            </div>
            <div>
              <label>Durée par question (secondes)</label>
              <input type="number" min={10} value={form.questionDurationSeconds}
                onChange={(e) => setForm({ ...form, questionDurationSeconds: Number(e.target.value) })} required />
            </div>
          </div>
          <div>
            <label>Nombre de questions par thème (total : {totalQuestions})</label>
            <div className="grid" style={{ gridTemplateColumns: 'repeat(auto-fill, minmax(180px, 1fr))' }}>
              {themes.map((t) => (
                <div key={t.id} style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                  <span style={{ flex: 1 }}>{t.name}</span>
                  <input type="number" min={0} style={{ width: 70 }} value={counts[t.id] ?? ''}
                    onChange={(e) => setCounts({ ...counts, [t.id]: e.target.value })} />
                </div>
              ))}
            </div>
          </div>
          <label style={{ display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
            <input type="checkbox" style={{ width: 'auto' }} checked={form.active}
              onChange={(e) => setForm({ ...form, active: e.target.checked })} />
            Test actif (visible par les candidats)
          </label>
          <div>
            <button className="btn" type="submit">{editing ? 'Mettre à jour' : 'Créer'}</button>
            {editing && (
              <button className="btn btn-outline" type="button" style={{ marginLeft: '0.5rem' }} onClick={reset}>Annuler</button>
            )}
          </div>
        </form>
      </div>

      <div className="card">
        <table>
          <thead>
            <tr><th>ID</th><th>Nom</th><th>Durée</th><th>Questions</th><th>Statut</th><th>Actions</th></tr>
          </thead>
          <tbody>
            {tests.map((t) => (
              <tr key={t.id}>
                <td>{t.id}</td>
                <td>{t.name}</td>
                <td>{t.totalDurationMinutes} min</td>
                <td>{t.totalQuestions}</td>
                <td>{t.active ? <span className="badge badge-green">Actif</span> : <span className="badge badge-gray">Inactif</span>}</td>
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
