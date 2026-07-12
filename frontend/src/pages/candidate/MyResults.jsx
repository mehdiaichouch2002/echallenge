import { useEffect, useState } from 'react'
import client from '../../api/client'

export default function MyResults() {
  const [results, setResults] = useState([])

  useEffect(() => {
    client.get('/candidate/results').then(({ data }) => setResults(data))
  }, [])

  const fmt = (dt) => dt ? new Date(dt).toLocaleString('fr-FR') : '—'

  return (
    <>
      <div className="page-head"><h2>Mes résultats</h2></div>
      {results.length === 0 && (
        <div className="card"><p className="muted">Aucun résultat pour le moment.</p></div>
      )}
      <div className="grid">
        {results.map((r) => (
          <div className="card stat" key={r.id}>
            <h3>{r.testSession?.test?.name}</h3>
            <div className="value">{r.score?.toFixed(1)} %</div>
            <div className="label">{r.correctAnswers} / {r.totalQuestions} bonnes réponses</div>
            <p className="mt">
              {r.passed
                ? <span className="stamp stamp-pass">Réussi</span>
                : <span className="stamp stamp-fail">Échoué</span>}
            </p>
            <p className="muted" style={{ fontSize: '0.8rem', marginTop: '0.5rem' }}>{fmt(r.completionTime)}</p>
          </div>
        ))}
      </div>
    </>
  )
}
