namespace DataAccess.Interfaces
{
    public interface IDAO<T>
    {
        void Insertar(T entidad);
        void Editar(T entidad);
        void Eliminar(int id);
        T? ObtenerPorId(int id);
        IEnumerable<T> ObtenerTodos();
    }
}