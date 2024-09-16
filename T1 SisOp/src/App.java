/*Claro! Vamos criar um esqueleto básico para um projeto Java baseado na descrição do algoritmo 
de escalonamento de processos com prioridades. 
A ideia é estruturar o código em classes e métodos para refletir o comportamento descrito.

Estrutura do Projeto Java
Classe Processo

Representa um processo com seus atributos e comportamentos.
Classe Escalonador

Gerencia a fila de processos e realiza o escalonamento.
Classe Main

Contém o método main para executar o programa e testar o escalonador.*/
//Código Esqueleto

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