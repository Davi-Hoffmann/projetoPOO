import java.util.List;

public class AlunoController {
    private AlunoDAO alunoDAO;

    public AlunoController() {
        alunoDAO = new AlunoDAO();
    }

    public void adicionarAluno(Aluno aluno) throws DAOException {
        alunoDAO.inserir(aluno);
    }

    public void atualizarAluno(Aluno aluno) throws DAOException {
        alunoDAO.atualizar(aluno);
    }

    public void removerAluno(int id) throws DAOException {
        alunoDAO.deletar(id);
    }

    public Aluno buscarAlunoPorEmailSenha(String email, String senha) throws DAOException {
        return alunoDAO.buscarAlunoPorEmailSenha(email, senha);
    }

    public List<Aluno> getAllAlunos() throws DAOException {
        return alunoDAO.buscarTodos();
    }

    public List<Aluno> getAlunosByName(String nome) throws DAOException {
        return alunoDAO.buscarPorNome(nome);
    }
}
