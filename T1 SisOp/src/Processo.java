public class Processo {
    private String nome;
    private int surtoCPU; // Tempo de uso de CPU antes do escalonamento
    private int tempoES; // Tempo de bloqueio para E/S
    private int tempoTotalCPU; // Tempo total de uso CPU maximo em execucao 
    private int ordem; // Critério de desempate
    private int prioridade; // Prioridade inicial
    private int creditos; // Créditos restantes para execução
    private Processo.Estado estado; // Estado do processo: Ready, Running, Blocked, Exit
    private int tempoRestanteES; // Tempo restante de bloqueio
    private int surtoRestante;

    public Processo(String nome, int surtoCPU, int tempoES, int tempoTotalCPU, int ordem, int prioridade) {
        this.nome = nome;
        this.surtoCPU = surtoCPU;
        this.tempoES = tempoES;
        this.tempoTotalCPU = tempoTotalCPU;
        this.ordem = ordem;
        this.prioridade = prioridade;
        this.creditos = prioridade;
        this.estado = Estado.READY; // Inicialmente, o processo está pronto para execução
        this.tempoRestanteES = 0;
        if(surtoCPU == 0){
            this.surtoCPU = prioridade;
        }
        surtoRestante = this.surtoCPU;
    }

    public enum Estado{
        READY,
        BLOCKED,
        RUNNING,
        EXIT
    }

    // Métodos para alterar o estado e manipular os atributos do processo

    public void executar(int tempo) {
        this.estado = Estado.RUNNING;

       if (tempoTotalCPU <= 0)  {
            estado = Estado.EXIT; // Processo completou sua execução            
        } else {
            // Reduz o tempo total de CPU restante
            tempoTotalCPU -= tempo;
            surtoRestante -= tempo; // Reduz o surto de CPU restante
        }
        
    }

    public void renovaSurto(){
        surtoRestante = this.surtoCPU;
    }

    public void defineEstadoBloqueio(int tempo){
        if (surtoRestante < tempo) {
            // Se o surto de CPU acabou, e o processo não tem operacao de E/S, o processo vai para o estado de READY
            if(this.tempoES == 0)
                estado = Estado.READY;
            else{
                tempoRestanteES = tempoES+1;
                estado = Estado.BLOCKED;
            }
        }
    }

    public void trataPassagemTempoBloqueio() {
        if (tempoRestanteES > 0) {
            tempoRestanteES--;
            if (tempoRestanteES == 0) {
                this.estado = Estado.READY; // Quando o tempo de E/S termina, o processo volta para "Ready"
                
            }
        }
    }

    public void atualizarCreditos() {
        creditos = creditos / 2 + prioridade; // Redistribuição dos créditos
    }

    @Override
    public String toString(){
        return "Processo " + nome + "- Surto CPU res:\t" + surtoRestante + " - Tempo Exec CPU res:\t" + tempoTotalCPU + 
        "\t- Estado:\t" + getEstado() + "\t- Créditos:\t" + creditos + "\tordem: " + ordem;
    }

    // Getters e Setters necessários para acessar os atributos
    public String getNome() { return nome; }
    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public Estado getEstado() { return estado; }
    public int getOrdem() { return ordem; }
    public int getTempoTotalCPU() { return tempoTotalCPU; }
    public int getSurtoCPU() { return surtoRestante; }
    public int getTempoRestanteES() { return tempoRestanteES; }
    public void setOrdem(int ordem) { this.ordem = ordem; }
}
