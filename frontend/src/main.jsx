// 1. Importaciones de librerías externas
import { StrictMode } from 'react' // Activa comprobaciones adicionales en modo desarrollo
import { createRoot } from 'react-dom/client' // Herramienta para renderizar la App en el DOM
import { BrowserRouter } from 'react-router-dom' // Habilita la navegación por URLs (rutas)

// 2. Importaciones de archivos locales
import './index.css' // Estilos globales de la aplicación
import App from './App.jsx' // El componente principal que contiene toda tu web

/**
 * createRoot: Selecciona el elemento con id 'root' de tu index.html.
 * .render(): Dibuja los componentes de React dentro de ese elemento.
 */
createRoot(document.getElementById('root')).render(
  <StrictMode>
    {/* BrowserRouter debe envolver a App para que puedas usar 
        enlaces (Links) y rutas (Routes) en cualquier parte del proyecto.
    */}
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>,
)