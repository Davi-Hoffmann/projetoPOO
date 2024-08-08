import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AlunoView extends JFrame {
    private final AlunoController controller;
    private JTextField nomeField, emailField, senhaField, cpfField, generoField, searchField;
    private JList<String> alunoList;
    private DefaultListModel<String> listModel;

    public AlunoView() {
        controller = new AlunoController();
        setTitle("Sistema de Gestão de Alunos");
        setSize(600, 500); // Ajustei o tamanho da janela para acomodar os novos tamanhos dos componentes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("BEM VINDO!!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(welcomeLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Iniciar");
        startButton.setFont(new Font("Serif", Font.BOLD, 18)); // Aumentar o tamanho da fonte do botão
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOptionsScreen();
            }
        });
        add(startButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void showOptionsScreen() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel optionsPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // Adicionei espaço entre os botões
        JButton addButton = new JButton("Cadastro de Aluno");
        addButton.setFont(new Font("Serif", Font.BOLD, 18)); // Aumentar o tamanho da fonte do botão
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCadastroAlunoScreen();
            }
        });
        optionsPanel.add(addButton);

        JButton updateButton = new JButton("Atualizar Aluno");
        updateButton.setFont(new Font("Serif", Font.BOLD, 18)); // Aumentar o tamanho da fonte do botão
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginScreen("Atualizar Aluno");
            }
        });
        optionsPanel.add(updateButton);

        JButton deleteButton = new JButton("Remover Aluno");
        deleteButton.setFont(new Font("Serif", Font.BOLD, 18)); // Aumentar o tamanho da fonte do botão
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginScreen("Remover Aluno");
            }
        });
        optionsPanel.add(deleteButton);

        add(optionsPanel, BorderLayout.NORTH);

        // Painel de pesquisa e lista de alunos
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Pesquisar");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAlunoList();
            }
        });

        searchBarPanel.add(searchField);
        searchBarPanel.add(searchButton);
        searchPanel.add(searchBarPanel);

        // Lista de alunos
        listModel = new DefaultListModel<>();
        alunoList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(alunoList);
        scrollPane.setPreferredSize(new Dimension(580, 300));
        searchPanel.add(scrollPane);

        add(searchPanel, BorderLayout.CENTER);

        // Carregar todos os alunos na lista
        updateAlunoList();

        revalidate();
        repaint();
    }

    private void updateAlunoList() {
        listModel.clear();
        List<Aluno> alunos;
        String searchQuery = searchField.getText();
        try {
            if (searchQuery.isEmpty()) {
                alunos = controller.getAllAlunos();
            } else {
                alunos = controller.getAlunosByName(searchQuery);
            }
            for (Aluno aluno : alunos) {
                listModel.addElement(aluno.getNome());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar alunos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showLoginScreen(String action) {
        getContentPane().removeAll();
        setLayout(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField();

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String senha = senhaField.getText();

                try {
                    Aluno aluno = controller.buscarAlunoPorEmailSenha(email, senha);
                    if (aluno != null) {
                        if (action.equals("Atualizar Aluno")) {
                            showAtualizarAlunoScreen(aluno);
                        } else if (action.equals("Remover Aluno")) {
                            showRemoverAlunoScreen(aluno);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Email ou Senha incorretos.");
                        showOptionsScreen();
                    }
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
                }
            }
        });

        add(emailLabel);
        add(emailField);
        add(senhaLabel);
        add(senhaField);
        add(new JLabel());
        add(submitButton);

        revalidate();
        repaint();
    }

    private void showCadastroAlunoScreen() {
        getContentPane().removeAll();
        setLayout(new GridLayout(6, 2, 5, 5));

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField();
        JLabel cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField();
        JLabel generoLabel = new JLabel("Gênero:");
        generoField = new JTextField();

        JButton submitButton = new JButton("Cadastrar");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarAluno();
            }
        });

        add(nomeLabel);
        add(nomeField);
        add(emailLabel);
        add(emailField);
        add(senhaLabel);
        add(senhaField);
        add(cpfLabel);
        add(cpfField);
        add(generoLabel);
        add(generoField);
        add(new JLabel());
        add(submitButton);

        revalidate();
        repaint();
    }

    private void showAtualizarAlunoScreen(Aluno aluno) {
        getContentPane().removeAll();
        setLayout(new GridLayout(6, 2, 5, 5));

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(aluno.getNome());
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(aluno.getEmail());
        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JTextField(aluno.getSenha());
        JLabel cpfLabel = new JLabel("CPF:");
        cpfField = new JTextField(aluno.getCpf());
        JLabel generoLabel = new JLabel("Gênero:");
        generoField = new JTextField(aluno.getGenero());

        JButton submitButton = new JButton("Atualizar");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String email = emailField.getText();
                String senha = senhaField.getText();
                String cpf = cpfField.getText();
                String genero = generoField.getText();

                if (!validarEmail(email) || !validarCpf(cpf) || !validarSenha(senha)) {
                    return;
                }

                Aluno updatedAluno = new Aluno(aluno.getId(), nome, email, senha, cpf, genero);

                try {
                    controller.atualizarAluno(updatedAluno);
                    JOptionPane.showMessageDialog(AlunoView.this, "Aluno atualizado com sucesso!");
                    showOptionsScreen();
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(AlunoView.this, "Erro ao atualizar aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(nomeLabel);
        add(nomeField);
        add(emailLabel);
        add(emailField);
        add(senhaLabel);
        add(senhaField);
        add(cpfLabel);
        add(cpfField);
        add(generoLabel);
        add(generoField);
        add(new JLabel());
        add(submitButton);

        revalidate();
        repaint();
    }

    private void showRemoverAlunoScreen(Aluno aluno) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JLabel confirmLabel = new JLabel("Deseja realmente remover o aluno: " + aluno.getNome() + "?");
        add(confirmLabel, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Remover");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.removerAluno(aluno.getId());
                    JOptionPane.showMessageDialog(AlunoView.this, "Aluno removido com sucesso!");
                    showOptionsScreen();
                } catch (DAOException ex) {
                    JOptionPane.showMessageDialog(AlunoView.this, "Erro ao remover aluno: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(confirmButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void adicionarAluno() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        String cpf = cpfField.getText();
        String genero = generoField.getText();

        if (!validarEmail(email) || !validarCpf(cpf) || !validarSenha(senha)) {
            return;
        }

        Aluno aluno = new Aluno(0, nome, email, senha, cpf, genero);

        try {
            controller.adicionarAluno(aluno);
            JOptionPane.showMessageDialog(AlunoView.this, "Aluno adicionado com sucesso!");
            showOptionsScreen();
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(AlunoView.this, "Erro ao adicionar aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarEmail(String email) {
        if (email.contains("@")) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Email inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean validarCpf(String cpf) {
        if (cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "CPF inválido. Use o formato xxx.xxx.xxx-xx.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean validarSenha(String senha) {
        if (senha.length() >= 6) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "A senha deve ter no mínimo 6 caracteres.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AlunoView().setVisible(true);
            }
        });
    }
}
