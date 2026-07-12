import { useEffect, useRef, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import client from '../../api/client'

export default function TakeTest() {
  const { sessionId } = useParams()
  const navigate = useNavigate()
  const [exam, setExam] = useState(null)
  const [answers, setAnswers] = useState({})
  const [secondsLeft, setSecondsLeft] = useState(null)
  const [error, setError] = useState(null)
  const [result, setResult] = useState(null)
  const submittedRef = useRef(false)
  const startedRef = useRef(false)

  useEffect(() => {
    if (startedRef.current) return
    startedRef.current = true
    client.post(`/candidate/sessions/${sessionId}/start`)
      .then(({ data }) => {
        setExam(data)
        setSecondsLeft(data.totalDurationMinutes * 60)
      })
      .catch((err) => setError(err.response?.data?.error || 'Impossible de démarrer le test'))
  }, [sessionId])

  useEffect(() => {
    if (secondsLeft === null || result) return
    if (secondsLeft <= 0) {
      submit()
      return
    }
    const timer = setTimeout(() => setSecondsLeft((s) => s - 1), 1000)
    return () => clearTimeout(timer)
  }, [secondsLeft, result])

  const submit = async () => {
    if (submittedRef.current) return
    submittedRef.current = true
    const payload = {
      answers: exam.questions.map((q) => ({
        questionId: q.id,
        selectedOptionId: answers[q.id] ?? null,
        timeSpentSeconds: 0,
      })),
    }
    try {
      const { data } = await client.post(`/candidate/sessions/${sessionId}/submit`, payload)
      setResult(data)
    } catch (err) {
      submittedRef.current = false
      setError(err.response?.data?.error || 'Échec de la soumission')
    }
  }

  if (error) {
    return (
      <div className="card auth-card">
        <div className="alert alert-error">{error}</div>
        <button className="btn" onClick={() => navigate('/candidate')}>Retour à mes sessions</button>
      </div>
    )
  }

  if (result) {
    return (
      <div className="card auth-card">
        <h2>Copie rendue</h2>
        <div className="stat mt">
          <div className="value">{result.score.toFixed(1)} %</div>
          <div className="label">{result.correctAnswers} / {result.totalQuestions} bonnes réponses</div>
        </div>
        <p className="mt" style={{ textAlign: 'center' }}>
          {result.passed
            ? <span className="stamp stamp-pass">Réussi</span>
            : <span className="stamp stamp-fail">Échoué</span>}
        </p>
        <button className="btn mt" style={{ width: '100%' }} onClick={() => navigate('/candidate/results')}>
          Voir mes résultats
        </button>
      </div>
    )
  }

  if (!exam) return <p className="muted">Chargement du test…</p>

  const mm = String(Math.floor(secondsLeft / 60)).padStart(2, '0')
  const ss = String(secondsLeft % 60).padStart(2, '0')
  const answered = Object.keys(answers).length

  return (
    <>
      <div className="exam-timer">
        <span>{exam.testName} — {answered}/{exam.questions.length} répondues</span>
        <span className="time">⏱ {mm}:{ss}</span>
      </div>
      {exam.questions.map((q, i) => (
        <div className="card question-card" key={q.id}>
          <p className="muted" style={{ fontSize: '0.8rem' }}>Question {i + 1} · {q.themeName} · {q.points} pt</p>
          <p className="qtext">{q.questionText}</p>
          {q.options.map((o) => (
            <label key={o.id} className={`option ${answers[q.id] === o.id ? 'selected' : ''}`}>
              <input
                type="radio"
                name={`q-${q.id}`}
                checked={answers[q.id] === o.id}
                onChange={() => setAnswers({ ...answers, [q.id]: o.id })}
              />
              {o.optionText}
            </label>
          ))}
        </div>
      ))}
      <div className="card" style={{ textAlign: 'center' }}>
        <button className="btn" onClick={submit}>Soumettre le test</button>
      </div>
    </>
  )
}
