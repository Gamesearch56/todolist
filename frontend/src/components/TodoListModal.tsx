import { useState } from 'react'
import api from '../api/axios'
import '../styles/App.css'

export default function TodoListModal({onClose}: { onClose: () => void }) {
    const [title, setTitle] = useState('')

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        await api.post('/api/todolists', {
            title: title,
        }).then(() => {
            onClose()
        }).catch((e) => alert(e))
    }

    return (
        <div className="modal">
            <div className="modal-content">
                <h3>Create New Todo List</h3>
                <form onSubmit={handleSubmit}>
                    <input type="text" value={title} onChange={e => setTitle(e.target.value)} placeholder="Title"
                           required/>
                    <button type="submit">Create</button>
                </form>
                <button onClick={onClose} style={{marginTop: '1rem'}}>Cancel</button>
            </div>
        </div>
    )
}
