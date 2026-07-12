import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import client from '../../api/client'

export default function AdminDashboard() {
  const [stats, setStats] = useState({ themes: 0, questions: 0, tests: 0, candidates: 0, sessions: 0, results: 0 })

  useEffect(() => {
    Promise.all([
      client.get('/admin/themes'),
      client.get('/admin/questions'),
      client.get('/admin/tests'),
      client.get('/admin/candidates'),
      client.get('/admin/sessions'),
      client.get('/admin/results'),
    ]).then(([themes, questions, tests, candidates, sessions, results]) => {
      setStats({
        themes: themes.data.length,
        questions: questions.data.length,
        tests: tests.data.length,
        candidates: candidates.data.length,
        sessions: sessions.data.length,
        results: results.data.length,
      })
    }).catch(() => {})
  }, [])

  const items = [
    { label: 'Thèmes', value: stats.themes, to: '/admin/themes' },
    { label: 'Questions', value: stats.questions, to: '/admin/questions' },
    { label: 'Tests', value: stats.tests, to: '/admin/tests' },
    { label: 'Candidats', value: stats.candidates, to: '/admin/candidates' },
    { label: 'Sessions', value: stats.sessions, to: '/admin/sessions' },
    { label: 'Résultats', value: stats.results, to: '/admin/results' },
  ]

  return (
    <>
      <h2 style={{ marginBottom: '1rem' }}>Tableau de bord administrateur</h2>
      <div className="grid">
        {items.map((item) => (
          <Link to={item.to} key={item.label} style={{ textDecoration: 'none', color: 'inherit' }}>
            <div className="card stat">
              <div className="value">{item.value}</div>
              <div className="label">{item.label}</div>
            </div>
          </Link>
        ))}
      </div>
    </>
  )
}
