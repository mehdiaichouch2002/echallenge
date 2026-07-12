import { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import client from '../../api/client'

const statusBadge = {
  NOT_STARTED: 'badge-blue',
  STARTED: 'badge-gray',
  COMPLETED: 'badge-green',
  EXPIRED: 'badge-red',
  CANCELLED: 'badge-red',
}

export default function CandidateDashboard() {
  const [sessions, setSessions] = useState([])
  const navigate = useNavigate()

  useEffect(() => {
    client.get('/candidate/sessions').then(({ data }) => setSessions(data))
  }, [])

  const fmt = (dt) => dt ? new Date(dt).toLocaleString('fr-FR') : '—'

  return (
    <>
      <div className="page-head">
        <h2>Mes sessions de test</h2>
        <Link className="btn" to="/candidate/enroll">+ S'inscrire à un test</Link>
      </div>
      <div className="card">
        {sessions.length === 0 && (
          <p className="muted">
            Vous n'êtes inscrit à aucun test. <Link to="/candidate/enroll">Inscrivez-vous</Link> pour commencer.
          </p>
        )}
        {sessions.length > 0 && (
          <table>
            <thead>
              <tr><th>Code</th><th>Test</th><th>Créneau</th><th>Statut</th><th>Action</th></tr>
            </thead>
            <tbody>
              {sessions.map((s) => (
                <tr key={s.id}>
                  <td>{s.sessionCode}</td>
                  <td>{s.test?.name}</td>
                  <td>{fmt(s.timeSlot?.startTime)}</td>
                  <td><span className={`badge ${statusBadge[s.status] || 'badge-gray'}`}>{s.status}</span></td>
                  <td>
                    {(s.status === 'NOT_STARTED' || s.status === 'STARTED') && (
                      <button className="btn btn-sm" onClick={() => navigate(`/candidate/take-test/${s.id}`)}>
                        {s.status === 'NOT_STARTED' ? 'Passer le test' : 'Reprendre'}
                      </button>
                    )}
                    {s.status === 'COMPLETED' && (
                      <Link className="btn btn-sm btn-outline" to="/candidate/results">Voir le résultat</Link>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </>
  )
}
