import java.util.List;

public interface GenericDAO<T> {
    void inserir(T obj) throws DAOException;
    void atualizar(T obj) throws DAOException;
    void deletar(int id) throws DAOException;
}
