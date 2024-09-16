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
        //mostrarEstado(processoAtual);
        while (!filaProntos.isEmpty()) {

            mostrarEstado(processoAtual);

            processoAtual.defineEstadoBloqueio(TEMPO_EXECUCAO_MS);

            if(processoAtual.getCreditos() == 0 && processoAtual.getSurtoCPU() == 0 && processoAtual.getEstado() == Processo.Estado.READY){
                filaProntos.add(processoAtual); // Se não tiver terminado, adiciona novamente à fila
            }

            if (processoAtual.getEstado() == Processo.Estado.BLOCKED) {
                processosBloqueados.add(processoAtual); // Se estiver bloqueado, Adiciona na lista de processos bloqueados
            }
            
            if(!processosBloqueados.isEmpty()){
                trataProcessosBloqueados();
            }
            
            if(processoAtual.getEstado() != Processo.Estado.RUNNING && !primeiraExec)
                processoAtual = filaProntos.poll(); // Obtém o processo com mais créditos
                // processoAtual.setOrdem(qtdProcessos+processoAtual.getOrdem());

            if(primeiraExec) primeiraExec = false;

            if (processoAtual.getCreditos() > 0 
                && (processoAtual.getEstado() == Processo.Estado.READY 
                || processoAtual.getEstado() == Processo.Estado.RUNNING )) {

                processoAtual.executar(TEMPO_EXECUCAO_MS); // Executa o processo por 1ms
                processoAtual.setCreditos(processoAtual.getCreditos() - 1); // Reduz os créditos
                
            } else {
                Processo aux;
                reordenarFila();
                // Se não há créditos, redistribui os créditos para todos os processos
                processoAtual.setEstado(Processo.Estado.READY);
                processoAtual.setOrdem(qtdProcessos+processoAtual.getOrdem());
                // atualizarCreditos(processoAtual);
                if(verificaSeTodosOsProcessosPrecisamDeCredito()){
                    processoAtual.atualizarCreditos();
                    atualizarCreditos();
                }
                else{
                    // processoAtual.atualizarCreditos();
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
            if(p.getCreditos() == 0){
                p.atualizarCreditos(); // Atualiza os créditos de cada processo
            }
            if(p.getSurtoCPU() == 0)
                p.renovaSurto();
        }
        if(!processosBloqueados.isEmpty()){
            for (Processo p : processosBloqueados) {
                if(p.getCreditos() == 0)
                    p.atualizarCreditos(); // Atualiza os créditos de cada processo
                if(p.getSurtoCPU() == 0)
                    p.renovaSurto();
            }
        }
    }

    public boolean verificaSeTodosOsProcessosPrecisamDeCredito(){
        boolean retorno = true;
        for (Processo p : filaProntos) {
            if(p.getCreditos() != 0){
                retorno = false;
            }
        }
        if(!retorno){
            for (Processo p : processosBloqueados) {
                if(p.getCreditos() != 0){
                    retorno = false;
                }
            }
        }
        else return retorno;

        return retorno;

    }

    private void trataProcessosBloqueados(){
        for(Processo p : processosBloqueados){
            p.trataPassagemTempoBloqueio();
            if(p.getEstado() == Processo.Estado.READY){
                processosBloqueados.remove(p);
                filaProntos.add(p);
                p.renovaSurto();
            }
        }
    }

    // Método para exibir o estado atual do escalonador e dos processos
    public void mostrarEstado(Processo processoAtual) {
        System.out.println("Tempo Atual: " + tempoAtual);
        //System.out.println("Processo " + processoAtual.getNome() + " - Estado: " + processoAtual.getEstado() + 
        System.out.println(processoAtual);
        for (Processo p : filaProntos) {
            System.out.println(p);
        }
        if(!processosBloqueados.isEmpty()){
            for (Processo p : processosBloqueados) {
                System.out.println(p);
            }
        }
    }

    // Função para reordenar a fila de processos (filaProntos) após mudanças de créditos ou ordem
private void reordenarFila() {
    // Armazenar os processos temporariamente
    LinkedList<Processo> listaTemporaria = new LinkedList<>(filaProntos);
    
    // Limpar a fila
    filaProntos.clear();
    
    // Adicionar os processos de volta na fila para que ela se reorganize automaticamente
    filaProntos.addAll(listaTemporaria);
}

}
