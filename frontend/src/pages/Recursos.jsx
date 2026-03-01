import { useState } from 'react';

// importamos las imágenes
import altavoces from '../assets/altavoces.jpg';
import coche from '../assets/coche_empresa.jpg';
import mac from '../assets/MacBook_Pro.jpg';
import monitor from '../assets/monitor_4k_Asus.jpg';
import proyector from '../assets/proyector_4k_sony.jpg';
import sala from '../assets/sala_juntas.jpg';
import tablet from '../assets/tablet.jpeg';
import perifericos from '../assets/teclado_raton_vertical.jpg';
import dron from '../assets/dron.jpg';
import estacion from '../assets/estacion.jpg';
import realidad from '../assets/realidad_virtual.jpg';
import router from '../assets/router.jpg';

// Array de recursos
const RECURSOS_LISTA = [
  { id: 1, nombre: 'Sala de Juntas', img: sala, cat: 'Espacio', desc: 'Espacio equipado para reuniones y presentaciones corporativas.' },
  { id: 2, nombre: 'MacBook Pro', img: mac, cat: 'Hardware', desc: 'Portátil de alta gama para tareas de desarrollo y diseño.' },
  { id: 3, nombre: 'Sistema de Audio', img: altavoces, cat: 'Hardware', desc: 'Altavoces profesionales para eventos.' },
  { id: 4, nombre: 'Monitor 4K ASUS', img: monitor, cat: 'Hardware', desc: 'Monitor de alta resolución para mejorar la productividad.' },
  { id: 5, nombre: 'Proyector 4K ASUS', img: proyector, cat: 'Hardware', desc: 'Proyector de alta definición para presentaciones y reuniones.' },
  { id: 6, nombre: 'Tablet', img: tablet, cat: 'Hardware', desc: 'Tablet para uso en presentaciones y tareas móviles.' },
  { id: 7, nombre: 'Teclado y Ratón Vertical', img: perifericos, cat: 'Hardware', desc: 'Periféricos ergonómicos para mejorar la comodidad en el trabajo.' },
  { id: 8, nombre: 'Dron', img: dron, cat: 'Hardware', desc: 'Dron para uso en proyectos de grabación de contenido.' },
  { id: 9, nombre: 'Realidad Virtual', img: realidad, cat: 'Hardware', desc: 'Kit de realidad virtual para experimentar entornos virtuales.' },
  { id: 10, nombre: 'Router 5G', img: router, cat: 'Hardware', desc: 'Router de red para garantizar conectividad estable en el entorno laboral.' },
  { id: 11, nombre: 'Coche de Empresa', img: coche, cat: 'Vehículo', desc: 'Vehículo disponible para desplazamientos laborales.' },
  { id: 12, nombre: 'Estación de Trabajo', img: estacion, cat: 'Vehículo', desc: 'Estación de carga para vehículos eléctricos.' },
];

// Necesario para que la nueva página se muestre correctamente
export default function Recursos() {
  // Estado para controlar la imagen que se ve en grande
  const [foto, setFoto] = useState(null);

  return (
    <div className="p-8 bg-slate-50 min-h-screen">
      {/* Cabecera de la página */}
      <div className="mb-10">
        <h1 className="text-3xl font-bold text-slate-800">Gestión de Recursos</h1>
        <p className="text-slate-500 mt-2">Explora el inventario de activos en ResourceHub.</p>
      </div>

      {/* Grid de Recursos */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        {RECURSOS_LISTA.map((item) => (
          <div 
            key={item.id} 
            className="bg-white rounded-xl shadow-sm border border-slate-200 overflow-hidden hover:shadow-2xl hover:-translate-y-2 transition-all duration-300 flex flex-col group"
          >
            {/* Imagen del recurso */}
            <div className="aspect-video cursor-pointer overflow-hidden" onClick={() => setFoto(item.img)}>
              <img 
                src={item.img} 
                alt={item.nombre} 
                className="w-full h-full object-cover hover:scale-110 transition-transform duration-300" 
              />
            </div>
            
            {/* Información del recurso */}
            <div className="p-5 flex-grow">
              <span className="text-[10px] font-bold text-blue-600 uppercase tracking-widest">
                {item.cat}
              </span>
              
              <h3 className="text-lg font-semibold text-slate-800 mt-1">
                {item.nombre}
              </h3>
              
              {/* Línea de descripción breve */}
              <p className="text-sm text-slate-600 mt-2 leading-relaxed">
                {item.desc}
              </p>
            </div>

            {/* Botón de acción */}
            <div className="p-4 pt-0">
              <button 
                onClick={() => setFoto(item.img)}
                className="w-full bg-slate-800 text-white py-2 rounded-lg text-sm font-medium group-hover:bg-blue-600 transition-colors duration-300"
              >
                Ver imagen
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* Modal Básico (se muestra solo cuando 'foto' no es null) */}
      {foto && (
        <div 
          className="fixed inset-0 bg-black/80 flex flex-col items-center justify-center z-50 p-4"
          onClick={() => setFoto(null)}
        >
          <div className="relative max-w-4xl max-h-[80vh]">
            <img 
              src={foto} 
              className="rounded-lg shadow-2xl border-4 border-white"
              alt="Vista previa" 
            />
            <button 
              className="absolute -top-10 right-0 text-white font-bold hover:text-gray-300"
              onClick={() => setFoto(null)}
            >
              CERRAR (X)
            </button>
          </div>
          <p className="text-white/70 mt-4 text-sm font-light italic text-center">
            Haz clic en cualquier lugar para volver
          </p>
        </div>
      )}
    </div>
  );
}