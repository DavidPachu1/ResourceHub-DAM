import { useState, useEffect } from 'react';
import { CalendarCheck, AlertCircle } from 'lucide-react';

export default function Reservas() {
  const [reservas, setReservas] = useState([]);
  const [cargando, setCargando] = useState(true);
  const [avisoSeguridad, setAvisoSeguridad] = useState(false);

  // --- ESTADOS DEL FORMULARIO ---
  const [recursoId, setRecursoId] = useState('');
  const [fechaInicio, setFechaInicio] = useState('');
  const [fechaFin, setFechaFin] = useState('');

  // Diccionario para mostrar nombres reales en lugar de IDs
  const nombresRecursos = {
    "1": "Sala de Juntas",
    "2": "MacBook Pro",
    "3": "Sistema de Audio",
    "4": "Monitor 4K ASUS",
    "5": "Proyector Sony",
    "6": "Tablet Samsung",
    "7": "Kit Periféricos",
    "8": "Dron de grabación"
  };

  // 1. CARGAR HISTORIAL (GET)
  useEffect(() => {
    const token = localStorage.getItem('token');
    
    fetch('http://localhost:8080/api/reservas', {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    })
      .then(respuesta => {
        if (!respuesta.ok) throw new Error('Error de conexión');
        return respuesta.json();
      })
      .then(datos => {
        setReservas(datos);
        setCargando(false);
      })
      .catch(error => {
        console.warn("Usando historial local:", error);
        setCargando(false);
      });
  }, []);

  // 2. CREAR RESERVA (POST)
  const manejarEnvio = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    
    const nuevaReserva = {
      recurso: { id: parseInt(recursoId) },
      fechaInicio: fechaInicio,
      fechaFin: fechaFin
    };

    try {
      const respuesta = await fetch('http://localhost:8080/api/reservas', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(nuevaReserva)
      });

      if (!respuesta.ok) throw new Error('Fallo en la reserva');

      const reservaGuardada = await respuesta.json();
      setReservas([...reservas, reservaGuardada]);
      setRecursoId(''); setFechaInicio(''); setFechaFin('');
      
    } catch (error) {
      setAvisoSeguridad(true);
      const simulada = {
        id: Date.now(),
        recurso: { 
          id: recursoId, 
          nombre: nombresRecursos[recursoId] || `Recurso #${recursoId}` 
        },
        fechaInicio: fechaInicio.replace('T', ' '),
        fechaFin: fechaFin.replace('T', ' '),
        estado: 'SIMULADA (Demo)'
      };
      setReservas([...reservas, simulada]);
      setTimeout(() => setAvisoSeguridad(false), 5000);
    }
  };

  return (
    <div className="p-8 bg-slate-50 min-h-screen">
      {/* Cabecera */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-slate-800 flex items-center gap-3">
          <CalendarCheck className="text-blue-600" size={32} />
          Panel de Reservas
        </h1>
        <p className="text-slate-500 mt-2">Gestiona el uso de activos y salas desde aquí.</p>
      </div>

      {/* Alerta de Simulación */}
      {avisoSeguridad && (
        <div className="mb-6 bg-amber-50 border-l-4 border-amber-400 p-4 rounded-r-lg flex items-center gap-3 text-amber-800 animate-pulse">
          <AlertCircle size={20} />
          <span className="text-sm font-medium">Nota: Se ha realizado una reserva simulada para la demostración.</span>
        </div>
      )}

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* FORMULARIO */}
        <div className="lg:col-span-1 bg-white p-6 rounded-xl shadow-sm border border-slate-200 h-fit">
          <h2 className="text-xl font-bold text-slate-800 mb-6">Nueva Solicitud</h2>
          <form onSubmit={manejarEnvio} className="space-y-4">
            <div>
              <label className="block text-xs font-bold text-slate-500 uppercase mb-1">Seleccionar Recurso</label>
              <select 
                required 
                value={recursoId} 
                onChange={(e) => setRecursoId(e.target.value)}
                className="w-full border border-slate-300 rounded-lg p-2 focus:ring-2 focus:ring-blue-500 outline-none bg-white text-slate-700"
              >
                <option value="">-- Selecciona qué necesitas --</option>
                <option value="1">Sala de Juntas</option>
                <option value="2">MacBook Pro</option>
                <option value="3">Sistema de Audio</option>
                <option value="4">Monitor 4K ASUS</option>
                <option value="5">Proyector Sony</option>
                <option value="6">Tablet Samsung</option>
                <option value="7">Kit Periféricos</option>
                <option value="8">Dron de grabación</option>
              </select>
            </div>
            <div>
              <label className="block text-xs font-bold text-slate-500 uppercase mb-1">Inicio</label>
              <input type="datetime-local" required value={fechaInicio} onChange={(e) => setFechaInicio(e.target.value)}
                className="w-full border border-slate-300 rounded-lg p-2 focus:ring-2 focus:ring-blue-500 outline-none" />
            </div>
            <div>
              <label className="block text-xs font-bold text-slate-500 uppercase mb-1">Fin</label>
              <input type="datetime-local" required value={fechaFin} onChange={(e) => setFechaFin(e.target.value)}
                className="w-full border border-slate-300 rounded-lg p-2 focus:ring-2 focus:ring-blue-500 outline-none" />
            </div>
            <button type="submit" className="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 rounded-lg transition-all shadow-md">
              Confirmar Reserva
            </button>
          </form>
        </div>

        {/* TABLA DE HISTORIAL */}
        <div className="lg:col-span-2 bg-white p-6 rounded-xl shadow-sm border border-slate-200">
          <h2 className="text-xl font-bold text-slate-800 mb-6">Historial de Actividad</h2>
          <div className="overflow-x-auto">
            <table className="w-full text-sm text-left">
              <thead className="text-xs text-slate-400 uppercase bg-slate-50">
                <tr>
                  <th className="px-4 py-3">Recurso</th>
                  <th className="px-4 py-3">Desde</th>
                  <th className="px-4 py-3 text-right">Estado</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100">
                {reservas.length === 0 ? (
                  <tr>
                    <td colSpan="3" className="px-4 py-10 text-center text-slate-400 italic">No hay reservas registradas aún.</td>
                  </tr>
                ) : (
                  reservas.map((res) => (
                    <tr key={res.id} className="hover:bg-slate-50 transition-colors">
                      <td className="px-4 py-4 font-semibold text-slate-700">
                        {res.recurso?.nombre || `Item #${res.recurso?.id || '?'}`}
                      </td>
                      <td className="px-4 py-4 text-slate-500">
                        {res.fechaInicio ? res.fechaInicio.replace('T', ' ') : '---'}
                      </td>
                      <td className="px-4 py-4 text-right">
                        <span className={`px-2 py-1 rounded-full text-[10px] font-black ${res.estado === 'SIMULADA (Demo)' ? 'bg-amber-100 text-amber-600' : 'bg-blue-100 text-blue-600'}`}>
                          {res.estado || 'CONFIRMADA'}
                        </span>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}