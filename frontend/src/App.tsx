import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import ProtectedRoute from './routes/ProtectedRoute'
import RegisterPage from './pages/RegisterPage.tsx'
import TodoListPage from './pages/TodoListPage.tsx'
import TodoListDetailPage from './pages/TodoListDetailPage.tsx'

const App = () => (
    <Router>
        <Routes>
            <Route path="/" element={
                <ProtectedRoute>
                    <TodoListPage/>
                </ProtectedRoute>
            }/>
            <Route path="/:id" element={
                <ProtectedRoute>
                    <TodoListDetailPage/>
                </ProtectedRoute>
            }/>
            <Route path="/login" element={<LoginPage/>}/>
            <Route path="/register" element={<RegisterPage/>}/>
        </Routes>
    </Router>
)

export default App
