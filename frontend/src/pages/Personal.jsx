/* Necesario para que la nueva página se muestre correctamente */
export default function Personal() {
  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold text-slate-800">Sobre nosotros</h1>
      <p className="text-slate-500 mt-2">Información general</p>
      <p className="mb-10"></p> {/* SEPARACION */}
      <p className="text-justify">
        ResourceHub es la plataforma integral diseñada para transformar la
        manera en que las organizaciones gestionan y comparten sus activos
        internos. En un entorno empresarial cada vez más dinámico, creemos que
        la eficiencia no solo reside en el talento humano, sino en la
        disponibilidad y el aprovechamiento máximo de cada recurso.
      </p>
      <p className="mb-10"></p> {/* SEPARACION */}
      <p className="text-justify">
        En ResourceHub, nacimos con la misión de redefinir la gestión operativa
        corporativa. Entendemos que en el ecosistema empresarial actual, la
        información fragmentada es el mayor obstáculo para el crecimiento. Por
        ello, hemos desarrollado una infraestructura integral que centraliza,
        optimiza y democratiza el acceso a los activos internos.
      </p>
      <p className="mb-10"></p> {/* SEPARACION */}
      <p className="text-justify">
        Nuestra filosofía se basa en la convergencia tecnológica: no solo
        facilitamos herramientas, sino que construimos el puente entre el
        potencial de los equipos de trabajo y los recursos técnicos. Creemos
        firmemente que una organización conectada es una organización imparable.
      </p>
      <p className="mb-10"></p> {/* SEPARACION */}
      {/* cards  */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
          <h3 className="text-slate-500 text-sm font-medium uppercase">
            David Fernández Martínez
          </h3>
          {/* Descripción de la tarjeta */}
          <p className="text-2xl font-bold text-slate-800 mt-1">
            UI/UX Designer
          </p>
          <p className="text-slate-600 mt-3 text-sm leading-relaxed">
            Encargado de transformar conceptos complejos en una interfaz
            intuitiva y dinámica utilizando React.
          </p>
          <div className="mt-4 flex gap-2">
            <span className="px-2 py-1 bg-blue-50 text-blue-600 text-xs font-semibold rounded">
              Tailwind
            </span>
            <span className="px-2 py-1 bg-purple-50 text-purple-600 text-xs font-semibold rounded">
              React
            </span>
          </div>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200 flex flex-col justify-between hover:shadow-md transition-shadow">
          <div>
            <h3 className="text-slate-500 text-sm font-medium uppercase tracking-wider">
              Marta Mercado Aguilar
            </h3>
            <p className="text-2xl font-bold text-slate-800 mt-1">
              Frontend & UI Specialist
            </p>
            <p className="text-slate-600 mt-3 text-sm leading-relaxed">
              Pieza clave en el desarrollo de la interfaz de usuario.
              <p>
                {" "}
                Persona encargada de optimizar la lógica del lado del cliente.
              </p>
            </p>
          </div>
          <div className="mt-4 flex gap-2 flex-wrap">
            <span className="px-2 py-1 bg-pink-50 text-pink-600 text-xs font-semibold rounded">
              UI/UX
            </span>
            <span className="px-2 py-1 bg-indigo-50 text-indigo-600 text-xs font-semibold rounded">
              Tailwind
            </span>
          </div>
        </div>
        {/* Diseño de la tarjeta */}
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200 flex flex-col justify-between">
          <div>
            <h3 className="text-slate-500 text-sm font-medium uppercase tracking-wider">
              Michael Jordan Telleria Guadalajara
            </h3>
            <p className="text-2xl font-bold text-slate-800 mt-1">
              Backend Engineer
            </p>
            <p className="text-slate-600 mt-3 text-sm leading-relaxed">
              Responsable de la arquitectura de microservicios, seguridad JWT y
              la persistencia de datos en ResourceHub.
            </p>
          </div>
          <div className="mt-4 flex gap-2">
            <span className="px-2 py-1 bg-green-50 text-green-600 text-xs font-semibold rounded">
              Spring Boot
            </span>
            <span className="px-2 py-1 bg-orange-50 text-orange-600 text-xs font-semibold rounded">
              MySQL
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
