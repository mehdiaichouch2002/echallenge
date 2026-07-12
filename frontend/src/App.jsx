import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { AuthProvider } from './auth/AuthContext'
import ProtectedRoute from './auth/ProtectedRoute'
import Layout from './components/Layout'
import Home from './pages/Home'
import Login from './pages/Login'
import Register from './pages/Register'
import AdminDashboard from './pages/admin/AdminDashboard'
import Themes from './pages/admin/Themes'
import Questions from './pages/admin/Questions'
import Tests from './pages/admin/Tests'
import TimeSlots from './pages/admin/TimeSlots'
import Candidates from './pages/admin/Candidates'
import Sessions from './pages/admin/Sessions'
import Results from './pages/admin/Results'
import CandidateDashboard from './pages/candidate/CandidateDashboard'
import Enroll from './pages/candidate/Enroll'
import TakeTest from './pages/candidate/TakeTest'
import MyResults from './pages/candidate/MyResults'

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Layout>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

            <Route path="/admin" element={<ProtectedRoute role="ADMIN"><AdminDashboard /></ProtectedRoute>} />
            <Route path="/admin/themes" element={<ProtectedRoute role="ADMIN"><Themes /></ProtectedRoute>} />
            <Route path="/admin/questions" element={<ProtectedRoute role="ADMIN"><Questions /></ProtectedRoute>} />
            <Route path="/admin/tests" element={<ProtectedRoute role="ADMIN"><Tests /></ProtectedRoute>} />
            <Route path="/admin/timeslots" element={<ProtectedRoute role="ADMIN"><TimeSlots /></ProtectedRoute>} />
            <Route path="/admin/candidates" element={<ProtectedRoute role="ADMIN"><Candidates /></ProtectedRoute>} />
            <Route path="/admin/sessions" element={<ProtectedRoute role="ADMIN"><Sessions /></ProtectedRoute>} />
            <Route path="/admin/results" element={<ProtectedRoute role="ADMIN"><Results /></ProtectedRoute>} />

            <Route path="/candidate" element={<ProtectedRoute role="CANDIDATE"><CandidateDashboard /></ProtectedRoute>} />
            <Route path="/candidate/enroll" element={<ProtectedRoute role="CANDIDATE"><Enroll /></ProtectedRoute>} />
            <Route path="/candidate/take-test/:sessionId" element={<ProtectedRoute role="CANDIDATE"><TakeTest /></ProtectedRoute>} />
            <Route path="/candidate/results" element={<ProtectedRoute role="CANDIDATE"><MyResults /></ProtectedRoute>} />
          </Routes>
        </Layout>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
