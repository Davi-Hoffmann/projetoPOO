import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO implements GenericDAO<Aluno> {
    private static final String URL = "jdbc:mysql://localhost:3306/POO";
    private static final String USER = "root";
    private static final String PASSWORD = "Bem-vindo##0614";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void inserir(Aluno aluno) throws DAOException {
        String sql = "INSERT INTO Aluno (Nome_Aluno, Email_Aluno, Senha_Aluno, CPF_Aluno, Genero_Aluno) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getSenha());
            stmt.setString(4, aluno.getCpf());
            stmt.setString(5, aluno.getGenero());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir aluno", e);
        }
    }

    @Override
    public void atualizar(Aluno aluno) throws DAOException {
        String sql = "UPDATE Aluno SET Nome_Aluno = ?, Email_Aluno = ?, Senha_Aluno = ?, CPF_Aluno = ?, Genero_Aluno = ? WHERE ID_Aluno = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getSenha());
            stmt.setString(4, aluno.getCpf());
            stmt.setString(5, aluno.getGenero());
            stmt.setInt(6, aluno.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar aluno", e);
        }
    }

    @Override
    public void deletar(int id) throws DAOException {
        String sql = "DELETE FROM Aluno WHERE ID_Aluno = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao deletar aluno", e);
        }
    }

    public Aluno buscarAlunoPorEmailSenha(String email, String senha) throws DAOException {
        String sql = "SELECT * FROM Aluno WHERE Email_Aluno = ? AND Senha_Aluno = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                            rs.getInt("ID_Aluno"),
                            rs.getString("Nome_Aluno"),
                            rs.getString("Email_Aluno"),
                            rs.getString("Senha_Aluno"),
                            rs.getString("CPF_Aluno"),
                            rs.getString("Genero_Aluno")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar aluno por email e senha: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Aluno> buscarTodos() throws DAOException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM Aluno";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("ID_Aluno"),
                        rs.getString("Nome_Aluno"),
                        rs.getString("Email_Aluno"),
                        rs.getString("Senha_Aluno"),
                        rs.getString("CPF_Aluno"),
                        rs.getString("Genero_Aluno")
                );
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar todos os alunos: " + e.getMessage(), e);
        }
        return alunos;
    }

    public List<Aluno> buscarPorNome(String nome) throws DAOException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM Aluno WHERE Nome_Aluno LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Aluno aluno = new Aluno(
                            rs.getInt("ID_Aluno"),
                            rs.getString("Nome_Aluno"),
                            rs.getString("Email_Aluno"),
                            rs.getString("Senha_Aluno"),
                            rs.getString("CPF_Aluno"),
                            rs.getString("Genero_Aluno")
                    );
                    alunos.add(aluno);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar alunos por nome: " + e.getMessage(), e);
        }
        return alunos;
    }
}
