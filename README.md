# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) (or [oxc](https://oxc.rs) when used in [rolldown-vite](https://vite.dev/guide/rolldown)) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## React Compiler

The React Compiler is currently not compatible with SWC. See [this issue](https://github.com/vitejs/vite-plugin-react/issues/428) for tracking the progress.

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.
# ResourceHub-DAM
Sistema de gestión de recursos, reservas y mantenimiento para empresas (TFG DAM)

## 📋 Características Principales
* **Gestión de Inventario:** Control total sobre los activos de la empresa.
* **Sistema de Reservas:** Reserva de recursos por franjas horarias con validación de disponibilidad.
* **Mantenimiento y Tickets:** Registro de incidencias y seguimiento de reparaciones.
* **Comunicación Interna:** Mensajería integrada para la resolución de dudas sobre préstamos.

## 🛠️ Stack Tecnológico
* **Backend:** Java 17, Spring Boot, Spring Data JPA, MySQL.
* **Frontend:** React, Tailwind CSS / Bootstrap.
* **Seguridad:** Autenticación basada en JWT.
* **Infraestructura:** API RESTful, Git/GitHub para control de versiones.

## 📂 Estructura del Repositorio
* `/backend`: Lógica de negocio y API REST (Spring Boot).
* `/frontend`: Interfaz de usuario web (React).


* `/docs`: Documentación del proyecto y esquemas de base de datos.

---
*Desarrollado por el equipo de ResourceHub - 2026*
## Authors

David Fernandez, Marta Mercado, Michael J. Telleria  
© 2026 All rights reserved
