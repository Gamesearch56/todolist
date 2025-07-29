import RegisterForm from '../components/RegisterForm.tsx'
import '../styles/App.css'

export default function RegisterPage() {
    return (
        <div className="container">
            <h1>Register</h1>
            <RegisterForm/>
            <p className="mb-0">Already have an account? <a href="/login">Login</a></p>
        </div>
    )
}