import { Routes, Route, Navigate, Link } from 'react-router-dom';
import { Home, Box, Calendar, Users, LogOut } from 'lucide-react';
// Páginas
import Login from './pages/Login';
import Recursos from './pages/Recursos';
import Reservas from './pages/Reservas';
import Personal from './pages/Personal';

// --- COMPONENTE PORTERO (Ruta Protegida) ---
// Este pequeño componente verifica si el Token existe. Si no, bloquea el acceso.
// Es fundamental para demostrar seguridad en el Frontend.
function RutaProtegida({ children }) {
  const token = localStorage.getItem('token');
  if (!token) return <Navigate to="/login" />;
  return children;
}

export default function App() {
  
  // Función para limpiar la sesión y volver al login
  const cerrarSesion = () => {
    localStorage.clear();
    window.location.href = '/login';
  };

  return (
    <Routes>
      {/* RUTAS ABIERTAS (Sin protección) */}
      <Route path="/login" element={<Login />} />
      <Route path="/personal" element={<Personal />} />

      {/* RUTAS PRIVADAS (Requieren estar logueado) */}
      <Route path="/*" element={
        <RutaProtegida>
          <div className="flex min-h-screen bg-slate-50">
            
            {/* SIDEBAR: Navegación lateral fija */}
            <aside className="w-64 bg-slate-900 text-white p-6 flex flex-col fixed h-full shadow-2xl z-20">
              <div className="mb-12 px-2">
                <h1 className="text-2xl font-black text-blue-500 italic">ResourceHub</h1>
                <p className="text-[10px] text-slate-400 font-bold uppercase tracking-[3px]">Admin Panel</p>
              </div>

              <nav className="space-y-2 flex-1">
                <Link to="/" className="flex items-center gap-3 p-3 hover:bg-white/10 rounded-xl transition-all font-medium">
                  <Home size={18} className="text-blue-400"/> Dashboard
                </Link>
                <Link to="/recursos" className="flex items-center gap-3 p-3 hover:bg-white/10 rounded-xl transition-all font-medium">
                  <Box size={18} className="text-blue-400"/> Inventario
                </Link>
                <Link to="/reservas" className="flex items-center gap-3 p-3 hover:bg-white/10 rounded-xl transition-all font-medium">
                  <Calendar size={18} className="text-blue-400"/> Reservas
                </Link>
                <Link to="/personal" className="flex items-center gap-3 p-3 hover:bg-white/10 rounded-xl transition-all font-medium">
                  <Users size={18} className="text-blue-400"/> Sobre nosotros
                </Link>
              </nav>

              <button onClick={cerrarSesion} className="flex items-center gap-3 p-3 text-red-400 hover:bg-red-500/10 rounded-xl transition-all font-bold mt-auto border border-red-500/20">
                <LogOut size={18}/> Salir
              </button>
            </aside>

            {/* CONTENIDO: Se desplaza a la derecha para dejar sitio al sidebar */}
            <main className="flex-1 ml-64 p-12 overflow-y-auto">
              <Routes>
                <Route path="/" element={<Inicio />} />
                <Route path="/recursos" element={<Recursos />} />
                <Route path="/reservas" element={<Reservas />} />
              </Routes>
            </main>
          </div>
        </RutaProtegida>
      } />
    </Routes>
  );
}

// --- PÁGINA DE INICIO (Resumen rápido) ---
function Inicio() {
  return (
    <div className="max-w-5xl">
      <header className="mb-12">
        <h1 className="text-4xl font-black text-slate-800">Panel de Control</h1>
        <p className="text-slate-500 mt-2 font-medium">Estado actual de los activos y operaciones de la empresa.</p>
      </header>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        <TarjetaResumen titulo="Préstamos hoy" valor="12" sub="Activos en uso" color="bg-blue-600" />
        <TarjetaResumen titulo="Recursos" valor="48" sub="En inventario" color="bg-slate-800" />
        <TarjetaResumen titulo="Incidencias" valor="0" sub="Ninguna alerta" color="bg-emerald-500" />
      </div>
    </div>
  );
}

function TarjetaResumen({ titulo, valor, sub, color }) {
  return (
    <div className="bg-white p-8 rounded-2xl border border-slate-200 shadow-sm hover:shadow-md transition-shadow">
      <h3 className="text-slate-400 text-xs font-black uppercase tracking-widest">{titulo}</h3>
      <p className="text-4xl font-black text-slate-800 mt-2">{valor}</p>
      <div className="flex items-center gap-2 mt-4">
        <div className={`w-2 h-2 rounded-full ${color}`}></div>
        <span className="text-sm font-bold text-slate-500">{sub}</span>
      </div>
    </div>
  );
}