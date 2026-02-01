/** @type {import('tailwindcss').Config} */
export default {
  // 1. CONTENT: Define las rutas de todos tus archivos de plantilla.
  // Tailwind escaneará estos archivos para generar solo el CSS que realmente usas.
  content: [
    "./index.html",               // Archivo raíz HTML
    "./src/**/*.{js,ts,jsx,tsx}", // Todos los archivos JS, TS, JSX y TSX dentro de la carpeta src
  ],

  // 2. THEME: Aquí es donde personalizas la apariencia de tu sitio.
  theme: {
    // 'extend' permite añadir configuraciones nuevas sin sobrescribir las de fábrica.
    // Por ejemplo: puedes añadir un color personalizado o una fuente propia aquí.
    extend: {}, 
  },

  // 3. PLUGINS: Permite añadir funcionalidades extra (como formularios mejorados,
  // relaciones de aspecto, o tipografía avanzada).
  plugins: [],
}