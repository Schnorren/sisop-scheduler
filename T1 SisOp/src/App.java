/*Claro! Vamos criar um esqueleto básico para um projeto Java baseado na descrição do algoritmo de escalonamento de processos com prioridades. A ideia é estruturar o código em classes e métodos para refletir o comportamento descrito.

Estrutura do Projeto Java
Classe Processo

Representa um processo com seus atributos e comportamentos.
Classe Escalonador

Gerencia a fila de processos e realiza o escalonamento.
Classe Main

Contém o método main para executar o programa e testar o escalonador.*/
Código Esqueleto
1. Classe Processo
public class Processo {
    private int id;
    private int prioridade;
    private int creditos;
    private int tempoCPU;
    private int tempoExecutado;
    private boolean bloqueado;
    private int ordem; // Utilizado para desempate

    public Processo(int id, int prioridade, int tempoCPU, int ordem) {
        this.id = id;
        this.prioridade = prioridade;
        this.creditos = prioridade;
        this.tempoCPU = tempoCPU;
        this.tempoExecutado = 0;
        this.bloqueado = false;
        this.ordem = ordem;
    }

    // Getters e setters

    public void executar(int tempo) {
        // Executa o processo por um determinado tempo
        if (bloqueado) return;
        tempoExecutado += tempo;
        tempoCPU -= tempo;
        if (tempoCPU <= 0) {
            // Processo finalizado
        }
    }

    public void bloquear() {
        bloqueado = true;
    }

    public void liberar() {
        bloqueado = false;
    }

    public void atualizarCreditos() {
        creditos = (creditos / 2) + prioridade - 1;
    }

    // Outros métodos conforme necessário
}
//2. Classe Escalonador
import java.util.PriorityQueue;
import java.util.Comparator;

public class Escalonador {
    private PriorityQueue<Processo> filaProntos;
    private int tempoAtual;

    public Escalonador() {
        filaProntos = new PriorityQueue<>(Comparator.comparingInt(Processo::getCreditos).reversed().thenComparingInt(Processo::getOrdem));
        tempoAtual = 0;
    }

    public void adicionarProcesso(Processo p) {
        filaProntos.add(p);
    }

    public void escalonar() {
        while (!filaProntos.isEmpty()) {
            Processo processoAtual = filaProntos.poll();
            if (processoAtual.getCreditos() > 0) {
                processoAtual.executar(1); // Executa por 1ms
                processoAtual.setCreditos(processoAtual.getCreditos() - 1);
                
                if (processoAtual.getCreditos() <= 0) {
                    processoAtual.bloquear();
                }
                
                if (processoAtual.getTempoCPU() > 0) {
                    filaProntos.add(processoAtual);
                }
                
                tempoAtual++;
            } else {
                atualizarCreditos();
            }
        }
    }

    private void atualizarCreditos() {
        for (Processo p : filaProntos) {
            p.atualizarCreditos();
        }
    }

    // Outros métodos conforme necessário
}
//3. Classe Main
public class Main {
    public static void main(String[] args) {
        Escalonador escalonador = new Escalonador();
        
        // Criar processos
        Processo p1 = new Processo(1, 5, 100, 1);
        Processo p2 = new Processo(2, 3, 50, 2);
        Processo p3 = new Processo(3, 4, 75, 3);
        
        // Adicionar processos ao escalonador
        escalonador.adicionarProcesso(p1);
        escalonador.adicionarProcesso(p2);
        escalonador.adicionarProcesso(p3);
        
        // Executar escalonamento
        escalonador.escalonar();
        
        // Exibir resultados
    }
}
/*Explicação Resumida
Classe Processo

Define os atributos do processo, como ID, prioridade, créditos, tempo de CPU, e se está bloqueado ou não.
Inclui métodos para executar o processo, bloquear e liberar, e atualizar os créditos.
Classe Escalonador

Utiliza uma PriorityQueue para gerenciar a fila de processos com base na prioridade e ordem.
Implementa o método escalonar para gerenciar a execução dos processos, atualizar os créditos e lidar com bloqueios e desbloqueios.
Classe Main

Cria instâncias de Processo, adiciona-os ao Escalonador e executa o escalonamento.
Este esqueleto fornece uma estrutura básica e pode ser expandido com funcionalidades adicionais conforme necessário, como tratamento mais detalhado de bloqueios, operações de E/S e monitoramento de tempo de execução.
*/