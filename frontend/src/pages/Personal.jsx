/* Necesario para que la nueva página se muestre correctamente */
export default function Personal() {
  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold text-slate-800">Gestión de Personal</h1>
      <p className="text-slate-500 mt-2">Lista de responsables</p>

{/* cards  */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
          <h3 className="text-slate-500 text-sm font-medium uppercase">David Fernández Martínez</h3>
          {/* Descripción de la tarjeta */}
          <p className="text-2xl font-bold text-slate-800">X</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
          <h3 className="text-slate-500 text-sm font-medium uppercase">Marta Mercado Aguilar</h3>
          <p className="text-2xl font-bold text-slate-800">X</p>
        </div>
{/* Diseño de la tarjeta */}
        <div className="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
          <h3 className="text-slate-500 text-sm font-medium uppercase">Michael Jordan Telleria Guadalajara</h3>
          <p className="text-2xl font-bold text-slate-800">X</p>
        </div>
      </div>
    </div>
  );
}