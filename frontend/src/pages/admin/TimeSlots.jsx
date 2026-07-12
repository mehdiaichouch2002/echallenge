import { useEffect, useState } from 'react'
import client from '../../api/client'
import Alert from '../../components/Alert'

export default function TimeSlots() {
  const [slots, setSlots] = useState([])
  const [form, setForm] = useState({ startTime: '', durationMinutes: 60 })
  const [error, setError] = useState(null)

  const load = () => client.get('/admin/timeslots').then(({ data }) => setSlots(data))

  useEffect(() => { load() }, [])

  const submit = async (e) => {
    e.preventDefault()
    setError(null)
    try {
      await client.post('/admin/timeslots', {
        startTime: form.startTime,
        durationMinutes: Number(form.durationMinutes),
      })
      setForm({ startTime: '', durationMinutes: 60 })
      load()
    } catch (err) {
      setError(err.response?.data?.error || 'Erreur lors de la création')
    }
  }

  const remove = async (id) => {
    if (!window.confirm('Supprimer ce créneau ?')) return
    try {
      await client.delete(`/admin/timeslots/${id}`)
      load()
    } catch (err) {
      setError(err.response?.data?.error || 'Suppression impossible (créneau réservé ?)')
    }
  }

  const fmt = (dt) => dt ? new Date(dt).toLocaleString('fr-FR') : '—'

  return (
    <>
      <div className="page-head"><h2>Gestion des créneaux</h2></div>
      {error && <Alert onClose={() => setError(null)}>{error}</Alert>}
      <div className="card">
        <h3>Nouveau créneau</h3>
        <form className="stack" onSubmit={submit}>
          <div className="form-row">
            <div>
              <label>Date et heure de début</label>
              <input type="datetime-local" value={form.startTime}
                onChange={(e) => setForm({ ...form, startTime: e.target.value })} required />
            </div>
            <div>
              <label>Durée (minutes)</label>
              <input type="number" min={10} value={form.durationMinutes}
                onChange={(e) => setForm({ ...form, durationMinutes: e.target.value })} required />
            </div>
          </div>
          <div><button className="btn" type="submit">Créer</button></div>
        </form>
      </div>
      <div className="card">
        <table>
          <thead>
            <tr><th>ID</th><th>Début</th><th>Fin</th><th>Durée</th><th>Statut</th><th>Actions</th></tr>
          </thead>
          <tbody>
            {slots.map((s) => (
              <tr key={s.id}>
                <td>{s.id}</td>
                <td>{fmt(s.startTime)}</td>
                <td>{fmt(s.endTime)}</td>
                <td>{s.durationMinutes} min</td>
                <td>{s.booked ? <span className="badge badge-red">Réservé</span> : <span className="badge badge-green">Libre</span>}</td>
                <td><button className="btn btn-sm btn-danger" onClick={() => remove(s.id)}>Supprimer</button></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  )
}
