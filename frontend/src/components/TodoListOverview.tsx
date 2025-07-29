import { useEffect, useState } from 'react'
import api from '../api/axios'
import { useNavigate } from 'react-router-dom'
import TodoListModal from './TodoListModal'
import '../styles/App.css'

interface TodoList {
    id: number
    title: string
}

export default function TodoListOverview() {
    const [lists, setLists] = useState<TodoList[]>([])
    const [showModal, setShowModal] = useState(false)
    const navigate = useNavigate()

    const fetchLists = async () => {
        try {
            const res = await api.get('/api/todolists/me')
            setLists(res.data)
        } catch (err: any) {
            console.error('Failed to load lists: ' + err.message)
        }
    }

    useEffect(() => {
        fetchLists()
    }, [])

    const handleDelete = async (id: number) => {
        await api.delete(`/api/todolists/${id}`)
        fetchLists()
    }

    return (
        <div className="container">
            <div className="todo-header">
                <h2>Your Todo Lists</h2>
                <button onClick={() => setShowModal(true)}>+ Create List</button>
            </div>

            {lists && lists.length > 0 ? (
                <ul className="todo-list">
                    {lists.map(list => (
                        <li className="todo-list-item" key={list.id} onClick={() => navigate(`/lists/${list.id}`)}>
                            {list.title}
                            <button onClick={(e) => {
                                e.stopPropagation()
                                handleDelete(list.id)
                            }}>
                                ❌
                            </button>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No todo lists found!</p>
            )}

            {showModal && <TodoListModal onClose={() => {
                setShowModal(false)
                fetchLists()
            }}/>}
        </div>
    )
}