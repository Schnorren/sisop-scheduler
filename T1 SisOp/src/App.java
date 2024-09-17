

//3. Classe Main
public class App {
    public static void main(String[] args) {
        Escalonador escalonador = new Escalonador();

        // Criar processos e adicionar ao escalonador
        Processo p1 = new Processo("A", 2, 5, 6, 1, 3);
        Processo p2 = new Processo("B", 3, 10, 6, 2, 3);
        Processo p3 = new Processo("C", 0, 0, 14, 3, 3);
        Processo p4 = new Processo("D", 0, 0, 10, 4, 3);

        escalonador.adicionarProcesso(p1);
        escalonador.adicionarProcesso(p2);
        escalonador.adicionarProcesso(p3);
        escalonador.adicionarProcesso(p4);

        // Iniciar o escalonamento
        escalonador.escalonar();
        // escalonador.mostrarEstado(); // Exibir o estado dos processos após o escalonamento
    }
}
