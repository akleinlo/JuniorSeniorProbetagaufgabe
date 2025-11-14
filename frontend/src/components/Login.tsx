import { useState } from "react";
import axios from "axios";
import * as React from "react";

const Login: React.FC = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [msg, setMsg] = useState("");

    const submit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const res = await axios.post("http://localhost:8080/api/login", { username, password });
            setMsg(res.data);
        } catch (err: unknown) {
            if (axios.isAxiosError(err)) {
                if (err.response?.status === 401) setMsg("Ungültige Zugangsdaten");
                else setMsg("Serverfehler");
            } else {
                setMsg("Unbekannter Fehler");
            }
        }
    }

    return (
        <form onSubmit={submit}>
            <input value={username} onChange={e => setUsername(e.target.value)} placeholder="Benutzername" />
            <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Passwort" />
            <button type="submit">Login</button>
            <p>{msg}</p>
        </form>
    );
}

export default Login;
