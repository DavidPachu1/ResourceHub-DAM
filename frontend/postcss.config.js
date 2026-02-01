/**
 * Archivo de configuración de PostCSS.
 * PostCSS es una herramienta que transforma el CSS con plugins de JavaScript.
 */
export default {
  plugins: {
    // 1. Tailwind CSS: Permite que el framework procese tus clases de utilidad
    // y las convierta en CSS real que el navegador entienda.
    '@tailwindcss/postcss': {},

    // 2. Autoprefixer: Añade automáticamente prefijos de navegador (-webkit-, -ms-, etc.)
    // a las reglas CSS que lo necesiten para asegurar la compatibilidad en todos los navegadores.
    autoprefixer: {},
  },
}