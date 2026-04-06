import { useState } from 'react';
import { LogIn, Lock, User } from 'lucide-react';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const manejarLogin = async (e) => {
    e.preventDefault();
    try {
      const respuesta = await fetch('http://localhost:8080/api/auth/authenticate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password }),
      });
      if (!respuesta.ok) throw new Error('Error');
      const data = await respuesta.json();
      localStorage.setItem('token', data.token);
      window.location.href = '/';
    } catch (err) {
      alert('Error al entrar. Revisa usuario y contraseña.');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-900 p-4">
      <div className="max-w-md w-full bg-white rounded-2xl p-8 shadow-xl">
        <h2 className="text-2xl font-bold text-center mb-8 text-slate-800">Iniciar Sesión</h2>
        <form onSubmit={manejarLogin} className="space-y-6">
          <input 
            type="email" placeholder="Email" required 
            className="w-full p-3 border rounded-lg outline-none focus:ring-2 focus:ring-blue-500"
            onChange={(e) => setEmail(e.target.value)}
          />
          <input 
            type="password" placeholder="Contraseña" required 
            className="w-full p-3 border rounded-lg outline-none focus:ring-2 focus:ring-blue-500"
            onChange={(e) => setPassword(e.target.value)}
          />
          <button type="submit" className="w-full bg-blue-600 text-white py-3 rounded-lg font-bold hover:bg-blue-700 transition-colors">
            Entrar
          </button>
        </form>
      </div>
    </div>
  );
}