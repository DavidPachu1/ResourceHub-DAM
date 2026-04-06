import { useState, useEffect } from 'react';

// --- IMPORTACIÓN DE ASSETS ---
// He importado todas las fotos de la carpeta assets para tenerlas listas 
// tanto para el Plan B como por si el backend me da problemas con las rutas.
import altavoces from '../assets/altavoces.jpg';
import mac from '../assets/MacBook_Pro.jpg';
import monitor from '../assets/monitor_4k_Asus.jpg';
import proyector from '../assets/proyector_4k_sony.jpg';
import sala from '../assets/sala_juntas.jpg';
import tablet from '../assets/tablet.jpeg';
import perifericos from '../assets/teclado_raton_vertical.jpg';
import dron from '../assets/dron.jpg';

// --- DATOS DE RESPALDO (PLAN B) ---
// Si el servidor de mi compañero está apagado o hay algún fallo,
// uso este array para que la página siempre muestre contenido en la presentación.
const DATOS_DE_RESPALDO = [
  { id: 1, nombre: 'Sala de Juntas', urlImagen: sala, tipo: 'Espacio', descripcion: 'Espacio equipado para reuniones y presentaciones.' },
  { id: 2, nombre: 'MacBook Pro', urlImagen: mac, tipo: 'Hardware', descripcion: 'Portátil de alta gama para tareas de desarrollo.' },
  { id: 3, nombre: 'Sistema de Audio', urlImagen: altavoces, tipo: 'Hardware', descripcion: 'Altavoces profesionales para eventos.' },
  { id: 4, nombre: 'Monitor 4K ASUS', urlImagen: monitor, tipo: 'Hardware', descripcion: 'Monitor de alta resolución para productividad.' },
  { id: 5, nombre: 'Proyector Sony', urlImagen: proyector, tipo: 'Hardware', descripcion: 'Proyector HD para presentaciones en sala.' },
  { id: 6, nombre: 'Tablet Samsung', urlImagen: tablet, tipo: 'Hardware', descripcion: 'Dispositivo móvil para firmas y control.' },
  { id: 7, nombre: 'Kit Periféricos', urlImagen: perifericos, tipo: 'Hardware', descripcion: 'Teclado y ratón ergonómicos.' },
  { id: 8, nombre: 'Dron de grabación', urlImagen: dron, tipo: 'Hardware', descripcion: 'Dron para contenido audiovisual.' }
];

export default function Recursos() {
  const [fotoModal, setFotoModal] = useState(null); // Controla la imagen que se ve en grande
  const [recursos, setRecursos] = useState([]);      // Aquí guardamos los datos del backend
  const [cargando, setCargando] = useState(true);    // Estado para el feedback de carga

  // --- CONEXIÓN CON EL BACKEND (SPRING BOOT) ---
  useEffect(() => {
    // Recuperamos el token JWT que guardamos en el localStorage al hacer Login
    const token = localStorage.getItem('token');

    fetch('http://localhost:8080/api/recursos', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        // ¡IMPORTANTE! Enviamos el sello (Bearer Token) para que el SecurityConfig nos deje pasar
        'Authorization': `Bearer ${token}` 
      }
    })
      .then(respuesta => {
        // Si el código no es 200 (OK), lanzamos un error para que salte el Plan B
        if (!respuesta.ok) throw new Error('No autorizado o servidor caído');
        return respuesta.json();
      })
      .then(datosDelBackend => {
        console.log("Conexión exitosa: Datos recibidos del backend.");
        setRecursos(datosDelBackend);
        setCargando(false);
      })
      .catch(error => {
        // Si hay cualquier fallo, activamos el Plan B para no quedarnos "en blanco"
        console.warn("Usando datos locales por fallo de red:", error);
        setRecursos(DATOS_DE_RESPALDO);
        setCargando(false);
      });
  }, []);

  return (
    <div className="p-8 bg-slate-50 min-h-screen">
      {/* Encabezado de la sección */}
      <div className="mb-10">
        <h1 className="text-3xl font-bold text-slate-800 tracking-tight">Gestión de Recursos</h1>
        <p className="text-slate-500 mt-2">Inventario actualizado de activos de la empresa.</p>
        
        {/* Indicador visual de carga */}
        {cargando && (
          <div className="flex items-center gap-2 mt-4 text-blue-600 font-medium">
            <div className="w-4 h-4 border-2 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
            Sincronizando con el servidor...
          </div>
        )}
      </div>

      {/* Grid de tarjetas de recursos */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {recursos.map((item) => (
          <div key={item.id} className="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden hover:shadow-lg transition-all duration-300 flex flex-col group">
            
            {/* Contenedor de la imagen */}
            <div 
              className="aspect-video cursor-pointer overflow-hidden bg-slate-100" 
              onClick={() => setFotoModal(item.urlImagen)}
            >
              <img 
                src={item.urlImagen} 
                alt={item.nombre} 
                className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
                // Si la URL del backend falla, ponemos una de repuesto rápida
                onError={(e) => { e.target.src = "https://placehold.co/600x400?text=Recurso"; }}
              />
            </div>
            
            {/* Detalles del recurso */}
            <div className="p-5 flex-grow">
              <span className="inline-block px-2 py-1 text-[10px] font-bold text-blue-700 bg-blue-50 rounded uppercase tracking-wider mb-2">
                {item.tipo || 'General'}
              </span>
              <h3 className="text-lg font-bold text-slate-800">
                {item.nombre}
              </h3>
              <p className="text-sm text-slate-500 mt-2 line-clamp-2">
                {item.descripcion}
              </p>
            </div>

            {/* Botón de acción */}
            <div className="p-4 border-t border-slate-50">
              <button 
                onClick={() => setFotoModal(item.urlImagen)}
                className="w-full bg-slate-100 hover:bg-blue-600 hover:text-white text-slate-700 py-2 rounded-lg text-sm font-semibold transition-colors duration-200"
              >
                Ver detalles
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* --- MODAL PARA AMPLIAR IMAGEN --- */}
      {/* Solo se muestra si 'fotoModal' tiene una URL guardada */}
      {fotoModal && (
        <div 
          className="fixed inset-0 bg-slate-900/90 backdrop-blur-sm flex flex-col items-center justify-center z-50 p-4 animate-in fade-in duration-300" 
          onClick={() => setFotoModal(null)}
        >
          <div className="relative max-w-4xl w-full">
            <img 
              src={fotoModal} 
              className="rounded-xl shadow-2xl border-2 border-white/20 w-full" 
              alt="Vista previa" 
            />
            <button 
              className="absolute -top-12 right-0 text-white text-sm bg-white/10 px-4 py-2 rounded-full hover:bg-white/20 transition-colors"
              onClick={() => setFotoModal(null)}
            >
              Cerrar (Esc)
            </button>
          </div>
        </div>
      )}
    </div>
  );
}