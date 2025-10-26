import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Classe genérica que representa uma Fila (FIFO - First In, First Out).
 * Utiliza células encadeadas para armazenar os elementos.
 * 
 * @param <E> Tipo dos elementos armazenados na fila
 */
public class Fila<E> {

      private final Celula<E> frente;
      private Celula<E> tras;

      /**
       * Construtor da fila. Inicializa a fila vazia com uma célula sentinela.
       */
      public Fila() {
            Celula<E> sentinela = new Celula<>();
            frente = sentinela;
            tras = sentinela;
      }

      /**
       * Verifica se a fila está vazia.
       * 
       * @return true se a fila estiver vazia, false caso contrário
       */
      public boolean vazia() {
            return frente == tras;
      }

      /**
       * Insere um elemento no final da fila.
       * 
       * @param item O elemento a ser inserido na fila
       */
      public void enfileirar(E item) {
            Celula<E> novaCelula = new Celula<>(item);
            tras.setProximo(novaCelula);
            tras = novaCelula;
      }

      /**
       * Remove e retorna o elemento da frente da fila.
       * 
       * @return O elemento removido da frente da fila
       * @throws NoSuchElementException se a fila estiver vazia
       */
      public E desenfileirar() {
            if (vazia()) {
                  throw new NoSuchElementException("Não há nenhum item na fila!");
            }

            Celula<E> desenfileirada = frente.getProximo();
            E item = desenfileirada.getItem();
            frente.setProximo(desenfileirada.getProximo());

            // Se a fila ficar vazia, ajusta o ponteiro tras
            if (desenfileirada == tras) {
                  tras = frente;
            }

            return item;
      }

      /**
       * Consulta o elemento da frente da fila sem removê-lo.
       * 
       * @return O elemento da frente da fila
       * @throws NoSuchElementException se a fila estiver vazia
       */
      public E consultarFrente() {
            if (vazia()) {
                  throw new NoSuchElementException("Não há nenhum item na fila!");
            }

            return frente.getProximo().getItem();
      }

      /**
       * Calcula e retorna o valor médio de um atributo específico dos primeiros
       * elementos da fila.
       * 
       * Este método utiliza uma função de extração para obter um valor numérico
       * (Double) de cada elemento e calcula a média aritmética desses valores
       * para os primeiros 'quantidade' elementos da fila.
       * 
       * @param extrator   Função que extrai um valor Double de cada elemento
       * @param quantidade Número de primeiros elementos a serem considerados no
       *                   cálculo
       * @return O valor médio calculado, ou 0.0 se a quantidade for 0 ou a fila
       *         estiver vazia
       * @throws IllegalArgumentException se a fila não contiver elementos suficientes
       */
      public double calcularValorMedio(Function<E, Double> extrator, int quantidade) {
            if (quantidade <= 0 || vazia()) {
                  return 0.0;
            }

            // Conta quantos elementos existem na fila
            int contador = 0;
            Celula<E> atual = frente.getProximo();
            while (atual != null) {
                  contador++;
                  atual = atual.getProximo();
            }

            // Verifica se há elementos suficientes
            if (contador < quantidade) {
                  throw new IllegalArgumentException(
                              "A fila não contém " + quantidade + " elementos! Existem apenas " + contador
                                          + " elementos.");
            }

            // Calcula a soma dos valores
            double soma = 0.0;
            atual = frente.getProximo();
            for (int i = 0; i < quantidade; i++) {
                  Double valor = extrator.apply(atual.getItem());
                  if (valor != null) {
                        soma += valor;
                  }
                  atual = atual.getProximo();
            }

            // Retorna a média
            return soma / quantidade;
      }

      /**
       * Filtra os elementos da fila com base em uma condição específica.
       * 
       * Este método cria e retorna uma nova fila contendo apenas os elementos dos
       * primeiros 'quantidade' elementos da fila original que satisfazem a condição
       * especificada pelo predicado.
       * 
       * A fila original não é modificada.
       * 
       * @param condicional Predicado que testa se um elemento deve ser incluído na
       *                    nova fila
       * @param quantidade  Número de primeiros elementos da fila original a serem
       *                    testados
       * @return Uma nova fila contendo os elementos que satisfazem a condição
       * @throws IllegalArgumentException se a fila não contiver elementos suficientes
       */
      public Fila<E> filtrar(Predicate<E> condicional, int quantidade) {
            Fila<E> filaFiltrada = new Fila<>();

            if (quantidade <= 0 || vazia()) {
                  return filaFiltrada;
            }

            // Conta quantos elementos existem na fila
            int contador = 0;
            Celula<E> atual = frente.getProximo();
            while (atual != null) {
                  contador++;
                  atual = atual.getProximo();
            }

            // Verifica se há elementos suficientes
            if (contador < quantidade) {
                  throw new IllegalArgumentException(
                              "A fila não contém " + quantidade + " elementos! Existem apenas " + contador
                                          + " elementos.");
            }

            // Percorre os primeiros 'quantidade' elementos
            atual = frente.getProximo();
            for (int i = 0; i < quantidade; i++) {
                  E elemento = atual.getItem();
                  // Se o elemento satisfaz a condição, adiciona na fila filtrada
                  if (condicional.test(elemento)) {
                        filaFiltrada.enfileirar(elemento);
                  }
                  atual = atual.getProximo();
            }

            return filaFiltrada;
      }

      /**
       * Retorna o número de elementos na fila.
       * 
       * @return Quantidade de elementos na fila
       */
      public int tamanho() {
            int contador = 0;
            Celula<E> atual = frente.getProximo();
            while (atual != null) {
                  contador++;
                  atual = atual.getProximo();
            }
            return contador;
      }
}
