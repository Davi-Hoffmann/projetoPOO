public class Aluno extends Usuario {
    private String genero;

    public Aluno(int id, String nome, String email, String senha, String cpf, String genero) {
        super(id, nome, email, senha, cpf);
        this.genero = genero;
    }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    @Override
    public void exibirInfo() {
        System.out.println("Aluno: " + getNome() + ", Email: " + getEmail());
    }
}
