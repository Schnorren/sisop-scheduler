import java.util.Comparator;
import java.util.PriorityQueue;

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
