import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../api/axios'
import './../styles/App.css'

export default function RegisterForm() {
    const [email, setEmail] = useState('')
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')
    const navigate = useNavigate()

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault()

        try {
            await api.post('/auth/register', {email, username, password})
            navigate('/login')
        } catch (err: any) {
            setError(err.response?.data || 'Registration failed')
        }
    }

    return (
        <div>
            {error && <div className="error">{error}</div>}
            <form onSubmit={handleRegister}>
                <input type="email" value={email} onChange={e => setEmail(e.target.value)} placeholder="Email"
                       required/>
                <input type="text" value={username} onChange={e => setUsername(e.target.value)} placeholder="Username"
                       required/>
                <input type="password" value={password} onChange={e => setPassword(e.target.value)}
                       placeholder="Password" required/>
                <button type="submit">Register</button>
            </form>
        </div>
    )
}