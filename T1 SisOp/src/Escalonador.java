import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Escalonador {
    private PriorityQueue<Processo> filaProntos;
    private int tempoAtual;
    private boolean primeiraExec = true;
    private LinkedList<Processo> processosBloqueados;
    private int qtdProcessos = 0;

    public Escalonador() {
        // Fila de prioridade baseada nos créditos e na ordem (desempate)
        filaProntos = new PriorityQueue<>(Comparator.comparingInt(Processo::getCreditos)
            .reversed().thenComparingInt(Processo::getOrdem));
        processosBloqueados = new LinkedList<Processo>();
        tempoAtual = 0;
    }

    public void adicionarProcesso(Processo p) {
        filaProntos.add(p); // Adiciona o processo à fila
        qtdProcessos++;
    }

    public void escalonar() {
        final int TEMPO_EXECUCAO_MS = 1;
        Processo processoAtual = filaProntos.poll(); // Obtém o processo com mais créditos

        while (!filaProntos.isEmpty()) {
            mostrarEstado(processoAtual);

            processoAtual.defineEstadoBloqueio(TEMPO_EXECUCAO_MS);

            if (processoAtual.getCreditos() == 0 && processoAtual.getSurtoCPU() == 0 && processoAtual.getEstado() == Processo.Estado.READY) {
                filaProntos.add(processoAtual); // Se não tiver terminado, adiciona novamente à fila
            }

            if (processoAtual.getEstado() == Processo.Estado.BLOCKED) {
                processosBloqueados.add(processoAtual); // Se estiver bloqueado, Adiciona na lista de processos bloqueados
            }

            if (!processosBloqueados.isEmpty()) {
                trataProcessosBloqueados();
            }

            if (processoAtual.getEstado() != Processo.Estado.RUNNING && !primeiraExec)
                processoAtual = filaProntos.poll(); // Obtém o processo com mais créditos

            if (primeiraExec) primeiraExec = false;

            if (processoAtual.getCreditos() > 0 
            && (processoAtual.getEstado() == Processo.Estado.READY || processoAtual.getEstado() == Processo.Estado.RUNNING )) {

                processoAtual.executar(TEMPO_EXECUCAO_MS); // Executa o processo por 1ms
                processoAtual.setCreditos(processoAtual.getCreditos() - 1); // Reduz os créditos

            } else {
                Processo aux;
                reordenarFila();

                if (verificaSeTodosOsProcessosPrecisamDeCredito()) {
                    processoAtual.atualizarCreditos();
                    atualizarCreditos();
                }

                aux = processoAtual;
                processoAtual = filaProntos.poll(); // Obtém o processo com mais créditos
                processoAtual.setEstado(Processo.Estado.RUNNING);
                processoAtual.setCreditos(processoAtual.getCreditos() - 1); // Reduz os créditos
                filaProntos.add(aux);
            }
            tempoAtual++;
            reordenarFila();
        }
    }

    private void atualizarCreditos() {
        for (Processo p : filaProntos) {
            if (p.getCreditos() == 0) {
                p.atualizarCreditos(); // Atualiza os créditos de cada processo
            }
            if (p.getSurtoCPU() == 0)
                p.renovaSurto();
        }
        if (!processosBloqueados.isEmpty()) {
            for (Processo p : processosBloqueados) {
                if (p.getCreditos() == 0)
                    p.atualizarCreditos(); // Atualiza os créditos de cada processo
                if (p.getSurtoCPU() == 0)
                    p.renovaSurto();
            }
        }
    }

    public boolean verificaSeTodosOsProcessosPrecisamDeCredito() {
        for (Processo p : filaProntos) {
            if (p.getCreditos() != 0) {
                return false;
            }
        }
        for (Processo p : processosBloqueados) {
            if (p.getCreditos() != 0) {
                return false;
            }
        }
        return true;
    }

    private void trataProcessosBloqueados() {
        for (Processo p : processosBloqueados) {
            p.trataPassagemTempoBloqueio();
            if (p.getEstado() == Processo.Estado.READY) {
                processosBloqueados.remove(p);
                filaProntos.add(p);
                p.renovaSurto();
            }
        }
    }

// Método para exibir o estado atual do escalonador e dos processos
public void mostrarEstado(Processo processoAtual) {
    System.out.println("\033[34mTempo Atual: " + tempoAtual + "\033[0m");

    // Criar uma lista combinada de todos os processos (prontos, bloqueados, e o atual)
    LinkedList<Processo> listaCombinada = new LinkedList<>(filaProntos);
    listaCombinada.addAll(processosBloqueados); // Adiciona os bloqueados na mesma lista
    if (processoAtual != null && !listaCombinada.contains(processoAtual)) {
        listaCombinada.add(processoAtual); // Adiciona o processo atual se não estiver na lista
    }

    // Ordena a lista combinada pela ordem dos processos
    listaCombinada.sort(Comparator.comparingInt(Processo::getOrdem));

    // Exibir os processos, todos juntos na mesma lista, com suas respectivas cores
    for (Processo p : listaCombinada) {
        System.out.println(getColoredProcessoString(p));
    }

    System.out.println("\n");
}

// Método auxiliar para colorir o estado do processo
private String getColoredProcessoString(Processo processo) {
    String color = "";
    switch (processo.getEstado()) {
        case RUNNING:
            color = "\033[34m"; // Azul para execução
            break;
        case READY:
            color = "\033[32m"; // Verde para pronto
            break;
        case BLOCKED:
            color = "\033[33m"; // Amarelo para bloqueado
            break;
        case EXIT:
            color = "\033[31m"; // Vermelho para exit
            break;
    }
    return color + processo + "\033[0m"; // Reseta a cor após o nome do processo
}

    private void reordenarFila() {
        LinkedList<Processo> listaTemporaria = new LinkedList<>(filaProntos);
        filaProntos.clear();
        filaProntos.addAll(listaTemporaria);
    }
}
