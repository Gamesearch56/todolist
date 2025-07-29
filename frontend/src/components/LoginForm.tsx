import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../api/axios'
import './../styles/App.css'

export default function LoginForm() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const navigate = useNavigate()

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault()

        try {
            const res = await api.post('/auth/login', {email, password})
            localStorage.setItem('token', res.data.token)
            navigate('/')
        } catch (err: any) {
            setError(err.response?.data || 'Login failed')
        }
    }

    return (
        <div>
            {error && <div className="error">{error}</div>}
            <form onSubmit={handleLogin}>
                <input type="email" value={email} onChange={e => setEmail(e.target.value)} placeholder="Email"
                       required/>
                <input type="password" value={password} onChange={e => setPassword(e.target.value)}
                       placeholder="Password" required/>
                <button type="submit">Login</button>
            </form>
        </div>
    )
}