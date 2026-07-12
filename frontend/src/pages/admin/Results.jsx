import { useEffect, useState } from 'react'
import client from '../../api/client'

export default function Results() {
  const [results, setResults] = useState([])

  useEffect(() => {
    client.get('/admin/results').then(({ data }) => setResults(data))
  }, [])

  const fmt = (dt) => dt ? new Date(dt).toLocaleString('fr-FR') : '—'

  return (
    <>
      <div className="page-head"><h2>Résultats des candidats</h2></div>
      <div className="card">
        <table>
          <thead>
            <tr><th>ID</th><th>Candidat</th><th>Test</th><th>Score</th><th>Bonnes réponses</th><th>Terminé le</th><th>Verdict</th></tr>
          </thead>
          <tbody>
            {results.map((r) => (
              <tr key={r.id}>
                <td>{r.id}</td>
                <td>{r.testSession?.candidate?.firstName} {r.testSession?.candidate?.lastName}</td>
                <td>{r.testSession?.test?.name}</td>
                <td>{r.score?.toFixed(1)} %</td>
                <td>{r.correctAnswers} / {r.totalQuestions}</td>
                <td>{fmt(r.completionTime)}</td>
                <td>{r.passed
                  ? <span className="badge badge-green">Réussi</span>
                  : <span className="badge badge-red">Échoué</span>}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  )
}
