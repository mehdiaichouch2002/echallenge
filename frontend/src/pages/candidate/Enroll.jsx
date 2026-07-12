import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import client from '../../api/client'
import Alert from '../../components/Alert'

export default function Enroll() {
  const [tests, setTests] = useState([])
  const [slots, setSlots] = useState([])
  const [testId, setTestId] = useState('')
  const [timeSlotId, setTimeSlotId] = useState('')
  const [error, setError] = useState(null)
  const navigate = useNavigate()

  useEffect(() => {
    client.get('/tests').then(({ data }) => setTests(data))
    client.get('/candidate/timeslots/available').then(({ data }) => setSlots(data))
  }, [])

  const submit = async (e) => {
    e.preventDefault()
    setError(null)
    try {
      await client.post('/candidate/enroll', {
        testId: Number(testId),
        timeSlotId: Number(timeSlotId),
      })
      navigate('/candidate')
    } catch (err) {
      setError(err.response?.data?.error || "Échec de l'inscription au test")
    }
  }

  const fmt = (dt) => dt ? new Date(dt).toLocaleString('fr-FR') : '—'

  return (
    <div className="card auth-card" style={{ maxWidth: 520 }}>
      <h2>S'inscrire à un test</h2>
      {error && <Alert className="mt" onClose={() => setError(null)}>{error}</Alert>}
      <form className="stack mt" onSubmit={submit}>
        <div>
          <label>Test</label>
          <select value={testId} onChange={(e) => setTestId(e.target.value)} required>
            <option value="">— choisir un test —</option>
            {tests.map((t) => (
              <option key={t.id} value={t.id}>
                {t.name} ({t.totalQuestions} questions, {t.totalDurationMinutes} min)
              </option>
            ))}
          </select>
        </div>
        <div>
          <label>Créneau disponible</label>
          <select value={timeSlotId} onChange={(e) => setTimeSlotId(e.target.value)} required>
            <option value="">— choisir un créneau —</option>
            {slots.map((s) => (
              <option key={s.id} value={s.id}>{fmt(s.startTime)} ({s.durationMinutes} min)</option>
            ))}
          </select>
        </div>
        {slots.length === 0 && (
          <p className="muted">Aucun créneau disponible. Contactez l'administrateur.</p>
        )}
        <button className="btn" type="submit" disabled={slots.length === 0}>Confirmer l'inscription</button>
      </form>
    </div>
  )
}
