// Importamos los iconos necesarios y componentes de React Router
import { Calendar, Home, Box, Users, ChevronRight, ScrollText } from 'lucide-react'; 
import { Routes, Route, Link } from 'react-router-dom';
// Importamos las páginas que hemos creado
import Reservas from './pages/Reservas';
import Recursos from './pages/Recursos';
import Personal from './pages/Personal';

function Inicio() {
  return (
    <>
      <header className="mb-8">
        <h1 className="text-3xl font-bold text-slate-800">Panel Principal de Gestión de Recursos</h1>
        <p className="text-slate-500">Bienvenid@. Aquí tienes el resumen de recursos.</p>
      </header>

      {/* Tarjetas de Resumen */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
          <h3 className="text-slate-500 text-sm font-medium uppercase">Reservas hoy</h3>
          <p className="text-2xl font-bold text-slate-800">X</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
          <h3 className="text-slate-500 text-sm font-medium uppercase">Recursos Totales</h3>
          <p className="text-2xl font-bold text-slate-800">X</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
          <h3 className="text-slate-500 text-sm font-medium uppercase">Recursos Disponibles</h3>
          <p className="text-2xl font-bold text-slate-800">X</p>
        </div>
      </div>
    </>
  );
}

function App() {
  return (
    <div className="flex min-h-screen bg-slate-50">
      {/* --- MENU LATERAL (Siempre visible) --- */}
      <aside className="w-64 bg-slate-900 text-slate-200 flex flex-col">
        <div className="p-6 text-xl font-bold text-white border-b border-slate-800">
          ResourceHub
        </div>
        
        <nav className="flex-1 p-4 space-y-2">
          {/* Cambiamos el botón por un Link a "/" */}
          <Link to="/" className="w-full flex items-center gap-3 p-3 rounded-lg hover:bg-slate-800 transition-colors">
            <Home size={20} /> Inicio
          </Link>
          
          <Link to="/reservas" className="w-full flex items-center justify-between p-3 rounded-lg hover:bg-slate-800 transition-colors">
            <div className="flex items-center gap-3">
              <Calendar size={20} /> Reservas
            </div>
            <ChevronRight size={16} />
          </Link>

          {/* Si quieres que Recursos sea una página interna, cámbialo a <Link to="/recursos"> */}
          <Link to="/recursos" className="w-full flex items-center justify-between p-3 rounded-lg hover:bg-slate-800 transition-colors">
            <div className="flex items-center gap-3">
              <ScrollText size={20} /> Recursos
            </div>
            <ChevronRight size={16} />
          </Link>
          
          <Link to="/personal" className="w-full flex items-center justify-between p-3 rounded-lg hover:bg-slate-800 transition-colors">
            <div className="flex items-center gap-3">
              <Users size={20} /> Quiénes somos
            </div>
            <ChevronRight size={16} />
          </Link>
        </nav>
      </aside>

      {/* --- CONTENIDO PRINCIPAL (Lo que cambia) --- */}
      <main className="flex-1 p-8">
        <Routes>
          {/* Aquí decimos: si la ruta es / muestra Inicio, si es /reservas muestra Reservas */}
          <Route path="/" element={<Inicio />} />
          <Route path="/reservas" element={<Reservas />} />
          <Route path="/recursos" element={<Recursos />} />
          <Route path="/personal" element={<Personal />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;