import LoginForm from '../components/LoginForm'
import '../styles/App.css'
import { Link } from 'react-router-dom'

export default function LoginPage() {
    return (
        <div className="container">
            <h1>Login</h1>
            <LoginForm/>
            <p className="mb-0">Don't have an account? <Link to="/register">Register</Link></p>
        </div>
    )
}
