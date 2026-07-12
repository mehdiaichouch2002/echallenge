import { Link, NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../auth/AuthContext'

export default function Layout({ children }) {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <div className="app">
      <header className="navbar">
        <Link to="/" className="brand">EChallenge</Link>
        <nav>
          {!user && (
            <>
              <NavLink to="/" end>Tests</NavLink>
              <NavLink to="/login">Connexion</NavLink>
              <NavLink to="/register" className="btn-nav">S'inscrire</NavLink>
            </>
          )}
          {user?.role === 'ADMIN' && (
            <>
              <NavLink to="/admin" end>Dashboard</NavLink>
              <NavLink to="/admin/themes">Thèmes</NavLink>
              <NavLink to="/admin/questions">Questions</NavLink>
              <NavLink to="/admin/tests">Tests</NavLink>
              <NavLink to="/admin/timeslots">Créneaux</NavLink>
              <NavLink to="/admin/candidates">Candidats</NavLink>
              <NavLink to="/admin/sessions">Sessions</NavLink>
              <NavLink to="/admin/results">Résultats</NavLink>
            </>
          )}
          {user?.role === 'CANDIDATE' && (
            <>
              <NavLink to="/candidate" end>Mes sessions</NavLink>
              <NavLink to="/candidate/enroll">S'inscrire à un test</NavLink>
              <NavLink to="/candidate/results">Mes résultats</NavLink>
            </>
          )}
          {user && (
            <span className="user-box">
              <span className="user-name">{user.fullName}</span>
              <button className="btn btn-sm" onClick={handleLogout}>Déconnexion</button>
            </span>
          )}
        </nav>
      </header>
      <main className="container">{children}</main>
    </div>
  )
}
