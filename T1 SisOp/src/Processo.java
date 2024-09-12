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

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos){
        this.creditos = creditos;
    }

    public int getOrdem(){
        return ordem;
    }

    public int getTempoCPU(){
        return tempoCPU;
    }

    // Outros métodos conforme necessário
}
