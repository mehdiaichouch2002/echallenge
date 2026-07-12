import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import client from '../api/client'
import { useAuth } from '../auth/AuthContext'

export default function Home() {
  const [tests, setTests] = useState([])
  const { user } = useAuth()

  useEffect(() => {
    client.get('/tests').then(({ data }) => setTests(data)).catch(() => {})
  }, [])

  return (
    <>
      <section className="hero">
        <p className="eyebrow">EChallenge — évaluation en ligne</p>
        <h1>Composez comme en salle d'examen.</h1>
        <p className="lede">
          Créez votre compte, réservez un créneau, répondez dans le temps imparti :
          votre copie est corrigée et notée dès la soumission.
        </p>
      </section>
      <h3 style={{ marginBottom: '0.8rem' }}>Tests disponibles</h3>
      <div className="grid">
        {tests.map((t) => (
          <div className="card" key={t.id}>
            <h3>{t.name}</h3>
            <p className="muted">{t.description}</p>
            <p className="mt">
              ⏱ {t.totalDurationMinutes} min · {t.totalQuestions} questions
            </p>
            {!user && (
              <Link className="btn mt" to="/register" style={{ marginTop: '0.8rem' }}>
                S'inscrire pour participer
              </Link>
            )}
            {user?.role === 'CANDIDATE' && (
              <Link className="btn mt" to="/candidate/enroll" style={{ marginTop: '0.8rem' }}>
                Réserver un créneau
              </Link>
            )}
          </div>
        ))}
        {tests.length === 0 && <p className="muted">Aucun test disponible pour le moment.</p>}
      </div>
    </>
  )
}
