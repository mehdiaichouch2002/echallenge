import { createContext, useContext, useState } from 'react'
import client from '../api/client'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const stored = localStorage.getItem('user')
    return stored ? JSON.parse(stored) : null
  })

  const saveAuth = (data) => {
    localStorage.setItem('token', data.token)
    const u = { userId: data.userId, email: data.email, role: data.role, fullName: data.fullName }
    localStorage.setItem('user', JSON.stringify(u))
    setUser(u)
    return u
  }

  const login = async (email, password) => {
    const { data } = await client.post('/auth/login', { email, password })
    return saveAuth(data)
  }

  const register = async (form) => {
    const { data } = await client.post('/auth/register', form)
    return saveAuth(data)
  }

  const logout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    setUser(null)
  }

  return (
    <AuthContext.Provider value={{ user, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  return useContext(AuthContext)
}
