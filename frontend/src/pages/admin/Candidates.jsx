import { useEffect, useState } from 'react'
import client from '../../api/client'
import Alert from '../../components/Alert'

export default function Candidates() {
  const [candidates, setCandidates] = useState([])
  const [error, setError] = useState(null)

  const load = () => client.get('/admin/candidates').then(({ data }) => setCandidates(data))

  useEffect(() => { load() }, [])

  const confirm = async (id) => {
    await client.put(`/admin/candidates/${id}/confirm`)
    load()
  }

  const remove = async (id) => {
    if (!window.confirm('Supprimer ce candidat ?')) return
    try {
      await client.delete(`/admin/candidates/${id}`)
      load()
    } catch (err) {
      setError(err.response?.data?.error || 'Suppression impossible (sessions existantes ?)')
    }
  }

  return (
    <>
      <div className="page-head"><h2>Gestion des candidats</h2></div>
      {error && <Alert onClose={() => setError(null)}>{error}</Alert>}
      <div className="card">
        <table>
          <thead>
            <tr><th>ID</th><th>Nom</th><th>Email</th><th>École</th><th>Filière</th><th>GSM</th><th>Code</th><th>Statut</th><th>Actions</th></tr>
          </thead>
          <tbody>
            {candidates.map((c) => (
              <tr key={c.id}>
                <td>{c.id}</td>
                <td>{c.firstName} {c.lastName}</td>
                <td>{c.email}</td>
                <td>{c.school}</td>
                <td>{c.field}</td>
                <td>{c.gsm}</td>
                <td>{c.code}</td>
                <td>{c.confirmed
                  ? <span className="badge badge-green">Confirmé</span>
                  : <span className="badge badge-gray">En attente</span>}</td>
                <td>
                  {!c.confirmed && (
                    <button className="btn btn-sm" onClick={() => confirm(c.id)}>Confirmer</button>
                  )}{' '}
                  <button className="btn btn-sm btn-danger" onClick={() => remove(c.id)}>Supprimer</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  )
}
