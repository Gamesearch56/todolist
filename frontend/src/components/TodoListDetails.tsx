import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import api from '../api/axios'
import '../styles/App.css'

interface Todo {
    id: number
    content: string
    done: boolean
    dueDate?: string
}

export default function TodoListDetails() {
    const {id} = useParams()
    const [todos, setTodos] = useState<Todo[]>([])
    const [newTodo, setNewTodo] = useState('')
    const [dueDate, setDueDate] = useState('')
    const navigate = useNavigate()

    const fetchTodos = async () => {
        const res = await api.get(`/api/todos/list/${id}`)
        setTodos(res.data)
    }

    const createTodo = async (e: React.FormEvent) => {
        e.preventDefault()

        const hasFiveWithDueDate = todos.filter(todo => todo.dueDate).length >= 5
        if (dueDate && hasFiveWithDueDate) {
            alert('Only 5 Todos with a due date allowed per list')
            return
        }

        await api.post(`/api/todos`, {
            content: newTodo,
            done: false,
            dueDate: dueDate || null,
            listId: id,
        })
        setNewTodo('')
        setDueDate('')
        fetchTodos()
    }

    const toggleDone = async (todoId: number, current: boolean) => {
        await api.put(`/api/todos/${todoId}`, {done: !current})
        fetchTodos()
    }

    const deleteTodo = async (todoId: number) => {
        await api.delete(`/api/todos/${todoId}`)
        fetchTodos()
    }

    useEffect(() => {
        fetchTodos()
    }, [])

    return (
        <div className="container">
            <h2>Todo List #{id}</h2>
            <form onSubmit={createTodo}>
                <input type="text" placeholder="New Todo" value={newTodo} onChange={e => setNewTodo(e.target.value)}
                       required/>
                <input type="date" value={dueDate} onChange={e => setDueDate(e.target.value)}/>
                <button type="submit">Add Todo</button>
            </form>
            <ul className="todo-list">
                {todos.map(todo => (
                    <li className="todo-list-item" key={todo.id}>
            <span
                style={{textDecoration: todo.done ? 'line-through' : 'none', cursor: 'pointer'}}
                onClick={() => toggleDone(todo.id, todo.done)}
            >
              {todo.content}
                {todo.dueDate && ` (Due: ${todo.dueDate})`}
            </span>
                        <button onClick={() => deleteTodo(todo.id)}>🗑️</button>
                    </li>
                ))}
            </ul>
            <button onClick={() => navigate('/lists')} style={{marginTop: '1rem'}}>← Back</button>
        </div>
    )
}
