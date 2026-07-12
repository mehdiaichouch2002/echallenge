import { useEffect, useState } from 'react'
import client from '../../api/client'
import Alert from '../../components/Alert'

const emptyOption = () => ({ optionText: '', correct: false, displayOrder: 0 })
const emptyForm = { themeId: '', questionTypeId: '', questionText: '', explanation: '', points: 1 }

export default function Questions() {
  const [questions, setQuestions] = useState([])
  const [themes, setThemes] = useState([])
  const [types, setTypes] = useState([])
  const [filterTheme, setFilterTheme] = useState('')
  const [form, setForm] = useState(emptyForm)
  const [options, setOptions] = useState([emptyOption(), emptyOption()])
  const [editing, setEditing] = useState(null)
  const [error, setError] = useState(null)

  const load = (themeId = filterTheme) =>
    client.get('/admin/questions', { params: themeId ? { themeId } : {} })
      .then(({ data }) => setQuestions(data))

  useEffect(() => {
    client.get('/admin/themes').then(({ data }) => setThemes(data))
    client.get('/admin/questions/types').then(({ data }) => setTypes(data))
    load()
  }, [])

  const changeFilter = (value) => {
    setFilterTheme(value)
    load(value)
  }

  const setOpt = (i, key, value) => {
    const next = [...options]
    next[i] = { ...next[i], [key]: value }
    setOptions(next)
  }

  const submit = async (e) => {
    e.preventDefault()
    setError(null)
    const payload = {
      theme: { id: Number(form.themeId) },
      questionType: { id: Number(form.questionTypeId) },
      questionText: form.questionText,
      explanation: form.explanation,
      points: Number(form.points),
      active: true,
      options: options
        .filter((o) => o.optionText.trim() !== '')
        .map((o, i) => ({ ...o, displayOrder: i + 1 })),
    }
    try {
      if (editing) {
        await client.put(`/admin/questions/${editing}`, payload)
      } else {
        await client.post('/admin/questions', payload)
      }
      reset()
      load()
    } catch (err) {
      setError(err.response?.data?.error || 'Erreur lors de la sauvegarde')
    }
  }

  const reset = () => {
    setEditing(null)
    setForm(emptyForm)
    setOptions([emptyOption(), emptyOption()])
  }

  const edit = (q) => {
    setEditing(q.id)
    setForm({
      themeId: q.theme.id,
      questionTypeId: q.questionType.id,
      questionText: q.questionText,
      explanation: q.explanation || '',
      points: q.points,
    })
    setOptions(q.options.length ? q.options.map((o) => ({
      id: o.id, optionText: o.optionText, correct: o.correct, displayOrder: o.displayOrder,
    })) : [emptyOption(), emptyOption()])
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  const remove = async (id) => {
    if (!window.confirm('Supprimer cette question ?')) return
    try {
      await client.delete(`/admin/questions/${id}`)
      load()
    } catch (err) {
      setError(err.response?.data?.error || 'Suppression impossible (question déjà utilisée ?)')
    }
  }

  return (
    <>
      <div className="page-head">
        <h2>Gestion des questions</h2>
        <select style={{ maxWidth: 220 }} value={filterTheme} onChange={(e) => changeFilter(e.target.value)}>
          <option value="">Tous les thèmes</option>
          {themes.map((t) => <option key={t.id} value={t.id}>{t.name}</option>)}
        </select>
      </div>
      {error && <Alert onClose={() => setError(null)}>{error}</Alert>}

      <div className={editing ? 'card card-editing' : 'card'}>
        <h3>{editing ? `Modifier la question n°${editing}` : 'Nouvelle question'}</h3>
        <form className="stack" onSubmit={submit}>
          <div className="form-row">
            <div>
              <label>Thème</label>
              <select value={form.themeId} onChange={(e) => setForm({ ...form, themeId: e.target.value })} required>
                <option value="">— choisir —</option>
                {themes.map((t) => <option key={t.id} value={t.id}>{t.name}</option>)}
              </select>
            </div>
            <div>
              <label>Type</label>
              <select value={form.questionTypeId} onChange={(e) => setForm({ ...form, questionTypeId: e.target.value })} required>
                <option value="">— choisir —</option>
                {types.map((t) => <option key={t.id} value={t.id}>{t.name}</option>)}
              </select>
            </div>
          </div>
          <div>
            <label>Énoncé</label>
            <textarea rows={2} value={form.questionText} onChange={(e) => setForm({ ...form, questionText: e.target.value })} required />
          </div>
          <div className="form-row">
            <div>
              <label>Points</label>
              <input type="number" min={1} value={form.points} onChange={(e) => setForm({ ...form, points: e.target.value })} />
            </div>
            <div>
              <label>Explication (optionnel)</label>
              <input value={form.explanation} onChange={(e) => setForm({ ...form, explanation: e.target.value })} />
            </div>
          </div>
          <div>
            <label>Options de réponse (cochez la/les bonne(s) réponse(s))</label>
            {options.map((o, i) => (
              <div key={i} style={{ display: 'flex', gap: '0.5rem', marginBottom: '0.4rem', alignItems: 'center' }}>
                <input placeholder={`Option ${i + 1}`} value={o.optionText}
                  onChange={(e) => setOpt(i, 'optionText', e.target.value)} />
                <label style={{ display: 'flex', alignItems: 'center', gap: '0.3rem', whiteSpace: 'nowrap', margin: 0 }}>
                  <input type="checkbox" style={{ width: 'auto' }} checked={o.correct}
                    onChange={(e) => setOpt(i, 'correct', e.target.checked)} />
                  Correcte
                </label>
                <button type="button" className="btn btn-sm btn-danger"
                  onClick={() => setOptions(options.filter((_, j) => j !== i))}>✕</button>
              </div>
            ))}
            <button type="button" className="btn btn-sm btn-outline" onClick={() => setOptions([...options, emptyOption()])}>
              + Ajouter une option
            </button>
          </div>
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
            <tr><th>ID</th><th>Énoncé</th><th>Thème</th><th>Type</th><th>Options</th><th>Actions</th></tr>
          </thead>
          <tbody>
            {questions.map((q) => (
              <tr key={q.id}>
                <td>{q.id}</td>
                <td>{q.questionText}</td>
                <td>{q.theme?.name}</td>
                <td>{q.questionType?.name}</td>
                <td>{q.options?.length}</td>
                <td>
                  <button className="btn btn-sm btn-outline" onClick={() => edit(q)}>Modifier</button>{' '}
                  <button className="btn btn-sm btn-danger" onClick={() => remove(q.id)}>Supprimer</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  )
}
