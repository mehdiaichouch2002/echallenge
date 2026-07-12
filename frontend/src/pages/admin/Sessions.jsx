import { useEffect, useState } from 'react'
import client from '../../api/client'
import Alert from '../../components/Alert'

const statusBadge = {
  NOT_STARTED: 'badge-blue',
  STARTED: 'badge-gray',
  COMPLETED: 'badge-green',
  EXPIRED: 'badge-red',
  CANCELLED: 'badge-red',
}

export default function Sessions() {
  const [sessions, setSessions] = useState([])
  const [error, setError] = useState(null)

  const load = () => client.get('/admin/sessions').then(({ data }) => setSessions(data))

  useEffect(() => { load() }, [])

  const cancel = async (id) => {
    if (!window.confirm('Annuler cette session ?')) return
    try {
      await client.put(`/admin/sessions/${id}/cancel`)
      load()
    } catch (err) {
      setError(err.response?.data?.error || "Impossible d'annuler")
    }
  }

  const fmt = (dt) => dt ? new Date(dt).toLocaleString('fr-FR') : '—'

  return (
    <>
      <div className="page-head"><h2>Sessions de test</h2></div>
      {error && <Alert onClose={() => setError(null)}>{error}</Alert>}
      <div className="card">
        <table>
          <thead>
            <tr><th>ID</th><th>Code</th><th>Candidat</th><th>Test</th><th>Créneau</th><th>Statut</th><th>Actions</th></tr>
          </thead>
          <tbody>
            {sessions.map((s) => (
              <tr key={s.id}>
                <td>{s.id}</td>
                <td>{s.sessionCode}</td>
                <td>{s.candidate?.firstName} {s.candidate?.lastName}</td>
                <td>{s.test?.name}</td>
                <td>{fmt(s.timeSlot?.startTime)}</td>
                <td><span className={`badge ${statusBadge[s.status] || 'badge-gray'}`}>{s.status}</span></td>
                <td>
                  {(s.status === 'NOT_STARTED' || s.status === 'STARTED') && (
                    <button className="btn btn-sm btn-danger" onClick={() => cancel(s.id)}>Annuler</button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  )
}
