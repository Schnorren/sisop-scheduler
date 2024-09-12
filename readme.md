# Sistemas Operacionais - Trabalho 1

## Implementação de um Escalonador de Processos

Este trabalho consiste na implementação de um simulador para o escalonamento de processos em um sistema operacional hipotético. O objetivo é criar um escalonador de processos baseado em prioridades, que execute os processos de acordo com um algoritmo descrito abaixo.

## Descrição do Algoritmo

O sistema operacional hipotético utiliza um algoritmo de escalonamento baseado em prioridades. O algoritmo opera da seguinte forma:

1. **Estados dos Processos**:
   - **Ready**: Pronto para execução.
   - **Running**: Em execução.
   - **Blocked**: Bloqueado, aguardando operação de E/S.
   - **Exit**: Concluído.

2. **Parâmetros dos Processos**:
   - **Nome do Processo**: Identificador do processo.
   - **Surto de CPU**: Tempo de CPU utilizado antes de uma operação de entrada e saída.
   - **Tempo de E/S**: Tempo que o processo fica bloqueado aguardando uma operação de entrada e saída.
   - **Tempo Total de CPU**: Tempo total de processamento do processo, incluindo surto de CPU e operações de E/S.
   - **Ordem**: Valor inicial que define a prioridade em caso de empate, variando com uma política rotativa.
   - **Prioridade**: Valor arbitrário que define a prioridade do processo, onde um valor maior indica maior prioridade.

3. **Funcionamento do Escalonador**:
   - Cada processo tem um número de créditos igual à sua prioridade inicial.
   - O processo com o maior número de créditos na fila de prontos é selecionado para execução.
   - A cada vez que o processo é escalonado (a cada 1ms), ele perde um crédito.
   - Quando os créditos chegam a zero ou o processo está bloqueado para E/S, o escalonador seleciona outro processo.
   - Se nenhum processo na fila de prontos possui créditos, os créditos são redistribuídos para todos os processos (inclusive bloqueados) com a fórmula: `cred = cred / 2 + prio`.
   - O parâmetro **Ordem** é utilizado como critério de desempate em caso de múltiplos processos com a mesma prioridade.

4. **Exemplo de Escalonamento**:

   | Processo | Surto de CPU | Tempo de E/S | Tempo Total de CPU | Ordem | Prioridade |
   |----------|--------------|--------------|---------------------|-------|------------|
   | A        | 2ms          | 5ms          | 6ms                 | 1     | 3          |
   | B        | 3ms          | 10ms         | 6ms                 | 2     | 3          |
   | C        | -            | -            | 14ms                | 3     | 3          |
   | D        | -            | -            | 10ms                | 4     | 3          |

## Requisitos

1. O simulador deve suportar qualquer número de processos especificados em uma lista, cada um com os parâmetros descritos.
2. A saída do simulador, incluindo o escalonamento e informações adicionais, deve ser apresentada em formato texto no terminal.
3. Qualquer linguagem de programação pode ser utilizada. Boas práticas de programação e modularidade são esperadas.
4. O grupo deve ser composto por 3 ou 4 integrantes. É responsabilidade do grupo organizar-se para o desenvolvimento e apresentação do trabalho.
5. A data de entrega é 17/09. As apresentações serão realizadas nos dias 17/09 e 19/09.
6. A entrega deve ser realizada pelo Moodle em um arquivo `.tar.gz` ou `.zip`, contendo o código fonte e um arquivo texto com os nomes completos dos integrantes. Apenas um dos integrantes do grupo deve efetuar a entrega.
